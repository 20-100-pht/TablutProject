import java.util.ArrayList;

public class Piece {
    Coordinate c;
    private PieceType type;

    public Piece(Coordinate c, PieceType t) {
        this.c = c;
        this.type = t;
    }

    public int getRow(){ return c.row;}

    public int getCol(){ return c.col;}

    public PieceType getType(){ return this.type;}

    public boolean isKing(){ return this.type == PieceType.KING;}

    public boolean isSamePosition(Coordinate d){
        return d.row == c.row && d.col == c.col;
    }

    public boolean kingIsOnVulnerablePosition(){
        return !( (c.row == 4 && c.col == 4) || (c.row == 4 && c.col == 3) || (c.row == 4 && c.col == 5) || (c.row == 3 && c.col == 4) || (c.row == 5 && c.col == 4) );
    }

    public boolean isAttacker(){ return this.type == PieceType.ATTACKER;}

    public boolean isDefender(){ return this.type == PieceType.DEFENDER;}

    public boolean isDefenderOrKing(){ return this.type == PieceType.DEFENDER || this.type == PieceType.KING;}

    public void setRow(int newRow){ c.row = newRow;}

    public void setCol(int newCol){ c.col = newCol;}

    public void setKing(boolean king){ type = PieceType.KING;}

    public void setAttacker(boolean attacker){ type = PieceType.ATTACKER;}

    public void setDefender(boolean defender){ type = PieceType.DEFENDER;}

    public String getSymbol(){
        if(type == PieceType.KING) return "K";
        else if(type == PieceType.DEFENDER) return "D";
        else return "A";
    }

    public boolean canMoveTo(Coordinate dest, Grid grid){

        if (grid.getPieceAtPosition(dest) != null) return false;
        if(dest.col != c.col && dest.row != c.row) return false;

        Coordinate dir = new Coordinate(Integer.compare(dest.row, c.row), Integer.compare(dest.col, c.col));

        Coordinate next = new Coordinate(c.row + dir.row, c.col + dir.col);

        while (next.row != dest.row || next.col != dest.col) {
            if (grid.getPieceAtPosition(next) != null) {
                return false;
            }
            next.setRow(next.row + dir.row);
            next.setCol(next.col + dir.col);
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
