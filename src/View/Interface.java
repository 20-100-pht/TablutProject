package View;

import Model.Game;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.File;
import java.io.IOException;

public class Interface {

    JFrame window;
    Timer refreshTimer;
    GameFrame gameFrame;
    InterfacePage page;
    Frame frame;
    Image imageKing;

    public Interface(){

        page = InterfacePage.MENU;

        loadAssets();

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
        BorderLayout layout = new BorderLayout();
        window.setLayout(layout);

        window.setIconImage(imageKing);
        window.setLocationRelativeTo(null);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        changePage(page);
        window.setVisible(true);
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
        refreshTimer = new Timer(40, refreshPerformer);
        refreshTimer.start();
    }

    public void refresh(){
        frame.updateAnimations(40); //A modifier
        //window.updateUI();
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
            frame = new MenuFrame(this);
        }
        else if(newPage == InterfacePage.GAME){
            frame = gameFrame;
        }
        else if(newPage == InterfacePage.NEWGAME){
            frame = new NewGameFrame(this);
        }
        else if(newPage == InterfacePage.STATS){
            frame = new StatsFrame(this);
        }

        page = newPage;
        frame.adaptWindow();
        frame.build();
        window.add(frame);
        frame.display();
    }

    public GameFrame getGameFrame(){
        return gameFrame;
    }

    Point getMouseLocation(){
        PointerInfo mouseInfo = MouseInfo.getPointerInfo();
        Point mouseLocation = mouseInfo.getLocation();
        return new Point((int) (mouseLocation.getX()-window.getX()), (int) (mouseLocation.getY()-window.getY()));
    }

    public void createGameFrame(Game game){
        gameFrame = new GameFrame(this, game);
    }

    public void loadAssets(){
        try{
            imageKing = ImageIO.read(new File("assets/images/king1.png"));
        } catch(IOException exp){
            exp.printStackTrace();
        }
    }
}
