import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Game {
    History history;
    boolean defender;
    boolean attacker;

    Piece king;
    Scanner scanner = new Scanner(System.in);
    Grid grid;


    public Game(){
        reset();
    }

    public void reset (){
        defender = false;
        attacker = true;
        grid = new Grid();
        king = grid.getPieceAtPosition(4,4);
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
        System.out.println("Défenseur, à vous de jouer !");

        Piece pieceMove = move();

        attack(pieceMove);

    }

    public void playTurnAttacker(){
        System.out.println("Attaquant, à vous de jouer !");

        Piece pieceMove = move();

        attack(pieceMove);
        //capture();
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

        selectedPiece.setRow(destRow);
        selectedPiece.setCol(destCol);

        grid.setPieceAtPosition(selectedPiece, destRow, destCol);
        grid.setPieceAtPosition(null, pieceRow, pieceCol);
        if(selectedPiece.isKing()) moveKing(destRow, destCol);

        return selectedPiece;
    }

    public int attack(Piece current){
        int nbAttack = 0;

        // attaque en haut
        nbAttack += attackWithArg(current, -1, 0 );

        // attaque en bas
        nbAttack += attackWithArg(current, 1, 0 );

        // attaque a gauche
        nbAttack += attackWithArg(current, 0, -1 );

        // attaque a droite
        nbAttack += attackWithArg(current, -1, 1 );


        return nbAttack;

    }

    private int attackWithArg(Piece current, int rowIndex, int colIndex){
        int rowCurrent = current.getRow();
        int colCurrent = current.getCol();
        int nbAttack = 0;

        Piece sideCurrent;
        Piece sideSideCurrent;

        if(grid.isInside(rowCurrent+rowIndex, colCurrent+colIndex)){
            sideCurrent = grid.getPieceAtPosition(rowCurrent+rowIndex, colCurrent+colIndex);
            if( sideCurrent != null && ( (current.isAttacker() && sideCurrent.isDefenderOrKing()) || (current.isDefenderOrKing() && sideCurrent.isAttacker()) ) ){
                if (grid.isInside(sideCurrent.getRow()+rowIndex, sideCurrent.getCol()+colIndex)){
                    sideSideCurrent = grid.getPieceAtPosition(sideCurrent.getRow()+rowIndex, sideCurrent.getCol()+colIndex);
                    if( sideSideCurrent != null && ( (current.isAttacker() && sideSideCurrent.isAttacker()) || (current.isDefenderOrKing() && sideSideCurrent.isDefenderOrKing()) ) ){
                        nbAttack++;
                        grid.setPieceAtPosition(null, rowCurrent+rowIndex, colCurrent+colIndex);
                    }

                }
                else{
                    nbAttack++;
                    grid.setPieceAtPosition(null, rowCurrent+rowIndex, colCurrent+colIndex);
                }
            }
        }

        return nbAttack;
    }

    public void capture(){
        //Pour le roi

        if(isCapturedOnThrone() || isCapturedNextToThrone() ){
            //Captured
            System.out.println("King has been captured!");
        }

    }

    public boolean isCapturedNextToThrone(){
        int x = king.getCol();
        int y = king.getRow();

        //Is next to throne
        if((x==4 && (y==3 || y==5)) || y==4 && (x==3 || x==5)){

            Piece leftPiece = grid.getPieceAtPosition(y,x-1);
            Piece rightPiece = grid.getPieceAtPosition(y,x+1);
            Piece topPiece = grid.getPieceAtPosition(y-1,x);
            Piece bottomPiece = grid.getPieceAtPosition(y+1,x);


            if( (leftPiece != null && leftPiece.isDefender()) || !(y==4 && x-1==4 && leftPiece == null)){
                return false;
            }
            if((rightPiece != null && rightPiece.isDefender()) || !(y==4 && x+1==4 && rightPiece == null)){
                return false;
            }
            if((topPiece != null && topPiece.isDefender()) || !(y-1==4 && x==4 && topPiece == null)){
                return false;
            }
            if((bottomPiece != null && bottomPiece.isDefender()) || !(y+1==4 && x==4 && bottomPiece == null)){
                return false;
            }

            //Is captured
            return true;
        }

        return false;
    }

    public boolean isCapturedOnThrone(){
        int x = king.getCol();
        int y = king.getRow();

        if(x==4 && y==4){
            Piece leftPiece = grid.getPieceAtPosition(4,3);
            Piece rightPiece = grid.getPieceAtPosition(4,5);
            Piece topPiece = grid.getPieceAtPosition(3,4);
            Piece bottomPiece = grid.getPieceAtPosition(5,3);

            if(leftPiece == null || leftPiece.isDefender()){
                return false;
            }
            if(rightPiece == null || rightPiece.isDefender()){
                return false;
            }
            if(topPiece == null || topPiece.isDefender()){
                return false;
            }
            if(bottomPiece == null || bottomPiece.isDefender()){
                return false;
            }

            //isCaptured
            return true;
        }
        return false;
    }

    public boolean isKingAtObjective (){
        // The objectives are at the 4 corners of the board
        int x = king.getCol();
        int y = king.getRow();
        return ((x == 0 && ((y == 0) || (y == grid.sizeGrid - 1))) || (x == grid.sizeGrid -1 && ((y == 0) || (y == grid.sizeGrid -1))));
    }

    private void moveKing(int destCol, int destRow){
        if (king.getCol() != destCol && king.getRow() != destRow){
            king.setCol(destCol);
            king.setRow(destRow);
        }
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