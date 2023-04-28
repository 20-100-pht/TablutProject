
public class Tablut {
    public static void main(String[] args) {
        System.out.println("Tablut2");

        //Interface ui = new Interface();
        Game game = new Game();
        int i = 0;
        while(i<10) {
            game.playGame();
            i++;
        }
        System.out.println("\n\nNombre de partie joué = " + i);
    }
}