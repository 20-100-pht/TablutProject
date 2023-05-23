package Animation;

import Controller.GameGraphicController;
import Controller.MoveAnimationType;
import Model.Piece;
import Model.PieceType;
import Structure.Coup;

import java.awt.*;

public class AnimationMove extends Animation {

    int xStart;
    int yStart;
    int xEnd;
    int yEnd;
    int x;
    int y;
    GameGraphicController gameGraphicController;
    Coup coup;
    PieceType pieceType;
    MoveAnimationType moveAnimationType;
    boolean finished;

    public AnimationMove(GameGraphicController gameGraphicController, Coup coup, PieceType pieceType, int duration, int xStart, int yStart, int xEnd, int yEnd, MoveAnimationType moveAnimationType){
        this.duration = duration;
        this.xStart = xStart;
        this.yStart = yStart;
        this.xEnd = xEnd;
        this.yEnd = yEnd;
        this.timeRemained = duration;
        this.gameGraphicController = gameGraphicController;
        this.coup = coup;
        this.pieceType = pieceType;
        x = xStart;
        y = yStart;
        this.moveAnimationType = moveAnimationType;
        finished = false;
    }

    @Override
    public void start(){

    }

    @Override
    public void update(int timeElapsed){

        int distanceX = xStart-xEnd;
        int distanceY = yStart-yEnd;

        float a = (float) (duration-timeRemained) / (float) duration;
        if(a != 0) {
            if (distanceX > 0) {
                x = (int) (xStart - a * (float) distanceX);
            } else {
                x = (int) (xStart - a * (float) distanceX);
            }
            if (distanceY > 0) {
                y = (int) (yStart - a * (float) distanceY);
            } else {
                y = (int) (yStart - a * (float) distanceY);
            }
        }

        timeRemained -= timeElapsed;
        if(timeRemained <= 0){
            gameGraphicController.executeMoveAnimation(coup, moveAnimationType);
            finished = true;
        }
    }

    @Override
    public void stop(){

    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    @Override
    public boolean isTerminated() {
        return finished;
    }

    public PieceType getPieceType(){
        return pieceType;
    }
}
