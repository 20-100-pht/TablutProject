import AI.AIDifficulty;
import Controller.GameConsoleController;
import Model.Game;
import View.Interface;

public class Tablut {
    private static final boolean ATTACKER_AI = false;
    private static final boolean DEFENDER_AI = true;

    public static void main(String[] args) {
        System.out.println("Tablut");

        Interface ui = new Interface();

        //Game game = new Game("", "", AIDifficulty.RANDOM, AIDifficulty.EASY);
        /*GameConsoleController gcc = new GameConsoleController(game);
        game.setGameControllerInstance(gcc);

        gcc.setPrintTerminal(true);
        gcc.playGame();*/
    }
}