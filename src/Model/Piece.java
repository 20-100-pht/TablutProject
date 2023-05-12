package Model;

import Structure.Coordinate;

import java.io.Serializable;
import java.util.ArrayList;

public class Piece implements Serializable {

    public Coordinate c;
    private PieceType type;

    /**
     * Class constructor creating a piece with coordinates and type
     *
     * @param c coordinate of piece on grid
     * @param t type of piece
     */
    public Piece(Coordinate c, PieceType t) {
        this.c = c;
        this.type = t;
    }

    public int getRow(){ return c.getRow();}

    public int getCol(){ return c.getCol();}

    public PieceType getType(){ return this.type;}

    /**
     * Check if piece is of type King
     * @return true/false
     */
    public boolean isKing(){ return this.type == PieceType.KING;}

    /**
     * Check if a given coordinate is equal to it's current one
     * @param d coordinate to check
     * @return true/false
     */
    public boolean isSamePosition(Coordinate d){
        return d.getRow() == c.getRow() && d.getCol() == c.getCol();
    }

    /*Structure.Position vulnérable = n'importe ou sur le plateau sauf sur le trone ou à directement à côté*/
    public boolean kingIsOnVulnerablePosition() {
        return !((c.getRow() == 4 && c.getCol() == 4) || (c.getRow() == 4 && c.getCol() == 3) || (c.getRow() == 4 && c.getCol() == 5) || (c.getRow() == 3 && c.getCol() == 4) || (c.getRow() == 5 && c.getCol() == 4));
    }

    /**
     * Check if piece is of type Attacker
     * @return true/false
     */
    public boolean isAttacker(){ return this.type == PieceType.ATTACKER;}

    /**
     * Check if piece is of type Defender
     * @return true/false
     */
    public boolean isDefender(){ return this.type == PieceType.DEFENDER;}

    /**
     * Check if piece is of type Defender or King
     * @return true/false
     */
    public boolean isDefenderOrKing(){ return this.type == PieceType.DEFENDER || this.type == PieceType.KING;}

    public void setRow(int newRow){ c.setRowCoord(newRow);}

    public void setCol(int newCol){ c.setColCoord(newCol);}

    public void setKing(boolean king){ type = PieceType.KING;}

    public void setAttacker(boolean attacker){ type = PieceType.ATTACKER;}

    public void setDefender(boolean defender){ type = PieceType.DEFENDER;}

    /**
     * Get symbol of piece type
     * @return string
     */
    public String getSymbol(){
        if(type == PieceType.KING) return "K";
        else if(type == PieceType.DEFENDER) return "D";
        else return "A";
    }

    /**
     * Check if piece can move to given coordinate on given grid
     * @param dest destination coordinate
     * @param grid current grid
     * @return true/false
     */
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

    /**
     * Clone piece
     * @return cloned piece
     */
    public Piece clonePiece(){
        return new Piece(new Coordinate(c.getRow(), c.getCol()), getType());
    }

    /**
     * Get all possible moves of piece
     * Can jump throne
     * @param board current board
     * @return array of destination coordinates
     */
    public ArrayList<Coordinate> possibleMoves(Piece[][] board) {
        boolean isKing = getType() == PieceType.KING;
        int throne = 4;
        int col = c.getCol();
        int row = c.getRow();
        ArrayList<Coordinate> MovesToPieceList = new ArrayList<>();

        //Add coordinate if not throne and not fortress (if not king)
        for (int i = col -1; i >= 0; i--){
            if (board[row][i] == null){
                if(row == throne && i == throne ||((i ==0 || i == 8)&&(row == 0 || row == 8) && !isKing)) continue;
                MovesToPieceList.add(new Coordinate(row, i));
                }
            else break;
        }
        for (int i = col + 1; i < 9; i++){
            if (board[row][i] == null){
                if(row == throne && i == throne ||((i ==0 || i == 8)&&(row == 0 || row == 8) && !isKing)) continue;
                MovesToPieceList.add(new Coordinate(row, i));
            }
            else break;
        }

        for (int r = row - 1; r >= 0; r--){
            if (board[r][col] == null){
                if(col == throne && r == throne || ((r ==0 || r == 8)&&(col == 0 || col == 8) && !isKing)) continue;
                MovesToPieceList.add(new Coordinate(r, col));
            }
            else break;
        }
        for (int r = row + 1; r < 9; r++){
            if (board[r][col] == null){
                if(col == throne && r == throne || ((r ==0 || r == 8)&&(col == 0 || col == 8)&& !isKing)) continue;
                MovesToPieceList.add(new Coordinate(r, col));
            }
            else break;
        }

        return MovesToPieceList;
    }


    public PieceType[] piecesNextToIt(Grid grid){
        PieceType[] tab = new PieceType[4];
        int r = getRow(); int c = getCol();
        if(r < 8 && grid.board[r+1][c] != null) tab[0] = grid.board[r+1][c].getType();
        if(r > 0 && grid.board[r-1][c] != null) tab[1] = grid.board[r-1][c].getType();
        if(c < 8 && grid.board[r][c+1] != null) tab[2] = grid.board[r][c+1].getType();
        if(c > 0 && grid.board[r][c-1] != null) tab[3] = grid.board[r][c-1].getType();
        return tab;
    }
    public Coordinate getCoords(){
        return new Coordinate(getRow(), getCol());
    }
    public void setCoords(Coordinate coords){
        c = coords;
    }

    public boolean inSameTeam(Piece otherPiece){
        return type == otherPiece.getType() || (type == PieceType.KING && otherPiece.getType() == PieceType.DEFENDER)
                || (type == PieceType.DEFENDER && otherPiece.getType() == PieceType.KING);
    }
}
