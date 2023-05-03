package Controller;

import Model.Game;
import Model.GameRules;
import Model.Grid;
import Model.Piece;
import Structure.Coordinate;
import Structure.Coup;
import View.GridPanel;

import java.awt.*;
import java.awt.event.MouseEvent;

public class GridPanelController {
    Grid grid;
    GridPanel gridPanel;
    GameRules logicGrid;
    GameGraphicController gameGraphicController;
    Game game;
    Coordinate pieceSelectedCoords;

    public GridPanelController(GridPanel gridPanel, GameRules logicGrid, GameGraphicController gameGraphicController){
        this.gridPanel = gridPanel;
        this.logicGrid = logicGrid;
        this.gameGraphicController = gameGraphicController;
        grid = logicGrid.getGrid();
        game = gameGraphicController.getGameInstance();
    }

    Coordinate getCaseFromPixelPosition(int mouseX, int mouseY){
        int caseX = (int) (mouseX / gridPanel.getCaseSize());
        int caseY = (int) (mouseY / gridPanel.getCaseSize());
        return new Coordinate(caseY, caseX);
    }

    Piece getPieceHovered(int mouseX, int mouseY){
        return grid.getPieceAtPosition(getCaseFromPixelPosition(mouseX, mouseY));
    }

    void processPossibleMoveMarks(Piece p){
        gridPanel.clearMovePossibleMarks();

        if(p.isAttacker() && !game.isAttackerTurn() || (p.isDefender() || p.isKing()) && game.isAttackerTurn()){
            return;
        }

        addPossibleMoveMarksTop(p.getCol(), p.getRow()-1);
        addPossibleMoveMarksBottom(p.getCol(), p.getRow()+1);
        addPossibleMoveMarksLeft(p.getCol()-1, p.getRow());
        addPossibleMoveMarksRight(p.getCol()+1, p.getRow());
    }

    void addPossibleMoveMarksTop(int x, int y){
        if(y < 0 || grid.getPieceAtPosition(new Coordinate(y, x)) != null){
            return;
        }
        gridPanel.addMovePossibleMark(new Coordinate(y, x));
        addPossibleMoveMarksTop(x, y-1);
    }

    void addPossibleMoveMarksBottom(int x, int y){
        if(y >= GridPanel.GRID_SIZE || grid.getPieceAtPosition(new Coordinate(y, x)) != null){
            return;
        }
        gridPanel.addMovePossibleMark(new Coordinate(y, x));
        addPossibleMoveMarksBottom(x, y+1);
    }

    void addPossibleMoveMarksLeft(int x, int y){
        if(x < 0 || grid.getPieceAtPosition(new Coordinate(y, x)) != null){
            return;
        }
        gridPanel.addMovePossibleMark(new Coordinate(y, x));
        addPossibleMoveMarksLeft(x-1, y);
    }
    void addPossibleMoveMarksRight(int x, int y){
        if(x >= GridPanel.GRID_SIZE || grid.getPieceAtPosition(new Coordinate(y, x)) != null){
            return;
        }
        gridPanel.addMovePossibleMark(new Coordinate(y, x));
        addPossibleMoveMarksRight(x+1, y);
    }

    public void mouseMovedHandler(MouseEvent e){
        Piece hoveredPiece = getPieceHovered(e.getX(), e.getY());
        if(hoveredPiece != null){
            processPossibleMoveMarks(hoveredPiece);
        }
        else{
            gridPanel.clearMovePossibleMarks();
        }
    }

    public void mouseReleasedHandler(MouseEvent e){

        Coordinate caseCoords = getCaseFromPixelPosition(e.getX(), e.getY());
        Piece pieceClicked = grid.getPieceAtPosition(caseCoords);

        //Si il y a une pièce sur la case d'arrivée inutile d'essayer de bouger
        if(pieceSelectedCoords != null && pieceClicked == null){

            Coup coup = new Coup(pieceSelectedCoords, caseCoords);
            gameGraphicController.play(coup);
            pieceSelectedCoords = null;
            gridPanel.setSelectionMarkCoords(null);
        }
        //On sélectionne la pièce s'il elle est au joueur qui joue
        else {
            if(pieceClicked != null){
                if((pieceClicked.isAttacker() && game.isAttackerTurn()) || ((pieceClicked.isDefender() || pieceClicked.isKing()) && !game.isAttackerTurn())){
                    pieceSelectedCoords = caseCoords;
                    gridPanel.setSelectionMarkCoords(caseCoords);
                }
            }
        }
    }
}
