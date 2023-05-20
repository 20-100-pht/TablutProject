package AI;

import Model.*;
import Structure.Coordinate;
import Structure.Direction;
import Structure.Node;

import java.util.ArrayList;
import java.util.List;

public class HeuristicUtils {

    static Coordinate CornerTopLeft = new Coordinate(0, 0);
    static Coordinate CornerTopRight = new Coordinate(0, 8);
    static Coordinate CornerBottomLeft = new Coordinate(8, 0);
    static Coordinate CornerBottomRight = new Coordinate(8, 8);

    public static double attackerCircleStrategy(Node node){

        Piece king = node.getLogicGrid().king;

        WeightedPositions wp = new WeightedPositions();
        int[][] weights = wp.getWeights().get(king.getRelativePosition().ordinal());

        double value = 0;
        Grid grid = node.getLogicGrid().getGrid();
        for(int i = 0; i<9; i++){
            for(int j = 0; j<9; j++){
                Piece piece = grid.getPieceAtPosition(new Coordinate(i,j));
                if( piece != null && piece.isAttacker()){
                    value +=weights[i][j];
                }
            }
        }

        return value;
    }

    public static int isNextToKing(Piece king, Grid grid){
        int x = king.getCol();
        int y = king.getRow();

        Piece leftPiece = grid.getPieceAtPosition(new Coordinate(y, x - 1));
        Piece rightPiece = grid.getPieceAtPosition(new Coordinate(y, x + 1));
        Piece topPiece = grid.getPieceAtPosition(new Coordinate(y - 1, x));
        Piece bottomPiece = grid.getPieceAtPosition(new Coordinate(y + 1, x));

        int risk = 0;
        if(leftPiece!=null && leftPiece.isAttacker()){
            risk = incrementalRisk(risk);
        }
        if(rightPiece!=null && rightPiece.isAttacker()){
            risk = incrementalRisk(risk);
        }
        if(topPiece!=null && topPiece.isAttacker()){
            risk = incrementalRisk(risk);
        }
        if(bottomPiece!=null && bottomPiece.isAttacker()){
            risk = incrementalRisk(risk);
        }

        return risk;
    }

    private static int incrementalRisk(int risk){
        switch (risk){
            case 0:
                return 1;
            case 1:
                return 4;
            case 4:
                return 10;
            case 10:
                return 10000;
            default:
                return 0;
        }
    }

    public static int isNextToKingAndSafe(Piece king, Grid grid) {
        int x = king.getCol();
        int y = king.getRow();

        Piece leftPiece = grid.getPieceAtPosition(new Coordinate(y, x - 1));
        Piece rightPiece = grid.getPieceAtPosition(new Coordinate(y, x + 1));
        Piece topPiece = grid.getPieceAtPosition(new Coordinate(y - 1, x));
        Piece bottomPiece = grid.getPieceAtPosition(new Coordinate(y + 1, x));

        int times = 0;
        if(leftPiece != null && leftPiece.getType() == PieceType.ATTACKER) times += isDefenderNextToIt(leftPiece, grid, 0,1);
        if(rightPiece != null && rightPiece.getType() == PieceType.ATTACKER) times += isDefenderNextToIt(rightPiece, grid, 0,1);
        if(topPiece != null && topPiece.getType() == PieceType.ATTACKER) times += isDefenderNextToIt(topPiece, grid, 2,3);
        if(bottomPiece != null && bottomPiece.getType() == PieceType.ATTACKER) times += isDefenderNextToIt(bottomPiece, grid, 2,3);
        return times;
    }

    private static int isDefenderNextToIt(Piece p, Grid grid, int i, int j){
        PieceType[] tab;
        if (p != null && p.isAttacker()) {
            tab = p.piecesNextToIt(grid);
            if (!(tab[i] == PieceType.DEFENDER ^ tab[j] == PieceType.DEFENDER)){
                return 1;
            }
        }
        return 0;
    }

    public static int GoToSuicide (Piece p, Grid grid, PieceType adverse){
        int result = 0;
        PieceType[] tab;
        if(p != null){
            tab = p.piecesNextToIt(grid);
            if( (p.getType() != adverse) && (tab[0] == adverse && tab[1] == adverse)) result += 2;
            else if ( (p.getType() != adverse) && (tab[2] == adverse && tab[3] == adverse)) result +=2;
        }
        return result;
        //Tab[0] == en haut
        //Tab[1] == en bas
        //Tab[2] == à droite
        //Tab[3] == à gauche
    }

