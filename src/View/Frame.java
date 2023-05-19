package View;

import Animation.Animation;
import Structure.Position;
import Global.GraphicUtils;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.File;
import java.io.IOException;
import java.util.Vector;

public abstract class Frame extends JComponent {

    Interface ui;
    Image selectorImage;
    boolean selectorDisplayed;
    Position selectorPos;
    Vector<Animation> animations;
    public Frame(Interface ui){
        this.ui = ui;

        selectorDisplayed = false;
        animations = new Vector<Animation>();

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

    public void display(){

    }

    public void updateAnimations(int timeElapsed){
        for(int i = 0; i < animations.size(); i++){
            Animation animation = animations.get(i);
            if(!animation.isTerminated()) {
                if(!animation.isPaused()) {
                    animation.update(timeElapsed);
                }
            }
            else{
                animations.remove(i);
            }
        }
    }

    public void addAnimation(Animation animation){
        animations.add(animation);
    }

    public void updateTheme(){

    }
}
