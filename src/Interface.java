
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

enum InterfacePage {
    INTRO,
    MENU,
    NEWGAME,
    GAME
}

public class Interface {

    JFrame window;
    Timer refreshTimer;
    GameFrame gameFrame;
    MenuFrame menuFrame;
    NewGameFrame newGameFrame;
    InterfacePage page;
    Frame frame;

    public Interface(){

        page = InterfacePage.MENU;

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                build();
                startRefreshLoop();
            }
        });
    }

    public void build(){
        window = new JFrame("Tablut");

        //setAppLookAndFeel();

        window.setLocationRelativeTo(null);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setVisible(true);

        window.addMouseListener(new AdapterMouse(this));
        window.addKeyListener(new AdapterKeyboard(this));

        menuFrame = new MenuFrame(this);    //JComponent on which we will draw the menu
        menuFrame.build();

        gameFrame = new GameFrame(this);    //JComponent on which we will draw the game

        newGameFrame = new NewGameFrame(this);
        newGameFrame.build();

        changePage(page);
    }

    public void setAppLookAndFeel(){
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (UnsupportedLookAndFeelException e) {
            throw new RuntimeException(e);
        }
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
        if(frame != null){
            window.remove(frame);
        }

        if(newPage == InterfacePage.MENU){
            frame = menuFrame;
        }
        else if(newPage == InterfacePage.GAME){
            frame = gameFrame;
        }
        else if(newPage == InterfacePage.NEWGAME){
            frame = newGameFrame;
        }
        page = newPage;
        window.add(frame);
        frame.adaptWindow();
    }
}
