package Controller;

import Model.*;
import Structure.Coup;
import Structure.ReturnValue;
import View.GameFrame;

import java.io.*;
import java.util.Vector;

public class GameGraphicController {

    GameFrame gameFrame;
    GameRules gameRules;
    Grid grid;
    Game game;

    public GameGraphicController(GameFrame gameFrame, Game game){
        this.gameFrame = gameFrame;
        this.game = game;
        gameRules = game.getGameRulesInstance();
        grid = gameRules.getGrid();
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
        Vector<Piece> killedPieces = gameRules.attack(pieceSelected);

        History history = game.getHistoryInstance();
        history.addMove(new HistoryMove(coup, killedPieces, game.isAttackerTurn()));

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

    public void bttnSaveClickHandler(){
        File saveFile = gameFrame.showSaveDialog();
        try {
            FileOutputStream fos = new FileOutputStream(saveFile);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(game);

            oos.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void bttnUndoClickHandler(){
        System.out.println("Cluck undo");
        History history = game.getHistoryInstance();
        if(history.canUndo()){
            System.out.println("Undo");
            HistoryMove move = history.undo();
            undoHistoryMove(move);
        }
    }

    public void bttnRedoClickHandler(){
        History history = game.getHistoryInstance();
        if(history.canRedo()){
            HistoryMove move = history.redo();
            redoHistoryMove(move);
        }
    }

    public void undoHistoryMove(HistoryMove move){
        Coup coup = move.getCoup();
        Piece piece = grid.getPieceAtPosition(coup.getDest());
        piece.setCol(coup.getInit().getCol());
        piece.setRow(coup.getInit().getRow());
        grid.setPieceAtPosition(piece, coup.getInit());
        grid.setPieceAtPosition(null, coup.getDest());

        for(int i = 0; i < move.getKilledPieces().size(); i++){
            Piece kPiece = move.getKilledPieces().get(i);
            grid.setPieceAtPosition(kPiece, kPiece.getCoords());
            if(kPiece.getType() == PieceType.DEFENDER){
                gameRules.incNbPieceDefenderOnGrid();
            }
            else if(kPiece.getType() == PieceType.ATTACKER){
                gameRules.incNbPieceAttackerOnGrid();
            }
        }
        game.setIsAttackerTurn(move.isAttackerMove());
    }

    public void redoHistoryMove(HistoryMove move){
        Coup coup = move.getCoup();
        Piece piece = grid.getPieceAtPosition(coup.getInit());
        ReturnValue m = gameRules.move(coup);
        gameRules.attack(piece);
        game.setIsAttackerTurn(!move.isAttackerMove());
    }
}
