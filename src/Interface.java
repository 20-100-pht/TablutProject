
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Interface {

    JFrame window;
    Timer refreshTimer;
    GameFrame gameFrame;
    MenuFrame menuFrame;

    public Interface(){

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                build();
            }
        });

    }

    public void build(){
        window = new JFrame("Tablut");

        Dimension sizeScreen = Toolkit.getDefaultToolkit().getScreenSize();
        int height = (int) (sizeScreen.height * 0.6);
        int width = (int) (sizeScreen.width * 0.6);
        window.setSize(width, height);

        window.setLocationRelativeTo(null);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setVisible(true);

        window.addMouseListener(new AdapterMouse(this));
        window.addKeyListener(new AdapterKeyboard(this));

        menuFrame = new MenuFrame();    //JComponent on which we will draw the menu
        gameFrame = new GameFrame();    //JComponent on which we will draw the game

        window.add(gameFrame);
    }

    private void startRefreshLoop(){

        refresh();
        ActionListener refreshPerformer = new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                refresh();
            }
        };
        refreshTimer = new Timer(200, refreshPerformer);
        refreshTimer.start();
    }

    public void refresh(){
        window.revalidate();
        window.repaint();
    }
}
