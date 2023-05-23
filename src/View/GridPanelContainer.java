package View;

import Model.Grid;

import java.awt.*;

public class GridPanelContainer extends RoundPanel {

    GridPanel gridPanel;

    public GridPanelContainer(GridPanel gridPanel, int roundValue){
        super(roundValue);
        this.gridPanel = gridPanel;
        this.setBackground(Color.BLACK);
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);

        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 15));

        int xN = 9;
        int yI = gridPanel.getCaseSize()/2 + 35;
        for(int i = 0; i < GridPanel.GRID_SIZE; i++){
            int y = yI + i*gridPanel.getCaseSize();
            g.drawString(Integer.toString(9-i), xN, y);
        }

        String letters = "ABCDEFGHIJK";
        int xI = gridPanel.getCaseSize()/2 + 28;
        int y = getHeight()-8;
        for(int i = 0; i < GridPanel.GRID_SIZE; i++){
            int xL = xI + i*gridPanel.getCaseSize();
            g.drawString(Character.toString(letters.charAt(i)), xL, y);
        }
    }
}
