package AI;

import Global.Configuration;
import Model.*;
import Structure.Coordinate;
import Structure.Direction;
import Structure.Node;

import java.util.ArrayList;
import java.util.List;

public class AIHard extends AI {

    @Override
    public double heuristic(Node current, int depth, PieceType maximizingPlayer){

        double value = 0;
        if(current.getLogicGrid().getEndGameType() == ResultGame.ATTACKER_WIN) {
            value += 1000000*(depth+1);
            if(depth == 0) return value;
        }
        else if (current.getLogicGrid().getEndGameType() == ResultGame.DEFENDER_WIN) {
            value -= 1000000*(depth+1);
            if(depth == 0) return value;
        }

        switch (maximizingPlayer){
            case ATTACKER:
                value += attackerHeuristic(current, depth, maximizingPlayer);
            case KING: case DEFENDER:
                value -=  defenderHeuristic(current, depth, maximizingPlayer);
        }

        return value;
    }

    private double attackerHeuristic(Node current, int depth, PieceType maximizingPlayer){
        LogicGrid gRules = current.getLogicGrid();
        Piece k = gRules.getKing();
        Grid grid = gRules.getGrid();
        PieceType adverse;
        if(maximizingPlayer == PieceType.ATTACKER) adverse = PieceType.DEFENDER;
        else adverse = PieceType.ATTACKER;

        double value = 0;
        value += (double) (current.getLogicGrid().getNbPieceAttackerOnGrid()/(current.getLogicGrid().getNbPieceDefenderOnGrid()+1)) * AIConfig.getPieceRatio_A();
        value += HeuristicUtils.canKingGoToCorner(current) * -AIConfig.getKingToCorner_A();
        value += HeuristicUtils.isNextToKing(current.getLogicGrid().getKing(), current.getLogicGrid().getGrid()) * AIConfig.getNextToKing_A();
        value += HeuristicUtils.attackerCircleStrategy(current) * AIConfig.getCircleStrat_A();

        //Attackers want a high value, Defenders want a low value
        return value;
    }
    private double defenderHeuristic(Node current, int depth, PieceType maximizingPlayer){
        LogicGrid gRules = current.getLogicGrid();
        Piece k = gRules.getKing();
        Grid grid = gRules.getGrid();

        double value =0;
        value += gRules.getNbPieceDefenderOnGrid()*5;
        value -= HeuristicUtils.isNextToKing(k,current.getLogicGrid().getGrid())*10;
        value += HeuristicUtils.canKingGoToCorner(current)*100;

        return value;
    }


}
