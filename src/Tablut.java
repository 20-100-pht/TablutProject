import Controller.GameConsoleController;
import Model.Game;
import View.Interface;

public class Tablut {

    public static void main(String[] args) {
        System.out.println("Tablut");

        //Interface ui = new Interface();

        GameConsoleController gcc = new GameConsoleController();
        gcc.setPrintTerminal(true);
        gcc.playGame();
    }
}