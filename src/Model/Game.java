package Model;

import AI.*;

import java.io.*;

public class Game {
    boolean attackerTurn;

    boolean defenderAI;
    boolean attackerAI;

    AIDifficulty attackerTypeAI;
    AIDifficulty defenderTypeAI;


    //AI.AIRandom aleatronDefender;
    AI aiMinMax;
    GameRules gameRules;

    AIRandom aleatron;

    public Game(){
        reset();
    }

    public void reset(){
        attackerTurn = true;
        defenderAI = true;
        attackerAI = true;
        attackerTypeAI = AIDifficulty.RANDOM;
        defenderTypeAI = AIDifficulty.RANDOM;
        gameRules = new GameRules();
        aleatron = new AIRandom();
        aiMinMax = new AIEasy();
    }

    public void loadFromFile(String filePath){
        try {
            FileInputStream fis = new FileInputStream(filePath);
            ObjectInputStream ois = new ObjectInputStream(fis);

            gameRules = (GameRules) ois.readObject();

            ois.close();

        } catch (IOException e) {
            System.out.println(e.toString());
        } catch (ClassNotFoundException e){
            System.out.println(e.toString());
        }
    }

    public void saveInFile(String filePath){
        try {
            FileOutputStream fos = new FileOutputStream(filePath);
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            oos.writeObject(gameRules);

            oos.close();

        } catch(IOException e) {
            System.out.println(e.toString());
        }
    }

    public Grid getGridInstance(){
        return gameRules.grid;
    }
    public GameRules getGameRulesInstance(){
        return gameRules;
    }
    public GameRules getLogicGrid(){
        return gameRules;
    }

    public AI getAiMinMax() {
        return aiMinMax;
    }

    public AIRandom getAleatron(){ return aleatron;}

    public boolean isAttackerAI() {
        return attackerAI;
    }

    public boolean isDefenderAI() {
        return defenderAI;
    }

    public boolean isAttackerTurn(){
        return attackerTurn;
    }

    public void toogleAttackerTurn(){
        attackerTurn = !attackerTurn;
    }

    public boolean isAiTest(){
        return attackerAI && defenderAI;
    }

    public void setGameDefenderAI(boolean isDefenderAI){defenderAI = isDefenderAI;}
    public void setGameAttackerAI(boolean isAttackerAI){attackerAI = isAttackerAI;}

    public void setAIAttackerDifficulty(AIDifficulty AIDiff){attackerTypeAI = AIDiff;}
    public void setAIDefenderDifficulty(AIDifficulty AIDiff){defenderTypeAI = AIDiff;}

    public AIDifficulty getAIDefenderDifficulty (){return attackerTypeAI;}
    public AIDifficulty getAIAttackerDifficulty (){return defenderTypeAI;}

}