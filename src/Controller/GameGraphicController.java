package Controller;

import Model.*;
import Structure.Coup;
import Structure.ReturnValue;
import View.GameFrame;

public class GameGraphicController {

    GameFrame gameFrame;
    GameRules gameRules;
    Game game;

    public GameGraphicController(GameFrame gameFrame){
        this.gameFrame = gameFrame;
        this.game = new Game();
        this.gameRules = game.getGameRulesInstance();
    }

    public Game getGameInstance(){
        return game;
    }

    public void play(Coup coup){

        if(gameRules.isEndGame()){
            return;
        }

        Grid grid = gameRules.getGrid();
        Piece pieceSelected = grid.getPieceAtPosition(coup.getInit());

        if(pieceSelected.isAttacker() && !game.isAttackerTurn() || (pieceSelected.isDefender() || pieceSelected.isKing()) && game.isAttackerTurn()){
            return;
        }

        ReturnValue result = gameRules.move(coup);
        if(result.getValue() != 0){
            return;
        }

        gameRules.capture();
        gameRules.attack(pieceSelected);

        if(gameRules.isAttackerWinConfiguration()) {
            gameFrame.showWinMessage(game.getAttackerName());
            gameFrame.showEndGameButtons();
        }
        else if(gameRules.isDefenderWinConfiguration()){
            gameFrame.showWinMessage(game.getDefenderName());
            gameFrame.showEndGameButtons();
        }

        game.toogleAttackerTurn();
    }

    public void bttnReplayClickHandler(){
        gameFrame.hideAllMessages();
        gameFrame.hideEndGameButtons();
        game.reset();
    }

    public void bttnUndoClickHandler(){

    }

    public void bttnRedoClickHandler(){

    }
}
