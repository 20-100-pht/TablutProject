package View;

import javax.swing.*;
import java.awt.*;

public class RoundPanel extends JPanel {

    int roundValue;
    Color color;

    public RoundPanel(int roundValue){
        this.roundValue = roundValue;
        setOpaque(false);
        color = Color.white;
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.setColor(color);
        g.fillRoundRect(0, 0, getWidth(), getHeight(), roundValue, roundValue);
    }

    public void setColor(Color color){
        this.color = color;
    }
}
