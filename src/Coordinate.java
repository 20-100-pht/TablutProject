public class Coordinate {

    byte row;
    byte col;

    Coordinate(int r, int c){
        row = r;
        col = c;
    }

    public void setRow(int i){
        row = i;
    }

    public void setCol(int i){
        col = i;
    }

    public int getRow(){
        return (int)row;
    }

    public int getCol(){
        return (int)col;
    }

}
