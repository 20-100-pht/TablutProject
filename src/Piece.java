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

    public boolean isAttacker(){ return this.type == PieceType.ATTACKER;}

    public boolean isDefender(){ return this.type == PieceType.DEFENDER;}

    public boolean isDefenderOrKing(){ return this.type == PieceType.DEFENDER || this.type == PieceType.KING;}

    public void setRow(int row){ this.row = row;}

    public void setCol(int col){ this.col = col;}

    public void setKing(boolean king){ type = PieceType.KING;}

    public void setAttacker(boolean attacker){ type = PieceType.ATTACKER;}

    public void setDefender(boolean defender){ type = PieceType.DEFENDER;}

    public String getSymbol(){
        if(type == PieceType.KING) return "K";
        else if(type == PieceType.DEFENDER) return "D";
        else return "A";
    }

    public boolean canMoveTo(int destRow, int destCol, Grid grid){

        int currentRow = getRow();
        int currentCol = getCol();

        if (grid.getPieceAtPosition(destRow, destCol) != null) return false;
        if(destCol != currentCol && destRow != currentRow) return false;

        int dirRow = Integer.compare(destRow, currentRow);
        int dirCol = Integer.compare(destCol, currentCol);

        int nextRow = currentRow + dirRow;
        int nextCol = currentCol + dirCol;



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
}
