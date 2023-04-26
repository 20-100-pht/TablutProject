import javax.swing.*;
import java.awt.*;

public class MenuFrame extends JComponent {

    Interface ui;
    Menu menu;
    public void MenuFrame(Interface ui){
        this.ui = ui;
        menu = new Menu();
    }

    @Override
    protected void paintComponent(Graphics g){
        g.drawString("Dessinez ici l'interface du jeu", 200, 200);
    }
}
