
import Controller.GameConsoleController;
import Global.Configuration;
import Model.Game;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class LoadGameTest {

    public static void main(String[] args) {

        File file = new File(Configuration.getFileLoadName());

        try {
            FileInputStream is = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(is);

            Game game = (Game) ois.readObject();
            ois.close();

            Configuration.setCoupHumanByList(true);

            GameConsoleController gcc = new GameConsoleController(game);
            game.setGameControllerInstance(gcc);
            gcc.setPrintTerminal(true);
            gcc.playGame();


        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}

