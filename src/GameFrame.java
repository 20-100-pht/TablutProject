import javax.swing.*;
import java.awt.*;

public class GameFrame extends Frame {

    Game game;
    public GameFrame(Interface ui){
        super(ui);

        //game.grid.print();
    }

    @Override
    public void adaptWindow(){
        JFrame window = ui.getWindow();

        Dimension sizeScreen = Toolkit.getDefaultToolkit().getScreenSize();
        int height = (int) (sizeScreen.height * 0.8);
        int width = (int) (sizeScreen.width * 0.8);
        window.setSize(width, height);
        window.setLocationRelativeTo(null);
    }

    @Override
    protected void paintComponent(Graphics g){
        g.drawString("Dessinez ici l'interface du jeu", 200, 200);
    }

    public void setGameInstance(Game game){
        this.game = game;
    }
}
