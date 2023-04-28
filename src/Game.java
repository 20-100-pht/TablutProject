import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Game {
    boolean defender;
    boolean attacker;

    Scanner scanner = new Scanner(System.in);
    GameControler gameControler;


    public Game(){
        reset();
    }

    public void reset (){
        defender = false;
        attacker = true;
        gameControler = new GameControler();
    }

    public void playGame(){
        while(!gameControler.isEndGame()){
            playTurn();
        }
    }

    public void endGame(ResultGame result){
        if(result == ResultGame.ATTACKER_WIN) System.out.println("Attacker win !");
        else System.out.println("Defender win !");
    }

    public void playTurn(){
        gameControler.print();

        if(attacker) playTurnAttacker();
        else playTurnDefender();

        defender = !defender;
        attacker = !attacker;
        
    }

    public void playTurnDefender(){
        System.out.println("Défenseur, à vous de jouer !");

        Piece current = null;
        while(current == null){
            System.out.println("Coordonnées de la pièce que vous souhaitez déplacer (ligne colonne) : ");
            int initialRow = scanner.nextInt();
            int initialCol = scanner.nextInt();


            System.out.println("Coordonnées de la case où vous souhaitez déplacer la pièce (ligne colonne) : ");
            int destRow = scanner.nextInt();
            int destCol = scanner.nextInt();

            current = gameControler.move(initialRow, initialCol, destRow, destCol);

            if(!current.isDefender()){
                System.out.println("Ce pion n'est pas le votre !");
                current = null;
            }

        }

        gameControler.attack(current);

        if(gameControler.isDefenderWinConfiguration()) endGame(ResultGame.DEFENDER_WIN);
    }

    public void playTurnAttacker(){
        System.out.println("Attaquant, à vous de jouer !");

        Piece current = null;
        while(current == null){
            System.out.println("Coordonnées de la pièce que vous souhaitez déplacer (ligne colonne) : ");
            int initialRow = scanner.nextInt();
            int initialCol = scanner.nextInt();


            System.out.println("Coordonnées de la case où vous souhaitez déplacer la pièce (ligne colonne) : ");
            int destRow = scanner.nextInt();
            int destCol = scanner.nextInt();

            current = gameControler.move(initialRow, initialCol, destRow, destCol);

            if(!current.isAttacker()){
                System.out.println("Ce pion n'est pas le votre !");
                current = null;
            }

        }

        //Check if the king has been captured while on or next to the throne
        gameControler.capture();

        /*
        * Regarder dans attack si la piece tuée est le ROI !
        * */
        int kill = gameControler.attack(current);
        System.out.println("nb kill : " + kill);


        if(gameControler.isAttackerWinConfiguration()) endGame(ResultGame.ATTACKER_WIN);

        /*int[] l = grid.gridToBinary();
        for(int i =0; i < 11; i++){
            String str = String.format("%16s", Integer.toBinaryString(l[i])).replace(' ', '0');
            System.out.println(str);
        }*/
    }


    public void loadFromFile(String filePath){
        try {
            FileInputStream fis = new FileInputStream(filePath);
            ObjectInputStream ois = new ObjectInputStream(fis);

            gameControler = (GameControler) ois.readObject();

            ois.close();

        } catch (IOException e) {
            System.out.println(e.toString());
        } catch (ClassNotFoundException e){
            System.out.println(e.toString());
        }
    }

    public void saveInFile(String filePath){
        try {
            FileOutputStream fos = new FileOutputStream(filePath);
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            oos.writeObject(gameControler);

            oos.close();

        } catch(IOException e) {
            System.out.println(e.toString());
        }
    }

}