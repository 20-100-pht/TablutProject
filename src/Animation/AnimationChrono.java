package Animation;

import Controller.GameGraphicController;
import Model.Game;
import View.GameFrame;

public class AnimationChrono extends Animation {

    Game game;
    GameFrame gameFrame;

    public AnimationChrono(Game game, GameFrame gameFrame){
        this.game = game;
        this.gameFrame = gameFrame;
    }

    @Override
    public void update(int timeElapsed){
        game.updatePlayerTurnChrono(timeElapsed);
        gameFrame.updateChronoLabels();
    }

    public boolean isTerminated() {
        return false;
    }
}
