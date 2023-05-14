package Model;

import Structure.Coordinate;
import Structure.Coup;

import java.io.Serializable;
import java.util.Vector;

public class HistoryMove implements Serializable {

    Coup coup;
    Vector<Piece> killedPieces;
    boolean isAttackerMove;
    int turnIndex;
    Coup previousCoup;

    public HistoryMove(Coup coup, Vector<Piece> killedPieces, boolean isAttackerMove, int turnIndex, Coup previousCoup){
        this.coup = coup;
        this.killedPieces = killedPieces;
        this.isAttackerMove = isAttackerMove;
        this.turnIndex = turnIndex;
        this.previousCoup = previousCoup;
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

    public Coup getPreviousCoup(){
        return previousCoup;
    }
}
