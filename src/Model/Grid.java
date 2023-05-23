package Model;

import Structure.Coordinate;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Grid implements Serializable {

    final int sizeGrid = 9;
    public Piece[][] board;

    public Grid() {
        reset();
    }

    public void testKingVulnerability(){
        board[4][4] = new Piece(new Coordinate(4,4), PieceType.KING);

        board[2][4] = new Piece(new Coordinate(2, 4), PieceType.ATTACKER);
        board[4][3] = new Piece(new Coordinate(4, 3), PieceType.ATTACKER);
        board[4][5] = new Piece(new Coordinate(4, 5), PieceType.ATTACKER);
        board[5][4] = new Piece(new Coordinate(5, 4), PieceType.ATTACKER);


    }

    public void testKingVulnerability2(){
        board[3][4] = new Piece(new Coordinate(3, 4), PieceType.KING);

        board[1][4] = new Piece(new Coordinate(1, 4), PieceType.ATTACKER);
        board[3][3] = new Piece(new Coordinate(3, 3), PieceType.ATTACKER);
        board[3][5] = new Piece(new Coordinate(3, 5), PieceType.ATTACKER);
    }

    public void testKingVulnerability3(){
        board[1][1] = new Piece(new Coordinate(1, 1), PieceType.KING);

        board[1][0] = new Piece(new Coordinate(1, 0), PieceType.ATTACKER);
        board[0][1] = new Piece(new Coordinate(0, 1), PieceType.ATTACKER);
        board[1][2] = new Piece(new Coordinate(1, 2), PieceType.ATTACKER);
        board[3][1] = new Piece(new Coordinate(3, 1), PieceType.ATTACKER);
    }

    public void testKingVulnerability4(){
        board[0][2] = new Piece(new Coordinate(0, 2), PieceType.KING);
        board[0][1] = new Piece(new Coordinate(0, 1), PieceType.ATTACKER);
        board[0][3] = new Piece(new Coordinate(0, 3), PieceType.ATTACKER);
        board[2][2] = new Piece(new Coordinate(2, 2), PieceType.ATTACKER);
    }

    public void testKingVulnerability5(){
        board[0][1] = new Piece(new Coordinate(0, 1), PieceType.KING);
        board[0][2] = new Piece(new Coordinate(0, 2), PieceType.ATTACKER);
        board[2][1] = new Piece(new Coordinate(2, 1), PieceType.ATTACKER);
    }

    public void captureKing(){
        board[0][2] = new Piece(new Coordinate(0, 2), PieceType.KING);
        board[0][1] = new Piece(new Coordinate(0, 1), PieceType.ATTACKER);
        board[0][3] = new Piece(new Coordinate(0, 3), PieceType.ATTACKER);
        board[1][2] = new Piece(new Coordinate(1, 2), PieceType.ATTACKER);
    }

    public void testTripleKill(){
        board[4][4] = new Piece(new Coordinate(4, 4), PieceType.KING);

        board[4][1] = new Piece(new Coordinate(4, 1), PieceType.ATTACKER);
        board[1][3] = new Piece(new Coordinate(1, 3), PieceType.ATTACKER);

        board[1][0] = new Piece(new Coordinate(1, 0), PieceType.DEFENDER);
        board[0][1] = new Piece(new Coordinate(0, 1), PieceType.DEFENDER);
        board[1][2] = new Piece(new Coordinate(1, 2), PieceType.DEFENDER);
    }

    public void noAttacker(){
        board[4][4] = new Piece(new Coordinate(4, 4), PieceType.KING);

        board[0][1] = new Piece(new Coordinate(0, 1), PieceType.ATTACKER);

        board[0][0] = new Piece(new Coordinate(0, 0), PieceType.DEFENDER);
        board[0][3] = new Piece(new Coordinate(1, 2), PieceType.DEFENDER);
    }

    public void testAttackSideCastle(){
        board[4][4] = new Piece(new Coordinate(4, 4), PieceType.KING);

        board[3][4] = new Piece(new Coordinate(3, 4), PieceType.DEFENDER);


        board[4][3] = new Piece(new Coordinate(4, 3), PieceType.ATTACKER);
        board[4][5] = new Piece(new Coordinate(4, 5), PieceType.ATTACKER);
        board[5][4] = new Piece(new Coordinate(5, 4), PieceType.ATTACKER);
        board[0][4] = new Piece(new Coordinate(0, 4), PieceType.ATTACKER);

    }

    public void reset() {

        board = new Piece[sizeGrid][sizeGrid];

        //KING
        board[4][4] = new Piece(new Coordinate(4, 4), PieceType.KING);

        // DEFENDER
        board[2][4] = new Piece(new Coordinate(2, 4), PieceType.DEFENDER);
        board[3][4] = new Piece(new Coordinate(3, 4), PieceType.DEFENDER);
        board[5][4] = new Piece(new Coordinate(5, 4), PieceType.DEFENDER);
        board[6][4] = new Piece(new Coordinate(6, 4), PieceType.DEFENDER);

        board[4][2] = new Piece(new Coordinate(4, 2), PieceType.DEFENDER);
        board[4][3] = new Piece(new Coordinate(4, 3), PieceType.DEFENDER);
        board[4][5] = new Piece(new Coordinate(4, 5), PieceType.DEFENDER);
        board[4][6] = new Piece(new Coordinate(4, 6), PieceType.DEFENDER);

        //ATTACKER
        board[0][3] = new Piece(new Coordinate(0, 3), PieceType.ATTACKER);
        board[0][4] = new Piece(new Coordinate(0, 4), PieceType.ATTACKER);
        board[0][5] = new Piece(new Coordinate(0, 5), PieceType.ATTACKER);
        board[1][4] = new Piece(new Coordinate(1, 4), PieceType.ATTACKER);

        board[8][3] = new Piece(new Coordinate(8, 3), PieceType.ATTACKER);
        board[8][4] = new Piece(new Coordinate(8, 4), PieceType.ATTACKER);
        board[8][5] = new Piece(new Coordinate(8, 5), PieceType.ATTACKER);
        board[7][4] = new Piece(new Coordinate(7, 4), PieceType.ATTACKER);

        board[3][0] = new Piece(new Coordinate(3, 0), PieceType.ATTACKER);
        board[4][0] = new Piece(new Coordinate(4, 0), PieceType.ATTACKER);
        board[5][0] = new Piece(new Coordinate(5, 0), PieceType.ATTACKER);
        board[4][1] = new Piece(new Coordinate(4, 1), PieceType.ATTACKER);

        board[3][8] = new Piece(new Coordinate(3, 8), PieceType.ATTACKER);
        board[4][8] = new Piece(new Coordinate(4, 8), PieceType.ATTACKER);
        board[5][8] = new Piece(new Coordinate(5, 8), PieceType.ATTACKER);
        board[4][7] = new Piece(new Coordinate(4, 7), PieceType.ATTACKER);

    }

    public void print() {
        for( int k = 0; k < sizeGrid; k++){
            if(k == 0) System.out.print("  ");
            System.out.print(k+" ");
        }
        System.out.println();
        for (int i = 0; i < sizeGrid; i++) {
            System.out.print(i+" ");
            for (int j = 0; j < sizeGrid; j++) {
                if (board[i][j] == null) {
                    System.out.print(". ");
                } else {
                    System.out.print(board[i][j].getSymbol() + " ");
                }
            }
            System.out.println();
        }
    }

    public String toString() {
        String stringGrid = "";
        for( int k = 0; k < sizeGrid; k++){
            if(k == 0) stringGrid += "  ";
            stringGrid +=k+" ";
        }
        stringGrid += "\n";
        for (int i = 0; i < sizeGrid; i++) {
            stringGrid += i+" ";

            for (int j = 0; j < sizeGrid; j++) {
                if (board[i][j] == null) {
                    stringGrid += ". ";
                } else {
                    stringGrid += board[i][j].getSymbol() + " ";
                }
            }
            stringGrid += "\n";
        }
        stringGrid += "\n";
        return stringGrid;
    }

    public ArrayList<Piece> returnListOfPiece(PieceType type){
        Piece current;
        ArrayList<Piece> list = new ArrayList<>();
        for (int i = 0; i < sizeGrid; i++) {
            for (int j = 0; j < sizeGrid; j++) {
                if( (current = board[i][j]) != null && ( (current.isKing() && type == PieceType.DEFENDER) || current.getType() == type) ){
                    list.add(current);
                }
            }
        }
        return list;
    }

    public Piece[][] getBoard(){ return board;}

    public int getSizeGrid(){ return sizeGrid;}

    public boolean isInside(Coordinate obj) {
        return obj.getRow() >= 0 && obj.getRow() < sizeGrid && obj.getCol() >= 0 && obj.getCol() < sizeGrid;
    }

    public boolean isCastle(Coordinate obj){
        return obj.getRow() == 4 && obj.getCol() == 4;
    }

    public boolean isNextToFortress(Coordinate obj){
        return (obj.getRow() == 1 && obj.getCol() == 0)
                || (obj.getRow() == 7 && obj.getCol() == 0)
                || (obj.getRow() == 1 && obj.getCol() == 8)
                || (obj.getRow() == 7 && obj.getCol() == 8)
                || (obj.getRow() == 0 && obj.getCol() == 1)
                || (obj.getRow() == 0 && obj.getCol() == 7)
                || (obj.getRow() == 8 && obj.getCol() == 1)
                || (obj.getRow() == 8 && obj.getCol() == 7);
    }

    public boolean isNextCastle(Coordinate obj){
        return ( (obj.getRow() == 4 && obj.getCol() == 3) || (obj.getRow() == 4 && obj.getCol() == 5) || (obj.getRow() == 3 && obj.getCol() == 4) || (obj.getRow() == 5 && obj.getCol() == 4));
    }

    public boolean isNextToWall(Coordinate obj){
        return (obj.getRow() == 8) || (obj.getRow() == 0) || (obj.getCol() == 8) || (obj.getCol() == 0);
    }

    public boolean isCommonCase(Coordinate obj){
        return !isCastle(obj) && !isNextCastle(obj) && !isNextToWall(obj);
    }
    public boolean isCornerPosition(Coordinate k){
        return ( (k.getRow() == 0 && k.getCol() == 0) || (k.getRow() == 0 && k.getCol() == 8) || (k.getRow() == 8 && k.getCol() == 0) || (k.getRow() == 8 && k.getCol() == 8) );
    }

    public Piece getPieceAtPosition(Coordinate obj) {
        if (obj.getRow() < 0 || obj.getRow() >= sizeGrid || obj.getCol() < 0 || obj.getCol() >= sizeGrid) {
            return null;
        }
        return board[obj.getRow()][obj.getCol()];
    }

    public void setPieceAtPosition(Piece piece, Coordinate p) {
        board[p.getRow()][p.getCol()] = piece;
    }

    public Grid cloneGrid() {
        Grid copy = new Grid();
        for (int i = 0; i < sizeGrid; i++) {
            for (int j = 0; j < sizeGrid; j++) {
                Coordinate coord = new Coordinate(0,0);
                coord.setRowCoord(i);
                coord.setColCoord(j);

                Piece piece = getPieceAtPosition(coord);
                if (piece != null) {
                    copy.setPieceAtPosition(piece.clonePiece(), coord);
                }else{
                    copy.setPieceAtPosition(null,coord);
                }
            }
        }
        return copy;
    }

    public void cloneFromOther(Grid oG) {
        for (int i = 0; i < sizeGrid; i++) {
            for (int j = 0; j < sizeGrid; j++) {
                board[j][i] = oG.getPieceAtPosition(new Coordinate(j, i));
            }
        }
    }
}