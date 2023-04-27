import java.util.ArrayList;

public class Node {

    Piece[][] board;
    ArrayList<Node> children;

    Node(Piece[][] b){
        board = b;
    }

    public void addChild(Node n){
        children.add(n);
    }

    public ArrayList<Node> getChildren(){
        return children;
    }

    public Piece[][] getBoard(){
        return board;
    }


}
