package Controller;

import AI.AIDifficulty;
import Model.*;
import Structure.Coordinate;
import Structure.Coup;

public class GameConsoleController {

    LogicGrid logicGrid;
    Grid grid;
    Game game;
    UserController user;

    int MAX_DEPTH = 3; //Exploration de l'IA avec heuristique
    int RANDOM_AI_MAX_DEPTH = 3;

    int nbTurn;

    boolean printGridTerminal;
    private static final int MAX_TURN_ALLOWED = 200;

    public GameConsoleController(){
        user = new UserController();
        game = new Game("", "", AIDifficulty.RANDOM, AIDifficulty.RANDOM);
        nbTurn = 0;
        printGridTerminal = false;
        logicGrid = game.getLogicGridInstance();
        grid = logicGrid.getGrid();
    }

    public ResultGame playGame(){
        while(!logicGrid.isEndGame()){
            playTurn();
            if(game.isAiTest() && (nbTurn == MAX_TURN_ALLOWED)) logicGrid.setEndGameVar(ResultGame.MAX_TURN_ENCOUTERED);
            nbTurn++;
        }
        return logicGrid.getEndGameType();
    }

    public void endGame(ResultGame result){
        //if(printGridTerminal){
            if(result == ResultGame.ATTACKER_WIN) System.out.println("Attacker win !");
            if (result == ResultGame.DEFENDER_WIN) System.out.println("Defender win !");
            logicGrid.print();
        //}
    }

    public void playTurn(){
        if(printGridTerminal) logicGrid.print();

        if(game.isAttackerTurn()) playTurnAttacker();
        else playTurnDefender();

        game.toogleAttackerTurn();

    }

    public Piece movePlayer(){

        Piece current = null;

        Coup coupPlayer = new Coup(new Coordinate(0,0), new Coordinate(0,0));

        while (current == null) {

            coupPlayer = user.getCoupUser();

            int error;
            if((error = logicGrid.isLegalMove(coupPlayer)) != 0){
                printMoveError(error);
                return null;
            }

            logicGrid.move(coupPlayer);
            current = grid.getPieceAtPosition(coupPlayer.getDest());

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
                    Coup coupAI = game.getAleatron().playMove(logicGrid, RANDOM_AI_MAX_DEPTH, PieceType.DEFENDER);
                    logicGrid.move(coupAI);
                    current = grid.getPieceAtPosition(coupAI.getDest());
                }
                ////if (nbTurn < 3) {
                else {
                    //Structure.Coup coupAI = aleatronDefender.playMove();
                    Coup coupAI = game.getAiMinMax().playMove(logicGrid, MAX_DEPTH, PieceType.DEFENDER);
                    logicGrid.move(coupAI);
                    current = grid.getPieceAtPosition(coupAI.getDest());

                    if (current == null) {
                        System.out.println("\n\nError : Defender Coup");
                        logicGrid.getGrid().print();
                        System.out.println("Source:" + coupAI.getInit().getRow() + "," + coupAI.getInit().getCol() + ", Dest:" + coupAI.getDest().getRow() + "," + coupAI.getDest().getCol());
                        System.exit(0);
                    }
                }
            }
        }
        else {
            current = movePlayer();
        }

        int kill = logicGrid.attack(current).size();
        if(printGridTerminal) System.out.println("nb kill : " + kill);

        if(logicGrid.isDefenderWinConfiguration()) endGame(ResultGame.DEFENDER_WIN);
    }

    public void playTurnAttacker(){
        if(printGridTerminal) System.out.println("\nAttaquant, à vous de jouer !");

        Piece current = null;

        if(game.isAttackerAI()){
            while(current == null) {
                if(game.getAIAttackerDifficulty() == AIDifficulty.RANDOM){
                    Coup coupAI = game.getAleatron().playMove(logicGrid, RANDOM_AI_MAX_DEPTH, PieceType.ATTACKER);
                    logicGrid.move(coupAI);
                    current = grid.getPieceAtPosition(coupAI.getDest());
                }
                //if (nbTurn < 3) {
                else {
                    Coup coupAI = game.getAiMinMax().playMove(logicGrid,MAX_DEPTH, PieceType.ATTACKER);
                    logicGrid.move(coupAI);
                    current = grid.getPieceAtPosition(coupAI.getDest());

                    if(current == null){
                        System.out.println("\n\nError : Attacker Coup");
                        logicGrid.getGrid().print();
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
        logicGrid.capture();

        int kill = logicGrid.attack(current).size();
        if(printGridTerminal) System.out.println("nb kill : " + kill);


        if(logicGrid.isAttackerWinConfiguration()) endGame(ResultGame.ATTACKER_WIN);
    }

    public void setPrintTerminal(boolean b) { printGridTerminal = b;}

    public Game getGame(){return game;}

    private void printMoveError (int errorIndex){
        switch(errorIndex){
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
