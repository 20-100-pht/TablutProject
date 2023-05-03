package Model;

import Model.AIRandom;
import Model.GameRules;
import Structure.Coordinate;
import Structure.Coup;
import Structure.ReturnValue;

import java.io.*;
import java.util.Scanner;

public class Game {
    boolean attackerTurn;

    boolean defenderAI;
    boolean attackerAI;

    //Model.AIRandom aleatronDefender;
    AIRandom aleatronAttacker;
    AI aiDefender;
    AI aiAttacker;
    GameRules gameRules;


    public Game(){
        reset();
    }

    public void reset(){
        attackerTurn = true;
        defenderAI = true;
        attackerAI = true;
        gameRules = new GameRules();
        //aleatronDefender = new Model.AIRandom(gameRules, Model.PieceType.DEFENDER);
        aleatronAttacker = new AIRandom(gameRules, PieceType.ATTACKER);
        aiDefender = new AI();
        //aiAttacker = new AI(gameRules);
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

    public AI getAiAttacker() {
        return aiAttacker;
    }

    public AI getAiDefender() {
        return aiDefender;
    }

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
}