package AI;

import Model.*;
import Structure.Coordinate;
import Structure.Coup;
import Structure.Node;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;
import java.util.Vector;

public abstract class AI implements Serializable {
    int dep = 0;

    public AI(){

    }
//TODO bug au bout d'un moment les IA jouent le premier coup possible genre tour 4-6
    /** BUG
     * Cela ne ce passe que pour 1 IA si l'autre est random
     * -> L'IA random bug et pas l'autre
     *  En vrai l'ia random est peut-être pas si random que ça
     *  * Dès le début
     * Cela ce produit sur l'interface et sur le terminal
     * Bon ben on va mettre des prints de partouts hein
     * - print à chaque fils le coups choisi et surtout print celui qu'on choisis pour le père
     * - print heuristique ?
     * -
     *
     * */
//

    /**
     * Returns the next (best or not) playable move
     * /!\ LogicGrid must be cloned first
     *
     * @param g     current game rules
     * @param depth depth of search
     * @param type  type of piece playing
     * @return a coup
     */
    public Coup playMove(LogicGrid g, int depth, PieceType type) {

        //Copy board
        dep = depth;

        //Set alpha and beta
        double alpha = Double.NEGATIVE_INFINITY;
        double beta = Double.POSITIVE_INFINITY;

        //Create first node
        Node n = new Node(g.cloneLogicGrid(), null, ResultGame.NO_END_GAME);

        //Call minimax
        return minimaxAlgo(n, depth, type, alpha, beta).getBestMove();

    }

    /**
     * Minimax algorithm with beta pruning,
     * searches the best next move based on different heuristics
     *
     * @param node             current game state
     * @param depth            current depth of search
     * @param maximizingPlayer type of player playing
     * @param alpha            value of best board
     * @param beta             value of worst board
     * @return current node with his children and best move
     */
    private Node minimaxAlgo(Node node, int depth, PieceType maximizingPlayer, double alpha, double beta) {

        LogicGrid currentGR = node.getLogicGrid();

        //Depth reached or end game
        if (depth == 0 || currentGR.getEndGameType() != ResultGame.NO_END_GAME ) {
            node.setHeuristic(heuristic(node, depth, maximizingPlayer));

            return node;
        }

        //Create all children of node (all possible states of current board)
        createNodeChildren(node, maximizingPlayer, depth);
        ArrayList<Node> children = node.getChildren();

        if (children.size() == 0) {
            return node;
        }

        //Return best node or best heuristic
        return maximizing(node, children, depth, alpha, beta, maximizingPlayer);
    }

    /**
     * Evaluates all children of current node
     *
     * @param currentNode current node
     * @param children    children of current node
     * @param depth       current depth of search
     * @param alpha       value of best board
     * @param beta        value of worst board
     * @param type        type of piece playing
     * @return current node with his children and best move
     */
    private Node maximizing(Node currentNode,ArrayList<Node> children, int depth, double alpha, double beta, PieceType type){

        int tmpD = depth - 1;
        Node rtNode = children.get(0);

        ResultGame win;
        double maxValue;
        PieceType opponent = type == PieceType.ATTACKER ? PieceType.DEFENDER : PieceType.ATTACKER;

        double value;

        //Set values according to team
        if (type == PieceType.ATTACKER) {
            value = Double.NEGATIVE_INFINITY;
            maxValue = 1000000;
            win = ResultGame.ATTACKER_WIN;
        } else {
            value = Double.POSITIVE_INFINITY;
            win = ResultGame.DEFENDER_WIN;
            maxValue = -1000000;
        }

        //For each child of node
        for (int i = 0; i < children.size(); i++) {

            //If child is a wining move return move
            if (children.get(i).getLogicGrid().getEndGameType() == win) {
                //System.out.println("Is wining move");

                //The closer to the root, the faster we win, the bigger the value
                value = maxValue * (depth + 1);
                rtNode = children.get(i);
                break;
            }

            //Calculate next best move
            Node tmp = minimaxAlgo(children.get(i), tmpD, opponent, alpha, beta);

            //Randomize selection of best heuristics
            Random r = new Random();
            double tmpHeuristic = tmp.getHeuristic();
            //Compare best value with calculated heuristic
            if ((tmpHeuristic > value && type == PieceType.ATTACKER) ||
                    (tmpHeuristic < value && type == PieceType.DEFENDER) ) {
                value = tmpHeuristic;
                rtNode = tmp;

                bestNodes.clear();
                bestNodes.add(rtNode);

            } else if (tmpHeuristic == value) {
                bestNodes.add(tmp);
            }

            if (type == PieceType.ATTACKER) {
                //Beta pruning
                alpha = Math.max(alpha, value);
                if (alpha >= beta) {
                    break;
                }
            } else {
                //alpha pruning
                beta = Math.min(beta, value);
                if (beta <= alpha) {
                    break;
                }
            }

        }

        //Set current node's heuristic to best calculated value and best move
        currentNode.setHeuristic(value);
        currentNode.setBestMove(rtNode.getCoup());
       /* System.out.print("row " + currentNode.getCoup().getDest().getRow());
        System.out.print("col " + currentNode.getCoup().getDest().getCol());
        System.out.println("");*/
        return currentNode;
    }

