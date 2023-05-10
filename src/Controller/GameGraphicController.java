package Controller;

import Animation.AnimationMove;
import Model.*;
import Structure.Coordinate;
import Structure.Coup;
import View.GameFrame;

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

    @Override
    public void startMoveAnimation(Coordinate piece1, Coordinate piece2){
        gameFrame.setPieceHided1Coords(piece1);
        gameFrame.setPieceHided2Coords(piece2);
        //AnimationMove animation = new AnimationMove(2000, );
    }

    @Override
    public void endMoveAnimation(){

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
