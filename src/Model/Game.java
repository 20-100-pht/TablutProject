package Model;

import java.io.*;

public class Game {
    boolean attackerTurn;

    boolean defenderAI;
    boolean attackerAI;

    //Model.AIRandom aleatronDefender;
    AI aiMinMax;
    GameRules gameRules;

    AIRandom aleatronDefender;
    AIRandom aleatronAttacker;


    public Game(){
        reset();
    }

    public void reset(){
        attackerTurn = true;
        defenderAI = true;
        attackerAI = true;
        gameRules = new GameRules();
        aleatronDefender = new Model.AIRandom(gameRules, Model.PieceType.DEFENDER);
        aleatronAttacker = new AIRandom(gameRules, PieceType.ATTACKER);
        aiMinMax = new AI();
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

    public AIRandom getAleatronDefender(){ return aleatronDefender;}
    public AIRandom getAleatronAttacker(){ return aleatronAttacker;}

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
}