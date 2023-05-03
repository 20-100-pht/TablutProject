package Structure;

import Model.Grid;
import Model.Piece;
import Model.PieceType;
import Model.ResultGame;

import java.util.ArrayList;

public class Node {

    Grid grid;
    Piece king;
    ArrayList<Node> children;
    Coup coup;
    ResultGame isEndGame;
    double heuristic;

    public Node(Grid b, Piece k, Coup c, ResultGame end){
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

    public Coup getCoup(){
        return coup;
    }

    public Piece getKing(){
        return king;
    }

    public ResultGame endGame(){
        return isEndGame;
    }
}