    public static int canKingGoToCorner(Node n){
        Grid grid = n.getLogicGrid().getGrid();
        int x = n.getLogicGrid().getKing().getCol();
        int y = n.getLogicGrid().getKing().getRow();

        int value = 0;

        if (n.getLogicGrid().getKing().canMoveTo(new Coordinate(y, 0), grid)) {
            value += canGoTo(n.getLogicGrid().grid, new Coordinate(y, 0), Direction.LEFT);
        }
        if (n.getLogicGrid().getKing().canMoveTo(new Coordinate(y, 8), grid)) {
            value += canGoTo(n.getLogicGrid().grid, new Coordinate(y, 8), Direction.RIGHT);
        }
        if (n.getLogicGrid().getKing().canMoveTo(new Coordinate(0, x), grid)) {
            value += canGoTo(n.getLogicGrid().grid, new Coordinate(0, x), Direction.TOP);
        }
        if (n.getLogicGrid().getKing().canMoveTo(new Coordinate(8, x), grid)) {
            value += canGoTo(n.getLogicGrid().grid, new Coordinate(8, x), Direction.BOTTOM);
        }

        return value;
    }

    /**
     * Check if corners are accessible from source position
     *
     * @param grid
     * @param source
     * @return
     */
    private static int canGoTo(Grid grid, Coordinate source, Direction bord){

        switch (bord){
            case TOP:
                return calculateCorner(CornerTopLeft, CornerTopRight, source, grid);
            case LEFT:
                return calculateCorner(CornerTopLeft, CornerBottomLeft, source, grid);
            case RIGHT:
                return calculateCorner(CornerTopRight, CornerBottomRight, source, grid);
            case BOTTOM:
                return calculateCorner(CornerBottomLeft, CornerBottomRight, source, grid);
            default:
                return 0;
        }
    }

    private static int calculateCorner(Coordinate corner1, Coordinate corner2, Coordinate source, Grid grid){
        int result = 0;
        if(canMoveToSC(corner1, source, grid) ) result++;
        if(canMoveToSC(corner2, source, grid) ){
            if(result == 1) result = 1000;
            else result ++;
        }
        return result;
    }

    private static boolean canMoveToSC(Coordinate dest, Coordinate c, Grid grid){

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
     *
     * Methode with no usage
     *
     */

    public static int kingDistanceToCorner(Piece king){
        int x = king.getCol();
        int y = king.getRow();

        int center = 4;
        int border = 8;

        int valX = 0;
        if(x>=center && x<=border){
            valX = 4-(x-4);
        } else if (x<=center && x >=0) {
            valX = 4-(4-x);
        }

        int valY = 0;
        if(y>=center && y<=border){
            valY = 4-(y-4);
        } else if (y<=center && y>=0) {
            valY = 4-(4-y);
        }

        return (valY+valX);
    }

    private static int moveToKing(List<Coordinate> pieceCord, Coordinate king){
        for (Coordinate cord:pieceCord) {
            if(cord.isSameCoordinate(new Coordinate(king.getRow()-1, king.getCol())) ) return 1;
            if(cord.isSameCoordinate(new Coordinate(king.getRow()+1, king.getCol())) ) return 1;
            if(cord.isSameCoordinate(new Coordinate(king.getRow(), king.getCol()-1)) ) return 1;
            if(cord.isSameCoordinate(new Coordinate(king.getRow(), king.getCol()+1)) ) return 1;
        }
        return 0;
    }

    public static List<Coordinate> getMoves(Piece[][] board, Coordinate coord){
        List<Coordinate> legalMoves = new ArrayList<>();
        int x = coord.getCol();
        int y = coord.getRow();

        for(int i = -1; i <= 1; i++){
            for(int j = -1; j <= 1; j++){
                if(Math.abs(i) == Math.abs(j) || (i == 0 && j == 0)){
                    continue;
                }

                int dx = i;
                int dy = j;
                while(x + dx >= 0 && x + dx < board.length && y + dy >= 0 && y + dy < board.length){
                    if(board[y + dy][x + dx] == null){
                        legalMoves.add(new Coordinate(x + dx, y + dy));
                    } else {
                        break;
                    }

                    dx += i;
                    dy += j;
                }
            }
        }

        return legalMoves;
    }

    public static int isKingAtWalls(Grid grid, Piece King){
        if (King.getCol() == 0 || King.getCol() == 8 || King.getRow() == 0 || King.getRow() == 8){
            return 1;
        }
        return 0;
    }

}
