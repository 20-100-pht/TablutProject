package View;

import Model.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Interface {

    JFrame window;
    Timer refreshTimer;
    GameFrame gameFrame;
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
        BorderLayout layout = new BorderLayout();
        window.setLayout(layout);

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
        refreshTimer = new Timer(200, refreshPerformer);
        refreshTimer.start();
    }

    public void refresh(){
        frame.updateFrame();
        frame.updateUI();
        frame.revalidate();
        frame.repaint();
    }

    public JFrame getWindow(){
        return window;
    }

    public void changePage(InterfacePage newPage){
        if(frame != null){
            System.out.println(frame.getComponentCount());
            window.remove(frame);
            //window.removeAll();
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
        page = newPage;
        frame.adaptWindow();
        frame.build();
        window.add(frame);
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
}
