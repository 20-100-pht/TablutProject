package Structure;

import Structure.Coordinate;

import java.io.Serializable;

public class Coup implements Serializable {
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

    public void reverse(){
        Coordinate tmp = init;
        init = dest;
        dest = tmp;
    }

    @Override
    public String toString(){
        return init + " -> " + dest;
    }

}
