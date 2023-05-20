package Model;

import java.io.Serializable;

public class PlayerStats implements Serializable {

    String name;
    int nTurnMean;
    int nWin;
    int nDefeat;
    boolean winAgainstEasy;
    boolean winAgainstMedium;
    boolean winAgainstHard;

    public PlayerStats(String name){
        this.name = name;
        nTurnMean = 0;
        nWin = 0;
        nDefeat = 0;
        winAgainstEasy = false;
        winAgainstMedium = false;
        winAgainstHard = false;
    }



    public String getName(){
        return name;
    }

}
