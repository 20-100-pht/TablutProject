package Model;

import AI.*;
import Controller.GameController;
import Global.Configuration;
import Structure.Coup;

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


    public Game(String defenderName, String attackerName, AIDifficulty defAiDifficulty, AIDifficulty attAiDifficulty, int blitzTime){

        if(defAiDifficulty != AIDifficulty.HUMAN) defenderIsAI = true;
        else defenderIsAI = false;
        if(attAiDifficulty != AIDifficulty.HUMAN) attackerIsAI = true;
        else attackerIsAI = false;

        defenderTypeAI = defAiDifficulty;
        attackerTypeAI = attAiDifficulty;

        if(defenderName.length() == 0) {
            if(!defenderIsAI) this.defenderName = "Defenseur humain";
            else this.defenderName = "Défenseur IA";
        } else{
            this.defenderName = defenderName;
        }
        if(attackerName.length() == 0) {
            if(!attackerIsAI) this.attackerName = "Attaqueur humain";
            else this.attackerName = "Attaqueur IA";
        }
        else {
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
        Coup coupAI = ai.playMove(logicGrid, 3, t);
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
        //play(coupAI, true);
        if(Configuration.isAnimationActived()) {
            gameController.startMoveAnimation(coupAI);
        }
        else{
            play(coupAI, true);
        }
        gameController.setFrozenView(false);
    }

    public void play(Coup coup, boolean addToHistory){

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

        if(addToHistory) {
            History history = getHistoryInstance();
            history.addMove(new HistoryMove(coup, killedPieces, isAttackerTurn(), getTurnIndex()));
        }

        toogleAttackerTurn();
        incTurnIndex();

        if(logicGrid.isAttackerWinConfiguration() || logicGrid.isDefenderWinConfiguration()) {
            gameController.updateViewEndGame();
            return;
        }

        gameController.updateViewAfterMove(coup);

        if(isAiTurn()) doAiTurnInSeparateThread();
    }

    public void undo(){

        if(!history.canUndo()){
            return;
        }

        HistoryMove move = history.undo();

        Coup coup = move.getCoup();
        Piece piece = grid.getPieceAtPosition(coup.getDest());
        piece.setCol(coup.getInit().getCol());
        piece.setRow(coup.getInit().getRow());
        grid.setPieceAtPosition(piece, coup.getInit());
        grid.setPieceAtPosition(null, coup.getDest());

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

        setIsAttackerTurn(move.isAttackerMove());
        setTurnIndex(move.getTurnIndex());

        gameController.updateViewAfterMove(null);
    }

    public void redo(){
        if(!history.canRedo()){
            return;
        }
        HistoryMove move = history.redo();
        Coup coup = move.getCoup();
        play(coup, false);
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

    public AIDifficulty getAIDefenderDifficulty (){
        return attackerTypeAI;
    }
    public AIDifficulty getAIAttackerDifficulty (){
        return defenderTypeAI;
    }

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
}