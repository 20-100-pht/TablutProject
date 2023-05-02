import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class GridPanel extends JPanel {

    GameFrame gameFrame;
    Image imageCase;
    Image imageDefender;
    Image imageAttacker;
    public static final int GRID_SIZE = 9;
    public GridPanel(GameFrame gameFrame){
        super();
        this.gameFrame = gameFrame;
        loadAssets();
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);

        Grid grid = gameFrame.GetGameInstance().getGridInstance();
        for(int l = 0; l < GRID_SIZE; l++){
            for(int c = 0; c < GRID_SIZE; c++){
                g.drawImage(imageCase, c*getCaseSize(), l*getCaseSize(), getCaseSize(), getCaseSize(), null);

                int pieceX = (int) c*getCaseSize() + getCaseSize()/4;
                int pieceY = (int) l*getCaseSize() + getCaseSize()/4;

                Piece piece = grid.getPieceAtPosition(new Coordinate(l, c));
                if(piece == null){
                    continue;
                }
                if(piece.isDefender()) {
                    g.drawImage(imageDefender, pieceX, pieceY, getCaseSize()/2, getCaseSize()/2, null);
                }
                else if(piece.isAttacker()){
                    g.drawImage(imageAttacker, pieceX, pieceY, getCaseSize()/2, getCaseSize()/2, null);
                }
            }
        }

        for(int l = 0; l <= GRID_SIZE; l++) {
            g.drawLine(0, l*getCaseSize(), GRID_SIZE*getCaseSize(), l*getCaseSize());
        }

        for(int c = 0; c <= GRID_SIZE; c++){
            g.drawLine(c*getCaseSize(), 0, c*getCaseSize(), GRID_SIZE*getCaseSize());
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
}
