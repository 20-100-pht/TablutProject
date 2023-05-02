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
    Image imageCase;
    Image imageDefender;
    Image imageAttacker;
    Vector<Coordinate> possibleMoveMarks;
    public static final int GRID_SIZE = 9;
    public GridPanel(GameFrame gameFrame){
        super();
        this.gameFrame = gameFrame;

        possibleMoveMarks = new Vector<Coordinate>();

        loadAssets();
        setEventsHandlers();
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);

        Grid grid = gameFrame.GetGameInstance().getGridInstance();
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

    public void loadAssets(){
        try{
            imageCase = ImageIO.read(new File("assets/case2.jpg"));
            imageDefender = ImageIO.read(new File("assets/defender.png"));
            imageAttacker = ImageIO.read(new File("assets/attacker.png"));
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

                mouseMovedHandler(e);
            }
        });

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);

            }
        });
    }

    Piece getPieceHovered(int mouseX, int mouseY){
        int caseX = (int) (mouseX / getCaseSize());
        int caseY = (int) (mouseY / getCaseSize());
        Coordinate caseCoordinates = new Coordinate(caseY, caseX);

        return gameFrame.GetGameInstance().getGridInstance().getPieceAtPosition(caseCoordinates);
    }

    void processPossibleMoveMarks(Piece p){
        possibleMoveMarks.clear();
        addPossibleMoveMarksTop(p.getCol(), p.getRow()-1);
        addPossibleMoveMarksBottom(p.getCol(), p.getRow()+1);
        addPossibleMoveMarksLeft(p.getCol()-1, p.getRow());
        addPossibleMoveMarksRight(p.getCol()+1, p.getRow());
    }

    void addPossibleMoveMarksTop(int x, int y){
        Grid grid = gameFrame.GetGameInstance().getGridInstance();
        if(y < 0 || grid.getPieceAtPosition(new Coordinate(y, x)) != null){
            return;
        }
        possibleMoveMarks.add(new Coordinate(y, x));
        addPossibleMoveMarksTop(x, y-1);
    }

    void addPossibleMoveMarksBottom(int x, int y){
        Grid grid = gameFrame.GetGameInstance().getGridInstance();
        if(y >= GRID_SIZE || grid.getPieceAtPosition(new Coordinate(y, x)) != null){
            return;
        }
        possibleMoveMarks.add(new Coordinate(y, x));
        addPossibleMoveMarksBottom(x, y+1);
    }

    void addPossibleMoveMarksLeft(int x, int y){
        Grid grid = gameFrame.GetGameInstance().getGridInstance();
        if(x < 0 || grid.getPieceAtPosition(new Coordinate(y, x)) != null){
            return;
        }
        possibleMoveMarks.add(new Coordinate(y, x));
        addPossibleMoveMarksLeft(x-1, y);
    }
    void addPossibleMoveMarksRight(int x, int y){
        Grid grid = gameFrame.GetGameInstance().getGridInstance();
        if(x >= GRID_SIZE || grid.getPieceAtPosition(new Coordinate(y, x)) != null){
            return;
        }
        possibleMoveMarks.add(new Coordinate(y, x));
        addPossibleMoveMarksRight(x+1, y);
    }

    void drawAccessibleMark(Graphics g, int caseX, int caseY){
        int x = (int) (caseX*getCaseSize() + getCaseSize()*0.3);
        int y = (int) (caseY*getCaseSize() + getCaseSize()*0.3);
        int markSize = (int) (getCaseSize()*0.3);
        g.setColor(Color.green);
        g.fillOval(x, y, markSize, markSize);
    }

    void mouseMovedHandler(MouseEvent e){
        Piece hoveredPiece = getPieceHovered(e.getX(), e.getY());
        if(hoveredPiece != null){
            processPossibleMoveMarks(hoveredPiece);
        }
    }
}
