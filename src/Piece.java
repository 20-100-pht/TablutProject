import java.util.ArrayList;

public class Piece {
    private int row;
    private int col;
    private PieceType type;

    public Piece(int row, int col, PieceType t) {
        this.row = row;
        this.col = col;
        this.type = t;
    }

    public int getRow(){ return row;}

    public int getCol(){ return col;}

    public PieceType getType(){ return this.type;}

    public boolean isKing(){ return this.type == PieceType.KING;}

    public boolean isSamePosition(int destRow, int destCol){
        return destRow == row && destCol == col;
    }

    public boolean kingIsOnVulnerablePosition(){
        return !( (row == 4 && col == 4) || (row == 4 && col == 3) || (row == 4 && col == 5) || (row == 3 && col == 4) || (row == 5 && col == 4) );
    }

    public boolean isAttacker(){ return this.type == PieceType.ATTACKER;}

    public boolean isDefender(){ return this.type == PieceType.DEFENDER;}

    public boolean isDefenderOrKing(){ return this.type == PieceType.DEFENDER || this.type == PieceType.KING;}

    public void setRow(int newRow){ this.row = newRow;}

    public void setCol(int newCol){ this.col = newCol;}

    public void setKing(boolean king){ type = PieceType.KING;}

    public void setAttacker(boolean attacker){ type = PieceType.ATTACKER;}

    public void setDefender(boolean defender){ type = PieceType.DEFENDER;}

    public String getSymbol(){
        if(type == PieceType.KING) return "K";
        else if(type == PieceType.DEFENDER) return "D";
        else return "A";
    }

    public boolean canMoveTo(int destRow, int destCol, Grid grid){

        if (grid.getPieceAtPosition(destRow, destCol) != null) return false;
        if(destCol != col && destRow != row) return false;

        int dirRow = Integer.compare(destRow, row);
        int dirCol = Integer.compare(destCol, col);

        int nextRow = row + dirRow;
        int nextCol = col + dirCol;



        while (nextRow != destRow || nextCol != destCol) {
            if (grid.getPieceAtPosition(nextRow, nextCol) != null) {
                return false;
            }
            nextRow += dirRow;
            nextCol += dirCol;
        }

        return true;


    }

    public Piece clonePiece(){
        Piece copy = new Piece(getRow(), getCol(), getType());
        return copy;
    }


    //TODO
    /*
    public ArrayList possibleMoves(Grid grid) {
        ArrayList<Piece> MovesToPieceList = new ArrayList<>();
        for (int c = 0; c<9;c++){
            if (this.canMoveTo(row,c, grid) && c != col){
                MovesList.add(grid.getPieceAtPosition(row,c));
            }
        }
        for (int r = 0; r<9;r++){
            if (this.canMoveTo(r,col, grid) && r != row){
                MovesList.add(grid.getPieceAtPosition(r,col));
            }
        }
        return MovesToPieceList;
    }
*/
}