    /**
     * Creates all possible versions of board 1 move away
     *
     * @param father main board
     * @param type   type of piece playing
     * @param depth  depth of search
     */
    private void createNodeChildren(Node father, PieceType type, int depth) {

        int in = 0;
        LogicGrid fatherGR = father.getLogicGrid();
        int boardSize = fatherGR.getGrid().getSizeGrid();
        Piece[][] board = fatherGR.getGrid().board;

        //For each piece on board
        for (int y = 0; y < boardSize; y++) {
            for (int x = 0; x < boardSize; x++) {

                if (board[y][x] == null) continue;
                Piece current = board[y][x].clonePiece();

                //if Model.Piece is of type type
                if (current != null && (current.getType() == type || (current.getType() == PieceType.KING && type == PieceType.DEFENDER))) {

                    //Get all possible moves for current piece
                    ArrayList<Coordinate> moves = current.possibleMoves(board);
                    if (moves.size() == 0) continue;

                    //Create and add children nodes
                    for (Coordinate coordDest : moves) {
                        //Create ne game rules
                        LogicGrid newLogicGrid = fatherGR.cloneLogicGrid();

                        //Get piece to move
                        Piece pieceToMove = newLogicGrid.getGrid().getPieceAtPosition(current.c);

                        //Create movement
                        Coup coup = new Coup(new Coordinate(pieceToMove.c.getRow(), pieceToMove.c.getCol()), new Coordinate(coordDest.getRow(), coordDest.getCol()));

                        //Move piece
                        newLogicGrid.move(coup);

                        //Check if king is captured
                        newLogicGrid.capture();

                        //Attack
                        int defendersKilled = 0;
                        int attackersKilled = 0;
                        Vector<Piece> c = newLogicGrid.attack(pieceToMove);
                        for (int i = 0; i < c.size(); i++) {
                            if (c.get(i).isDefender()) {
                                defendersKilled++;
                            } else {
                                attackersKilled++;
                            }
                        }

                        //Check if defenders win
                        if (type == PieceType.DEFENDER) {
                            //Call to check if defenders have won
                            newLogicGrid.isDefenderWinConfiguration();
                        }

                        ResultGame end = newLogicGrid.getEndGameType();


                        //Cut the child only if father has children

                        if (preEvaluate(newLogicGrid, type) && end == ResultGame.NO_END_GAME && father.getChildren().size() > 0) {
                            in++;
                            continue;
                        }


                        Node tmpNode = new Node(newLogicGrid, new Coup(new Coordinate(y, x), coordDest), end);


                        //Add child to first place
                        if (defendersKilled >= 1 || end == ResultGame.ATTACKER_WIN || attackersKilled >= 1 || end == ResultGame.DEFENDER_WIN) {
                            if (end != ResultGame.NO_END_GAME) {
                                //System.out.println(end + " in "+ ((dep+1)-depth) + " moves");
                                /*System.out.println(end + " in "+ ((dep+1)-depth) + " moves");
                                newLogicGrid.grid.print();
                                System.out.println("\n");*/
                            }
                            father.addChildTo(0, tmpNode);
                        } else {
                            father.addChild(tmpNode);
                        }

                    }
                }
            }
        }
        //System.out.println("Cut " + in + " branches");
    }

