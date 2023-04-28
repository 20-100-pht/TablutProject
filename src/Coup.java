public class Coup {
    Coordinate init;
    Coordinate dest;
    Coup(Coordinate i, Coordinate d){
        init = i;
        dest = d;
    }

    public Coordinate getInit(){
        return init;
    }
    public Coordinate getDest(){
        return dest;
    }

}
