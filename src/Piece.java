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

    /*Position vulnérable = n'importe ou sur le plateau sauf sur le trone ou à directement à côté*/
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
        Piece copy = new Piece(c, getType());
        return copy;
    }



    public ArrayList possibleMoves(Piece[][] board) {
        ArrayList<Coordinate> MovesToPieceList = new ArrayList<>();
        for (int i = c.col - 1; i >= 0; i--){
            if (board[c.row][i] == null) MovesToPieceList.add(new Coordinate(c.row, i));
            else break;
        }
        for (int i = c.col + 1; i < 9; i++){
            if (board[c.row][i] == null) MovesToPieceList.add(new Coordinate(c.row, i));
            else break;
        }

        for (int r = c.row - 1; r >= 0; r--){
            if (board[r][c.col] == null) MovesToPieceList.add(new Coordinate(r, c.col));
            else break;
        }
        for (int r = c.row + 1; r < 9; r++){
            if (board[r][c.col] == null) MovesToPieceList.add(new Coordinate(r, c.col));
            else break;
        }
        return MovesToPieceList;
    }

}
