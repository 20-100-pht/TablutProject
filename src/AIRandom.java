import java.util.List;
import java.util.Random;

public class AIRandom {

    Random r;
    GameController AIGameController;
    PieceType typeAI;

    AIRandom(GameController g, PieceType t){

        AIGameController = g;
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
            List<Piece> listPiece = AIGameController.grid.returnListOfPiece(typeAI);
            int aleaP = r.nextInt(listPiece.size());

            Piece current = listPiece.get(aleaP);

            List<Coordinate> listDest = current.possibleMoves(AIGameController.grid.board);
            if (listDest.size() > 0) {
                int aleaD = r.nextInt(listDest.size());
                return new Coup(new Coordinate(current.getRow(), current.getCol()), listDest.get(aleaD));
            }
        }
    }
}
