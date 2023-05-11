package Controller;

import Animation.AnimationMove;
import Model.*;
import Structure.Coordinate;
import Structure.Coup;
import View.GameFrame;
import View.GridPanel;

import java.io.*;

import static java.lang.Thread.sleep;

public class GameGraphicController extends GameController {

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

    @Override
    public void updateViewEndGame(){
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

    public void startMoveAnimation(Coup coup){

        System.out.println("dsqdd");

        Coordinate piece1Coords = coup.getInit();
        Coordinate piece2Coords = coup.getDest();
        Piece piece1 = grid.getPieceAtPosition(piece1Coords);

        GridPanel gridPanel = gameFrame.getGridPanelInstance();
        gridPanel.setPieceHidedCoords(piece1Coords);

        int xStart = piece1Coords.getCol()*gridPanel.getCaseSize();
        int yStart = piece1Coords.getRow()*gridPanel.getCaseSize();
        int xEnd = piece2Coords.getCol()*gridPanel.getCaseSize();
        int yEnd = piece2Coords.getRow()*gridPanel.getCaseSize();

        AnimationMove animation = new AnimationMove(this, coup, piece1.getType(), 500, xStart, yStart, xEnd, yEnd);
        animation.start();

        gameFrame.addAnimation(animation);
        gridPanel.setAnimationMove(animation);
    }



    public void endMoveAnimation(Coup coup){
        game.play(coup, true);
        GridPanel gridPanel = gameFrame.getGridPanelInstance();
        gridPanel.setPieceHidedCoords(null);
        gridPanel.setAnimationMove(null);
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
        //gameFrame.setPlayerStatus(Integer.(game.isAttackerTurn()));
    }

    @Override
    public void setFrozenView(boolean frozen){
        gameFrame.setFrozen(frozen);
    }

    public void startGame(){
        if(game.isAiTurn()) game.doAiTurnInSeparateThread();
    }
}
