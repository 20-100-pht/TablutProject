package AI;

import Structure.Coordinate;
import Structure.Coup;

public class BestMove {

    Coup coup;
    Double heuristic;

    BestMove(Coup coup, Double heuristic){
        this.coup = coup;
        this.heuristic = heuristic;
    }

    public Coup getCoup() {
        return coup;
    }

    public void setCoup(Coup coup) {
        this.coup = coup;
    }

    public Double getHeuristic() {
        return heuristic;
    }

    public void setHeuristic(Double heuristic) {
        this.heuristic = heuristic;
    }
}
