package Controller;

import Animation.AnimationMove;
import Global.Configuration;
import Model.*;
import Structure.Coordinate;
import Structure.Coup;
import View.GridPanel;

import java.awt.*;
import java.awt.event.MouseEvent;

public class GridPanelController {
    Grid grid;
    GridPanel gridPanel;
    LogicGrid logicGrid;
    GameGraphicController gameGraphicController;
    Game game;
    Coordinate pieceSelectedCoords;

    public GridPanelController(GridPanel gridPanel, LogicGrid logicGrid, GameGraphicController gameGraphicController){
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

        addPossibleMoveMarksTop(p.getCol(), p.getRow()-1, p.getType());
        addPossibleMoveMarksBottom(p.getCol(), p.getRow()+1, p.getType());
        addPossibleMoveMarksLeft(p.getCol()-1, p.getRow(), p.getType());
        addPossibleMoveMarksRight(p.getCol()+1, p.getRow(), p.getType());
    }

    void addPossibleMoveMarksTop(int x, int y, PieceType t){
        if(y < 0 || grid.getPieceAtPosition(new Coordinate(y, x)) != null) return;
        if(t != PieceType.KING && grid.isCornerPosition(new Coordinate(y, x))) return;
        if(!grid.isCastle(new Coordinate(y,x))) gridPanel.addMovePossibleMark(new Coordinate(y, x));
        addPossibleMoveMarksTop(x, y-1, t);
    }

    void addPossibleMoveMarksBottom(int x, int y, PieceType t){
        if(y >= GridPanel.GRID_SIZE || grid.getPieceAtPosition(new Coordinate(y, x)) != null) return;
        if(t != PieceType.KING && grid.isCornerPosition(new Coordinate(y, x))) return;
        if(!grid.isCastle(new Coordinate(y,x))) gridPanel.addMovePossibleMark(new Coordinate(y, x));
        addPossibleMoveMarksBottom(x, y+1, t);
    }

    void addPossibleMoveMarksLeft(int x, int y, PieceType t){
        if(x < 0 || grid.getPieceAtPosition(new Coordinate(y, x)) != null) return;
        if(t != PieceType.KING && grid.isCornerPosition(new Coordinate(y, x))) return;
        if(!grid.isCastle(new Coordinate(y,x))) gridPanel.addMovePossibleMark(new Coordinate(y, x));
        addPossibleMoveMarksLeft(x-1, y, t);
    }
    void addPossibleMoveMarksRight(int x, int y, PieceType t){
        if(x >= GridPanel.GRID_SIZE || grid.getPieceAtPosition(new Coordinate(y, x)) != null) return;
        if(t != PieceType.KING && grid.isCornerPosition(new Coordinate(y, x))) return;
        if(!grid.isCastle(new Coordinate(y,x))) gridPanel.addMovePossibleMark(new Coordinate(y, x));
        addPossibleMoveMarksRight(x+1, y, t);
    }

    public void mouseMovedHandler(MouseEvent e){
        if(game.isAiTurn()) {
            gridPanel.clearMovePossibleMarks();
            return;
        }

        if(pieceSelectedCoords == null) {
            Piece hoveredPiece = getPieceHovered(e.getX(), e.getY());
            if (hoveredPiece != null) {
                processPossibleMoveMarks(hoveredPiece);
            } else {
                gridPanel.clearMovePossibleMarks();
            }
        }
    }

    public void mouseReleasedHandler(MouseEvent e){

        if(e.getButton() == MouseEvent.BUTTON1){
            mouseLeftBttnReleasedHandler(e);
        }
        else if(e.getButton() == MouseEvent.BUTTON3){
            mouseRightBttnReleasedHandler(e);
        }
    }

    public void treatSelection(Coordinate caseCoords){
        if(game.isReviewMode()) return;
        if(game.isEnded()) return;
        if(!game.isStartTimerEnded()) return;

        Piece pieceClicked = grid.getPieceAtPosition(caseCoords);

        if(gridPanel.anAnimationNotTerminated()) return;

        //Si il y a une pièce sur la case d'arrivée inutile d'essayer de bouger
        if(pieceSelectedCoords != null && pieceClicked == null){

            //On récupère le coup choisis par le joueur et si est autorisé on l'exécute après l'animation
            Coup coup = new Coup(pieceSelectedCoords, caseCoords);
            if(logicGrid.isLegalMove(coup) != 0){
                resetSelection();
                return;
            }
            //On enlève les indications sur les mouvements possibles avant de bouger
            resetSelection();

            if(Configuration.isAnimationActived()) {
                gameGraphicController.startMoveAnimation(coup, MoveAnimationType.CLASSIC);
            }
            else{
                game.play(coup, MoveAnimationType.CLASSIC);
            }
        }
        //On sélectionne la pièce s'il elle est au joueur qui joue
        else {
            if(pieceClicked != null){
                if((pieceClicked.isAttacker() && game.isAttackerTurn()) || ((pieceClicked.isDefender() || pieceClicked.isKing()) && !game.isAttackerTurn())){
                    gridPanel.setHintMarkCoords(null);
                    pieceSelectedCoords = caseCoords;
                    gridPanel.setSelectionMarkCoords(caseCoords);
                    processPossibleMoveMarks(grid.getPieceAtPosition(pieceSelectedCoords));
                }
                else{
                    resetSelection();
                }
            }
        }
    }

    public void mouseLeftBttnReleasedHandler(MouseEvent e){
        Coordinate caseCoords = getCaseFromPixelPosition(e.getX(), e.getY());
        treatSelection(caseCoords);
    }

    public void mouseRightBttnReleasedHandler(MouseEvent e){
        resetSelection();
    }

    public void resetSelection(){
        pieceSelectedCoords = null;
        gridPanel.setSelectionMarkCoords(null);
        gridPanel.clearMovePossibleMarks();
        gridPanel.setHintMarkCoords(null);
    }

    public void updateViewAfterReplay(){
        gridPanel.setMoveMarkCoords(null);
    }
}
