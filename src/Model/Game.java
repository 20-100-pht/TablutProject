package Model;

import AI.*;
import Controller.GameController;
import Controller.MoveAnimationType;
import Global.Configuration;
import Structure.Coup;

import java.io.*;
import java.util.Vector;

public class Game implements Serializable {
    final String DEFAULT_ATT_NAME = "Attaquant";
    final String DEFAULT_DEF_NAME = "Défenseur";
    boolean attackerTurn;
    int turnIndex;
    boolean defenderIsAI;
    boolean attackerIsAI;
    int defTimeRemainedMs;
    int attTimeRemainedMs;
    boolean blitzMode;
    int blitzTime;
    boolean startTimerEnded;
    boolean iaPause;
    Coup previousCoup;
    boolean ended;

    boolean reviewMode;
    int reviewTurnIndex;
    boolean anIaThinking;

    AI iaHint;

    AIDifficulty attackerTypeAI;
    AIDifficulty defenderTypeAI;

    AI aiMinMax;

    public AI defenderAI;
    public AI attackerAI;
    LogicGrid logicGrid;
    Grid grid;
    String defenderName;
    String attackerName;
    History history;
    transient GameController gameController;
    transient PlayersStats playersStats;



    public Game(String defenderName, String attackerName, AIDifficulty defAiDifficulty, AIDifficulty attAiDifficulty, int blitzTime){

        playersStats = new PlayersStats();

        if(defAiDifficulty != AIDifficulty.HUMAN) defenderIsAI = true;
        else defenderIsAI = false;
        if(attAiDifficulty != AIDifficulty.HUMAN) attackerIsAI = true;
        else attackerIsAI = false;

        defenderTypeAI = defAiDifficulty;
        attackerTypeAI = attAiDifficulty;

        if(defenderName.length() == 0) {
            this.defenderName = DEFAULT_DEF_NAME;
        } else {
            this.defenderName = defenderName;

        } if(attackerName.length() == 0) {
            this.attackerName = DEFAULT_ATT_NAME;
        } else {
            this.attackerName = attackerName;
        }

        this.blitzTime = blitzTime;
        if(blitzTime != 0){
            blitzMode = true;
        }

        createIaInstances();

        logicGrid = new LogicGrid();
        grid = logicGrid.getGrid();
        history = new History();
        reset();
    }

    void createIaInstances(){
        if(defenderIsAI){
            if(defenderTypeAI == AIDifficulty.EASY){
                defenderAI = new AIEasy();
            }else if(defenderTypeAI == AIDifficulty.MID){
                defenderAI = new AIMedium();
            }else if(defenderTypeAI == AIDifficulty.HARD){
                defenderAI = new AIHard();
            }
        }
        if(attackerIsAI){
            if(attackerTypeAI == AIDifficulty.EASY){
                attackerAI = new AIEasy();
            }else if(attackerTypeAI == AIDifficulty.MID){
                attackerAI = new AIMedium();
            }
            else if(attackerTypeAI == AIDifficulty.HARD){
                attackerAI = new AIHard();
            }
        }

        iaHint = new AIHard();
    }

    public void reset(){
        attackerTurn = true;
        turnIndex = 0;
        logicGrid.reset();
        aiMinMax = new AIMedium();
        history.reset();
        attTimeRemainedMs = blitzTime*1000;
        defTimeRemainedMs = blitzTime*1000;
        iaPause = false;
        previousCoup = null;
        reviewMode = false;
        reviewTurnIndex = turnIndex;
        anIaThinking = false;
        ended = false;

        if(attackerIsAI || blitzMode){
            startTimerEnded = false;
        }
        else{
            startTimerEnded = true;
        }
    }

    public boolean isAiTurn(){
        return isAttackerTurn() && isAttackerAI() || !isAttackerTurn() && isDefenderAI();
    }

    public void doAiTurnInSeparateThread(){
        Thread threadAI = new Thread(new Runnable() {
            @Override
            public void run() {
                doAiTurn();
            }
        });
        threadAI.start();
    }

