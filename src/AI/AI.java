package AI;

import Model.*;
import Structure.Coordinate;
import Structure.Coup;
import Structure.Node;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

public abstract class AI implements Serializable {

    int dep = 0;

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

        dep = depth;

        //Set alpha and beta
        double alpha = Double.NEGATIVE_INFINITY;
        double beta = Double.POSITIVE_INFINITY;

        //Create first node
        Node n = new Node(g.cloneLogicGrid(), null);

        int intType = 1;
        if(type == PieceType.DEFENDER){
            intType = -1;
        }

        //Call minimax and return best move to play
        BestMove bm = minimax(n, depth, intType, alpha, beta);

        /*System.out.println("Heuristic :" + bm.getHeuristic());
        System.out.println("Move :" + bm.getCoup().toString());
        System.out.println("Attackers :" + n.getLogicGrid().getNbPieceAttackerOnGrid());
        System.out.println("Defenders :" + n.getLogicGrid().getNbPieceDefenderOnGrid());*/

        return bm.getCoup();
    }

    public BestMove minimax(Node node, int depth, int colour, double alpha, double beta){

        PieceType maximizingPlayer = PieceType.ATTACKER;
        if(colour == -1) maximizingPlayer = PieceType.DEFENDER;

        if(depth == 0 || node.getLogicGrid().isEndGame()){
            double h = heuristic(node,depth,maximizingPlayer);
            BestMove bm =  new BestMove(node.getCoup(),h);

            return bm;
        }

        //Get all children of node
        ArrayList<Node> children = createChildren(node,colour,depth);
        node.setChildren(children);
        ArrayList<BestMove> bestMovesArray = maximize(children,depth,node, colour, alpha,beta);

        //If children have no moves return this move
        if(bestMovesArray.size() == 0){
            double h = heuristic(node,depth,maximizingPlayer);
            BestMove bm =  new BestMove(node.getCoup(),h);
            return bm;
        }

        BestMove bmToReturn = bestMovesArray.get(0);

        Random random = new Random();

        if(bestMovesArray.size() > 1){
            bmToReturn = bestMovesArray.get(random.nextInt(bestMovesArray.size()));
        }

        return bmToReturn;
    }

    private ArrayList<BestMove> maximize(ArrayList<Node> children, int depth, Node node, int colour, double alpha, double beta){

        double value = Double.NEGATIVE_INFINITY;
        if(colour == -1){
            value = Double.POSITIVE_INFINITY;
        }

        BestMove bmToReturn;
        ArrayList<BestMove> bestMovesArray = new ArrayList<>();

        for(int i = 0; i < children.size(); i++){

            BestMove bm = minimax(children.get(i), depth-1, -colour, alpha, beta);

            if((colour == 1 && value < bm.getHeuristic())
                    || (colour == -1 && value > bm.getHeuristic())){

                Coup cp = node.getCoup();
                if(cp == null) cp = bm.getCoup();
                bmToReturn = new BestMove(cp,bm.getHeuristic());

                bestMovesArray.clear();
                bestMovesArray.add(bmToReturn);

                value = bm.getHeuristic();
            }
            else if (value == bm.getHeuristic()) {
                Coup cp = node.getCoup();
                if(cp == null) cp = bm.getCoup();
                bmToReturn = new BestMove(cp,bm.getHeuristic());

                bestMovesArray.add(bmToReturn);
            }


            /*if(colour==-1){
                if(alpha >= value){
                    break;
                }
                beta = Math.min(beta,value);
            }else{
                if(beta <= value){
                    break;
                }
                alpha = Math.max(alpha,value);
            }*/
        }

        return bestMovesArray;
    }

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

                        if(child.getLogicGrid().getEndGameType() == winType){
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
    public abstract double heuristic(Node current, int depth,PieceType maximizingPlayer);

}