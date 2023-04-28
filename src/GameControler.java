import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GameControler {
    ResultGame endGameVar = ResultGame.NO_END_GAME;

    Piece king;
    Grid grid;

    int nbPieceAttackerOnGrid;


    public GameControler(){
        resetGameControler();
    }

    public void resetGameControler (){
        grid = new Grid();
        king = grid.getPieceAtPosition(4,4);
        nbPieceAttackerOnGrid = 16;
    }

    public boolean isEndGame(){
        return endGameVar != ResultGame.NO_END_GAME;
    }

    public void print(){
        grid.print();
    }

    public boolean isDefenderWinConfiguration(){
        return grid.isCornerPosition(king.getRow(), king.getCol()) || nbPieceAttackerOnGrid == 0;
    }

    public boolean isAttackerWinConfiguration(){
        return endGameVar == ResultGame.ATTACKER_WIN;
    }

    public Piece move(int initialRow, int initialCol, int destRow, int destCol){
        // attention on doit avant appeler move vérifier que le pion est bien un pion du joueur courant

        Piece selectedPiece;

        // coordonnées initial
        if(!grid.isInside(initialRow, initialCol)) {
            System.out.println("Coordonnées invalides (en dehors de la grille), veuillez saisir à nouveau.");
            return null;
        }
        if( (selectedPiece = grid.getPieceAtPosition(initialRow, initialCol)) == null) {
            System.out.println("Coordonnées invalides (pas un pion), veuillez saisir à nouveau.");
            return null;
        }

        // coordonnées destination
        if(!grid.isInside(destRow, destCol)){
            System.out.println("Case de destination invalide, veuillez saisir à nouveau.");
            return null;
        }

        if(grid.isCastle(destRow, destCol)){
            System.out.println("Case de destination invalide (château), veuillez saisir à nouveau.");
            return null;
        }

        if(selectedPiece.isSamePosition(destRow, destCol)){
            System.out.println("Case de destination invalide (même position), veuillez saisir à nouveau.");
            return null;
        }

        if(!selectedPiece.canMoveTo(destRow, destCol, grid)){
            System.out.println("La pièce sélectionnée ne peut pas se déplacer sur la case de destination, veuillez saisir à nouveau.");
            return null;
        }

        selectedPiece.setRow(destRow);
        selectedPiece.setCol(destCol);

        grid.setPieceAtPosition(selectedPiece, destRow, destCol);
        grid.setPieceAtPosition(null, initialRow, initialCol);
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
        nbAttack += attackWithArg(current, 0, 1 );


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
            if( sideCurrent != null && ( (current.isAttacker() && (sideCurrent.isDefender() || (sideCurrent.isKing() && sideCurrent.kingIsOnVulnerablePosition()) ) ) || (current.isDefender() && sideCurrent.isAttacker()) ) ){
                if (grid.isInside(sideCurrent.getRow() + rowIndex, sideCurrent.getCol() + colIndex)) {
                    sideSideCurrent = grid.getPieceAtPosition(sideCurrent.getRow() + rowIndex, sideCurrent.getCol() + colIndex);
                    if (grid.isCastle(sideCurrent.getRow() + rowIndex, sideCurrent.getCol() + colIndex) || (sideSideCurrent != null && ((current.isAttacker() && sideSideCurrent.isAttacker()) || (current.isDefender() && sideSideCurrent.isDefender())))) {
                        nbAttack++;
                        if(sideCurrent.isKing()) endGameVar = ResultGame.ATTACKER_WIN;
                        else{
                            if(sideCurrent.isAttacker()) nbPieceAttackerOnGrid--;
                            grid.setPieceAtPosition(null, rowCurrent + rowIndex, colCurrent + colIndex);
                        }
                    }

                } else {
                    nbAttack++;
                    if(sideCurrent.isKing()) endGameVar = ResultGame.ATTACKER_WIN;
                    else{
                        if(sideCurrent.isAttacker()) nbPieceAttackerOnGrid--;
                        grid.setPieceAtPosition(null, rowCurrent + rowIndex, colCurrent + colIndex);
                    }
                }
            }
        }

        return nbAttack;
    }

    public void capture(){
        if(isCapturedOnThrone() || isCapturedNextToThrone()) {
            //King is captured : end Game
            System.out.println("King has been captured!");
            endGameVar = ResultGame.ATTACKER_WIN;
        }
    }

    public boolean isCapturedNextToThrone(){
        int x = king.getCol();
        int y = king.getRow();

        //If king is next to throne
        if((x==4 && (y==3 || y==5)) || y==4 && (x==3 || x==5)){

            //Get all pieces adjacent to the king
            Piece leftPiece = grid.getPieceAtPosition(y,x-1);
            Piece rightPiece = grid.getPieceAtPosition(y,x+1);
            Piece topPiece = grid.getPieceAtPosition(y-1,x);
            Piece bottomPiece = grid.getPieceAtPosition(y+1,x);

            //If piece is attacker or throne
            if( (leftPiece != null && leftPiece.isDefender()) || (y==4 && x+1==4 && leftPiece == null)){
                return false;
            }
            if((rightPiece != null && rightPiece.isDefender()) || (y==4 && x-1==4 && rightPiece == null)){
                return false;
            }
            if((topPiece != null && topPiece.isDefender()) || (y+1==4 && x==4 && topPiece == null)){
                return false;
            }
            if((bottomPiece != null && bottomPiece.isDefender()) || (y-1==4 && x==4 && bottomPiece == null)){
                return false;
            }

            //King is captured
            return true;
        }

        //King isn't captured
        return false;
    }

    public boolean isCapturedOnThrone(){
        int x = king.getCol();
        int y = king.getRow();

        //If king is on throne
        if(x==4 && y==4){

            //Get each adjacent piece to the throne
            Piece leftPiece = grid.getPieceAtPosition(4,3);
            Piece rightPiece = grid.getPieceAtPosition(4,5);
            Piece topPiece = grid.getPieceAtPosition(3,4);
            Piece bottomPiece = grid.getPieceAtPosition(5,4);

            //If piece is an attacker
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

            //King is captured
            return true;
        }

        //King isn't captured
        return false;
    }

    public boolean isKingAtObjective (){
        // The objectives are at the 4 corners of the board
        int x = king.getCol();
        int y = king.getRow();
        return ((x == 0 && ((y == 0) || (y == grid.sizeGrid - 1))) || (x == grid.sizeGrid -1 && ((y == 0) || (y == grid.sizeGrid -1))));
    }


}