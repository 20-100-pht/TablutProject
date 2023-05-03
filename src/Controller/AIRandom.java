package Controller;

import Model.GameRules;
import Model.Piece;
import Model.PieceType;
import Structure.Coordinate;
import Structure.Coup;

import java.util.List;
import java.util.Random;

public class AIRandom {

    Random r;
    GameRules AIGameRules;
    PieceType typeAI;

    public AIRandom(GameRules g, PieceType t){

        AIGameRules = g;
        typeAI = t;
        r = new Random(System.currentTimeMillis());
    }

    public void setAIType(PieceType t){
        typeAI = t;
    }

    public PieceType getTypeAI(){
        return typeAI;
    }

    public Coup playMove(){

        while(true) {
            List<Piece> listPiece = AIGameRules.grid.returnListOfPiece(typeAI);
            int aleaP = r.nextInt(listPiece.size());

            Piece current = listPiece.get(aleaP);

            List<Coordinate> listDest = current.possibleMoves(AIGameRules.grid.getBoard());
            if (listDest.size() > 0) {
                int aleaD = r.nextInt(listDest.size());
                return new Coup(new Coordinate(current.getRow(), current.getCol()), listDest.get(aleaD));
            }
        }
    }
}
