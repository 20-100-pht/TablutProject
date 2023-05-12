package Animation;

public class Animation {

    int duration;
    int timeRemained;
    boolean isPaused = false;

    public void start(){

    }

    public void update(int timeElapsed){ //In ms

    }

    public void stop(){

    }

    public boolean isTerminated() {
        return true;
    }

    public void setPaused(boolean paused){
        isPaused = paused;
    }

    public boolean isPaused(){
        return isPaused;
    }
}
