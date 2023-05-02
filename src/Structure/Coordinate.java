package Structure;

public class Coordinate {

    byte row;
    byte col;

    public Coordinate(int r, int c){
        row = (byte)r;
        col = (byte)c;
    }

    public void setRowCoord(int i){ row = (byte)i;}

    public void setColCoord(int i){
        col = (byte)i;
    }

    public int getRow(){
        return (int)row;
    }

    public int getCol(){
        return (int)col;
    }

}
