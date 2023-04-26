import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

public class Button extends JButton {

    final int SELECTOR_SIZE = 16;
    boolean selector;
    boolean hovered = false;
    Interface ui;

    public Button(String name) {
        this(name, false, null);
    }
    public Button(String name, boolean selector, Interface ui){
        super(name);
        this.selector = selector;
        this.ui = ui;

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

    }

    public void mouseEnteredHandler(MouseEvent e){
        if(!hovered){
            ui.menuFrame.setVisibleSelector(true);

            int x = this.getX() - 50;
            int y = this.getY() + this.getHeight()/2 - SELECTOR_SIZE/2;

            ui.menuFrame.setSelectorPos(new Position(x, y));
        }
        hovered = true;
    }

    public void mouseExitedhandler(MouseEvent e){
        if(hovered){
            //ui.menuFrame.setVisibleSelector(false);
        }
        hovered = false;
    }
}
