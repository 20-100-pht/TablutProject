package Controller;

import Animation.AnimationMove;
import Model.*;
import Structure.Coordinate;
import Structure.Coup;
import View.GameFrame;
import View.GridPanel;

import java.io.*;
import java.util.Vector;

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
        if(logicGrid.isAttackerWinConfiguration() || (game.getDefTimeRemained() == 0 && game.isBlitzMode())) {
            gameFrame.showWinMessage(game.getAttackerName());
        }
        else if(logicGrid.isDefenderWinConfiguration() || (game.getAttTimeRemained() == 0 && game.isBlitzMode())){
            gameFrame.showWinMessage(game.getDefenderName());
        }

        gameFrame.showEndGameButtons();
        gameFrame.setFrozen(true);
    }

    public void bttnReplayClickHandler(){
        updateViewAfterReplay();
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

        gameFrame.showTextMessage("Partie sauvegardée avec succès !");
    }

    @Override
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
    public void updateViewAfterMove(Coup coup) {
        gameFrame.setTurnLabelValue(game.getTurnIndex());

        GridPanel gridPanel = gameFrame.getGridPanelInstance();
        if(coup != null) {
            Vector<Coordinate> casesCoords = logicGrid.getCoupCasesCrossed(coup);
            gridPanel.setMoveMarkCoords(casesCoords);
        }
        else{
            gridPanel.setMoveMarkCoords(null);
        }
        gameFrame.updatePlayerStatus();
    }

    @Override
    public void setFrozenView(boolean frozen){
        gameFrame.setFrozen(frozen);
    }

    public void startGame(){
        if(game.isAiTurn()) game.doAiTurnInSeparateThread();
    }

    public void updateViewAfterReplay(){
        gameFrame.hideAllMessages();
        gameFrame.hideEndGameButtons();
        gameFrame.setFrozen(false);

        GridPanelController gridPanelController = gameFrame.getGridPanelInstance().getGridPanelController();
        gridPanelController.updateViewAfterReplay();
    }
}
