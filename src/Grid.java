import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Grid implements Serializable {

    final int sizeGrid = 9;
    Piece[][] board = new Piece[sizeGrid][sizeGrid];

    public Grid() {
        reset();
        //testAttackSideCastle();
        //testKingVulnerability2();
        //testKingVulnerability3();
        //testKingVulnerability4();
        //testTripleKill();
        //noAttacker();

    }

    public void testKingVulnerability(){
        board[4][4] = new Piece(new Coordinate(4,4),PieceType.KING);

        board[2][4] = new Piece(new Coordinate(2, 4), PieceType.ATTACKER);
        board[4][3] = new Piece(new Coordinate(4, 3), PieceType.ATTACKER);
        board[4][5] = new Piece(new Coordinate(4, 5), PieceType.ATTACKER);
        board[5][4] = new Piece(new Coordinate(5, 4), PieceType.ATTACKER);


    }

    public void testKingVulnerability2(){
        board[3][4] = new Piece(new Coordinate(3, 4), PieceType.KING);

        board[1][4] = new Piece(new Coordinate(1, 4), PieceType.ATTACKER);
        board[3][2] = new Piece(new Coordinate(3, 2), PieceType.ATTACKER);
        board[3][5] = new Piece(new Coordinate(3, 5), PieceType.ATTACKER);
        board[4][2] = new Piece(new Coordinate(4, 2), PieceType.DEFENDER);
    }

    public void testKingVulnerability3(){
        board[1][1] = new Piece(new Coordinate(1, 1), PieceType.KING);

        board[1][0] = new Piece(new Coordinate(1, 0), PieceType.ATTACKER);
        board[1][3] = new Piece(new Coordinate(1, 3), PieceType.ATTACKER);
    }

    public void testKingVulnerability4(){
        board[0][1] = new Piece(new Coordinate(0, 1), PieceType.KING);
        board[2][1] = new Piece(new Coordinate(2, 1), PieceType.ATTACKER);
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

    public List<Piece> returnListOfPiece(PieceType type){
        Piece current;
        List<Piece> list = new ArrayList<>();
        for (int i = 0; i < sizeGrid; i++) {
            for (int j = 0; j < sizeGrid; j++) {
                if( (current = board[i][j]) != null && ( (current.isKing() && type == PieceType.DEFENDER) || current.getType() == type) ){
                    list.add(current);
                }
            }
        }
        return list;
    }

    public boolean isInside(Coordinate obj) {
        return obj.row >= 0 && obj.row < sizeGrid && obj.col >= 0 && obj.col < sizeGrid;
    }

    public boolean isCastle(Coordinate obj){
        return obj.row == 4 && obj.col ==4;
    }

    public boolean isCornerPosition(Coordinate k){
        return ( (k.row == 0 && k.col == 0) || (k.row == 0 && k.col == 8) || (k.row == 8 && k.col == 0) || (k.row == 8 && k.col == 8) );
    }

    public Piece getPieceAtPosition(Coordinate obj) {
        if (obj.row < 0 || obj.row >= sizeGrid || obj.col < 0 || obj.col >= sizeGrid) {
            return null;
        }
        return board[obj.row][obj.col];
    }

    public void setPieceAtPosition(Piece piece, Coordinate p) {
        board[p.row][p.col] = piece;
    }

    public Grid cloneGrid() {
        Grid copy = new Grid();
        Coordinate coord = new Coordinate(0,0);
        for (int i = 0; i < sizeGrid; i++) {
            coord.setRowCoord(i);
            for (int j = 0; j < sizeGrid; j++) {
                coord.setColCoord(j);
                Piece piece = getPieceAtPosition(coord);
                if (piece != null) {
                    copy.setPieceAtPosition(piece.clonePiece(), new Coordinate(coord.getRow(), coord.getCol()));
                }
            }
        }
        return copy;
    }

    /**
     mise à 1 du bit de position x dans la variable v

     v = v | (1 << x);

     mise à 0 du bit de position x dans la variable v

     v = v & ~(1 << x);

     valeur du bit de position x dans la variable v

     (v >> x) & 1;
     */
    public int[] gridToBinary(){

        //int aa = 0b11111111111111111111111111111111;

        int[] grid = new int[11];
        int ind = 0, innerInd = 0;
        for(int y = 0; y < sizeGrid; y++){
            for(int x = 0; x < sizeGrid; x++){

                if(board[y][x] == null){
                    grid[ind] = grid[ind] | (1 << innerInd);
                    grid[ind] = grid[ind] | (1 << innerInd+1);
                }else{
                    switch (board[y][x].getType()){
                        case KING :
                            grid[ind] = grid[ind] & ~(1 << innerInd);
                            grid[ind] = grid[ind] & ~(1 << innerInd+1);
                            break;

                        case DEFENDER:
                            grid[ind] = grid[ind] & ~(1 << innerInd);
                            grid[ind] = grid[ind] | (1 << innerInd+1);
                            break;

                        case ATTACKER:
                            grid[ind] = grid[ind] |(1 << innerInd);
                            grid[ind] = grid[ind] & ~(1 << innerInd+1);
                            break;

                        default :
                            break;
                    }
                }

                innerInd = (innerInd+1) % 8;
                if(innerInd == 0) ind++;

            }
        }
        return grid;
    }
}