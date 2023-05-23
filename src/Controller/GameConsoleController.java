package Controller;

import AI.AIDifficulty;
import Global.Configuration;
import Model.*;
import Structure.Coordinate;
import Structure.Coup;

public class GameConsoleController extends GameController {

    LogicGrid logicGrid;
    Grid grid;
    Game game;
    UserController user;

    CoupHumanList coupHumanByList;

    int nbTurn;

    boolean printGridTerminal;
    private static final int MAX_TURN_ALLOWED = Configuration.getMaxTurn();

    public GameConsoleController(Game game){
        user = new UserController();
        this.game = game;
        game.setGameControllerInstance(this);
        nbTurn = 0;
        printGridTerminal = false;
        logicGrid = game.getLogicGridInstance();
        grid = logicGrid.getGrid();
        if(Configuration.isListCoupHumanGame()) coupHumanByList = new CoupHumanList();
        else coupHumanByList = null;
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
        if(printGridTerminal){
            if(result == ResultGame.ATTACKER_WIN) System.out.println("Attacker win !");
            if (result == ResultGame.DEFENDER_WIN) System.out.println("Defender win !");
            logicGrid.print();
        }
    }

    public void playTurn(){
        if(printGridTerminal) logicGrid.print();

        if(printGridTerminal){
            if(game.isAttackerTurn()) System.out.println("\nAttaquant, à vous de jouer !");
            else System.out.println("\nDéfenseur, à vous de jouer !");
        }

        Piece current = null;

        if((game.isDefenderAI() && !game.isAttackerTurn()) || (game.isAttackerAI() && game.isAttackerTurn())){

            Coup coupAI = null;
            PieceType AiPieces;
            if(game.isAttackerTurn()) AiPieces = PieceType.ATTACKER;
            else AiPieces = PieceType.DEFENDER;

           if(game.isDefenderAI() && !game.isAttackerTurn()){
               if(game.getAIDefenderDifficulty() == AIDifficulty.EASY) coupAI = game.getDefenderAI().playMove(logicGrid, Configuration.getMaxAiRandomDepth(), AiPieces);
               else coupAI = game.getDefenderAI().playMove(logicGrid, Configuration.getMaxAiDepth(), AiPieces);
           }
           else if(game.isAttackerAI() && game.isAttackerTurn()){
               if(game.getAIAttackerDifficulty() == AIDifficulty.EASY) coupAI = game.getAttackerAI().playMove(logicGrid, Configuration.getMaxAiRandomDepth(), AiPieces);
               else coupAI = game.getAttackerAI().playMove(logicGrid, Configuration.getMaxAiDepth(), AiPieces);
           }

            logicGrid.move(coupAI);
            current = grid.getPieceAtPosition(coupAI.getDest());

        }
        else current = movePlayer();

        int kill = logicGrid.attack(current).size();
        if(printGridTerminal) System.out.println("nb kill : " + kill);

        if (game.isAttackerTurn()) logicGrid.capture();

        if(logicGrid.isDefenderWinConfiguration()) endGame(ResultGame.DEFENDER_WIN);
        else if(logicGrid.isAttackerWinConfiguration()) endGame(ResultGame.ATTACKER_WIN);

        game.toogleAttackerTurn();

    }

    public Piece movePlayer(){

        Piece current = null;

        Coup coupPlayer = new Coup(new Coordinate(0,0), new Coordinate(0,0));

        while (current == null) {

            if(coupHumanByList == null) coupPlayer = user.getCoupUser();
            else coupPlayer = coupHumanByList.getCoup();

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
