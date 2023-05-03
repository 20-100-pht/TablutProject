package Model;

import Model.*;
import Structure.Coordinate;
import Structure.Coup;
import Structure.Node;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AI {

    GameRules gRules;
    int dep = 0;

    /*Les poids associés aux différents paramètres que nous évaluons dans l'heuristique*/
    int WAttackerOnTot, WDefenderOnTot, WDistanceKingToCorner;
//TODO multiplier les poids et les paramètres
    public AI(){
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
        Node n = new Node(currentGrid, king, null, ResultGame.NO_END_GAME);

        double alpha = Double.NEGATIVE_INFINITY;
        double beta = Double.POSITIVE_INFINITY;
        //Call minimax
        if (type == PieceType.ATTACKER){
            return minimaxAlgo(n, depth, true, alpha, beta).getCoup();
        }else{
            return minimaxAlgo(n, depth, false, alpha, beta).getCoup();
        }

    }

    private Node minimaxAlgo(Node node, int depth, boolean maximizingPlayer, double alpha, double beta) {

        Grid currentBoard = node.getGrid();
        Piece currentKing = node.getKing();

        if (depth == 0 || node.endGame() != ResultGame.NO_END_GAME ) {
            node.setHeuristic(heuristic20100(currentBoard.board, currentKing.c));//, node));
            //System.out.println("Heuristic :" + node.getHeuristic());
            return node;
        }

        //Create all children of node (all possible states of current board)
        createNodeChildren(node, maximizingPlayer?PieceType.ATTACKER:PieceType.DEFENDER, depth);
        ArrayList<Node> children = node.getChildren();

        double value = 0;

        if(children.size() == 0){
            return node;
        }

        Node rtNode = children.get(0);

        if (maximizingPlayer) {
            //Attacker

            value = Double.NEGATIVE_INFINITY;
            for(int i = 0; i<children.size(); i++){
                int tmpD = depth-1;
                Node tmp = minimaxAlgo(children.get(i), tmpD, false, alpha, beta);

                //Randomize selection of same heuristic nodes
                Random r = new Random();
                if(tmp.getHeuristic() > value || (tmp.getHeuristic() == value && r.nextInt()%2==0)){
                    value = tmp.getHeuristic();
                    rtNode=tmp;
                }

                //Beta pruning
                if(value >= beta){
                    //If return current node with best value
                    if(dep!=depth){
                        node.setHeuristic(value);
                        return node;
                    }
                    //Return child with best value
                    return rtNode;
                }
                alpha = Math.max(alpha,value);
            }

        } else {
            //Defender

            value = Double.POSITIVE_INFINITY;
            int tmpD = depth -1;
            for(int i = 0; i<children.size(); i++){

                Node tmp = minimaxAlgo(children.get(i), tmpD, true, alpha, beta);

                //Randomize selection of same heuristic nodes
                Random r = new Random();
                if(tmp.getHeuristic() < value || (tmp.getHeuristic() == value && r.nextInt()%2==0)){
                    value = tmp.getHeuristic();
                    rtNode=tmp;
                }

                if(alpha >= value){
                    //If return current node with best value
                    if(dep!=depth){
                        node.setHeuristic(value);
                        return node;
                    }
                    //Return child with best value
                    return rtNode;
                }
                beta = Math.min(beta,value);
            }
        }

        //If recursion depth is start depth return node of selected child (best value)
        if(dep == depth){
            //System.out.println("\n\nSelected node heuristic : " + rtNode.getHeuristic());
            //rtNode.getGrid().print();
            //System.exit(0);
            return rtNode;
        }
        //Else set current node's heuristic to best calculated value
        else node.setHeuristic(value);

        return node;
    }

    private double heuristic(Piece[][] board, Coordinate k, Node current){
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

        //TODO ranger ce bordel
        if(current.endGame() == ResultGame.ATTACKER_WIN)
            return Double.POSITIVE_INFINITY;
        else if (current.endGame() == ResultGame.DEFENDER_WIN) {
            return Double.NEGATIVE_INFINITY;
        }

        //Heuristic qui semble faire du 50/50
        //double value = ((double) (1-king)*( defenders + (kingDistanceToCorner(k)+1)*10 + attackers + nearKing*17));

        double value = ((double) (attackers - defenders)*1000 + nearKing*10000);

        //Attackers want a high value, Defenders want a low value
        //double value = ((double) (1-king)*( (attackers - defenders) + (kingDistanceToCorner(k)+1)*100));
        //return heuristic20100(board, k);
        return value;
    }

    private void createNodeChildren(Node father, PieceType type, int depth){

        int boardSize = father.getGrid().getSizeGrid();
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
                    for (Coordinate coordMove : moves){

                        Grid newGrid = father.getGrid().cloneGrid();
                        gRules.grid = newGrid;
                        gRules.king = gRules.grid.getPieceAtPosition(father.getKing().c);

                        Piece currentPiece = newGrid.getPieceAtPosition(current.c);

                        Coup coup = new Coup(new Coordinate(currentPiece.c.getRow(),currentPiece.c.getCol()), new Coordinate(coordMove.getRow(),coordMove.getCol()));
                        gRules.move(coup);

                        int c = gRules.attack(currentPiece);

                        Piece currentKing;
                        if(currentPiece.isKing()){
                            currentKing = currentPiece;
                        }else{
                            currentKing = gRules.grid.getPieceAtPosition(father.getKing().c);
                        }

                        Node tmpNode = new Node(newGrid, currentKing, new Coup(new Coordinate(y,x),coordMove), gRules.isEndGameType());

                        //Add child to first place
                        if(c>=1){
                            father.addChildTo(0,tmpNode);
                        }
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


    private double heuristic20100(Piece[][] board, Coordinate k){
        int numAttackers = 0;
        int numDefenders = 0;
        int numPieces = 0;
        int mobilityAdvantage = 0;
        int centralKingBonus = 0;


        for(int y = 0; y < board.length; y++){
            for(int x = 0; x < board.length; x++){
                if(board[y][x] != null){
                    numPieces++;
                    if(board[y][x].getType() == PieceType.ATTACKER){
                        numAttackers++;
                    } else if(board[y][x].getType() == PieceType.DEFENDER){
                        numDefenders++;
                    }

                    // avantage mobilité
                    int numMoves = getLegalMoves(board, new Coordinate(x, y)).size();
                    if(board[y][x].isAttacker()){
                        mobilityAdvantage += numMoves;
                    } else {
                        mobilityAdvantage -= numMoves;
                    }

                    // roi position trone
                    if(board[y][x].getType() == PieceType.KING){
                        int distFromCenter = Math.abs(x - 4) + Math.abs(y - 4);
                        centralKingBonus += (8 - distFromCenter);
                    }
                }
            }
        }

        return  (double) 1000 * ((double) numAttackers / numPieces) + 500 * ((double) numDefenders / numPieces) + 10 * mobilityAdvantage + 5 * centralKingBonus * 100;
    }

    public List<Coordinate> getLegalMoves(Piece[][] board, Coordinate coord){
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