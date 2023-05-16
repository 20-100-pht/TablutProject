package Structure;

import Model.*;

import java.util.ArrayList;

public class Node {

    LogicGrid logicGrid;
    ArrayList<Node> children;
    Coup coup;
    Coup bestMove;
    double heuristic;

    public Node(LogicGrid g, Coup c){
        logicGrid = g;
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
    public void setChildren(ArrayList<Node> c){
        children = c;
    }

    public void setHeuristic(double h){
        heuristic = h;
    }

    public double getHeuristic(){
        return heuristic;
    }
    public LogicGrid getLogicGrid(){
        return logicGrid;
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
