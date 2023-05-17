package AI;

import Model.*;
import Structure.Coordinate;
import Structure.Direction;
import Structure.Node;

import java.util.ArrayList;
import java.util.List;

public class AIMedium extends AI {

    Coordinate CornerTopLeft = new Coordinate(0, 0);
    Coordinate CornerTopRight = new Coordinate(0, 8);
    Coordinate CornerBottomLeft = new Coordinate(8, 0);
    Coordinate CornerBottomRight = new Coordinate(8, 8);

    @Override
    public double heuristic(Node current, int depth, PieceType maximizingPlayer){

        double value = 0;
        if(current.getLogicGrid().getEndGameType() == ResultGame.ATTACKER_WIN) {
            value += 1000000*(depth+1);
            if(depth == 0) return value;
        }
        else if (current.getLogicGrid().getEndGameType() == ResultGame.DEFENDER_WIN) {
            value -= 1000000*(depth+1);
            if(depth == 0) return value;
        }

        switch (maximizingPlayer){
            case ATTACKER:
                value += attackerHeuristic(current, depth, maximizingPlayer);
            case KING: case DEFENDER:
                value -=  defenderHeuristic(current, depth, maximizingPlayer);
        }

        return value;
    }

    private double attackerHeuristic(Node current, int depth, PieceType maximizingPlayer){
        LogicGrid gRules = current.getLogicGrid();
        Piece k = gRules.getKing();
        Grid grid = gRules.getGrid();
        PieceType adverse;
        if(maximizingPlayer == PieceType.ATTACKER) adverse = PieceType.DEFENDER;
        else adverse = PieceType.ATTACKER;

        double value = 0;
        value += (double) (current.getLogicGrid().getNbPieceAttackerOnGrid()/(current.getLogicGrid().getNbPieceDefenderOnGrid()+1))*2;
        value += GoToSuicide(grid.getPieceAtPosition(current.getCoup().getDest()), grid, adverse);
        value += canKingGoToCorner(current)*-10;
        value += isNextToKing(current.getLogicGrid().getKing(), current.getLogicGrid().getGrid())*6;
        value += attackerCircleStrategy(current)*3;

        //Attackers want a high value, Defenders want a low value
        return value;
    }
    private double defenderHeuristic(Node current, int depth, PieceType maximizingPlayer){
        LogicGrid gRules = current.getLogicGrid();
        Piece k = gRules.getKing();
        Grid grid = gRules.getGrid();

        double value =0;
        value += gRules.getNbPieceDefenderOnGrid()*5;
        value -= isNextToKing(k,current.getLogicGrid().getGrid())*10;
        value += canKingGoToCorner(current)*100;

        return value;
    }



    private double attackerCircleStrategy(Node node){

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

        //if(value > 8) return 1;

        return value;
    }

    public int kingDistanceToCorner(Piece king){
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

    private int isNextToKing(Piece king, Grid grid){
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

    private int incrementalRisk(int risk){
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

    private int isNextToKingAndSafe(Piece king, Grid grid) {
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

    private int isDefenderNextToIt(Piece p, Grid grid, int i, int j){
        PieceType[] tab;
        if (p != null && p.isAttacker()) {
            tab = p.piecesNextToIt(grid);
            if (!(tab[i] == PieceType.DEFENDER ^ tab[j] == PieceType.DEFENDER)){
                return 1;
            }
        }
        return 0;
    }

        //Tab[0] == en haut
        //Tab[1] == en bas
        //Tab[2] == à droite
        //Tab[3] == à gauche
    public int GoToSuicide (Piece p, Grid grid, PieceType adverse){
        int result = 0;
        PieceType[] tab;
        if(p != null){
            tab = p.piecesNextToIt(grid);
            if( (p.getType() != adverse) && (tab[0] == adverse && tab[1] == adverse)) result += 2;
            else if ( (p.getType() != adverse) && (tab[2] == adverse && tab[3] == adverse)) result +=2;
        }
        return result;
    }


    public int canKingGoToCorner(Node n){
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

    private int isKingAtWalls(Grid grid, Piece King){
        if (King.getCol() == 0 || King.getCol() == 8 || King.getRow() == 0 || King.getRow() == 8){
            return 1;
        }
        return 0;
    }


    /**
     * Check if corners are accessible from source position
     *
     * @param grid
     * @param source
     * @return
     */
    private int canGoTo(Grid grid, Coordinate source, Direction bord){

        switch (bord){
            case TOP:
                return fautLuiTrouverUnNom(CornerTopLeft, CornerTopRight, source, grid);
            case LEFT:
                return fautLuiTrouverUnNom(CornerTopLeft, CornerBottomLeft, source, grid);
            case RIGHT:
                return fautLuiTrouverUnNom(CornerTopRight, CornerBottomRight, source, grid);
            case BOTTOM:
                return fautLuiTrouverUnNom(CornerBottomLeft, CornerBottomRight, source, grid);
            default:
                return 0;

        }
    }

    private int fautLuiTrouverUnNom(Coordinate corner1, Coordinate corner2, Coordinate source, Grid grid){
        int result = 0;
        if(canMoveToSC(corner1, source, grid) ) result++;
        if(canMoveToSC(corner2, source, grid) ){
            if(result == 1) result = 1000;
            else result ++;
        }
        return result;
    }

    public boolean canMoveToSC(Coordinate dest, Coordinate c, Grid grid){

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

















    private double heuristic20100(Grid grid, Coordinate k, Node current, int depth){
        /*int numAttackers = 0;
        int numDefenders = 0;
        int numPieces = 0;
        int centralKingBonus = 0;
        int focusKing = 0;

        Piece[][] board = grid.board;

        for(int y = 0; y < board.length; y++){
            for(int x = 0; x < board.length; x++){
                if(board[y][x] != null){

                    numPieces++;
                    if(board[y][x].getType() == PieceType.ATTACKER){
                        //focusKing += moveToKing(board[y][x].possibleMoves(board), k);
                        numAttackers++;
                    } else if(board[y][x].getType() == PieceType.DEFENDER){
                        numDefenders++;
                    }
                    if(board[y][x].getType() == PieceType.KING){
                        int distFromCenter = Math.abs(x - 4) + Math.abs(y - 4);
                        centralKingBonus += (8 - distFromCenter);
                    }
                }
            }
        }

        focusKing = isNextToKing(k,grid);

        if(current.endGame() == ResultGame.ATTACKER_WIN)
            return Double.POSITIVE_INFINITY;
        else if (current.endGame() == ResultGame.DEFENDER_WIN) {
            return Double.NEGATIVE_INFINITY;
        }

        //canKingWin(k, board);
        return  (double) 10 * ((double) numAttackers / numPieces) + 5 * ((double) numDefenders / numPieces) + 1 * centralKingBonus + focusKing*1000;
    */
        return 0;
    }


    private int moveToKing(List<Coordinate> pieceCord, Coordinate king){
        for (Coordinate cord:pieceCord) {
            if(cord.isSameCoordinate(new Coordinate(king.getRow()-1, king.getCol())) ) return 1;
            if(cord.isSameCoordinate(new Coordinate(king.getRow()+1, king.getCol())) ) return 1;
            if(cord.isSameCoordinate(new Coordinate(king.getRow(), king.getCol()-1)) ) return 1;
            if(cord.isSameCoordinate(new Coordinate(king.getRow(), king.getCol()+1)) ) return 1;
        }
        return 0;
    }







    public List<Coordinate> getMoves(Piece[][] board, Coordinate coord){
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
}
