package AI;

import Model.*;
import Structure.Coordinate;
import Structure.Node;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class AIMedium extends AI {

    @Override
    public double heuristic(Node current, int depth){
        int attackers = 0;
        int defenders = 0;
        int nearKing = 0;

        LogicGrid gRules = current.getLogicGrid();
        Piece k = gRules.getKing();
        Grid grid = gRules.getGrid();

        Piece[][] board = grid.board;
        for(int y = 0; y < board.length; y++){
            for(int x = 0; x < board.length; x++){
                if(board[y][x]!=null){
                    switch (board[y][x].getType()){
                        case DEFENDER:
                            defenders++;
                            break;
                        case ATTACKER:
                            attackers++;
                            int dX = Math.abs(k.getCol()-x);
                            int dY = Math.abs(k.getRow()-y);
                            nearKing+= dX+dY;
                            break;
                        default:
                            break;
                    }
                }
            }
        }

        if(current.getLogicGrid().getEndGameType() == ResultGame.ATTACKER_WIN)
            return 1000000*(depth+1);
        else if (current.getLogicGrid().getEndGameType() == ResultGame.DEFENDER_WIN) {
            return -1000000*(depth+1);
        }

        double value = (double)
                (attackers)*.05 +
                isNextToKing(k,current.getLogicGrid().getGrid())*0.15 -
                canKingGoToWall(current)*1;


        //Attackers want a high value, Defenders want a low value
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

        int times = 0;
        if(leftPiece!=null && leftPiece.isAttacker()){
            times++;
        }
        if(rightPiece!=null && rightPiece.isAttacker()){
            times++;
        }
        if(topPiece!=null && topPiece.isAttacker()){
            times++;
        }
        if(bottomPiece!=null && bottomPiece.isAttacker()){
            times++;
        }

        return times;
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
    private int canKingGoToWall(Node n){
        Grid grid = n.getLogicGrid().getGrid();
        int x = n.getLogicGrid().getKing().getCol();
        int y = n.getLogicGrid().getKing().getRow();

        int value = 12;

        if (n.getLogicGrid().getKing().canMoveTo(new Coordinate(y, 0), grid)) {
            value--;
            value -= canGoTo(n.getLogicGrid().grid, new Coordinate(y, 0));
        }
        if (n.getLogicGrid().getKing().canMoveTo(new Coordinate(y, 8), grid)) {
            value--;
            value -= canGoTo(n.getLogicGrid().grid, new Coordinate(y, 8));
        }
        if (n.getLogicGrid().getKing().canMoveTo(new Coordinate(0, x), grid)) {
            value--;
            value -= canGoTo(n.getLogicGrid().grid, new Coordinate(0, x));
        }
        if (n.getLogicGrid().getKing().canMoveTo(new Coordinate(8, x), grid)) {
            value--;
            value -= canGoTo(n.getLogicGrid().grid, new Coordinate(8, x));
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
    private int canGoTo(Grid grid, Coordinate source) {
        
        int dirX = 0;
        int dirY = 0;

        int y = source.getRow();
        int x = source.getCol();

        if(source.getRow() == 0 || source.getRow() == 8){
            dirY = 1;
        } else if(source.getCol() == 0 || source.getCol() == 8){
            dirX = 1;
        }else if ((source.getRow() == 0 || source.getRow() == 8) &&(source.getCol() == 0 || source.getCol() == 8)){
            return 0;
        }

        Coordinate top = new Coordinate(y + dirY, x + dirX);
        Coordinate bottom = new Coordinate(y - dirY, x - dirX);

        int exits = 0;
        while (top.getRow() < 9 && top.getCol() < 9) {
            if (grid.getPieceAtPosition(top) != null) {
                exits++;
                break;
            }
            top.setRowCoord(top.getRow() + dirY);
            top.setColCoord(top.getCol() + dirX);
        }

        while (bottom.getCol() >= 0 && bottom.getRow() >= 0) {
            if (grid.getPieceAtPosition(bottom) != null) {
                exits++;
                break;
            }
            bottom.setRowCoord(bottom.getRow() - dirY);
            bottom.setColCoord(bottom.getCol() - dirX);
        }

        return exits;
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