    public boolean preEvaluate(LogicGrid logicGrid, PieceType type) {
        Grid grid = logicGrid.grid;
        Piece[][] board = grid.board;
        Piece king = logicGrid.king;

        int defenders = 0;
        int attackers = 0;

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                Piece piece = grid.getPieceAtPosition(new Coordinate(i, j));

                if (piece != null) {
                    if (piece.isAttacker()) {
                        attackers++;
                    } else if (piece.isDefenderOrKing()) {
                        defenders++;
                    }
                }
            }
        }

        //Cut the child if
        if (type == PieceType.ATTACKER) {
            return ((double) attackers / defenders) < 1.6;
        } else {
            return ((double) attackers / defenders) > 1.8;
        }
    }

    /**
     * Evaluate board
     *
     * @param current current state of a board
     * @param depth   depth of the board
     * @return a value indicating the winnability of the board
     */
    public abstract double heuristic(Node current, int depth, PieceType maximizingPlayer);
}





    /*
    public Coup playMove(LogicGrid g, int depth, PieceType type) {

        Node origin = new Node(g,null,ResultGame.NO_END_GAME);
        Node res;
        if (type == PieceType.ATTACKER) {
            res = minimax(origin, depth, true);
        }else{
            res = minimax(origin, depth, false);
        }
        return res.getCoup();
    }

    public Node minimax(Node node, int depth, boolean maximizingPlayer) {
        double value, value2;
        Grid grid = node.getLogicGrid().grid;
//        ArrayList<Piece> PiecesListTeam = new ArrayList<>();
//        ArrayList<Coordinate> MovesListPiecesTeam = new ArrayList<>();
        Node bestNode = null;
        Node d;
        if (depth == 0) {
            node.setHeuristic(heuristic(node, depth));
            return node;
        } else if (node.getLogicGrid().isEndGame()) {
            node.setResGame(node.getLogicGrid().getEndGameType());
            node.setHeuristic(heuristic(node, depth));
            return node;
        }
        if (maximizingPlayer) {
            value = -10000000;
//            PiecesListTeam = grid.returnListOfPiece(PieceType.ATTACKER);

            for (Piece piece : grid.returnListOfPiece(PieceType.ATTACKER)) {
//                MovesListPiecesTeam.addAll(piece.possibleMoves(grid.board));

                for (Coordinate pieceMove : piece.possibleMoves(grid.board)) {
                    LogicGrid LGridRec = node.getLogicGrid().cloneLogicGrid();
                    Coup coupRec = new Coup(piece.c, pieceMove);
                    LGridRec.move(coupRec);
                    //TODO isendgame . ..
                    Node nodeRec = new Node(LGridRec, coupRec, null);
                    d = minimax(nodeRec, depth - 1, false);
                    if(d == null){
                        System.out.println("oh no");
                    }
                    value2 = Math.max(value, d.getHeuristic());
                    if (value2 > value) {//TODO on peut >= pour moves random
                        bestNode =  new Node(LGridRec, coupRec, nodeRec.getResultGame());
                        value = value2;
                    }
                }
//                MovesListPiecesTeam.clear();
            }
        } else { //minimizing player
            value = 10000000;
//            PiecesListTeam = grid.returnListOfPiece(PieceType.DEFENDER);

            for (Piece piece : grid.returnListOfPiece(PieceType.DEFENDER)){
//                MovesListPiecesTeam.addAll(piece.possibleMoves(grid.board));

                for(Coordinate pieceMoves : piece.possibleMoves(grid.board)){
                    LogicGrid LGridRec = node.getLogicGrid().cloneLogicGrid();
                    Coup coupRec = new Coup(piece.c, pieceMoves);
                    LGridRec.move(coupRec);
                    Node nodeRec = new Node(LGridRec, coupRec, null);
                    value2 = Math.min(value, minimax(nodeRec, depth-1, true).getHeuristic());
                    if(value2 < value){//TODO on peut >= pour moves random
                        bestNode =  new Node(LGridRec, coupRec, nodeRec.getResultGame());
                        value = value2;

                    }
                }
//                MovesListPiecesTeam.clear();
            }
        }
        return bestNode;
    }


    public abstract double heuristic(Node current, int depth);

}
*/
//            for(Node child : node.getChildren()){
//                value = Math.max(value, minimax(child, depth-1, false).getHeuristic());
//            }
