package View;

import javax.swing.*;

public class TimerLabel extends JLabel {

    int duration;       //In seconds

    public TimerLabel(int duration){
        this.duration = duration;
        updateTextValue();
    }

    public String getMinutesS(){
        int nMin = duration / 60;
        String result;
        if(nMin > 9){
            result = "" + nMin;
        }
        else{
            result = "0"+nMin;
        }
        return result;
    }

    public String getSecondsS(){
        int nSec = duration % 60;
        String result;
        if(nSec > 9){
            result = "" + nSec;
        }
        else{
            result = "0"+nSec;
        }
        return result;
    }

    public void updateTextValue(){
        setText(getMinutesS()+":"+getSecondsS());
    }

    public void setDuration(int duration){
        this.duration = duration;
    }
}
