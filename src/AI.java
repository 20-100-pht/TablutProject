import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AI {

    GameRules gRules;
    int dep = 0;

    AI(){
        gRules = new GameRules();
    }

    public Coup minimax(Grid currentGrid,Piece king,int depth, PieceType type) {

        //printCoordArray(getPossiblePiecePositions(board,new Coordinate(3,4)));
        //printBoard(board);

        //Copy board
        dep = depth;

        //Create first node
        Node n = new Node(currentGrid.cloneGrid(), king.clonePiece(), null, false);

        //Call minimax
        if (type == PieceType.ATTACKER){
            return minimaxAlgo(n, depth, true).coup;
        }else{
            return minimaxAlgo(n, depth, false).coup;
        }

    }

    private Node minimaxAlgo(Node node, int depth, boolean maximizingPlayer) {

        Grid currentBoard = node.grid;
        Piece currentKing = node.king;

        if (depth == 0 || node.isEndGame) {
            node.setHeuristic(heuristic(currentBoard.board, currentKing.c));
            //System.out.println(node.move.heuristic);
            return node; //Return heuristic
        }

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

                Random r = new Random();
                if(tmp.getHeuristic() < value || (tmp.getHeuristic() == value && r.nextInt()%2==0)){
                    value = tmp.getHeuristic();
                    rtNode=tmp;
                }
            }
        }

        //System.out.println(depth  + " " + maximizingPlayer);

        if(dep == depth) return rtNode;
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

        /*PieceType end = isEndGame(board,k);
        if(end == PieceType.ATTACKER)
            return Double.POSITIVE_INFINITY;
        else if (end == PieceType.DEFENDER) {
            return Double.NEGATIVE_INFINITY;
        }*/

        //Attackers want a high value, Defenders want a low value
        double value = ((double) (1-king)*20 + defenders + (kingDistanceToCorner(k)+1)*50 + attackers + nearKing*10);
        return value;
    }

    private void createNodeChildren(Node father, PieceType type, int depth){

        int boardSize = father.getGrid().sizeGrid;
        Piece[][] board = father.getGrid().board;
        //Coordinate k = Father.king;

        //For each piece on board
        for(int y = 0; y < boardSize; y++){
            for(int x = 0; x < boardSize; x++){

                if(board[y][x] == null) continue;
                Piece current = board[y][x].clonePiece();

                //if Piece is of type type
                if(current != null && (current.getType() == type || (current.getType() == PieceType.KING && type == PieceType.DEFENDER)) ){

                    //Get all possible moves for current piece
                    ArrayList<Coordinate> moves = current.possibleMoves(board);
                    if(moves.size() == 0) continue;

                    //Create and add children nodes
                    for(int i = 0; i < moves.size(); i++){

                        Grid newGrid = father.grid.cloneGrid();
                        gRules.grid = newGrid;
                        gRules.king = gRules.grid.getPieceAtPosition(father.king.c);

                        Piece currentPiece = newGrid.getPieceAtPosition(current.c);

                        Coup coup = new Coup(new Coordinate(currentPiece.c.getRow(),currentPiece.c.getCol()), new Coordinate(moves.get(i).getRow(),moves.get(i).getCol()));
                        gRules.move(coup);

                        int c = gRules.attack(currentPiece);

                        Piece currentKing;
                        if(currentPiece.isKing()){
                            currentKing = currentPiece;
                        }else{
                            currentKing = gRules.grid.getPieceAtPosition(father.king.c);
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
            valX = x-4;
        } else if (x<=center && x >=0) {
            valX = 4-x;
        }

        int valY = 0;
        if(y>=center && y<=border){
            valY = y-4;
        } else if (y<=center && y>=0) {
            valY = 4-y;
        }

        return (valY+valX);
    }

    public int distanceToKing(Piece[][] board,Coordinate king){
        int x = king.getCol();
        int y = king.getRow();

        int val = 0;
        for(int i = 0; i< board.length; i++){
            for(int j = 0; j< board.length; j++){
                if(board[i][j] != null && board[i][j].getType() == PieceType.ATTACKER){
                    int dX = Math.abs(j-x);
                    int dY = Math.abs(i-y);
                    val+= dX+dY;
                }
            }
        }

        return val;
    }

}