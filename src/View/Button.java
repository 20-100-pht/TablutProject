package View;

import Structure.Position;
import View.Frame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Button extends JPanel {

    final int SELECTOR_SIZE = 16;
    boolean selector;
    boolean hovered;
    Frame frame;
    String name;
    Font font;
    int roundValue;
    Color backgroundColor;
    Color textColor;
    Color backgroundColorHov;
    Color textColorHov;

    public Button(String name, boolean selector, Frame frame){
        this.selector = selector;
        this.frame = frame;
        this.name = name;
        hovered = false;
        roundValue = 0;
        backgroundColor = Color.WHITE;
        textColor = Color.BLACK;
        backgroundColorHov = new Color(150,158,255);
        textColorHov = Color.WHITE;

        setCursor(new Cursor(Cursor.HAND_CURSOR));

        font = new Font(Font.DIALOG, Font.BOLD, 23);

        this.setOpaque(false);
        //this.setBackground(Color.blue);

        this.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseEntered(MouseEvent e){
                mouseEnteredHandler(e);
            }
            @Override
            public void mouseExited(MouseEvent e) {
                mouseExitedhandler(e);
            }
        });
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);

        if(hovered) {
            g.setColor(backgroundColorHov);
        }
        else g.setColor(backgroundColor);

        g.fillRoundRect(0, 0, this.getWidth()-1, this.getHeight()-1, roundValue, roundValue);

        if(hovered) g.setColor(textColorHov);
        else g.setColor(textColor);
        g.setFont(font);

        FontMetrics fontMetrics = g.getFontMetrics(font);
        int textWidth = fontMetrics.stringWidth(name);
        int textHeight = fontMetrics.getHeight();
        int textX = getWidth()/2 - textWidth/2;
        int textY = getHeight()/2 + textHeight/4;

        g.drawString(name, textX, textY);
    }


    public void mouseEnteredHandler(MouseEvent e){
        if(!hovered){
            frame.setVisibleSelector(true);

            int x = this.getX() - 50;
            int y = this.getY() + this.getHeight()/2 - SELECTOR_SIZE/2;

            frame.setSelectorPos(new Position(x, y));
        }
        hovered = true;
    }

    public void mouseExitedhandler(MouseEvent e){
        if(hovered){
            //ui.menuFrame.setVisibleSelector(false);
        }
        hovered = false;
    }

    public void setRoundValue(int roundValue){
        this.roundValue = roundValue;
    }
    public void setFont(Font font){
        this.font = font;
    }

    public void setBackgroundColor(Color color){
        backgroundColor = color;
    }
    public void setBackgroundColorHov(Color color){
        backgroundColorHov = color;
    }
    public void setTextColor(Color color){
        textColor = color;
    }
    public void setTextColorHov(Color color){
        textColorHov = color;
    }

    public void setText(String text){
        this.name = text;
    }
}
