package View;

import Animation.AnimationMove;
import Controller.GameGraphicController;
import Controller.GridPanelController;
import Model.*;
import Structure.Coordinate;
import View.GameFrame;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.File;
import java.io.IOException;
import java.util.Vector;

public class GridPanel extends JPanel {

    GameFrame gameFrame;
    GridPanelController gridPanelController;
    GameGraphicController gameGraphicController;
    Game game;
    Grid grid;
    LogicGrid gameLogic;
    Image imageCase;
    Image imageDefender;
    Image imageAttacker;
    Image imageKing;
    Image imageTrone;
    Image imageFortress;
    Vector<Coordinate> possibleMoveMarks;
    Coordinate selectionMarkCoords;
    Coordinate moveMarksCoords;
    boolean frozen;
    Coordinate pieceHidedCoords = null;
    AnimationMove animationMove;

    public static final int GRID_SIZE = 9;

    public GridPanel(GameFrame gameFrame){
        super();
        this.gameFrame = gameFrame;
        gameGraphicController = gameFrame.getGraphicGameController();
        game = gameGraphicController.getGameInstance();
        grid = game.getGridInstance();
        gameLogic = game.getLogicGridInstance();
        this.gridPanelController = new GridPanelController(this, gameGraphicController.getGameInstance().getLogicGrid(), gameGraphicController);

        possibleMoveMarks = new Vector<Coordinate>();

        frozen = false;

        loadAssets();
        setEventsHandlers();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (int l = 0; l < GRID_SIZE; l++) {
            for (int c = 0; c < GRID_SIZE; c++) {
                g.drawImage(imageCase, c * getCaseSize(), l * getCaseSize(), getCaseSize(), getCaseSize(), null);

                int pieceX = (int) (c * getCaseSize() + getCaseSize() * 0.15);
                int pieceY = (int) (l * getCaseSize() + getCaseSize() * 0.15);
                int pieceSize = (int) (getCaseSize() * 0.7);

                if (grid.isCornerPosition(new Coordinate(l, c))) {
                    g.drawImage(imageFortress, pieceX, pieceY, pieceSize, pieceSize, null);
                }
                if(l == 4 && c == 4){
                    //g.drawImage(imageTrone, pieceX, pieceY, pieceSize, pieceSize, null);
                }

                Piece piece = grid.getPieceAtPosition(new Coordinate(l, c));
                if (piece == null) {
                    continue;
                }
                if(pieceHidedCoords != null && c == pieceHidedCoords.getCol() && l == pieceHidedCoords.getRow()) continue;

                Image pieceImage = getPieceImage(piece.getType());
                g.drawImage(pieceImage, pieceX, pieceY, pieceSize, pieceSize, null);

                if(animationMove != null){
                    Image imagePieceAnim = getPieceImage(animationMove.getPieceType());
                    g.drawImage(imagePieceAnim, (int) (animationMove.getX()+getCaseSize() * 0.15), (int) (animationMove.getY()+getCaseSize() * 0.15), pieceSize, pieceSize, null);
                }
            }
        }

        /*for (int l = 0; l <= GRID_SIZE; l++) {
            g.drawLine(0, l * getCaseSize(), GRID_SIZE * getCaseSize(), l * getCaseSize());
        }

        for (int c = 0; c <= GRID_SIZE; c++) {
            g.drawLine(c * getCaseSize(), 0, c * getCaseSize(), GRID_SIZE * getCaseSize());
        }*/

        if (!gameLogic.isEndGame()){
            for (int i = 0; i < possibleMoveMarks.size(); i++) {
                Coordinate piecePos = possibleMoveMarks.get(i);
                drawAccessibleMark(g, piecePos.getCol(), piecePos.getRow());
            }
        }

        // Case selected mark
        if(selectionMarkCoords != null && !gameLogic.isEndGame()) {
            int markX = (int) (selectionMarkCoords.getCol() * getCaseSize() + getCaseSize() * 0.1);
            int markY = (int) (selectionMarkCoords.getRow() * getCaseSize() + getCaseSize() * 0.1);

            g.setColor(Color.red);
            g.drawRect(markX, markY, (int) (getCaseSize() * 0.8), (int) (getCaseSize() * 0.8));
        }
    }

    void drawAccessibleMark(Graphics g, int caseX, int caseY){
        int caseSize = getCaseSize();
        int x = (int) (caseX*caseSize + caseSize*0.35);
        int y = (int) (caseY*caseSize + caseSize*0.35);
        int markSize = (int) (caseSize*0.3);
        g.setColor(Color.green);
        g.fillOval(x, y, markSize, markSize);
    }

    public void loadAssets(){
        try{
            imageCase = ImageIO.read(new File("assets/images/case2.jpg"));
            imageDefender = ImageIO.read(new File("assets/images/defender.png"));
            imageAttacker = ImageIO.read(new File("assets/images/attacker.png"));
            imageKing = ImageIO.read(new File("assets/images/king.png"));
            imageFortress = ImageIO.read(new File("assets/images/fortress.png"));
            imageTrone = ImageIO.read(new File("assets/images/trone.png"));
        } catch(IOException exp){
            exp.printStackTrace();
        }
    }

    public int getCaseSize(){
        return this.getWidth()/GRID_SIZE;
    }

    //@Override
    public Dimension getPreferredSize(){
        int i = Math.min((int) ((gameFrame.getWidth()*0.5) / 2), (int) ((gameFrame.getHeight()*0.7) / 2));
        return new Dimension(i*2, i*2);
    }

    void setEventsHandlers(){
        this.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                super.mouseMoved(e);
                if(frozen) return;
                gridPanelController.mouseMovedHandler(e);
            }
        });

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                possibleMoveMarks.clear();
            }

            @Override
            public void mouseReleased(MouseEvent e){
                super.mouseClicked(e);
                if(frozen) return;
                gridPanelController.mouseReleasedHandler(e);
            }
        });
    }

    public void addMovePossibleMark(Coordinate c){
        possibleMoveMarks.add(c);
    }

    public void clearMovePossibleMarks(){
        possibleMoveMarks.clear();
    }

    public void setSelectionMarkCoords(Coordinate coords){
        selectionMarkCoords = coords;
    }

    public void setFrozen(boolean frozen){
        this.frozen = frozen;
    }

    public void setPieceHidedCoords(Coordinate pieceHidedCoords){
        this.pieceHidedCoords = pieceHidedCoords;
    }

    public void setAnimationMove(AnimationMove animation){
        animationMove = animation;
    }

    public boolean anAnimationNotTerminated(){
        return animationMove != null && !animationMove.isTerminated();
    }

    public Image getPieceImage(PieceType type){
        if (type == PieceType.DEFENDER) {
            return imageDefender;
        } else if (type == PieceType.KING) {
            return imageKing;
        } else if (type == PieceType.ATTACKER) {
            return imageAttacker;
        }
        return null;
    }
}
