import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class GridPanel extends JPanel {

    GameFrame gameFrame;
    Image imageCase;
    int gridCaseSize = 64;
    public static final int GRID_SIZE = 11;
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
                g.drawImage(imageCase, c*gridCaseSize, l*gridCaseSize, gridCaseSize, gridCaseSize, null);
            }
        }

        for(int l = 0; l < GRID_SIZE; l++) {
            g.drawLine(0, l*gridCaseSize, GRID_SIZE*gridCaseSize, l*gridCaseSize);
        }

        for(int c = 0; c < GRID_SIZE; c++){
            g.drawLine(c*gridCaseSize, 0, c*gridCaseSize, GRID_SIZE*gridCaseSize);
        }
    }

    public void loadAssets(){
        try{
            imageCase = ImageIO.read(new File("assets/case2.jpg"));
        } catch(IOException exp){
            exp.printStackTrace();
        }
    }

    public void setCaseSize(int caseSize){
        gridCaseSize = caseSize;
    }

    public int getCaseSize(){
        return gridCaseSize;
    }
}
