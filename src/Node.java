import java.util.ArrayList;

public class Node {

    Piece[][] board;
    ArrayList<Node> children;

    Node(Grid b, Piece k, Coup c,boolean end){
        grid = b;
        king = k;
        coup = c;
        isEndGame = end;
        children = new ArrayList<>();
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
