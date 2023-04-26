public class Position {
    int x;
    int y;
    public Position(int x, int y){
        this.x = x;
        this.y = y;
    }

    void SetX(int x){
        this.x = x;
    }

    void SetY(int y){
        this.y = y;
    }

    int GetX(){
        return x;
    }

    int GetY(){
        return y;
    }
}
