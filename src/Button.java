import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

public class Button extends JButton {

    boolean selector;
    boolean hovered = false;
    Image selectorImage;

    public Button(String name) {
        this(name, false);
    }
    public Button(String name, boolean selector){
        super(name);
        this.selector = selector;

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

        try{
            ImageIO.read(new File("assets/arrow.png"));
        } catch(IOException exp){
            exp.printStackTrace();
        }
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);

        //System.out.println(hovered);

        if(hovered){
            g.drawImage(selectorImage, 50/*-getWidth()/2-40*/, 50/*+getHeight()/2*/, null);
        }
    }

    public void mouseEnteredHandler(MouseEvent e){
        hovered = true;
    }

    public void mouseExitedhandler(MouseEvent e){
        hovered = false;
    }
}
