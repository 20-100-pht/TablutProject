package Model;

import Structure.Coup;

import java.io.Serializable;
import java.util.Vector;

public class HistoryMove implements Serializable {

    Coup coup;
    Vector<Piece> killedPieces;
    boolean isAttackerMove;
    int turnIndex;
    public HistoryMove(Coup coup, Vector<Piece> killedPieces, boolean isAttackerMove, int turnIndex){
        this.coup = coup;
        this.killedPieces = killedPieces;
        this.isAttackerMove = isAttackerMove;
        this.turnIndex = turnIndex;
    }

    public Coup getCoup(){
        return coup;
    }

    public Vector<Piece> getKilledPieces(){
        return killedPieces;
    }

    public boolean isAttackerMove() {
        return isAttackerMove;
    }

    public int getTurnIndex(){
        return turnIndex;
    }
}
