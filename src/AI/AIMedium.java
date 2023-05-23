package AI;

import Global.Configuration;
import Model.*;
import Structure.Coordinate;
import Structure.Direction;
import Structure.Node;

import java.util.ArrayList;
import java.util.List;

public class AIMedium extends AI {

    @Override
    public double heuristic(Node current, int depth, PieceType maximizingPlayer, PieceType player){

        double value = 0;
        if(current.getLogicGrid().getEndGameType() == ResultGame.ATTACKER_WIN) {
            value += 1000000*(depth+1);
        }
        else if (current.getLogicGrid().getEndGameType() == ResultGame.DEFENDER_WIN) {
            value -= 1000000*(depth+1);
        }

        switch (player){
            case ATTACKER:
                value += attackerHeuristic(current);
            case KING: case DEFENDER:
                value -=  defenderHeuristic(current);
        }

        return value;
    }

    private double attackerHeuristic(Node current){
        double value = 0;
        value += (double) (current.getLogicGrid().getNbPieceAttackerOnGrid()/(current.getLogicGrid().getNbPieceDefenderOnGrid()+1)) * AIConfig.getPieceRatio_A();
        value += HeuristicUtils.canKingGoToCorner(current) * -AIConfig.getKingToCorner_A();

        //Attackers want a high value, Defenders want a low value
        return value;
    }

    private double defenderHeuristic(Node current){
        LogicGrid gRules = current.getLogicGrid();

        double value =0;
        value += gRules.getNbPieceDefenderOnGrid()*AIConfig.getPieceRatio_D();

        return value;
    }
}
