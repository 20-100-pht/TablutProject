import java.util.ArrayList;

public class AI {

    AI(){

    }
/*
    private Node constructTreeLayer(Piece[][] board){
        ArrayList<Piece> Pieces; //On suppose qu'on a une fonc qui créé une array list de toutes les pièces d'une couleur donnée
        for (Piece p : Pieces) {
           // for
        }


        return null;
    }
    */

    private int minimax(Piece[][] board, int depth, boolean maximizingPlayer) {
        if (depth == 0 /*|| board.defendersWin()*/) {
            return 0; //Return heuristic
        }

        //constructTreeLayer(board);

        if (maximizingPlayer) {
            //Attacker

        } else {
            //Defender

        }


        return 0;
    }

    public int heuriticAttacker(Piece[][] board){
        int king = 0;
        int attackers = 0;
        int defenders = 0;

        for(int y = 0; y < board.length; y++){
            for(int x = 0; x < board.length; x++){
                if(board[y][x]!=null){
                    switch (board[y][x].getType()){
                        case KING :
                            break;
                    }
                }
            }
        }
        
        return 0;
    }


}
