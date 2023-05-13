package Controller;

import AI.AIDifficulty;
import Model.*;
import Structure.Coordinate;
import Structure.Coup;

public class GameConsoleController extends GameController {

    LogicGrid logicGrid;
    Grid grid;
    Game game;
    UserController user;

    int MAX_DEPTH = 4; //Exploration de l'IA avec heuristique
    int RANDOM_AI_MAX_DEPTH = 3;

    int nbTurn;

    boolean printGridTerminal;
    private static final int MAX_TURN_ALLOWED = 200;

    public GameConsoleController(Game game){
        user = new UserController();
        this.game = game;
        game.setGameControllerInstance(this);
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

            Coup coupAI;
            PieceType AiType;
            if(game.isAttackerTurn()) AiType = PieceType.ATTACKER;
            else AiType = PieceType.DEFENDER;
//TODO ?? de base y'avait pas AiType == PieceType.DEFENDER en vrai je suis pas sur si c'est nécéssaire car le problème venait d'une autre fonction mais bon
            //TODO à modifier quand il y aura plusieurs difficultées
            if( (AiType == PieceType.DEFENDER && game.isDefenderAI() && (game.getAIDefenderDifficulty() == AIDifficulty.RANDOM)) || (AiType == PieceType.ATTACKER && game.isAttackerAI() && (game.getAIAttackerDifficulty() == AIDifficulty.RANDOM)) ){
                coupAI = game.getAleatron().playMove(logicGrid, RANDOM_AI_MAX_DEPTH, AiType);
            }
            else coupAI = game.getAiMinMax().playMove(logicGrid, MAX_DEPTH, AiType);

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