    public void doAiTurn(){
        if(isIaPaused()){
            return;
        }
        //On empêche le joueur de faire des actions pendant que l'IA joue
        gameController.setFrozenView(true);
        anIaThinking = true;

        AI ai;
        PieceType t;
        if(isAttackerTurn()){
            ai = getAttackerAI();
            t = PieceType.ATTACKER;
        }
        else{
            ai = getDefenderAI();
            t = PieceType.DEFENDER;
        }
        long start = System.currentTimeMillis();

        int MAX_DEPTH = Configuration.getMaxAiDepth();
        if(ai.getAiType() == 0){
            MAX_DEPTH = Configuration.getMaxAiRandomDepth();
        }
        Coup coupAI = ai.playMove(logicGrid, MAX_DEPTH, t);
        long end = System.currentTimeMillis();


        long timeToWait = 1500-(end-start);
        //On évite d'enchainer les coups de l'IA trop vite
        try {
            if(timeToWait > 0) {
                Thread.sleep(timeToWait);
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        anIaThinking = false;

        if(Configuration.isAnimationActived()) {
            gameController.startMoveAnimation(coupAI, MoveAnimationType.CLASSIC);
        }
        else{
            play(coupAI, MoveAnimationType.CLASSIC);
        }
        gameController.setFrozenView(false);
    }

    public void play(Coup coup, MoveAnimationType moveAnimationType){

        Grid grid = logicGrid.getGrid();
        Piece pieceSelected = grid.getPieceAtPosition(coup.getInit());

        if(pieceSelected.isAttacker() && !isAttackerTurn() || (pieceSelected.isDefender() || pieceSelected.isKing()) && isAttackerTurn()){
            return;
        }

        if(logicGrid.isLegalMove(coup) != 0){
            return;
        }
        logicGrid.move(coup);

        logicGrid.capture();
        Vector<Piece> killedPieces = logicGrid.attack(pieceSelected);

        toogleAttackerTurn();
        if(!isReviewMode()) {
            incTurnIndex();
            reviewTurnIndex = turnIndex;
        }

        gameController.updateViewAfterMove(coup, moveAnimationType);
        if(moveAnimationType == MoveAnimationType.CLASSIC) {
            History history = getHistoryInstance();

            history.addMove(new HistoryMove(coup, killedPieces, isAttackerTurn(), getTurnIndex(), previousCoup, defTimeRemainedMs, attTimeRemainedMs));
            previousCoup = coup;
        }

        if(logicGrid.isAttackerWinConfiguration() || logicGrid.isDefenderWinConfiguration()) {
            if(!ended) {
                treatEndGame();
            }
            ended = true;
        }

        if(isAiTurn() && moveAnimationType != MoveAnimationType.DOUBLE_REDO && !ended) doAiTurnInSeparateThread();
    }

    public void respawnKilledPieces(Vector<Piece> pieces){
        for(int i = 0; i < pieces.size(); i++){
            Piece kPiece = pieces.get(i);
            grid.setPieceAtPosition(kPiece, kPiece.getCoords());
            if(kPiece.getType() == PieceType.DEFENDER){
                logicGrid.incNbPieceDefenderOnGrid();
            }
            else if(kPiece.getType() == PieceType.ATTACKER){
                logicGrid.incNbPieceAttackerOnGrid();
            }
        }
    }

    public void undo(boolean doubleUndo){
        if(!history.canUndo()){
            return;
        }

        gameController.setFrozenView(true);

        HistoryMove move = history.undo();
        Coup coup = new Coup(move.getCoup().getDest(), move.getCoup().getInit());

        respawnKilledPieces(move.killedPieces);

        setIsAttackerTurn(!move.isAttackerMove());
        if(!isReviewMode()) {
            setTurnIndex(move.getTurnIndex() - 1);
        }
        previousCoup = move.previousCoup;

        attTimeRemainedMs = move.getAttTimeRemainedInMs();
        defTimeRemainedMs = move.getDefTimeRemainedInMs();

        MoveAnimationType mat = null;
        if(doubleUndo)
            mat = MoveAnimationType.DOUBLE_UNDO;
        else
            mat = MoveAnimationType.UNDO;

        reviewTurnIndex--;

        if(Configuration.isAnimationActived()){
            gameController.startMoveAnimation(coup, mat);
        }
        else{
            executeMove(coup, mat);
        }
    }

    public void redo(boolean doubleRedo){
        if(!history.canRedo()){
            return;
        }

        gameController.setFrozenView(true);

        HistoryMove move = history.redo();
        Coup coup = move.getCoup();

        MoveAnimationType mat = null;
        if(doubleRedo)
            mat = MoveAnimationType.DOUBLE_REDO;
        else
            mat = MoveAnimationType.REDO;

        if(reviewMode){
            reviewTurnIndex++;
        }

        if(Configuration.isAnimationActived()){
            gameController.startMoveAnimation(coup, mat);
        }
        else{
            executeMove(coup, mat);
        }
    }

    public void executeMove(Coup coup, MoveAnimationType moveAnimationType){
        if(moveAnimationType == MoveAnimationType.UNDO || moveAnimationType == MoveAnimationType.DOUBLE_UNDO){
            logicGrid.move(coup);
        }
        else {
            play(coup, moveAnimationType);
        }

        if(reviewTurnIndex == turnIndex){
            reviewMode = false;
        }
        
        if(moveAnimationType == MoveAnimationType.DOUBLE_UNDO){
            undo(false);
        }
        if(moveAnimationType == MoveAnimationType.DOUBLE_REDO){
            redo(false);
        }
        if(moveAnimationType == MoveAnimationType.CLASSIC || moveAnimationType == MoveAnimationType.REDO || moveAnimationType == MoveAnimationType.UNDO){
            gameController.setFrozenView(false);
        }

        gameController.updateViewAfterMove(coup, moveAnimationType);
    }

    public Grid getGridInstance(){
        return logicGrid.grid;
    }
    public LogicGrid getLogicGridInstance(){
        return logicGrid;
    }
    public LogicGrid getLogicGrid(){
        return logicGrid;
    }

    public boolean isAttackerAI() {
        return attackerIsAI;
    }

    public boolean isDefenderAI() {
        return defenderIsAI;
    }

    public boolean isAttackerTurn(){
        return attackerTurn;
    }

    public void toogleAttackerTurn(){
        attackerTurn = !attackerTurn;
    }

    public void setIsAttackerTurn(boolean isAttackerTurn){
        attackerTurn = isAttackerTurn;
    }

    public boolean isAiTest(){
        return attackerIsAI && defenderIsAI;
    }

    public String getDefenderName(){
        return defenderName;
    }

    public String getAttackerName(){
        return attackerName;
    }

    public History getHistoryInstance(){
        return history;
    }

    public void setAIAttackerDifficulty(AIDifficulty AIDiff){
        attackerTypeAI = AIDiff;
    }
    public void setAIDefenderDifficulty(AIDifficulty AIDiff){
        defenderTypeAI = AIDiff;
    }

    public AIDifficulty getAIDefenderDifficulty (){
        return defenderTypeAI;
    }
    public AIDifficulty getAIAttackerDifficulty (){return attackerTypeAI;}

    public AI getDefenderAI(){
        return defenderAI;
    }
    public AI getAttackerAI(){
        return attackerAI;
    }

    public int getTurnIndex(){
        return turnIndex;
    }
    public void incTurnIndex(){
        turnIndex++;
    }
    public void setTurnIndex(int turnIndex) {
        this.turnIndex = turnIndex;
    }

    public void setGameControllerInstance(GameController gameController){
        this.gameController = gameController;
    }

    public int getDefTimeRemained(){
        return defTimeRemainedMs/1000;
    }

    public int getAttTimeRemained(){
        return attTimeRemainedMs/1000;
    }

    public void updatePlayerTurnChrono(int timeElapsed){

        if(!startTimerEnded || reviewMode || isEnded()){
            return;
        }

        if(isAttackerTurn()){
            if(attTimeRemainedMs - timeElapsed < 0) attTimeRemainedMs = 0;
            else attTimeRemainedMs -= timeElapsed;
        }
        else{
            if(defTimeRemainedMs - timeElapsed < 0) defTimeRemainedMs = 0;
            else defTimeRemainedMs -= timeElapsed;
        }

        if((attTimeRemainedMs == 0 || defTimeRemainedMs == 0) && !ended){
            treatEndGame();
            ended = true;
        }
    }

    public boolean isBlitzMode(){
        return blitzMode;
    }

    public void setStartTimerEnded(){
        startTimerEnded = true;
    }

    public void startGame(){
        if((isAiTurn() || blitzMode) && !startTimerEnded){
            gameController.startStartCount();
            return;
        }
        if(isAiTurn()) doAiTurnInSeparateThread();
    }

    public void setIaPause(boolean paused){
        iaPause = paused;
    }

    public boolean isIaPaused(){
        return iaPause;
    }

    public Coup getPreviousCoup(){
        return previousCoup;
    }

    public int getNbIa(){
        int r = 0;
        if(isAttackerAI()) r++;
        if(isDefenderAI()) r++;
        return r;
    }

    public boolean canUndo(){
        return history.canUndo();
    }

    public boolean canRedo(){
        return history.canRedo();
    }

    public boolean isReviewMode(){
        return reviewMode;
    }

    public void setPreviewMode(boolean reviewMode){
        this.reviewMode = reviewMode;
    }

    public int getReviewTurnIndex(){
        return reviewTurnIndex;
    }

    public boolean anIaThinking(){
        return anIaThinking;
    }

    public boolean isEnded(){
        return ended;
    }

    public Coup getHint(){
        PieceType t = null;
        if(isAttackerTurn()){
            t = PieceType.ATTACKER;
        }
        else{
            t = PieceType.DEFENDER;
        }

        anIaThinking = true;
        Coup coup = iaHint.playMove(logicGrid, 3, t);
        anIaThinking = false;

        return coup;
    }

    public boolean isStartTimerEnded() {
        return startTimerEnded;
    }

    public void treatEndGame(){
        gameController.updateViewEndGame();

        if(!isAttackerAI() && !attackerName.equals(DEFAULT_ATT_NAME)){
            PlayerStats attStats = playersStats.getPlayerStatsFromName(attackerName);
            if(attStats == null){
                playersStats.addPlayer(new PlayerStats(attackerName));
            }
            updatePlayerStats(attackerName, true);
        }

        if(!isDefenderAI() && !defenderName.equals(DEFAULT_DEF_NAME)){
            PlayerStats defStats = playersStats.getPlayerStatsFromName(defenderName);
            if(defStats == null){
                playersStats.addPlayer(new PlayerStats(defenderName));
            }
            updatePlayerStats(defenderName, false);
        }
    }

    public void updatePlayerStats(String playerName, boolean isAttacker){
        PlayerStats playerStats = playersStats.getPlayerStatsFromName(playerName);

        if(isAttacker){
            if(logicGrid.isAttackerWinConfiguration() || (blitzMode && defTimeRemainedMs <= 0)) {
                playerStats.addWin();
                if(isDefenderAI()) {
                    if(defenderTypeAI == AIDifficulty.EASY) playerStats.setWinAgainstEasy(true);
                    if(defenderTypeAI == AIDifficulty.MID) playerStats.setWinAgainstMedium(true);
                    if(defenderTypeAI == AIDifficulty.HARD) playerStats.setWinAgainstHard(true);
                }
            }
            else {
                playerStats.addLoose();
            }
        }
        if(!isAttacker){
            if(logicGrid.isDefenderWinConfiguration() || (blitzMode && attTimeRemainedMs <= 0)) {
                playerStats.addWin();
                if(isAttackerAI()) {
                    if(attackerTypeAI == AIDifficulty.EASY) playerStats.setWinAgainstEasy(true);
                    if(attackerTypeAI == AIDifficulty.MID) playerStats.setWinAgainstMedium(true);
                    if(attackerTypeAI == AIDifficulty.HARD) playerStats.setWinAgainstHard(true);
                }
            }
            else {
                playerStats.addLoose();
            }
        }

        int nGame = (playerStats.getNWin()+playerStats.getNLoose());
        playerStats.setTurnMean((playerStats.getNTurnMean()*nGame+turnIndex+1)/(nGame+1));

        playersStats.save();
    }
}