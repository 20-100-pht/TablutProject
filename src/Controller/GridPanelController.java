package Controller;

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
    Coordinate pieceSelectedCoords;

    public GridPanelController(GridPanel gridPanel, GameRules logicGrid){
        this.gridPanel = gridPanel;
        this.logicGrid = logicGrid;
        grid = logicGrid.getGrid();
    }

    Coordinate getCaseFromPixelPosition(int mouseX, int mouseY){
        int caseX = (int) (mouseX / gridPanel.getCaseSize());
        int caseY = (int) (mouseY / gridPanel.getCaseSize());
        return new Coordinate(caseY, caseY);
    }

    Piece getPieceHovered(int mouseX, int mouseY){
        return grid.getPieceAtPosition(getCaseFromPixelPosition(mouseX, mouseY));
    }

    void processPossibleMoveMarks(Piece p){
        gridPanel.clearMovePossibleMarks();
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

    public void mouseClickedHandler(MouseEvent e){

        System.out.println("dqsd");

        if(pieceSelectedCoords != null){
            Coordinate newCoords = getCaseFromPixelPosition(e.getX(), e.getY());
            Coup coup = new Coup(pieceSelectedCoords, newCoords);
            logicGrid.move(coup);
            pieceSelectedCoords = null;
        }
        else {
            pieceSelectedCoords = getCaseFromPixelPosition(e.getX(), e.getY());
        }
    }
}
