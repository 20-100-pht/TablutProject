package Controller;

import AI.AIDifficulty;
import Model.*;
import Structure.Coordinate;
import Structure.Coup;
import Structure.ReturnValue;

public class GameConsoleController {

    GameRules gameRules;
    Game game;
    UserController user;

    int MAX_DEPTH = 3; //Exploration de l'IA avec heuristique
    int RANDOM_AI_MAX_DEPTH = 3;

    int nbTurn;

    boolean printGridTerminal;
    private static final int MAX_TURN_ALLOWED = 200;

    public GameConsoleController(){
        user = new UserController();
        game = new Game("", "");
        nbTurn = 0;
        printGridTerminal = false;
        gameRules = game.getGameRulesInstance();
    }

    public ResultGame playGame(){
        while(!gameRules.isEndGame()){
            playTurn();
            if(game.isAiTest() && (nbTurn == MAX_TURN_ALLOWED)) gameRules.setEndGameVar(ResultGame.MAX_TURN_ENCOUTERED);
            nbTurn++;
        }
        return gameRules.getEndGameType();
    }

    public void endGame(ResultGame result){
        //if(printGridTerminal){
            if(result == ResultGame.ATTACKER_WIN) System.out.println("Attacker win !");
            if (result == ResultGame.DEFENDER_WIN) System.out.println("Defender win !");
            gameRules.print();
        //}
    }

    public void playTurn(){
        if(printGridTerminal) gameRules.print();

        if(game.isAttackerTurn()) playTurnAttacker();
        else playTurnDefender();

        game.toogleAttackerTurn();

    }

    public Piece movePlayer(){

        Piece current = null;

        Coup coupPlayer = new Coup(new Coordinate(0,0), new Coordinate(0,0));

        while (current == null) {

            coupPlayer = user.getCoupUser();

            ReturnValue returnValue = gameRules.move(coupPlayer);
            current = returnValue.getPiece();

            if(current == null){errorPlayerMovingPiece(returnValue);}

            if (current != null && ( (current.isAttacker() && !game.isAttackerTurn()) || (current.isDefenderOrKing() && game.isAttackerTurn()) ) ){
                if(printGridTerminal) System.out.println("Ce pion n'est pas le votre !");
                current = null;
            }

        }

        return current;
    }

    public void playTurnDefender(){
        if(printGridTerminal) System.out.println("\nDéfenseur, à vous de jouer !");

        Piece current = null;

        if(game.isDefenderAI()){
            while(current == null) {
                if(game.getAIDefenderDifficulty() == AIDifficulty.RANDOM){
                    Coup coupAI = game.getAleatron().playMove(gameRules, RANDOM_AI_MAX_DEPTH, PieceType.DEFENDER);
                    ReturnValue returnValue = gameRules.move(coupAI);
                    current = returnValue.getPiece();
                }
                ////if (nbTurn < 3) {
                else {
                    //Structure.Coup coupAI = aleatronDefender.playMove();
                    Coup coupAI = game.getAiMinMax().playMove(gameRules, MAX_DEPTH, PieceType.DEFENDER);
                    ReturnValue returnValue = gameRules.move(coupAI);
                    current = returnValue.getPiece();

                    if (current == null) {
                        System.out.println("\n\nError : Defender Coup");
                        gameRules.getGrid().print();
                        System.out.println("Source:" + coupAI.getInit().getRow() + "," + coupAI.getInit().getCol() + ", Dest:" + coupAI.getDest().getRow() + "," + coupAI.getDest().getCol());
                        System.exit(0);
                    }
                }
            }
        }
        else {
            current = movePlayer();
        }

        int kill = gameRules.attack(current).size();
        if(printGridTerminal) System.out.println("nb kill : " + kill);

        if(gameRules.isDefenderWinConfiguration()) endGame(ResultGame.DEFENDER_WIN);
    }

    public void playTurnAttacker(){
        if(printGridTerminal) System.out.println("\nAttaquant, à vous de jouer !");

        Piece current = null;

        if(game.isAttackerAI()){
            while(current == null) {
                if(game.getAIAttackerDifficulty() == AIDifficulty.RANDOM){
                    Coup coupAI = game.getAleatron().playMove(gameRules, RANDOM_AI_MAX_DEPTH, PieceType.ATTACKER);
                    ReturnValue returnValue = gameRules.move(coupAI);
                    current = returnValue.getPiece();
                }
                //if (nbTurn < 3) {
                else {
                    Coup coupAI = game.getAiMinMax().playMove(gameRules,MAX_DEPTH, PieceType.ATTACKER);
                    ReturnValue returnValue = gameRules.move(coupAI);
                    current = returnValue.getPiece();

                    if(current == null){
                        System.out.println("\n\nError : Attacker Coup");
                        gameRules.getGrid().print();
                        System.out.println("Source:" + coupAI.getInit().getRow() + ","+coupAI.getInit().getCol() + ", Dest:" + coupAI.getDest().getRow() + ","+ coupAI.getDest().getCol());
                        System.exit(0);
                    }
                }
            }
        }
        else {
            current = movePlayer();
        }

        //Check if the king has been captured while on or next to the throne
        gameRules.capture();

        int kill = gameRules.attack(current).size();
        if(printGridTerminal) System.out.println("nb kill : " + kill);


        if(gameRules.isAttackerWinConfiguration()) endGame(ResultGame.ATTACKER_WIN);
    }

    public void setPrintTerminal(boolean b) { printGridTerminal = b;}

    public Game getGame(){return game;}

    private void errorPlayerMovingPiece (ReturnValue returnValue){
        switch(returnValue.getValue()){
            case 1:
                if(printGridTerminal) System.out.println("Coordonnées invalides (en dehors de la grille), veuillez saisir à nouveau.");
                break;

            case 2:
                if(printGridTerminal) System.out.println("Coordonnées invalides (pas un pion), veuillez saisir à nouveau.");
                break;

            case 3:
                if(printGridTerminal) System.out.println("Case de destination invalide, veuillez saisir à nouveau.");
                break;
            case 4:
                if(printGridTerminal) System.out.println("Case de destination invalide (château), veuillez saisir à nouveau.");
                break;
            case 5:
                if(printGridTerminal) System.out.println("Case de destination invalide (même position), veuillez saisir à nouveau.");
                break;
            case 6:
                if(printGridTerminal) System.out.println("La pièce sélectionnée ne peut pas se déplacer sur la case de destination, veuillez saisir à nouveau.");
                break;
            case 7:
                if(printGridTerminal) System.out.println("La pièce sélectionnée ne peut pas se déplacer sur une forteresse, veuillez saisir à nouveau.");
                break;
            default:
                if(printGridTerminal) System.out.println("Erreur valeur de retour");
                break;
        }
    }
}
