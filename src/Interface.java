
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

enum InterfacePage {
    INTRO,
    MENU,
    GAME
}

public class Interface {

    JFrame window;
    Timer refreshTimer;
    GameFrame gameFrame;
    MenuFrame menuFrame;
    InterfacePage page;

    public Interface(){

        page = InterfacePage.MENU;

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                build();
            }
        });
    }

    public void build(){
        window = new JFrame("Tablut");

        window.setLocationRelativeTo(null);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setVisible(true);

        window.addMouseListener(new AdapterMouse(this));
        window.addKeyListener(new AdapterKeyboard(this));

        menuFrame = new MenuFrame(this);    //JComponent on which we will draw the menu
        menuFrame.build();

        gameFrame = new GameFrame(this);    //JComponent on which we will draw the game

        changePage(page);
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

    public JFrame getWindow(){
        return window;
    }

    public void changePage(InterfacePage newPage){
        //window.removeAll();
        if(newPage == InterfacePage.MENU){
            menuFrame.adaptWindow();
            window.add(menuFrame);
        }
        else if(newPage == InterfacePage.GAME){
            gameFrame.adaptWindow();
            window.add(gameFrame);
        }
        page = newPage;
    }
}
