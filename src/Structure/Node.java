package Structure;

import Model.*;

import java.util.ArrayList;

public class Node {

    GameRules gameRules;
    ArrayList<Node> children;
    Coup coup;
    Coup bestMove;
    double heuristic;

    public Node(GameRules g, Coup c, ResultGame end){
        gameRules = g;
        coup = c;
        children = new ArrayList<>();
    }

    public void addChild(Node n){
        children.add(n);
    }

    public void addChildTo(int i,Node n){
        children.add(i,n);
    }

    public ArrayList<Node> getChildren(){
        return children;
    }

    public void setHeuristic(double h){
        heuristic = h;
    }

    public double getHeuristic(){
        return heuristic;
    }
    public GameRules getGameRules(){
        return gameRules;
    }

    public Coup getCoup(){
        return coup;
    }

    public Coup getBestMove() {
        return bestMove;
    }
    public void setBestMove(Coup bestMove) {
        this.bestMove = bestMove;
    }

}
