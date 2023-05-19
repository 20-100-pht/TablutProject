package AI;

import Model.Game;
import Structure.Node;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class KingToCornerTest {

    public static void main(String[] args) {
        //File file = new File("1MoveWin");
        File file = new File("src/Test/AI/king_2_corners");
        int possible=0;

        try {
            FileInputStream is = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(is);

            Game game = (Game) ois.readObject();
            ois.close();

            Node node = new Node(game.getLogicGrid().cloneLogicGrid(),null);
            int result = HeuristicUtils.canKingGoToCorner(node);
            System.out.println("Result : " + result);

            /*game.defenderAI = new AIRandom();
            game.setGameDefenderAI(true);
            game.setAIDefenderDifficulty(AIDifficulty.RANDOM);

            game.attackerAI = new AIRandom();
            game.setGameAttackerAI(true);
            game.setAIAttackerDifficulty(AIDifficulty.RANDOM);

            GameConsoleController gcc = new GameConsoleController(game);
            game.setGameControllerInstance(gcc);
            gcc.setPrintTerminal(true);
            gcc.playGame();*/




        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println(possible);
    }
}

