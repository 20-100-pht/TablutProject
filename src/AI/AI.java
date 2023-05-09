package AI;

import Model.*;
import Structure.Coordinate;
import Structure.Coup;
import Structure.Node;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Vector;

public abstract class AI {

    int dep = 0;

    public AI(){

    }

    /**
     * Returns the next (best or not) playable move
     * /!\ GameRules must be cloned first
     *
     * @param g current game rules
     * @param depth depth of search
     * @param type type of piece playing
     * @return a coup
     */
    public Coup playMove(GameRules g, int depth, PieceType type) {

        //Copy board
        dep = depth;

        //Set alpha and beta
        double alpha = Double.NEGATIVE_INFINITY;
        double beta = Double.POSITIVE_INFINITY;

        //Create first node
        Node n = new Node(g.cloneGameRules(), null, ResultGame.NO_END_GAME);

        //Call minimax
        return minimaxAlgo(n, depth, type, alpha, beta).getBestMove();
    }

    /**
     * Minimax algorithm with beta pruning,
     * searches the best next move based on different heuristics
     *
     * @param node current game state
     * @param depth current depth of search
     * @param maximizingPlayer type of player playing
     * @param alpha value of best board
     * @param beta value of worst board
     * @return current node with his children and best move
     */
    private Node minimaxAlgo(Node node, int depth, PieceType maximizingPlayer, double alpha, double beta) {

        GameRules currentGR = node.getGameRules();

        //Depth reached or end game
        if (depth == 0 || currentGR.getEndGameType() != ResultGame.NO_END_GAME ) {
            node.setHeuristic(heuristic(node, depth));

            return node;
        }

        //Create all children of node (all possible states of current board)
        createNodeChildren(node, maximizingPlayer, depth);
        ArrayList<Node> children = node.getChildren();

        if(children.size() == 0){
            return node;
        }

        //Return best node or best heuristic
        return maximizing(node, children, depth, alpha, beta, maximizingPlayer);

    }

    /**
     * Evaluates all children of current node
     *
     * @param currentNode current node
     * @param children children of current node
     * @param depth current depth of search
     * @param alpha value of best board
     * @param beta value of worst board
     * @param type type of piece playing
     * @return current node with his children and best move
     */
    private Node maximizing(Node currentNode,ArrayList<Node> children, int depth, double alpha, double beta, PieceType type){

        int tmpD = depth-1;
        Node rtNode = children.get(0);

        ResultGame win;
        double maxValue;
        PieceType opponent = type==PieceType.ATTACKER?PieceType.DEFENDER:PieceType.ATTACKER;

        double value;

        //Set values according to team
        if(type == PieceType.ATTACKER){
            value = Double.NEGATIVE_INFINITY;
            maxValue = 1000000;
            win = ResultGame.ATTACKER_WIN;
        }else{
            value = Double.POSITIVE_INFINITY;
            win = ResultGame.DEFENDER_WIN;
            maxValue = -1000000;
        }

        //For each child of node
        for(int i = 0; i<children.size(); i++){

            //If child is a wining move return move
            if(children.get(i).getGameRules().getEndGameType() == win){
                //System.out.println("Is wining move");

                //The closer to the root, the faster we win, the bigger the value
                value = maxValue*(depth+1);
                rtNode = children.get(i);
                break;
            }

            //Calculate next best move
            Node tmp = minimaxAlgo(children.get(i), tmpD, opponent, alpha, beta);

            //Randomize selection of best heuristics
            Random r = new Random();
            double tmpHeuristic = tmp.getHeuristic();
            //Compare best value with calculated heuristic
            if((tmpHeuristic > value && type == PieceType.ATTACKER) ||
                    (tmpHeuristic < value && type == PieceType.DEFENDER) ||
                    (tmpHeuristic == value && (r.nextInt()%(children.size()*2))==0)){
                value = tmpHeuristic;
                rtNode=tmp;
            }

            if(type == PieceType.ATTACKER){
                //Beta pruning
                alpha = Math.max(alpha,value);
                if(alpha >= beta){
                    break;
                }
            }else {
                //alpha pruning
                beta = Math.min(beta,value);
                if(beta <= alpha){
                    break;
                }
            }

        }

        //Set current node's heuristic to best calculated value and best move
        currentNode.setHeuristic(value);
        currentNode.setBestMove(rtNode.getCoup());

        return currentNode;
    }

    /**
     * Creates all possible versions of board 1 move away
     *
     * @param father main board
     * @param type type of piece playing
     * @param depth depth of search
     */
    private void createNodeChildren(Node father, PieceType type, int depth){

        GameRules fatherGR = father.getGameRules();
        int boardSize = fatherGR.getGrid().getSizeGrid();
        Piece[][] board = fatherGR.getGrid().board;

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
                    for (Coordinate coordDest : moves){
                        //Create ne game rules
                        GameRules newGameRules = fatherGR.cloneGameRules();

                        //Get piece to move
                        Piece pieceToMove = newGameRules.getGrid().getPieceAtPosition(current.c);

                        //Create movement
                        Coup coup = new Coup(new Coordinate(pieceToMove.c.getRow(),pieceToMove.c.getCol()), new Coordinate(coordDest.getRow(),coordDest.getCol()));

                        //Move piece
                        newGameRules.move(coup);

                        //Check if king is captured
                        newGameRules.capture();

                        //Attack
                        Vector<Piece> c = newGameRules.attack(pieceToMove);

                        //Check if defenders win
                        if(type == PieceType.DEFENDER){
                            //Call to check if defenders have won
                            newGameRules.isDefenderWinConfiguration();
                        }

                        ResultGame end = newGameRules.getEndGameType();

                        Node tmpNode = new Node(newGameRules, new Coup(new Coordinate(y,x),coordDest), end);


                        //Add child to first place
                        if(c.size()>=1 || (end == ResultGame.ATTACKER_WIN && type == PieceType.ATTACKER) || (end == ResultGame.DEFENDER_WIN && type == PieceType.DEFENDER)){
                            if(end != ResultGame.NO_END_GAME){
                                //System.out.println(end + " in "+ ((dep+1)-depth) + " moves");
                                /*System.out.println(end + " in "+ ((dep+1)-depth) + " moves");
                                newGameRules.grid.print();
                                System.out.println("\n");*/
                            }
                            father.addChildTo(0,tmpNode);
                        }else{
                            father.addChild(tmpNode);
                        }

                    }
                }
            }
        }
    }

    /**
     * Evaluate board
     *
     * @param current current state of a board
     * @param depth depth of the board
     * @return a value indicating the winnability of the board
     */
    public abstract double heuristic(Node current, int depth);

}