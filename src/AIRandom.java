import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AIRandom {

    Random r;
    GameControler AIGameControler;
    PieceType typeAI;

    AIRandom(GameControler g, PieceType t){

        AIGameControler = g;
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
            List<Piece> listPiece = AIGameControler.grid.returnListOfPiece(typeAI);
            int aleaP = r.nextInt(listPiece.size());

            Piece current = listPiece.get(aleaP);

            List<Coordinate> listDest = current.possibleMoves(AIGameControler.grid.board);
            if (listDest.size() > 0) {
                int aleaD = r.nextInt(listDest.size());
                return new Coup(new Coordinate(current.getRow(), current.getCol()), listDest.get(aleaD));
            }
        }
    }
}
