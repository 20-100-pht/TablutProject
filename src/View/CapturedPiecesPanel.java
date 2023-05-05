package View;

import Model.GameRules;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class CapturedPiecesPanel extends JPanel {

    final int PIECE_SIZE = 32;
    Image imageDefender;
    Image imageAttacker;
    boolean isAttackerPieces;
    GameRules gameLogic;
    public CapturedPiecesPanel(GameFrame gameFrame, boolean isAttackerPieces){
        this.isAttackerPieces = isAttackerPieces;
        gameLogic = gameFrame.getGraphicGameController().getGameInstance().getLogicGrid();
        loadAssets();
    }

    public void loadAssets(){
        try{
            imageDefender = ImageIO.read(new File("assets/images/defender.png"));
            imageAttacker = ImageIO.read(new File("assets/images/attacker.png"));
        } catch(IOException exp){
            exp.printStackTrace();
        }
    }

    int getNPiece(){
        int result = 0;
        if(isAttackerPieces) result = 16 - gameLogic.getNbPieceAttackerOnGrid();
        if(!isAttackerPieces) result = 8 - gameLogic.getNbPieceDefenderOnGrid();
        return result;
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);

        int xL1 = getWidth()/2 - getFirstLineWidth()/2;
        int xL2 = getWidth()/2 - getSecondLineWidth()/2;
        drawLine(g, xL1, 0, Math.min(8, getNPiece()));
        if(getNPiece() >= 9) {
            drawLine(g, xL2, PIECE_SIZE / 2, getNPiece() % 8);
        }
    }

    public void drawLine(Graphics g, int xS, int yS, int n){
        int x = xS;
        Image image;
        if(isAttackerPieces){
            image = imageAttacker;
        }
        else{
            image = imageDefender;
        }

        for(int i = 0; i < n; i++){
            g.drawImage(image, x, yS, PIECE_SIZE, PIECE_SIZE, null);
            x += PIECE_SIZE/2;
        }
    }

    public int getFirstLineWidth(){
        int n = Math.min(8, getNPiece());
        return n * (PIECE_SIZE/2) + PIECE_SIZE/2;
    }

    public int getSecondLineWidth(){
        int n = getNPiece()%8;
        return n * (PIECE_SIZE/2) + PIECE_SIZE/2;
    }
}
