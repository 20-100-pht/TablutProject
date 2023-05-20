package Model;

import java.io.Serializable;

public class PlayerStats implements Serializable {

    String name;
    double nTurnMean;
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

    public void addWin(){
        nWin++;
    }

    public void addLoose(){
        nDefeat++;
    }

    public int getNWin(){
        return nWin;
    }

    public double getNTurnMean(){
        return nTurnMean;
    }

    public void setTurnMean(double nTurnMean){
        this.nTurnMean = nTurnMean;
    }

    public int getNLoose(){
        return nDefeat;
    }

    public boolean hasWinAgainstEasy(){
        return winAgainstEasy;
    }

    public boolean hasWinAgainstMedium(){
        return winAgainstMedium;
    }

    public boolean hasWinAgainstHard(){
        return winAgainstHard;
    }

    public void setWinAgainstEasy(boolean win){
        this.winAgainstEasy = win;
    }

    public void setWinAgainstMedium(boolean win){
        this.winAgainstMedium = win;
    }

    public void setWinAgainstHard(boolean win){
        this.winAgainstHard = win;
    }
}
