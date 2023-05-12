package Controller;

import Model.Game;
import View.Interface;
import View.InterfacePage;
import View.MenuFrame;

import java.io.*;

public class MenuController {
    MenuFrame menuFrame;

    public MenuController(MenuFrame menuFrame){
        this.menuFrame = menuFrame;
    }

    public void loadGame(){
        File file = menuFrame.showLoadDialog();
        if(file == null){
            return;
        }
        try {
            FileInputStream is = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(is);

            Game game = (Game) ois.readObject();

            Interface inter = menuFrame.getInterface();
            inter.createGameFrame(game);
            inter.changePage(InterfacePage.GAME);
            inter.getGameFrame().showTextMessage("Partie chargée avec succès !");
            inter.getGameFrame().getGraphicGameController().startGame();

            ois.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
