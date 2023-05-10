package Animation;

public class AnimationMove extends Animation {

    int xStart;
    int yStart;
    int xEnd;
    int yEnd;
    int x;
    int y;

    public AnimationMove(int duration, int xStart, int yStart, int xEnd, int yEnd){
        this.duration = duration;
        this.xStart = xStart;
        this.yStart = yStart;
        this.xEnd = xEnd;
        this.yEnd = yEnd;
        this.timeRemained = duration;
    }

    @Override
    public void start(){

    }

    @Override
    public void update(int timeElapsed){

        int distanceX = xEnd - xStart;
        int distanceY = yEnd - yStart;

        float a = (float) timeRemained / (float) duration;
        x = (int) (xStart + a*(float)distanceX);
        y = (int) (yStart + a*(float)distanceY);

        timeRemained -= timeElapsed;
        if(timeRemained <= 0){

        }
    }

    @Override
    public void stop(){

    }

    int getX(){
        return x;
    }

    int getY(){
        return y;
    }
}
