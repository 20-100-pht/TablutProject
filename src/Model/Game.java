package Model;

import AI.*;

import java.io.*;

public class Game implements Serializable {
    boolean attackerTurn;
    int turnIndex;
    boolean defenderIsAI;
    boolean attackerIsAI;

    AIDifficulty attackerTypeAI;
    AIDifficulty defenderTypeAI;


    //AI.AIRandom aleatronDefender;
    AI aiMinMax;
    AIRandom aleatron;
    AI defenderAI;
    AI attackerAI;
    LogicGrid logicGrid;
    String defenderName = "Alexandre";
    String attackerName = "Philippe";
    History history;


    public Game(String defenderName, String attackerName, AIDifficulty defAiDifficulty, AIDifficulty attAiDifficulty){

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

        createIaInstances();

        logicGrid = new LogicGrid();
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
}