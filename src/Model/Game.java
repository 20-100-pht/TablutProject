package Model;

import Model.AIRandom;
import Model.GameRules;
import Structure.Coordinate;
import Structure.Coup;
import Structure.ReturnValue;

import java.io.*;
import java.util.Scanner;

public class Game {
    boolean attacker;

    boolean defenderAI;
    boolean attackerAI;

    //Model.AIRandom aleatronDefender;
    AIRandom aleatronAttacker;
    AI aiDefender;
    AI aiAttacker;

    Scanner scanner = new Scanner(System.in);
    GameRules gameRules;


    public Game(){
        reset();
    }

    public void reset (){
        attacker = true;
        defenderAI = true;
        attackerAI = true;
        gameRules = new GameRules();
        //aleatronDefender = new Model.AIRandom(gameRules, Model.PieceType.DEFENDER);
        aleatronAttacker = new AIRandom(gameRules, PieceType.ATTACKER);
        aiDefender = new AI();
        //aiAttacker = new AI(gameRules);
    }

    public void playGame(){
        while(!gameRules.isEndGame()){
            playTurn();
        }
    }

    public void endGame(ResultGame result){
        if(result == ResultGame.ATTACKER_WIN) System.out.println("Attacker win !");
        else System.out.println("Defender win !");
        gameRules.print();
    }

    public void playTurn(){
        gameRules.print();

        if(attacker) playTurnAttacker();
        else playTurnDefender();

        attacker = !attacker;
        
    }

    public Piece movePlayer(){

        Piece current = null;

        Coup coupPlayer = new Coup(new Coordinate(0,0), new Coordinate(0,0));

        while (current == null) {
            System.out.println("Coordonnées de la pièce que vous souhaitez déplacer (ligne colonne) : ");
            coupPlayer.getInit().setRowCoord(scanner.nextInt());
            coupPlayer.getInit().setColCoord(scanner.nextInt());


            System.out.println("Coordonnées de la case où vous souhaitez déplacer la pièce (ligne colonne) : ");
            coupPlayer.getDest().setRowCoord(scanner.nextInt());
            coupPlayer.getDest().setColCoord(scanner.nextInt());

            ReturnValue returnValue = gameRules.move(coupPlayer);
            current = returnValue.getPiece();

            if(current == null){
                switch(returnValue.getValue()){
                    case 1:
                        System.out.println("Coordonnées invalides (en dehors de la grille), veuillez saisir à nouveau.");
                        break;

                    case 2:
                        System.out.println("Coordonnées invalides (pas un pion), veuillez saisir à nouveau.");
                        break;

                    case 3:
                        System.out.println("Case de destination invalide, veuillez saisir à nouveau.");
                        break;
                    case 4:
                        System.out.println("Case de destination invalide (château), veuillez saisir à nouveau.");
                        break;
                    case 5:
                        System.out.println("Case de destination invalide (même position), veuillez saisir à nouveau.");
                        break;
                    case 6:
                        System.out.println("La pièce sélectionnée ne peut pas se déplacer sur la case de destination, veuillez saisir à nouveau.");
                        break;
                    case 7:
                        System.out.println("La pièce sélectionnée ne peut pas se déplacer sur une forteresse, veuillez saisir à nouveau.");
                        break;
                    default:
                        System.out.println("Erreur valeur de retour");
                        break;
                }
            }


            if (current != null && ( (current.isAttacker() && !attacker) || (current.isDefenderOrKing() && attacker) ) ){
                System.out.println("Ce pion n'est pas le votre !");
                current = null;
            }

        }

        return current;
    }

    public void playTurnDefender(){
        System.out.println("Défenseur, à vous de jouer !");


        Piece current = null;

        if(defenderAI){
            while(current == null) {

                //Structure.Coup coupAI = aleatronDefender.playMove();
                Coup coupAI = aiDefender.minimax(gameRules.grid,gameRules.king,3, PieceType.DEFENDER);
                ReturnValue returnValue = gameRules.move(coupAI);
                current = returnValue.getPiece();
            }
        }
        else {
            current = movePlayer();
        }

        int kill = gameRules.attack(current);
        System.out.println("nb kill : " + kill);

        if(gameRules.isDefenderWinConfiguration()) endGame(ResultGame.DEFENDER_WIN);
    }

    public void playTurnAttacker(){
        System.out.println("Attaquant, à vous de jouer !");

        Piece current = null;

        if(attackerAI){
            while(current == null) {
                Coup coupAI = aleatronAttacker.playMove();
                ReturnValue returnValue = gameRules.move(coupAI);
                current = returnValue.getPiece();
            }
        }
        else {
            current = movePlayer();
        }

        //Check if the king has been captured while on or next to the throne
        gameRules.capture();

        int kill = gameRules.attack(current);
        System.out.println("nb kill : " + kill);


        if(gameRules.isAttackerWinConfiguration()) endGame(ResultGame.ATTACKER_WIN);
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

}