public class Coordinate {

    int row;
    int col;

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
        return row;
    }

    public int getCol(){
        return col;
    }

}
