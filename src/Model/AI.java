package Model;

import Structure.Coordinate;
import Structure.Coup;
import Structure.Node;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AI {

    GameRules gRules;
    int dep = 0;

    AI(){
        gRules = new GameRules();
    }

    public Coup minimax(Grid currentGrid, Piece king, int depth, PieceType type) {

        //printCoordArray(getPossiblePiecePositions(board,new Structure.Coordinate(3,4)));
        //printBoard(board);

        //System.out.println("Minimax board : ");
        //currentGrid.print();

        //Copy board
        dep = depth;

        //Create first node
        Node n = new Node(currentGrid, king, null, false);

        //Call minimax
        if (type == PieceType.ATTACKER){
            return minimaxAlgo(n, depth, true).getCoup();
        }else{
            return minimaxAlgo(n, depth, false).getCoup();
        }

    }

    private Node minimaxAlgo(Node node, int depth, boolean maximizingPlayer) {

        Grid currentBoard = node.getGrid();
        Piece currentKing = node.getKing();

        if (depth == 0 || node.endGame()) {
            node.setHeuristic(heuristic(currentBoard.board, currentKing.c));
            //System.out.println("Heuristic :" + node.getHeuristic());
            return node;
        }

        //Create all children of node (all possible states of current board)
        createNodeChildren(node, maximizingPlayer?PieceType.ATTACKER:PieceType.DEFENDER, depth);
        ArrayList<Node> children = node.getChildren();

        double value = 0;
        Node rtNode = children.get(0);

        if (maximizingPlayer) {
            //Attacker

            value = Double.NEGATIVE_INFINITY;
            for(int i = 0; i<children.size(); i++){
                int tmpD = depth-1;
                Node tmp = minimaxAlgo(children.get(i), tmpD, false);

                //Randomize selection of same heuristic nodes
                Random r = new Random();
                if(tmp.getHeuristic() > value || (tmp.getHeuristic() == value && r.nextInt()%2==0)){
                    value = tmp.getHeuristic();
                    rtNode=tmp;
                }
            }

        } else {
            //Defender

            value = Double.POSITIVE_INFINITY;
            for(int i = 0; i<children.size(); i++){
                int tmpD = depth -1;
                Node tmp = minimaxAlgo(children.get(i), tmpD, true);

                //Randomize selection of same heuristic nodes
                Random r = new Random();
                if(tmp.getHeuristic() < value || (tmp.getHeuristic() == value && r.nextInt()%2==0)){
                    value = tmp.getHeuristic();
                    rtNode=tmp;
                }
            }
        }

        //System.out.println(depth  + " " + maximizingPlayer);

        //If recursion depth is start depth return node of selected child (best value)
        if(dep == depth){
            //System.out.println("\n\nSelected node heuristic : " + rtNode.getHeuristic());
            //rtNode.getGrid().print();
            return rtNode;
        }
        //Else set current node's heuristic to best calculated value
        else node.setHeuristic(value);

        return node;
    }

    private double heuristic(Piece[][] board, Coordinate k){
        int king = 0;
        int attackers = 0;
        int defenders = 0;

        int nearKing = 256;
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
                            nearKing-= dX+dY;
                            break;
                    }

                }
            }
        }

        //isCaptured(board, k);

        /*Model.PieceType end = isEndGame(board,k);
        if(end == Model.PieceType.ATTACKER)
            return Double.POSITIVE_INFINITY;
        else if (end == Model.PieceType.DEFENDER) {
            return Double.NEGATIVE_INFINITY;
        }*/

        /**
         * Heuristic qui semble faire du 50/50
         double value = ((double) (1-king)*( defenders + (kingDistanceToCorner(k)+1)*30 + attackers + nearKing*10));
         *
         */

        //Attackers want a high value, Defenders want a low value
        double value = ((double) (1-king)*( (attackers - defenders) + (kingDistanceToCorner(k)+1)*100));
        return value;
    }

    private void createNodeChildren(Node father, PieceType type, int depth){

        int boardSize = father.getGrid().sizeGrid;
        Piece[][] board = father.getGrid().board;
        //Structure.Coordinate k = Father.king;

        //For each piece on board
        for(int y = 0; y < boardSize; y++){
            for(int x = 0; x < boardSize; x++){

                if(board[y][x] == null) continue;
                Piece current = board[y][x].clonePiece();

                //if Model.Piece is of type type
                if(current != null && (current.getType() == type || (current.getType() == PieceType.KING && type == PieceType.DEFENDER)) ){

                    //Get all possible moves for current piece
                    ArrayList<Coordinate> moves = current.possibleMoves(board);
                    if(moves.size() == 0) continue;

                    //Create and add children nodes
                    for(int i = 0; i < moves.size(); i++){

                        Grid newGrid = father.getGrid().cloneGrid();
                        gRules.grid = newGrid;
                        gRules.king = gRules.grid.getPieceAtPosition(father.getKing().c);

                        Piece currentPiece = newGrid.getPieceAtPosition(current.c);

                        Coup coup = new Coup(new Coordinate(currentPiece.c.getRow(),currentPiece.c.getCol()), new Coordinate(moves.get(i).getRow(),moves.get(i).getCol()));
                        gRules.move(coup);

                        int c = gRules.attack(currentPiece);

                        Piece currentKing;
                        if(currentPiece.isKing()){
                            currentKing = currentPiece;
                        }else{
                            currentKing = gRules.grid.getPieceAtPosition(father.getKing().c);
                        }

                        Node tmpNode = new Node(newGrid, currentKing, new Coup(new Coordinate(y,x),moves.get(i)), gRules.isEndGame());
                        father.addChild(tmpNode);
                    }
                }
            }
        }
    }

    public int kingDistanceToCorner(Coordinate king){
        int x = king.getCol();
        int y = king.getRow();

        int center = 4;
        int border = 8;

        int valX = 0;
        if(x>=center && x<=border){
            valX = 8-x;
        } else if (x<=center && x >=0) {
            valX = x-8;
        }

        int valY = 0;
        if(y>=center && y<=border){
            valY = y-4;
        } else if (y<=center && y>=0) {
            valY = 4-y;
        }

        return (valY+valX);
    }

}