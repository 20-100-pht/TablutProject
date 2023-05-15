package Model;

import AI.*;
import Controller.GameController;
import Controller.MoveAnimationType;
import Global.Configuration;
import Structure.Coordinate;
import Structure.Coup;
import View.GridPanel;

import java.io.*;
import java.util.Vector;

public class Game implements Serializable {
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

    AIDifficulty attackerTypeAI;
    AIDifficulty defenderTypeAI;

    AI aiMinMax;
    AIRandom aleatron;
    AI defenderAI;
    AI attackerAI;
    LogicGrid logicGrid;
    Grid grid;
    String defenderName = "Alexandre";
    String attackerName = "Philippe";
    History history;
    transient GameController gameController;

    int MAX_AI_DEPTH = 4;

    public Game(String defenderName, String attackerName, AIDifficulty defAiDifficulty, AIDifficulty attAiDifficulty, int blitzTime){

        if(defAiDifficulty != AIDifficulty.HUMAN) defenderIsAI = true;
        else defenderIsAI = false;
        if(attAiDifficulty != AIDifficulty.HUMAN) attackerIsAI = true;
        else attackerIsAI = false;

        defenderTypeAI = defAiDifficulty;
        attackerTypeAI = attAiDifficulty;

        if(defenderName.length() == 0) {
            if(!defenderIsAI) this.defenderName = "Defenseur humain";
            else if(defAiDifficulty == AIDifficulty.RANDOM ){
                this.defenderName = "Défenseur IA [RANDOM]";
            }
            else if(defAiDifficulty == AIDifficulty.MID ) {
                this.defenderName = "Défenseur IA [MOYEN]";
            }
            /*
            else if(defAiDifficulty == AIDifficulty.HARD ) {
                this.defenderName = "Défenseur IA [DIFFICILE]";
            }
            */
        } else {
            this.defenderName = defenderName;

        } if(attackerName.length() == 0) {
            if(!attackerIsAI) this.attackerName = "Attaquant humain";
            else if(attAiDifficulty == AIDifficulty.RANDOM){
                this.attackerName = "Attaquant IA [RANDOM]";
            }
            else if(attAiDifficulty == AIDifficulty.MID){
                this.attackerName = "Attaquant IA [MOYEN]";
            }
            /*
            else if(attAiDifficulty == AIDifficulty.HARD){
                this.attackerName = "Attaquant IA [DIFFICILE]";
            }
            */
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
            if(defenderTypeAI == AIDifficulty.RANDOM){
                defenderAI = new AIRandom();
            }else if(defenderTypeAI == AIDifficulty.MID){
                defenderAI = new AIMedium();
            }
        }
        if(attackerIsAI){
            if(attackerTypeAI == AIDifficulty.RANDOM){
                attackerAI = new AIRandom();
            }else if(attackerTypeAI == AIDifficulty.MID){
                attackerAI = new AIMedium();
            }
        }
    }

    public void reset(){
        attackerTurn = true;
        turnIndex = 0;
        logicGrid.reset();
        aleatron = new AIRandom();
        aiMinMax = new AIMedium();
        history.reset();
        attTimeRemainedMs = blitzTime*1000;
        defTimeRemainedMs = blitzTime*1000;
        startTimerEnded = false;
        iaPause = false;
        previousCoup = null;
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
        Coup coupAI = ai.playMove(logicGrid, MAX_AI_DEPTH, t);
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
        if(Configuration.isAnimationActived()) {
            gameController.startMoveAnimation(coupAI, MoveAnimationType.CLASSIC);
        }
        else{
            play(coupAI, MoveAnimationType.CLASSIC);
        }
        gameController.setFrozenView(false);
    }

    public void play(Coup coup, MoveAnimationType moveAnimationType){

        if(logicGrid.isEndGame()){
            return;
        }

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
        incTurnIndex();

        gameController.updateViewAfterMove(coup, moveAnimationType);
        if(moveAnimationType == MoveAnimationType.CLASSIC) {
            History history = getHistoryInstance();
            history.addMove(new HistoryMove(coup, killedPieces, isAttackerTurn(), getTurnIndex(), previousCoup));
            previousCoup = coup;
        }

        if(logicGrid.isAttackerWinConfiguration() || logicGrid.isDefenderWinConfiguration()) {
            gameController.updateViewEndGame();
            return;
        }

        if(isAiTurn() && moveAnimationType != MoveAnimationType.DOUBLE_REDO) doAiTurnInSeparateThread();
    }

    public void undo(boolean doubleUndo){
        if(!history.canUndo()){
            return;
        }

        gameController.setFrozenView(true);

        HistoryMove move = history.undo();

        Coup coup = new Coup(move.getCoup().getDest(), move.getCoup().getInit());

        for(int i = 0; i < move.getKilledPieces().size(); i++){
            Piece kPiece = move.getKilledPieces().get(i);
            grid.setPieceAtPosition(kPiece, kPiece.getCoords());
            if(kPiece.getType() == PieceType.DEFENDER){
                logicGrid.incNbPieceDefenderOnGrid();
            }
            else if(kPiece.getType() == PieceType.ATTACKER){
                logicGrid.incNbPieceAttackerOnGrid();
            }
        }

        setIsAttackerTurn(!move.isAttackerMove());
        setTurnIndex(move.getTurnIndex()-1);
        previousCoup = move.previousCoup;

        MoveAnimationType mat = null;
        if(doubleUndo)
            mat = MoveAnimationType.DOUBLE_UNDO;
        else
            mat = MoveAnimationType.UNDO;

        if(Configuration.isAnimationActived()){
            gameController.startMoveAnimation(coup, mat);
        }
        else{
            logicGrid.move(coup);
            if(doubleUndo){
                undo(false);
            }
            else {
                gameController.setFrozenView(false);
            }
            gameController.updateViewAfterMove(coup, mat);
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

        if(Configuration.isAnimationActived()){
            gameController.startMoveAnimation(coup, mat);
        }
        else{
            play(coup, mat);
            if(doubleRedo){
                redo(false);
            }
            else {
                gameController.setFrozenView(false);
            }
        }
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

    public AI getAiMinMax() {
        return aiMinMax;
    }

    public AIRandom getAleatron(){ return aleatron;}

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
    
    public void setGameDefenderAI(boolean isDefenderAI){
        defenderIsAI = isDefenderAI;
    }
    public void setGameAttackerAI(boolean isAttackerAI){
        attackerIsAI = isAttackerAI;
    }

    public void setAIAttackerDifficulty(AIDifficulty AIDiff){
        attackerTypeAI = AIDiff;
    }
    public void setAIDefenderDifficulty(AIDifficulty AIDiff){
        defenderTypeAI = AIDiff;
    }

//modif entre les deux (c'était inversé)
    public AIDifficulty getAIDefenderDifficulty (){
        return defenderTypeAI;
    }
    public AIDifficulty getAIAttackerDifficulty (){return attackerTypeAI;}
    //

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

        if(!startTimerEnded){
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

        if(attTimeRemainedMs == 0 || defTimeRemainedMs == 0) {
            gameController.updateViewEndGame();
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
}