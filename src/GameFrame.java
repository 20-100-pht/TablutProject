import javax.swing.*;
import java.awt.*;

public class GameFrame extends JComponent {

    Interface ui;
    Game game;
    public void GameFrame(Interface ui){

        this.ui = ui;
        game = new Game();
        game.grid.print();
    }

    @Override
    protected void paintComponent(Graphics g){
        g.drawString("Dessinez ici l'interface du jeu", 200, 200);
    }
}
