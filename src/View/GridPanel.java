package View;

import Controller.GridPanelController;
import Model.Grid;
import Model.Piece;
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
    Grid grid;
    Image imageCase;
    Image imageDefender;
    Image imageAttacker;
    Vector<Coordinate> possibleMoveMarks;
    Coordinate selectionMarkCoords;
    public static final int GRID_SIZE = 9;
    public GridPanel(GameFrame gameFrame){
        super();
        this.gameFrame = gameFrame;
        grid = gameFrame.GetGameInstance().getGridInstance();
        this.gridPanelController = new GridPanelController(this, gameFrame.GetGameInstance().getLogicGrid());

        possibleMoveMarks = new Vector<Coordinate>();

        loadAssets();
        setEventsHandlers();
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);

        for(int l = 0; l < GRID_SIZE; l++){
            for(int c = 0; c < GRID_SIZE; c++){
                g.drawImage(imageCase, c*getCaseSize(), l*getCaseSize(), getCaseSize(), getCaseSize(), null);

                int pieceX = (int) (c*getCaseSize() + getCaseSize()*0.15);
                int pieceY = (int) (l*getCaseSize() + getCaseSize()*0.15);
                int pieceSize = (int) (getCaseSize()*0.7);

                Piece piece = grid.getPieceAtPosition(new Coordinate(l, c));
                if(piece == null){
                    continue;
                }
                if(piece.isDefender()) {
                    g.drawImage(imageDefender, pieceX, pieceY, pieceSize, pieceSize, null);
                }
                else if(piece.isAttacker()){
                    g.drawImage(imageAttacker, pieceX, pieceY, pieceSize, pieceSize, null);
                }
            }
        }

        for(int l = 0; l <= GRID_SIZE; l++) {
            g.drawLine(0, l*getCaseSize(), GRID_SIZE*getCaseSize(), l*getCaseSize());
        }

        for(int c = 0; c <= GRID_SIZE; c++){
            g.drawLine(c*getCaseSize(), 0, c*getCaseSize(), GRID_SIZE*getCaseSize());
        }

        for(int i = 0; i < possibleMoveMarks.size(); i++){
            Coordinate piecePos = possibleMoveMarks.get(i);
            System.out.println(piecePos.getRow() + "---"+piecePos.getCol());
            drawAccessibleMark(g, piecePos.getCol(), piecePos.getRow());
        }
    }

    void drawAccessibleMark(Graphics g, int caseX, int caseY){
        int caseSize = getCaseSize();
        int x = (int) (caseX*caseSize + caseSize*0.3);
        int y = (int) (caseY*caseSize + caseSize*0.3);
        int markSize = (int) (caseSize*0.3);
        g.setColor(Color.green);
        g.fillOval(x, y, markSize, markSize);
    }

    public void loadAssets(){
        try{
            imageCase = ImageIO.read(new File("assets/images/case2.jpg"));
            imageDefender = ImageIO.read(new File("assets/images/defender.png"));
            imageAttacker = ImageIO.read(new File("assets/images/attacker.png"));
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
            public void mouseClicked(MouseEvent e){
                super.mouseClicked(e);
                gridPanelController.mouseClickedHandler(e);
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
}
