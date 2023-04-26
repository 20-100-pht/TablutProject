import java.io.Serializable;

public class Grid implements Serializable {

    final int sizeGrid = 9;
    Piece[][] board = new Piece[sizeGrid][sizeGrid];

    public Grid() {
        reset();
    }

    public void reset() {

        //KING
        board[4][4] = new Piece(4, 4, PieceType.KING);


        // DEFENDER
        board[2][4] = new Piece(2, 4, PieceType.DEFENDER);
        board[3][4] = new Piece(3, 4, PieceType.DEFENDER);
        board[5][4] = new Piece(5, 4, PieceType.DEFENDER);
        board[6][4] = new Piece(6, 4, PieceType.DEFENDER);

        board[4][2] = new Piece(4, 2, PieceType.DEFENDER);
        board[4][3] = new Piece(4, 3, PieceType.DEFENDER);
        board[4][5] = new Piece(4, 5, PieceType.DEFENDER);
        board[4][6] = new Piece(4, 6, PieceType.DEFENDER);

        //ATTACKER
        board[0][3] = new Piece(0, 3, PieceType.ATTACKER);
        board[0][4] = new Piece(0, 4, PieceType.ATTACKER);
        board[0][5] = new Piece(0, 5, PieceType.ATTACKER);
        board[1][4] = new Piece(1, 4, PieceType.ATTACKER);

        board[8][3] = new Piece(8, 3, PieceType.ATTACKER);
        board[8][4] = new Piece(8, 4, PieceType.ATTACKER);
        board[8][5] = new Piece(8, 5, PieceType.ATTACKER);
        board[7][4] = new Piece(7, 4, PieceType.ATTACKER);

        board[3][0] = new Piece(3, 0, PieceType.ATTACKER);
        board[4][0] = new Piece(4, 0, PieceType.ATTACKER);
        board[5][0] = new Piece(5, 0, PieceType.ATTACKER);
        board[4][1] = new Piece(4, 1, PieceType.ATTACKER);

        board[3][8] = new Piece(3, 8, PieceType.ATTACKER);
        board[4][8] = new Piece(4, 8, PieceType.ATTACKER);
        board[5][8] = new Piece(5, 8, PieceType.ATTACKER);
        board[4][7] = new Piece(4, 7, PieceType.ATTACKER);

    }

    public void print() {
        for (int i = 0; i < sizeGrid; i++) {
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

    public boolean isInside(int row, int col) {
        return row >= 0 && row <= 8 && col >= 0 && col <= 8;
    }

    public Piece getPieceAtPosition(int row, int col) {
        if (row < 0 || row >= sizeGrid || col < 0 || col >= sizeGrid) {
            return null;
        }
        return board[row][col];
    }

    public void setPieceAtPosition(Piece piece, int row, int col) {
        board[row][col] = piece;
    }

    public Grid cloneGrid() {
        Grid copy = new Grid();
        for (int i = 0; i < sizeGrid; i++) {
            for (int j = 0; j < sizeGrid; j++) {
                Piece piece = getPieceAtPosition(i, j);
                if (piece != null) {
                    copy.setPieceAtPosition(piece.clonePiece(), i, j);
                }
            }
        }
        return copy;
    }
}