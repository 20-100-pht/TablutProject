import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Game {
    History history;
    boolean defender;
    boolean attacker;
    Scanner scanner = new Scanner(System.in);
    Grid grid;


    public Game(){
        reset();
    }

    public void reset (){
        defender = false;
        attacker = true;
        grid = new Grid();
        history = new History();
        history.addGrid(grid);

    }

    public void playGame(){
        boolean endGame = false;
        while(!endGame){
            playTurn();
        }
    }

    public void playTurn(){
        grid.print();
        if(attacker) playTurnAttacker();
        else playTurnDefender();

        defender = !defender;
        attacker = !attacker;
        
    }

    public void playTurnDefender(){
        System.out.println("Defender, à vous de jouer !");

        Piece pieceMove = move();

        attack();

    }

    public void playTurnAttacker(){
        System.out.println("Attaquant, à vous de jouer !");

        Piece pieceMove = move();

        attack();
        capture();

        // vérif fin de partie (bloque le roi)
    }



    public Piece move(){

        Piece selectedPiece;

        boolean validInput = false;
        int pieceRow = -1;
        int pieceCol = -1;

        while(!validInput){
            System.out.println("Coordonnées de la pièce que vous souhaitez déplacer (ligne colonne) : ");
            pieceRow = scanner.nextInt();
            pieceCol = scanner.nextInt();
            if(grid.isInside(pieceRow, pieceCol)){
                selectedPiece = grid.getPieceAtPosition(pieceRow, pieceCol);
                if(selectedPiece != null && ( (defender && selectedPiece.isDefenderOrKing()) || (attacker && selectedPiece.isAttacker()) )){
                    validInput = true;
                }
            }

            if(!validInput) System.out.println("Coordonnées invalides, veuillez saisir à nouveau.");
        }

        selectedPiece = grid.getPieceAtPosition(pieceRow, pieceCol);

        validInput = false;
        int destRow = -1;
        int destCol = -1;

        while(!validInput){
            System.out.println("Coordonnées de la case où vous souhaitez déplacer la pièce (ligne colonne) : ");
            destRow = scanner.nextInt();
            destCol = scanner.nextInt();

            if(!grid.isInside(destRow, destCol)){
                System.out.println("Case de destination invalide, veuillez saisir à nouveau.");
                continue;
            }
            
            if(!selectedPiece.canMoveTo(destRow, destCol, grid)){
                System.out.println("La pièce sélectionnée ne peut pas se déplacer sur la case de destination, veuillez saisir à nouveau.");
                continue;
            }

            validInput = true;
        }

        history.addGrid(grid);

        grid.setPieceAtPosition(selectedPiece, destRow, destCol);
        grid.setPieceAtPosition(null, pieceRow, pieceCol);

        return selectedPiece;
    }

    public void attack(){

    }

    public void capture(){

    }

    public void loadFromFile(String filePath){
        try {
            FileInputStream fis = new FileInputStream(filePath);
            ObjectInputStream ois = new ObjectInputStream(fis);

            history = (History) ois.readObject();
            grid = (Grid) ois.readObject();

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

            oos.writeObject(history);
            oos.writeObject(grid);

            oos.close();

        } catch(IOException e) {
            System.out.println(e.toString());
        }
    }

}