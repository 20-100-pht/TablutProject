package AI;

import Model.*;
import Structure.Coordinate;
import Structure.Direction;
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
        /*
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



        //Heuristic qui semble faire du 50/50
        //double value = ((double) (1-king)*( defenders + (kingDistanceToCorner(k)+1)*10 + attackers + nearKing*17));

        //double value = ((double)
        //                (attackers - defenders)*100 +
        //                (kingDistanceToCorner(k)+1)*1000 +
        //                //-nearKing*1000 +
        //                isNextToKing(k,current.getLogicGrid().getGrid())*500 +
        //                canKingGoToCorner(current,current.getLogicGrid().getGrid()))*2000;

        double value = (double)
                (attackers)*500; /*+
                isNextToKingAndSafe(k,current.getLogicGrid().getGrid())*1000 +
                canKingGoToCorner(current)*1000;*/

        //Attackers want a high value, Defenders want a low value
        //double value = ((double) (1-king)*( (attackers - defenders) + (kingDistanceToCorner(k)+1)*100));*/
        int value=0;
        if(current.getLogicGrid().getEndGameType() == ResultGame.ATTACKER_WIN)
            value+=1000000*(depth+1);
        else if (current.getLogicGrid().getEndGameType() == ResultGame.DEFENDER_WIN) {
            value-=1000000*(depth+1);
        }
        value+=(8-gRules.getNbPieceDefenderOnGrid())*3000;
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

    private int isNextToKingAndSafe(Piece king, Grid grid){
        int x = king.getCol();
        int y = king.getRow();

        Piece leftPiece = grid.getPieceAtPosition(new Coordinate(y, x - 1));
        Piece rightPiece = grid.getPieceAtPosition(new Coordinate(y, x + 1));
        Piece topPiece = grid.getPieceAtPosition(new Coordinate(y - 1, x));
        Piece bottomPiece = grid.getPieceAtPosition(new Coordinate(y + 1, x));
//todo je vais le réduire no soucis les frérot et en vrai à tester
        int times = 0;
        boolean Secure = true;
        if(leftPiece!=null && leftPiece.isAttacker()){
           // regarde la colonne de la ièce gauche -1
            if (x-2>=0) {
                for (int i = y; i >= 0 ; i--) {
                    Piece tmp = grid.getPieceAtPosition(new Coordinate(i, x - 2));
                    if (tmp == null) continue;
                    if (tmp.isAttacker()){
                        break;
                    }
                    if (tmp.isDefender()){
                        Secure = false;
                    }
                }
                for (int i = y; i <= 8 ; i++) {
                    Piece tmp = grid.getPieceAtPosition(new Coordinate(i, x - 2));
                    if (tmp == null) continue;
                    if (tmp.isAttacker()){
                        break;
                    }
                    if (tmp.isDefender()){
                        Secure = false;
                    }
                }

            }
            if (Secure) times++;
            Secure = true;
        }
        if(rightPiece!=null && rightPiece.isAttacker()){
            // regarde la colonne de la ièce gauche -1
            if (x+2<9) {
                for (int i = y; i >= 0 ; i--) {
                    Piece tmp = grid.getPieceAtPosition(new Coordinate(i, x + 2));
                    if (tmp == null) continue;
                    if (tmp.isAttacker()){
                        break;
                    }
                    if (tmp.isDefender()){
                        Secure = false;
                    }
                }
                for (int i = y; i <= 8 ; i++) {
                    Piece tmp = grid.getPieceAtPosition(new Coordinate(i, x + 2));
                    if (tmp == null) continue;
                    if (tmp.isAttacker()){
                        break;
                    }
                    if (tmp.isDefender()){
                        Secure = false;
                    }
                }

            }
            if (Secure) times++;
            Secure = true;
        }
        if(topPiece!=null && topPiece.isAttacker()){
            // regarde la colonne de la ièce gauche -1
            if (y+2<9) {
                for (int i = x; i >= 0 ; i--) {
                    Piece tmp = grid.getPieceAtPosition(new Coordinate(y+2, i));
                    if (tmp == null) continue;
                    if (tmp.isAttacker()){
                        break;
                    }
                    if (tmp.isDefender()){
                        Secure = false;
                    }
                }
                for (int i = x; i <= 8 ; i++) {
                    Piece tmp = grid.getPieceAtPosition(new Coordinate(y+2, i));
                    if (tmp == null) continue;
                    if (tmp.isAttacker()){
                        break;
                    }
                    if (tmp.isDefender()){
                        Secure = false;
                    }
                }

            }
            if (Secure) times++;
            Secure = true;
        }
        if(bottomPiece!=null && bottomPiece.isAttacker()){
            // regarde la colonne de la ièce gauche -1
            if (y-2>=0) {
                for (int i = x; i >= 0 ; i--) {
                    Piece tmp = grid.getPieceAtPosition(new Coordinate(y-2, i));
                    if (tmp == null) continue;
                    if (tmp.isAttacker()){
                        break;
                    }
                    if (tmp.isDefender()){
                        Secure = false;
                    }
                }
                for (int i = x; i <= 8 ; i++) {
                    Piece tmp = grid.getPieceAtPosition(new Coordinate(y-2, i));
                    if (tmp == null) continue;
                    if (tmp.isAttacker()){
                        break;
                    }
                    if (tmp.isDefender()){
                        Secure = false;
                    }
                }

            }
            if (Secure) times++;
            Secure = true;
        }

        return times;
    }


    private boolean isPieceSafe(Piece p, Grid grid, boolean isHorizontal){
        int x = p.getCol();
        int y = p.getRow();
        if (x-1>0) {
            for (int i = y; i >= 0 ; i--) {
                Piece tmp = grid.getPieceAtPosition(new Coordinate(i, x - 1));
                if (tmp.isAttacker()){
                    break;
                }
                if (tmp.isDefender()){
                    return false;
                }
            }
            for (int i = y; i <= 8 ; i++) {
                Piece tmp = grid.getPieceAtPosition(new Coordinate(i, x - 1));
                if (tmp.isAttacker()){
                    break;
                }
                if (tmp.isDefender()){
                    return false;
                }
            }
            return true;
        }else{
            return false;
        }
    }

    private int canKingGoToCorner(Node n){
        Grid grid = n.getLogicGrid().getGrid();
        int x = n.getLogicGrid().getKing().getCol();
        int y = n.getLogicGrid().getKing().getRow();

        int value = 0;

        if (n.getLogicGrid().getKing().canMoveTo(new Coordinate(y, 0), grid)) {
            value--;
            value -= canGoTo(n.getLogicGrid().grid, new Coordinate(y, 0), Direction.LEFT);
        }
        if (n.getLogicGrid().getKing().canMoveTo(new Coordinate(y, 8), grid)) {
            value--;
            value -= canGoTo(n.getLogicGrid().grid, new Coordinate(y, 8), Direction.RIGHT);
        }
        if (n.getLogicGrid().getKing().canMoveTo(new Coordinate(0, x), grid)) {
            value--;
            value -= canGoTo(n.getLogicGrid().grid, new Coordinate(0, x), Direction.TOP);
        }
        if (n.getLogicGrid().getKing().canMoveTo(new Coordinate(8, x), grid)) {
            value--;
            value -= canGoTo(n.getLogicGrid().grid, new Coordinate(8, x), Direction.BOTTOM);
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
    private int canGoTo(Grid grid, Coordinate source, Direction bord){

        int possible = 0;

        switch (bord){
            case TOP:
                if(canMoveToSC(new Coordinate(0,0), source, grid) ) possible++;
                if(canMoveToSC(new Coordinate(0,8), source, grid) ) possible++;
                break;
            case LEFT:
                if(canMoveToSC(new Coordinate(0,0), source, grid) ) possible++;
                if(canMoveToSC(new Coordinate(8,0), source, grid) ) possible++;
                break;
            case RIGHT:
                if(canMoveToSC(new Coordinate(0,8), source, grid) ) possible++;
                if(canMoveToSC(new Coordinate(8,8), source, grid) ) possible++;
                break;
            case BOTTOM:
                if(canMoveToSC(new Coordinate(8,0), source, grid) ) possible++;
                if(canMoveToSC(new Coordinate(8,8), source, grid) ) possible++;
                break;
            default:
                return 0;

        }

        return possible;
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
