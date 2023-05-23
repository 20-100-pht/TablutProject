package AI;

import Model.*;
import Structure.Coordinate;
import Structure.Coup;
import Structure.Node;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

public abstract class AI implements Serializable {

    ArrayList<Grid> grids = new ArrayList<>();
    ArrayList<Integer> gridTimes = new ArrayList<>();

    int startingDepth = 0;
    int aiType = -1;

    int prunning_alpha_cutoffs = 0;
    int prunning_beta_cutoffs = 0;
    PieceType player;


    public AI(){
    }

    /**
     * Returns the next (best or not) playable move
     *
     * @param g current game rules
     * @param depth depth of search
     * @param type type of piece playing
     * @return a coup
     */
    public Coup playMove(LogicGrid g, int depth, PieceType type) {

        player = type;

        //Set alpha and beta
        double alpha = Double.NEGATIVE_INFINITY;
        double beta = Double.POSITIVE_INFINITY;

        prunning_alpha_cutoffs = 0;
        prunning_beta_cutoffs = 0;

        startingDepth = depth;

        //Create first node
        Node n = new Node(g.cloneLogicGrid(), null);

        int colour = 1;
        if(type == PieceType.DEFENDER){
            colour = -1;
        }

        //Create all children of node
        ArrayList<Node> children =  createChildren(n, colour, depth);

        double bestValue = 0;
        ArrayList<Coup> bestCoups = new ArrayList<>();
        //Get best move
        for (int i = 0; i < children.size(); i++) {

            Node child = children.get(i);
            
            //Call minimax and return heuristic
            double value = minimax(child, depth-1, -colour, alpha, beta);

            if(i==0){
                bestValue = value;
            }

            if(colour == 1){
                if(bestValue < value){
                    bestValue = value;
                    bestCoups.clear();
                }
            }else{
                if(bestValue > value){
                    bestValue = value;
                    bestCoups.clear();
                }
            }

            if(bestValue == value){
                bestCoups.add(child.getCoup());
            }
        }

        Random r = new Random();
        Coup nextMove = bestCoups.get(r.nextInt(bestCoups.size()));

        return nextMove;
    }


    /**
     * Minimax algorithm with alpha beta pruning
     * @param node Board configuration
     * @param depth Current search depth
     * @param colour Maximizing player
     * @param alpha Alpha value for pruning
     * @param beta Beta value for pruning
     * @return heuristic value
     */
    public double minimax(Node node, int depth, int colour, double alpha, double beta){

        PieceType maximizingPlayer = PieceType.ATTACKER;
        double value = Double.NEGATIVE_INFINITY;
        if(colour == -1){
            maximizingPlayer = PieceType.DEFENDER;
            value = Double.POSITIVE_INFINITY;
        }

        if(depth == 0 || node.getLogicGrid().isEndGame()){
            return heuristic(node,depth,maximizingPlayer, player);
        }

        //Get all children of node
        ArrayList<Node> children = createChildren(node,colour,depth);
        node.setChildren(children);

        for(int i = 0; i < children.size(); i++){

            double heuristic = minimax(children.get(i), depth-1, (-colour), alpha, beta);

            if(colour == 1){
                value = Math.max(value,heuristic);

                //Max
                if (value > beta) {
                    prunning_beta_cutoffs++;
                    break;  // Beta cutoff
                }
                alpha = Math.max(alpha, value);

            }else{
                value = Math.min(value,heuristic);

                //Min
                if (value < alpha) {
                    prunning_alpha_cutoffs++;
                    break;  // Alpha cutoff
                }
                beta = Math.min(beta, value);
            }
        }

        return value;
    }


    /**
     * Create all versions of a board;
     * @param node Board configuration
     * @param colour Maximizing player
     * @param depth Current search depth
     * @return heuristic value
     */
    private ArrayList<Node> createChildren(Node node, int colour, int depth){
        int boardSize = node.getLogicGrid().getGrid().getSizeGrid();
        Piece[][] board = node.getLogicGrid().getGrid().getBoard();

        ArrayList<Node> children = new ArrayList<>();

        ResultGame winType = ResultGame.ATTACKER_WIN;
        PieceType type = PieceType.ATTACKER;
        if(colour == -1){
            type = PieceType.DEFENDER;
            winType = ResultGame.DEFENDER_WIN;
        }

        for(int y = 0; y < boardSize; y++){
            for(int x = 0; x < boardSize; x++){
                Piece current = board[y][x];
                if(current != null && (current.getType() == type || (current.isKing() && type == PieceType.DEFENDER))){
                    ArrayList<Coordinate> moves = current.possibleMoves(node.getLogicGrid().getGrid().getBoard());
                    for(int i = 0; i < moves.size(); i++){
                        Node child = createChild(node, current, moves.get(i));

                        if(child.getLogicGrid().getEndGameType() == winType ||
                                (child.getLogicGrid().getNbPieceAttackerOnGrid()/(child.getLogicGrid().getNbPieceDefenderOnGrid()+1) >= 2 && colour == 1) ||
                                (child.getLogicGrid().getNbPieceAttackerOnGrid()/(child.getLogicGrid().getNbPieceDefenderOnGrid()+1) < 1.5 && colour == -1)){
                            children.add(0,child);
                        }else{
                            children.add(child);
                        }
                    }
                }
            }
        }
        return children;
    }

    private Node createChild(Node node,Piece piece, Coordinate dest){
        LogicGrid newLogicGrid = node.getLogicGrid().cloneLogicGrid();

        //Get piece to move
        Piece pieceToMove = newLogicGrid.getGrid().getPieceAtPosition(piece.getCoords());

        //Create move
        Coordinate source = new Coordinate(pieceToMove.getRow(), pieceToMove.getCol());
        Coordinate destination = new Coordinate(dest.getRow(), dest.getCol());
        Coup newCoup = new Coup(source,destination);

        //Move piece
        newLogicGrid.move(newCoup);

        //Check if king is captured
        newLogicGrid.capture();

        //Attack
        newLogicGrid.attack(pieceToMove);

        newLogicGrid.isDefenderWinConfiguration();

        Node newChild = new Node(newLogicGrid,newCoup);
        return newChild;
    }


    /**
     * Evaluate board
     *
     * @param current current state of a board
     * @param depth depth of the board
     * @return a value indicating the winnability of the board
     */
    public abstract double heuristic(Node current, int depth,PieceType maximizingPlayer, PieceType player);

    public int getAiType(){
        return aiType;
    }




}