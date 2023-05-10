package Controller;

import Model.*;
import Structure.Coup;
import View.GameFrame;
import AI.AI;

import java.io.*;
import java.util.Vector;

import static java.lang.Thread.sleep;

public class GameGraphicController {

    GameFrame gameFrame;
    LogicGrid logicGrid;
    Grid grid;
    Game game;

    public GameGraphicController(GameFrame gameFrame, Game game){
        this.gameFrame = gameFrame;
        this.game = game;
        game.setGameControllerInstance(this);
        logicGrid = game.getLogicGridInstance();
        grid = logicGrid.getGrid();
    }

    public Game getGameInstance(){
        return game;
    }

    public void play(Coup coup, boolean addToHistory){

        if(logicGrid.isEndGame()){
            return;
        }

        Grid grid = logicGrid.getGrid();
        Piece pieceSelected = grid.getPieceAtPosition(coup.getInit());

        if(pieceSelected.isAttacker() && !game.isAttackerTurn() || (pieceSelected.isDefender() || pieceSelected.isKing()) && game.isAttackerTurn()){
            return;
        }

        if(logicGrid.isLegalMove(coup) != 0){
            return;
        }
        logicGrid.move(coup);

        logicGrid.capture();
        Vector<Piece> killedPieces = logicGrid.attack(pieceSelected);

        if(addToHistory) {
            History history = game.getHistoryInstance();
            history.addMove(new HistoryMove(coup, killedPieces, game.isAttackerTurn(), game.getTurnIndex()));
        }

        TreatPossibleEndGame();

        game.toogleAttackerTurn();
        game.incTurnIndex();
        gameFrame.setTurnLabelValue(game.getTurnIndex());

        if(isAiTurn()) doAiTurnInSeparateThread();
    }

    void TreatPossibleEndGame(){
        if(logicGrid.isAttackerWinConfiguration()) {
            gameFrame.showWinMessage(game.getAttackerName());
        }
        else if(logicGrid.isDefenderWinConfiguration()){
            gameFrame.showWinMessage(game.getDefenderName());
        }

        gameFrame.showEndGameButtons();
        gameFrame.setFrozen(true);
    }

    public void bttnReplayClickHandler(){
        gameFrame.hideAllMessages();
        gameFrame.hideEndGameButtons();
        gameFrame.setFrozen(false);
        game.reset();
        startGame();
    }

    public void bttnSaveClickHandler(){
        File saveFile = gameFrame.showSaveDialog();
        if(saveFile == null){
            return;
        }
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
        game.undo();
    }

    public void bttnRedoClickHandler(){
        game.redo();
    }

    @Override
    public void updateViewAfterMove() {
        gameFrame.setTurnLabelValue(game.getTurnIndex());
    }

    @Override
    public void setFrozenView(boolean frozen){
        gameFrame.setFrozen(frozen);
    }

    public void startGame(){
        if(game.isAiTurn()) game.doAiTurnInSeparateThread();
    }
}
