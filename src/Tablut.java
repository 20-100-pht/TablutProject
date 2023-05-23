import AI.AIConfig;
import AI.AIDifficulty;
import Controller.GameConsoleController;
import Global.Configuration;
import Model.Game;
import View.Interface;

public class Tablut {
    private static final boolean ATTACKER_AI = false;
    private static final boolean DEFENDER_AI = true;

    public static void main(String[] args) {
        System.out.println("Bienvenue dans Tablut ! ");

        Configuration.instance();

        Interface ui = new Interface();
    }
}