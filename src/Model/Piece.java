package Model;

import Structure.Coordinate;

import java.util.ArrayList;

public class Piece {
    Coordinate c;
    private PieceType type;

    public Piece(Coordinate c, PieceType t) {
        this.c = c;
        this.type = t;
    }

    public int getRow(){ return c.getRow();}

    public int getCol(){ return c.getCol();}

    public PieceType getType(){ return this.type;}

    public boolean isKing(){ return this.type == PieceType.KING;}

    public boolean isSamePosition(Coordinate d){
        return d.getRow() == c.getRow() && d.getCol() == c.getCol();
    }

    /*Structure.Position vulnérable = n'importe ou sur le plateau sauf sur le trone ou à directement à côté*/
    public boolean kingIsOnVulnerablePosition(){
        return !( (c.getRow() == 4 && c.getCol() == 4) || (c.getRow() == 4 && c.getCol() == 3) || (c.getRow() == 4 && c.getCol() == 5) || (c.getRow() == 3 && c.getCol() == 4) || (c.getRow() == 5 && c.getCol() == 4) );
    }

    public boolean isAttacker(){ return this.type == PieceType.ATTACKER;}

    public boolean isDefender(){ return this.type == PieceType.DEFENDER;}

    public boolean isDefenderOrKing(){ return this.type == PieceType.DEFENDER || this.type == PieceType.KING;}

    public void setRow(int newRow){ c.setRowCoord(newRow);}

    public void setCol(int newCol){ c.setColCoord(newCol);}

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
        if(dest.getCol() != c.getCol() && dest.getRow() != c.getRow()) return false;

        Coordinate dir = new Coordinate(Integer.compare(dest.getRow(), c.getRow()), Integer.compare(dest.getCol(), c.getCol()));

        Coordinate next = new Coordinate(c.getRow() + dir.getRow(), c.getCol() + dir.getCol());

        while (next.getRow() != dest.getRow() || next.getCol() != dest.getCol()) {
            if (grid.getPieceAtPosition(next) != null) {
                return false;
            }
            next.setRowCoord(next.getRow() + dir.getRow());
            next.setColCoord(next.getCol() + dir.getCol());
        }

        return true;


    }

    public Piece clonePiece(){
        Piece copy = new Piece(new Coordinate(c.getRow(), c.getCol()), getType());
        return copy;
    }



    public ArrayList possibleMoves(Piece[][] board) {
        ArrayList<Coordinate> MovesToPieceList = new ArrayList<>();
        for (int i = c.getCol() - 1; i >= 0; i--){
            if (board[c.getRow()][i] == null) MovesToPieceList.add(new Coordinate(c.getRow(), i));
            else break;
        }
        for (int i = c.getCol() + 1; i < 9; i++){
            if (board[c.getRow()][i] == null) MovesToPieceList.add(new Coordinate(c.getRow(), i));
            else break;
        }

        for (int r = c.getRow() - 1; r >= 0; r--){
            if (board[r][c.getCol()] == null) MovesToPieceList.add(new Coordinate(r, c.getCol()));
            else break;
        }
        for (int r = c.getRow() + 1; r < 9; r++){
            if (board[r][c.getCol()] == null) MovesToPieceList.add(new Coordinate(r, c.getCol()));
            else break;
        }
        return MovesToPieceList;
    }

    public Coordinate getCoords(){
        return c;
    }
}
