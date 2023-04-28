public class ReturnValue {

    int value;
    Piece current;

    ReturnValue(int i, Piece p){
        current = p;
        value = i;
    }

    public void setValue(int i) {
        value = i;
    }

    public int getValue(){
        return value;
    }

    public void setPiece(Piece i) {
        current = i;
    }

    public Piece getPiece(){
        return current;
    }
}
