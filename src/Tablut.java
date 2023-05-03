import Controller.GameConsoleController;
import View.Interface;

public class Tablut {
    private static int AIGAMES = 100;
    private static boolean AITRAINING = false;

    public static void main(String[] args) {
        System.out.println("Tablut");

        if(AITRAINING){
            for (int i = 0; i< AIGAMES; i++){
                GameConsoleController gcc = new GameConsoleController();
                gcc.playGame();
            }
        }else{

            Interface ui = new Interface();

            GameConsoleController gcc = new GameConsoleController();
            gcc.playGame();
        }


    }
}