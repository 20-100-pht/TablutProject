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
    int defTimeRemainedInMs;
    int attTimeRemainedInMs;

    public HistoryMove(Coup coup, Vector<Piece> killedPieces, boolean isAttackerMove, int turnIndex, Coup previousCoup, int defTimeRemainedInMs, int attTimeRemainedInMs){
        this.coup = coup;
        this.killedPieces = killedPieces;
        this.isAttackerMove = isAttackerMove;
        this.turnIndex = turnIndex;
        this.previousCoup = previousCoup;
        this.defTimeRemainedInMs = defTimeRemainedInMs;
        this.attTimeRemainedInMs = attTimeRemainedInMs;
    }

    public Coup getCoup(){
        return coup;
    }

    public boolean isAttackerMove() {
        return isAttackerMove;
    }

    public int getTurnIndex(){
        return turnIndex;
    }

    public int getDefTimeRemainedInMs() {
        return defTimeRemainedInMs;
    }

    public int getAttTimeRemainedInMs() {
        return attTimeRemainedInMs;
    }

}
