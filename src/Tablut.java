import Model.Game;
import View.Interface;

public class Tablut {
    public static void main(String[] args) {
        System.out.println("Tablut2");

        //Interface ui = new Interface();

        Game game = new Game();
        game.playGame();

        /*int games = 0;
        while(true){
            Game game = new Game();
            game.playGame();
            games++;
            System.out.println("Number of games :" + games);
        }*/


    }
}