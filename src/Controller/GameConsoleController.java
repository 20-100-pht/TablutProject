package Controller;

import Model.*;
import Structure.Coordinate;
import Structure.Coup;
import Structure.ReturnValue;

import java.util.Scanner;

public class GameConsoleController {

    GameRules gameRules;
    Game game;
    Scanner scanner;

    public GameConsoleController(){
        scanner = new Scanner(System.in);
        game = new Game();
        gameRules = game.getGameRulesInstance();
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

        if(game.isAttackerTurn()) playTurnAttacker();
        else playTurnDefender();

        game.toogleAttackerTurn();

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


            if (current != null && ( (current.isAttacker() && !game.isAttackerTurn()) || (current.isDefenderOrKing() && game.isAttackerTurn()) ) ){
                System.out.println("Ce pion n'est pas le votre !");
                current = null;
            }

        }

        return current;
    }

    public void playTurnDefender(){
        System.out.println("Défenseur, à vous de jouer !");

        Piece current = null;

        if(game.isDefenderAI()){
            while(current == null) {

                //Structure.Coup coupAI = aleatronDefender.playMove();
                Coup coupAI = game.getAiDefender().minimax(gameRules.getGrid().cloneGrid(), gameRules.getKing().clonePiece(),3, PieceType.DEFENDER);
                ReturnValue returnValue = gameRules.move(coupAI);
                current = returnValue.getPiece();

                if(current == null){
                    System.out.println("\n\nError : Defender Coup : " + current);
                    gameRules.getGrid().print();
                    System.out.println("Source:" + coupAI.getInit().getRow() + ","+coupAI.getInit().getCol() + ", Dest:" + coupAI.getDest().getRow() + ","+ coupAI.getDest().getCol());
                    System.exit(0);
                }
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

        if(game.isAttackerAI()){
            while(current == null) {
                //Coup coupAI = aleatronAttacker.playMove();
                Coup coupAI = game.getAiDefender().minimax(gameRules.getGrid().cloneGrid(),gameRules.getKing().clonePiece(),3, PieceType.ATTACKER);
                ReturnValue returnValue = gameRules.move(coupAI);
                current = returnValue.getPiece();

                if(current == null){
                    System.out.println("\n\nError : Attacker Coup : " + current);
                    gameRules.getGrid().print();
                    System.out.println("Source:" + coupAI.getInit().getRow() + ","+coupAI.getInit().getCol() + ", Dest:" + coupAI.getDest().getRow() + ","+ coupAI.getDest().getCol());
                    System.exit(0);
                }
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
}
