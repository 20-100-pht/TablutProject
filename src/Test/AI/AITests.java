package AI;

import Model.LogicGrid;
import Structure.Node;

import java.util.ArrayList;

public class AITests {

    public void viewChildren(ArrayList<Node> nodes){

        for (int i = 0; i < nodes.size(); i++){
            LogicGrid l = nodes.get(i).getLogicGrid();
            System.out.println("\nPossible move n°" + i + " of value " + nodes.get(i).getHeuristic());
            System.out.println(l.getNbPieceAttackerOnGrid() + " attackers, " + l.getNbPieceDefenderOnGrid() + " defenders");
            nodes.get(i).getLogicGrid().print();
        }

    }
}
