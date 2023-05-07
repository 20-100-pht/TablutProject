package Structure;

import Structure.Coordinate;

public class Coup {
    Coordinate init;
    Coordinate dest;

    public Coup(Coordinate i, Coordinate d){
        init = i;
        dest = d;
    }

    public Coordinate getInit(){
        return init;
    }
    public Coordinate getDest(){
        return dest;
    }

    @Override
    public String toString(){
        return init + " -> " + dest;
    }

}
