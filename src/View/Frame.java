package View;

import Structure.Position;
import Global.GraphicUtils;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.File;
import java.io.IOException;

public abstract class Frame extends JComponent {

    Interface ui;
    Image selectorImage;
    boolean selectorDisplayed;
    Position selectorPos;
    public Frame(Interface ui){
        this.ui = ui;

        selectorDisplayed = false;

        try{
            selectorImage = ImageIO.read(new File("assets/images/arrow.png"));
            selectorImage= GraphicUtils.resizeImage(selectorImage, 16, 16);
        } catch(IOException exp){
            exp.printStackTrace();
        }

        setEventHandlers();
    }

    public abstract void build();

    public abstract void adaptWindow();

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);

        if(selectorDisplayed){
            g.drawImage(selectorImage, selectorPos.GetX(), selectorPos.GetY(), null);
        }
    }

    public void setVisibleSelector(boolean isVisible){
        selectorDisplayed = isVisible;
    }

    public void setSelectorPos(Position pos){
        selectorPos = pos;
    }

    private void setEventHandlers(){
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);

                Dimension wSize = ui.getWindow().getSize();
                Dimension wMinSize = ui.getWindow().getMinimumSize();
                if(wSize.width < wMinSize.width){
                    wSize.width = wMinSize.width;
                }
                if(wSize.height < wMinSize.height){
                    wSize.height = wMinSize.height;
                }
                ui.getWindow().setSize(wSize);
            }
        });
    }

    public Interface getInterface(){
        return ui;
    }
}
