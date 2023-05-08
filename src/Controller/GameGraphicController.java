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

        if(gameRules.isLegalMove(coup) != 0){
            return;
        }
        gameRules.move(coup);

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

        if(isAiTurn()) doAiTurnInSeparateThread();
    }

    public void bttnReplayClickHandler(){
        gameFrame.hideAllMessages();
        gameFrame.hideEndGameButtons();
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
        History history = game.getHistoryInstance();
        if(history.canUndo()){
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
        gameRules.move(coup);
        gameRules.attack(piece);
        game.setIsAttackerTurn(!move.isAttackerMove());
    }

    public boolean isAiTurn(){
        return game.isAttackerTurn() && game.isAttackerAI() || !game.isAttackerTurn() && game.isDefenderAI();
    }

    public void doAiTurnInSeparateThread(){
        Thread threadAI = new Thread(new Runnable() {
            @Override
            public void run() {
                doAiTurn();
            }
        });
        threadAI.start();
    }

    public void doAiTurn(){
        AI ai;
        PieceType t;
        if(game.isAttackerTurn()){
            ai = game.getAttackerAI();
            t = PieceType.ATTACKER;
        }
        else{
            ai = game.getDefenderAI();
            t = PieceType.DEFENDER;
        }
        long start = System.currentTimeMillis();
        Coup coupAI = ai.playMove(gameRules, 3, t);
        long end = System.currentTimeMillis();

        System.out.println(end-start);

        long timeToWait = 1500-(end-start);
        //On évite d'enchainer les coups de l'IA trop vite
        try {
            if(timeToWait > 0) {
                Thread.sleep(timeToWait);
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        play(coupAI);
    }

    public void startGame(){
        if(isAiTurn()) doAiTurnInSeparateThread();
    }
}
