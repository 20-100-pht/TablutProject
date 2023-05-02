import java.util.ArrayList;

public class Node {

    Piece[][] board;
    Coordinate king;
    ArrayList<Node> children;
    Coup move;

    Node(Piece[][] b, Coordinate k, Coup m){
        board = b;
        king = k;
        children = new ArrayList<>();
        move = m;
    }

    public void addChild(Node n){
        children.add(n);
    }

    public ArrayList<Node> getChildren(){
        return children;
    }

    /*public GameController getGC(){
        return gController;
    }*/


}
