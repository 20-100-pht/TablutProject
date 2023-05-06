package Model;

import Structure.Coordinate;
import Structure.Node;

import java.util.ArrayList;
import java.util.List;

public class AIEasy extends AI {

    @Override
    public double heuristic(Node current, int depth){
        /*int king = 0;
        int attackers = 0;
        int defenders = 0;

        int nearKing = 0;

        Piece[][] board = grid.board;
        for(int y = 0; y < board.length; y++){
            for(int x = 0; x < board.length; x++){
                if(board[y][x]!=null){
                    switch (board[y][x].getType()){
                        case KING :
                            king++;
                            break;
                        case DEFENDER:
                            defenders++;
                            break;
                        case ATTACKER:
                            attackers++;
                            int dX = Math.abs(k.getCol()-x);
                            int dY = Math.abs(k.getRow()-y);
                            nearKing+= dX+dY;
                            break;
                    }
                }
            }
        }

        //TODO ranger ce bordel
        if(current.endGame() == ResultGame.ATTACKER_WIN)
            return 10000*depth;
        else if (current.endGame() == ResultGame.DEFENDER_WIN) {
            return -10000*depth;
        }

        //Heuristic qui semble faire du 50/50
        //double value = ((double) (1-king)*( defenders + (kingDistanceToCorner(k)+1)*10 + attackers + nearKing*17));

        double value = ((double)
                (attackers - defenders)*100 +
                (kingDistanceToCorner(k)+1)*1000 +
                -nearKing*1000 +
                isNextToKing(k,grid)*500 +
                canKingGoToWall(current,grid))*2000;

        //Attackers want a high value, Defenders want a low value
        //double value = ((double) (1-king)*( (attackers - defenders) + (kingDistanceToCorner(k)+1)*100));
        //return heuristic20100(board, k);
        return value;*/
        return 0;
    }

    public int kingDistanceToCorner(Coordinate king){
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

    private int isNextToKing(Coordinate king, Grid grid){
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

    private int canKingGoToWall(Node n, Grid grid){
       /* int x = n.getKing().getCol();
        int y = n.getKing().getRow();

        int value = 4;
        if(x == 0 || x == 8){
            value--;
        }
        if(y == 0 || y == 8){
            value--;
        }
        if(n.getKing().canMoveTo(new Coordinate(y,0),grid)){
            value--;
        }
        if(n.getKing().canMoveTo(new Coordinate(y,8),grid)){
            value--;
        }
        if(n.getKing().canMoveTo(new Coordinate(x,0),grid)){
            value--;
        }
        if(n.getKing().canMoveTo(new Coordinate(x,8),grid)){
            value--;
        }

        return value;*/
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
