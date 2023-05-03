package View;


import Controller.GameGraphicController;

import javax.swing.*;
import java.awt.*;
import java.nio.file.DirectoryNotEmptyException;

public class MessageWinPanel extends JPanel {

    GameFrame gameFrame;
    GameGraphicController gameGraphicController;
    String winnerName;
    public MessageWinPanel() {
        setPreferredSize(new Dimension(200, 100));
        setOpaque(false);
        super.setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawString("fdqdqdqsd", 20, 20);
    }

    public void setWinnerName(String winnerName){
        this.winnerName = winnerName;
    }
}
