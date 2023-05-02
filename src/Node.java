import java.util.ArrayList;

public class Node {

    Grid grid;
    Piece king;
    ArrayList<Node> children;
    Coup coup;
    boolean isEndGame;
    double heuristic;

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

    public Grid getGrid(){
        return grid;
    }

    public void setHeuristic(double h){
        heuristic = h;
    }

    public double getHeuristic(){
        return heuristic;
    }


}
