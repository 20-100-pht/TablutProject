package AI;


import Model.*;
import Structure.Node;

public class AIHard extends AI {

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
                value -= defenderHeuristic(current);
        }

        return value;
    }

    private double attackerHeuristic(Node current){
        LogicGrid gRules = current.getLogicGrid();

        double value = 0;
        value += (double) (current.getLogicGrid().getNbPieceAttackerOnGrid()/(current.getLogicGrid().getNbPieceDefenderOnGrid()+1)) * AIConfig.getPieceRatio_A();
        value += HeuristicUtils.canKingGoToCorner(current) * -AIConfig.getKingToCorner_A();
        value += HeuristicUtils.isNextToKing(current.getLogicGrid().getKing(), current.getLogicGrid().getGrid()) * AIConfig.getNextToKing_A();
        value += HeuristicUtils.attackerCircleStrategy(current) * AIConfig.getCircleStrat_A();
        value += HeuristicUtils.GoToSuicide(gRules.grid.getPieceAtPosition(current.getCoup().getDest()), gRules.grid, PieceType.DEFENDER);

        //Attackers want a high value, Defenders want a low value
        return value;
    }
    private double defenderHeuristic(Node current){
        LogicGrid gRules = current.getLogicGrid();
        Piece k = gRules.getKing();

        double value =0;
        value += gRules.getNbPieceDefenderOnGrid()*AIConfig.getPieceRatio_D();
        value -= HeuristicUtils.isNextToKing(k,current.getLogicGrid().getGrid())*AIConfig.getNextToKing_D();
        value += HeuristicUtils.canKingGoToCorner(current)*AIConfig.getKingToCorner_D();
        value += HeuristicUtils.GoToSuicide(gRules.grid.getPieceAtPosition(current.getCoup().getDest()), gRules.grid, PieceType.DEFENDER);

        return value;
    }


}
