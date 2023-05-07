package AI;

import Model.GameRules;
import Model.ResultGame;
import Structure.Node;

public class AIRandom extends AI{


    @Override
    public double heuristic(Node current, int depth) {

        GameRules gm = current.getGameRules();

        if(gm.getEndGameType() == ResultGame.DEFENDER_WIN){
            //If defender can win in max 3 moves (Defender - Attacker - Defender)
            System.out.println("Defenders can win");
            return -100000;

        }else if(gm.getEndGameType() == ResultGame.ATTACKER_WIN){
            //If attackers can win in max 3 moves
            System.out.println("Attackers can win");
            return 100000;
        }

        return 0;
    }

}
