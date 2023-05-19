package AI;

import Model.LogicGrid;
import Model.Piece;
import Model.PieceType;
import Structure.Coup;

import java.util.ArrayList;

public class AITest {

    public static void visualiseMoves(ArrayList<Coup> bestMoves, LogicGrid lg, PieceType type){

        PieceType t = PieceType.MARKER_D;
        if(type == PieceType.ATTACKER){
            t = PieceType.MARKER_A;
        }
        LogicGrid l = lg.cloneLogicGrid();
        for (Coup c :bestMoves) {
            l.getGrid().setPieceAtPosition(new Piece(c.getDest(), t),c.getDest());
        }

        l.print();
    }



}
