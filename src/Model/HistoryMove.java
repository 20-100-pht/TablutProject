package Model;

import Structure.Coup;

import java.io.Serializable;
import java.util.Vector;

public class HistoryMove implements Serializable {

    Coup coup;
    Vector<Piece> killedPieces;
    boolean isAttackerMove;
    public HistoryMove(Coup coup, Vector<Piece> killedPieces, boolean isAttackerMove){
        this.coup = coup;
        this.killedPieces = killedPieces;
        this.isAttackerMove = isAttackerMove;
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
}
