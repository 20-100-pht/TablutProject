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

        GameConsoleController gcc = new GameConsoleController();
        gcc.getGame().setGameAttackerAI(ATTACKER_AI);
        gcc.getGame().setGameDefenderAI(DEFENDER_AI);
        gcc.getGame().setAIAttackerDifficulty(AIDifficulty.RANDOM);
        gcc.getGame().setAIDefenderDifficulty(AIDifficulty.EASY);

        gcc.setPrintTerminal(true);
        gcc.playGame();
    }
}