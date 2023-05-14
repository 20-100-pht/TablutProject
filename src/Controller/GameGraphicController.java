package Controller;

import Animation.AnimationMove;
import Model.*;
import Structure.Coordinate;
import Structure.Coup;
import View.GameFrame;
import View.GridPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Collections;
import java.util.Vector;
import java.util.Collections;

import static java.lang.Thread.sleep;

public class GameGraphicController extends GameController {

    GameFrame gameFrame;
    LogicGrid logicGrid;
    Grid grid;
    Game game;
    Timer timerCount;
    int count;


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
        game.startGame();
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

        gameFrame.showTextMessage("Partie sauvegardée avec succès !", 3000);
    }

    @Override
    public void startMoveAnimation(Coup coup, MoveAnimationType moveAnimationType){

        Coordinate piece1Coords = coup.getInit();
        Coordinate piece2Coords = coup.getDest();
        Piece piece1 = grid.getPieceAtPosition(piece1Coords);

        GridPanel gridPanel = gameFrame.getGridPanelInstance();
        gridPanel.setPieceHidedCoords(piece1Coords);

        int xStart = piece1Coords.getCol()*gridPanel.getCaseSize();
        int yStart = piece1Coords.getRow()*gridPanel.getCaseSize();
        int xEnd = piece2Coords.getCol()*gridPanel.getCaseSize();
        int yEnd = piece2Coords.getRow()*gridPanel.getCaseSize();

        AnimationMove animation = new AnimationMove(this, coup, piece1.getType(), 500, xStart, yStart, xEnd, yEnd, moveAnimationType);
        animation.start();

        gameFrame.addAnimation(animation);
        gridPanel.setAnimationMove(animation);
    }



    public void endMoveAnimation(Coup coup, MoveAnimationType moveAnimationType){
        GridPanel gridPanel = gameFrame.getGridPanelInstance();
        if(moveAnimationType == MoveAnimationType.UNDO){
            logicGrid.move(coup);
            updateViewAfterMove(coup, moveAnimationType);
        }
        else {
            game.play(coup, moveAnimationType);
        }
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
    public void updateViewAfterMove(Coup coup, MoveAnimationType moveAnimationType) {
        gameFrame.setTurnLabelValue(game.getTurnIndex());

        GridPanel gridPanel = gameFrame.getGridPanelInstance();
        if(moveAnimationType != MoveAnimationType.UNDO) {
            Vector<Coordinate> casesCoords = logicGrid.getCoupCasesCrossed(coup);
            gridPanel.setMoveMarkCoords(casesCoords);
        }
        else{
            Vector<Coordinate> casesCoords = logicGrid.getCoupCasesCrossed(game.getPreviousCoup());
            gridPanel.setMoveMarkCoords(casesCoords);
        }
        gameFrame.updatePlayerStatus();
    }

    @Override
    public void setFrozenView(boolean frozen){
        gameFrame.setFrozen(frozen);
    }

    public void updateViewAfterReplay(){
        gameFrame.hideAllMessages();
        gameFrame.hideEndGameButtons();
        gameFrame.setFrozen(false);

        GridPanelController gridPanelController = gameFrame.getGridPanelInstance().getGridPanelController();
        gridPanelController.updateViewAfterReplay();
    }

    @Override
    public void startStartCount(){
        gameFrame.showTextMessage("Début dans 3 secondes...", -1);
        count = 3;
        timerCount = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                count -= 1;

                if(count == -1){
                    gameFrame.hideAllMessages();
                    timerCount.stop();
                    game.setStartTimerEnded();
                    game.startGame();
                    return;
                }

                if(count != 0) {
                    gameFrame.setTextMessage("Début dans " + count + " secondes...");
                }
                else{
                    gameFrame.setTextMessage("C'est parti !");
                }
            }
        });
        timerCount.setRepeats(true);
        timerCount.start();
    }

    public void bttnPauseIaClickHandler(){
        if(game.isIaPaused()){
            game.setIaPause(false);
            gameFrame.setBttnIaPauseText("Stopper IAs");
        }
        else{
            game.setIaPause(true);
            gameFrame.setBttnIaPauseText("Relancer IAs");
        }
        if((game.isAttackerTurn() && game.isAttackerAI()) || (!game.isAttackerTurn() && game.isDefenderAI())){
            AnimationMove anim = gameFrame.getGridPanelInstance().getAnimationMove();
            if(anim == null || anim.isTerminated())
                game.doAiTurnInSeparateThread();
        }
    }
}

