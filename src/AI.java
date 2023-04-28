import java.util.ArrayList;
import java.util.List;

public class AI {

    boolean isAttacker;
    GameControler AIGameController;

    AI(GameControler g, boolean b){
        AIGameController = g; isAttacker = b;
    }
/*
    private Node constructTreeLayer(Node Father){
        ArrayList<Piece> Pieces; //On suppose qu'on a une fonc qui créé une array list de toutes les pièces d'une couleur donnée
        Piece[][] Fboard = Father.board;
        Piece[][] nBord = Fboard; // on veut faire une copie de Fboard
        for (Piece p : Pieces) {
           ArrayList<Coordinate> moves = p.possibleMoves(board);
           for (Coordinate k : moves){
               nBord[p.getRow()][p.getCol()] = null;
               nBord[k.row][k.col] = p;
               Node n = new Node(nBord);
               Father.addChild(n);
           }
        }
        return Father;
    }*/

    private float minimaxAttacker(Piece[][] board, int depth, boolean maximizingPlayer, Node node) {
        if (depth == 0 /*|| board.hasWinner()*/) {
            return heuristic(board); //Return heuristic
        }
        //constructTreeLayer(board);

        if (maximizingPlayer) {
            //Attacker
            double value = Double.POSITIVE_INFINITY;


        } else {
            //Defender

        }
        return 0;
    }

    private float minimaxDefender(Piece[][] board, int depth, boolean maximizingPlayer, Node node) {
        if (depth == 0 /*|| board.hasWinner()*/) {
            return heuristic(board); //Return heuristic
        }

        //constructTreeLayer(board);

        if (maximizingPlayer) {
            //Defender

        } else {
            //Attacker

        }

        return 0;
    }


    private float minimax(Piece[][] board, int depth, boolean maximizingPlayer) {

        Node n = new Node(board);

        if (isAttacker){
            return minimaxAttacker(board, depth, maximizingPlayer, n);
        }else{
            return minimaxDefender(board, depth, maximizingPlayer, n);
        }
    //faut peut être aussi renvoyer le node choisi (= le coup qu'on fait) ou le stocker
    }


    public float heuristic(Piece[][] board){
        int king = 0;
        int attackers = 0;
        int defenders = 0;

        AIGameController.grid.board = board;

        for(int y = 0; y < board.length; y++){
            for(int x = 0; x < board.length; x++){
                if(board[y][x]!=null){
                    switch (board[y][x].getType()){
                        case KING :
                            king++;
                            AIGameController.king = board[y][x];
                            break;
                        case DEFENDER:
                            defenders++;
                            break;
                        case ATTACKER:
                            attackers++;
                            break;
                    }
                }
            }
        }

        AIGameController.capture();

        if(AIGameController.endGameVar == ResultGame.ATTACKER_WIN || defenders == 0)
            return 0;
        else if (AIGameController.endGameVar == ResultGame.DEFENDER_WIN || attackers == 0 || AIGameController.isKingAtObjective()) {
            return 1;
        }

        float value = king * (defenders/attackers);
        return value;
    }
/* //TODO
    private List<Piece> returnListOfPieceBoard(PieceType type){
        Piece current;
        List<Piece> list = new ArrayList<>();
        for (int i = 0; i < sizeGrid; i++) {
            for (int j = 0; j < sizeGrid; j++) {
                if( (current = board[i][j]) != null && ( (current.isKing() && type == PieceType.DEFENDER) || current.getType() == type) ){
                    list.add(current);
                }
            }
        }
        return list;
    }
*/
}