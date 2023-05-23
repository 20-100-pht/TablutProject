package Controller;

import Animation.AnimationMove;
import Global.Configuration;
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
    Thread threadAiHint;


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
        updateTurnLabel();
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

        stopHintProcess();

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

    public void executeMoveAnimation(Coup coup, MoveAnimationType moveAnimationType){

        GridPanel gridPanel = gameFrame.getGridPanelInstance();
        gridPanel.setPieceHidedCoords(null);
        gridPanel.setAnimationMove(null);

        game.executeMove(coup, moveAnimationType);
    }

    public void bttnUndoClickHandler(){
        if(!gameFrame.isAnimationMoveTerminated()) return;
        if(game.isReviewMode()) return;
        if(game.isAttackerAI() && game.isDefenderAI()) return;
        if(game.isEnded()) return;
        if(!game.canUndo()) return;

        if(!game.isAiTurn()){
            if(game.getNbIa() == 1) game.undo(true);
            else game.undo(false);
        }
    }

    public void bttnRedoClickHandler(){
        if(!gameFrame.isAnimationMoveTerminated()) return;
        if(game.isReviewMode()) return;
        if(game.isAttackerAI() && game.isDefenderAI()) return;
        if(!game.canRedo()) return;
        if(game.isEnded()) return;

        if(!game.isAiTurn()) {
            if (game.getNbIa() == 1) game.redo(true);
            else game.redo(false);
        }
    }

    @Override
    public void updateViewAfterMove(Coup coup, MoveAnimationType moveAnimationType) {

        stopHintProcess();

        updateTurnLabel();

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

    public void updateTurnLabel(){
        if(game.isReviewMode()){
            gameFrame.setTurnLabelValue("Tour "+(game.getReviewTurnIndex()+1)+"/"+(game.getTurnIndex()+1));
        }
        else{
            gameFrame.setTurnLabelValue("Tour "+Integer.toString(game.getTurnIndex()+1));
        }
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

        if(game.isEnded()) return;

        if(game.isReviewMode()) return;

        if(game.isIaPaused()){
            game.setIaPause(false);
        }
        else{
            game.setIaPause(true);
        }
        updateBttnPauseIa();

        if((game.isAttackerTurn() && game.isAttackerAI()) || (!game.isAttackerTurn() && game.isDefenderAI())){
            AnimationMove anim = gameFrame.getGridPanelInstance().getAnimationMove();
            if(gameFrame.isAnimationMoveTerminated() && !game.anIaThinking())
                game.doAiTurnInSeparateThread();
        }
    }

    public void updateBttnPauseIa(){
        if(!game.isIaPaused()){
            gameFrame.setBttnIaPauseText("Stopper IAs");
        }
        else{
            gameFrame.setBttnIaPauseText("Relancer IAs");
        }
    }

    public void bttnPreviousTurnClickHandler(){
        if(!game.canUndo()) return;
        if(!gameFrame.isAnimationMoveTerminated()) return;
        if(game.isAiTurn() && !game.isIaPaused()){
            game.setIaPause(true);
            updateBttnPauseIa();
            return;
        }

        game.setPreviewMode(true);
        game.undo(false);

        updateTurnLabel();
    }

    public void bttnReviewModeClicked(){

    }

    public void bttnNextTurnClickHandler(){
        if(!game.canRedo()) return;
        if(game.getReviewTurnIndex() >= game.getTurnIndex()) return;
        if(game.isAiTurn() && !game.isIaPaused()){
            game.setIaPause(true);
            updateBttnPauseIa();
            return;
        }
        if(!gameFrame.isAnimationMoveTerminated()) return;

        game.setPreviewMode(true);
        game.redo(false);

        updateTurnLabel();
    }

    public void updateViewAfterLoad(){
        updateTurnLabel();
    }

    public void bttnHintClickHandler(){
        if(game.isAiTurn()) return;
        if(game.anIaThinking()) return;
        if(game.isReviewMode()) return;
        if(game.isEnded()) return;
        processHintInSeparateThread();
    }

    public void processHintInSeparateThread(){
        threadAiHint = new Thread(new Runnable() {
            @Override
            public void run() {
                Coup coup = game.getHint();
                GridPanel gridPanel = gameFrame.getGridPanelInstance();
                gridPanel.getGridPanelController().treatSelection(coup.getInit());
                gridPanel.setHintMarkCoords(coup.getDest());
            }
        });
        threadAiHint.start();
    }

    public void stopHintProcess(){
        if(threadAiHint != null) {
            threadAiHint.stop();
        }
    }
}

