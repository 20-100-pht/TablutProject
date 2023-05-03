package Controller;

import Model.Game;
import Model.GameRules;
import Model.Grid;
import Model.Piece;
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
        Grid grid = gameRules.getGrid();
        Piece pieceSelected = grid.getPieceAtPosition(coup.getInit());

        if(pieceSelected.isAttacker() && !game.isAttackerTurn() || (pieceSelected.isDefender() || pieceSelected.isKing()) && game.isAttackerTurn()){
            return;
        }

        ReturnValue result = gameRules.move(coup);
        if(result.getValue() != 0){
            return;
        }

        gameRules.attack(pieceSelected);

        game.toogleAttackerTurn();
    }
}
