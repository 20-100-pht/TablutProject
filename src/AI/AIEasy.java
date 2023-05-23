package AI;

import Model.LogicGrid;
import Model.PieceType;
import Model.ResultGame;
import Structure.Node;

public class AIEasy extends AI{

    @Override
    public double heuristic(Node current, int depth, PieceType maximizingPlayer, PieceType player) {

        LogicGrid gm = current.getLogicGrid();

        if(gm.getEndGameType() == ResultGame.DEFENDER_WIN){
            //If defender can win in max 3 moves (Defender - Attacker - Defender)
            return -100000*(depth+1);

        }else if(gm.getEndGameType() == ResultGame.ATTACKER_WIN){
            //If attackers can win in max 3 moves
            return 100000*(depth+1);
        }

        return 0;
    }

}
