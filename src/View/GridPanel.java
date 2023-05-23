package View;

import Animation.AnimationMove;
import Controller.GameGraphicController;
import Controller.GridPanelController;
import Global.Configuration;
import Model.*;
import Structure.Coordinate;
import View.GameFrame;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.File;
import java.io.IOException;
import java.util.Vector;

public class GridPanel extends JPanel {

    GameFrame gameFrame;
    GridPanelController gridPanelController;
    GameGraphicController gameGraphicController;
    Game game;
    Grid grid;
    LogicGrid gameLogic;
    Image imageCase;
    Image imageCase2;
    Image imageDefender;
    Image imageAttacker;
    Image imageKing;
    Image imageTrone;
    Image imageFortress;
    Image imageStepsH;
    Image imageStepsV;
    Image imageStepsHEnd;
    Image imageStepsVEnd;
    Image imageSelection;
    Image imageBulb;

    Vector<Coordinate> possibleMoveMarks;
    Coordinate selectionMarkCoords;
    Vector<Coordinate> moveMarksCoords;
    Coordinate hintMarkCoords;
    boolean frozen;
    Coordinate pieceHidedCoords = null;
    AnimationMove animationMove;

    String KING_ASSET_PATH;
    String DEFENDER_ASSET_PATH;
    String ATTACKER_ASSET_PATH;
    String THRONE_ASSET_PATH;
    String FORTRESS_ASSET_PATH;
    String TILE_ASSET_PATH;
    String TILE_2_ASSET_PATH;
    String TILE_ASSET_SELECTION;
    String STEPS_HORIZONTAL_ASSET_PATH;
    String STEPS_HORIZONTAL_END_ASSET_PATH;
    String STEPS_VERTICAL_ASSET_PATH;
    String STEPS_VERTICAL_END_ASSET_PATH;
    String SELECTION_ASSET_PATH;

    int theme;

    public static final int GRID_SIZE = 9;

    public GridPanel(GameFrame gameFrame){
        super();
        this.gameFrame = gameFrame;
        gameGraphicController = gameFrame.getGraphicGameController();
        game = gameGraphicController.getGameInstance();
        grid = game.getGridInstance();
        gameLogic = game.getLogicGridInstance();
        this.gridPanelController = new GridPanelController(this, gameGraphicController.getGameInstance().getLogicGrid(), gameGraphicController);

        possibleMoveMarks = new Vector<Coordinate>();

        frozen = false;
        theme = Configuration.getThemeIndex();

        loadThemeFileNames();
        loadAssets();
        setEventsHandlers();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (int l = 0; l < GRID_SIZE; l++) {
            for (int c = 0; c < GRID_SIZE; c++) {

                Image tileImage = imageCase;
                if((c+l)%2 == 0) tileImage = imageCase2;

                g.drawImage(tileImage, c * getCaseSize(), l * getCaseSize(), getCaseSize(), getCaseSize(), null);

                // Case selected mark
                if(selectionMarkCoords != null && !gameLogic.isEndGame()) {
                    int markX = (int) (selectionMarkCoords.getCol() * getCaseSize() + getCaseSize() * 0.01);
                    int markY = (int) (selectionMarkCoords.getRow() * getCaseSize() + getCaseSize() * 0.01);

                    g.drawImage(imageSelection, markX, markY, getCaseSize(), getCaseSize(), null);
                }
            }
        }
        if(moveMarksCoords != null && !moveMarksCoords.isEmpty()){

            Coordinate dir = new Coordinate(Integer.compare(moveMarksCoords.get(1).getRow(),moveMarksCoords.get(0).getRow()),Integer.compare(moveMarksCoords.get(1).getCol(),moveMarksCoords.get(0).getCol()));

            for(int i = 0; i < moveMarksCoords.size(); i++){
                Coordinate markCoords = moveMarksCoords.get(i);

                int markX = (int) (markCoords.getCol() * getCaseSize());
                int markY = (int) (markCoords.getRow() * getCaseSize());

                Image stepsEnd = imageStepsHEnd;
                Image steps = imageStepsH;

                int padX = (int) (getCaseSize()*0.5);
                int padY = (int) (getCaseSize()*0.5);

                if (dir.getCol() == 0) {
                    padX=0;
                }
                if (dir.getRow() == 0) {
                    padY=0;
                }else{
                    stepsEnd = imageStepsVEnd;
                    steps = imageStepsV;
                }

                if(i == 0 && theme != 0){
                    if(dir.getCol()<0 || dir.getRow()<0){
                        padX=0;
                        padY=0;
                    }
                    g.drawImage(stepsEnd,markX+padX, markY+padY, getCaseSize(), getCaseSize(), null);
                } else if (i == moveMarksCoords.size()-1  && theme != 0) {
                    if(dir.getCol()>0 || dir.getRow()>0){
                        padX=0;
                        padY=0;
                    }
                    g.drawImage(stepsEnd,markX+padX, markY+padY, getCaseSize(), getCaseSize(), null);
                }else{
                    g.drawImage(steps,markCoords.getCol() * getCaseSize(), markCoords.getRow() * getCaseSize(), getCaseSize(), getCaseSize(), null);
                }

            }
        }

        for (int l = 0; l < GRID_SIZE; l++) {
            for (int c = 0; c < GRID_SIZE; c++) {
                int pieceX = (int) (c * getCaseSize() + getCaseSize() * 0.15);
                int pieceY = (int) (l * getCaseSize() + getCaseSize() * 0.15);
                int pieceSize = (int) (getCaseSize() * 0.7);

                if (grid.isCornerPosition(new Coordinate(l, c))) {
                    g.drawImage(imageFortress, pieceX, pieceY, pieceSize, pieceSize, null);
                }

                Piece piece = grid.getPieceAtPosition(new Coordinate(l, c));

                if(l == 4 && c == 4){
                    g.drawImage(imageTrone, c * getCaseSize(), l * getCaseSize(), getCaseSize(), getCaseSize(), null);
                }

                if (piece == null) {
                    continue;
                }

                if(pieceHidedCoords != null && c == pieceHidedCoords.getCol() && l == pieceHidedCoords.getRow()) continue;

                Image pieceImage = getPieceImage(piece.getType());
                g.drawImage(pieceImage, pieceX, pieceY, pieceSize, pieceSize, null);

                if(animationMove != null){
                    Image imagePieceAnim = getPieceImage(animationMove.getPieceType());
                    g.drawImage(imagePieceAnim, (int) (animationMove.getX()+getCaseSize() * 0.15), (int) (animationMove.getY()+getCaseSize() * 0.15), pieceSize, pieceSize, null);
                }
            }
        }
        if (!gameLogic.isEndGame()){
            for (int i = 0; i < possibleMoveMarks.size(); i++) {
                Coordinate piecePos = possibleMoveMarks.get(i);
                if(piecePos.isSameCoordinate(hintMarkCoords)){
                    continue;
                }
                drawAccessibleMark(g, piecePos.getCol(), piecePos.getRow());
            }
        }

        if(hintMarkCoords != null){
            int markX = (int) (hintMarkCoords.getCol() * getCaseSize() + getCaseSize() * 0.2);
            int markY = (int) (hintMarkCoords.getRow() * getCaseSize() + getCaseSize() * 0.2);
            int markSize = (int) (getCaseSize()*0.6);
            g.drawImage(imageBulb, markX, markY, markSize, markSize, null);
        }
    }

    void drawAccessibleMark(Graphics g, int caseX, int caseY){
        int caseSize = getCaseSize();
        int x = (int) (caseX*caseSize + caseSize*0.35);
        int y = (int) (caseY*caseSize + caseSize*0.35);
        int markSize = (int) (caseSize*0.3);
        g.setColor(new Color(50,50,50,50));
        g.fillOval(x, y, markSize, markSize);
    }

    public void loadAssets(){
        try{
            imageCase = ImageIO.read(new File(TILE_ASSET_PATH));
            imageCase2 = ImageIO.read(new File(TILE_2_ASSET_PATH));
            imageDefender = ImageIO.read(new File(DEFENDER_ASSET_PATH));
            imageAttacker = ImageIO.read(new File(ATTACKER_ASSET_PATH));
            imageKing = ImageIO.read(new File(KING_ASSET_PATH));
            imageFortress = ImageIO.read(new File(FORTRESS_ASSET_PATH));
            imageTrone = ImageIO.read(new File(THRONE_ASSET_PATH));
            imageStepsH = ImageIO.read(new File(STEPS_HORIZONTAL_ASSET_PATH));
            imageStepsHEnd = ImageIO.read(new File(STEPS_HORIZONTAL_END_ASSET_PATH));
            imageStepsV = ImageIO.read(new File(STEPS_VERTICAL_ASSET_PATH));
            imageStepsVEnd = ImageIO.read(new File(STEPS_VERTICAL_END_ASSET_PATH));
            imageSelection = ImageIO.read(new File(TILE_ASSET_SELECTION));
            imageBulb = ImageIO.read(new File("assets/images/bulb.png"));
        } catch(IOException exp){
            exp.printStackTrace();
        }
    }

    public int getCaseSize(){
        return (this.getWidth()+4)/GRID_SIZE;
    }

    @Override
    public Dimension getPreferredSize(){
        int i = 0;
        if(gameFrame.getHeight() > 1000)
            i = Math.min((int) ((gameFrame.getWidth()*0.5) / 2), (int) ((gameFrame.getHeight()*0.7) / 2));
        else
            i = Math.min((int) ((gameFrame.getWidth()*0.5) / 2), (int) ((gameFrame.getHeight()*0.6) / 2));
        return new Dimension(i*2, i*2);
    }

    void setEventsHandlers(){
        this.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                super.mouseMoved(e);
                if(frozen) return;
                gridPanelController.mouseMovedHandler(e);
            }
        });

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                if(selectionMarkCoords == null) {
                    possibleMoveMarks.clear();
                }
            }

            @Override
            public void mouseReleased(MouseEvent e){
                super.mouseClicked(e);
                if(frozen) return;
                gridPanelController.mouseReleasedHandler(e);
            }
        });
    }

    public void addMovePossibleMark(Coordinate c){
        possibleMoveMarks.add(c);
    }

    public void clearMovePossibleMarks(){
        possibleMoveMarks.clear();
    }

    public void setSelectionMarkCoords(Coordinate coords){
        selectionMarkCoords = coords;
    }

    public void setFrozen(boolean frozen){
        this.frozen = frozen;
    }

    public void setPieceHidedCoords(Coordinate pieceHidedCoords){
        this.pieceHidedCoords = pieceHidedCoords;
    }

    public void setAnimationMove(AnimationMove animation){
        animationMove = animation;
    }

    public boolean anAnimationNotTerminated(){
        return animationMove != null && !animationMove.isTerminated();
    }

    public Image getPieceImage(PieceType type){
        if (type == PieceType.DEFENDER) {
            return imageDefender;
        } else if (type == PieceType.KING) {
            return imageKing;
        } else if (type == PieceType.ATTACKER) {
            return imageAttacker;
        }
        return null;
    }

    public void setMoveMarkCoords(Vector<Coordinate> markCoords){
        moveMarksCoords = markCoords;
    }

    public GridPanelController getGridPanelController(){
        return gridPanelController;
    }

    public void loadThemePixelartFileNames(){
        KING_ASSET_PATH = "assets/images/king.png";
        DEFENDER_ASSET_PATH = "assets/images/defender2.png";
        ATTACKER_ASSET_PATH ="assets/images/attacker2.png";
        THRONE_ASSET_PATH = "assets/images/throne3.png";
        FORTRESS_ASSET_PATH = "assets/images/fortress2.png";
        TILE_ASSET_PATH= "assets/images/tile_11.png";
        TILE_2_ASSET_PATH= "assets/images/tile_12.png";
        TILE_ASSET_SELECTION= "assets/images/tile_11_selection.png";
        STEPS_HORIZONTAL_ASSET_PATH= "assets/images/steps2H.png";
        STEPS_HORIZONTAL_END_ASSET_PATH= "assets/images/steps2HE.png";
        STEPS_VERTICAL_ASSET_PATH= "assets/images/steps2V.png";
        STEPS_VERTICAL_END_ASSET_PATH= "assets/images/steps2VE.png";

    }
    public void loadThemeSimpleFileNames(){
        KING_ASSET_PATH = "assets/images/theme_1_king.png";
        DEFENDER_ASSET_PATH = "assets/images/theme_1_defender.png";
        ATTACKER_ASSET_PATH ="assets/images/theme_1_attacker.png";
        THRONE_ASSET_PATH = "assets/images/theme_1_throne.png";
        FORTRESS_ASSET_PATH = "assets/images/theme_1_fortress.png";
        TILE_ASSET_PATH= "assets/images/theme_1_tile_1.png";
        TILE_2_ASSET_PATH= "assets/images/theme_1_tile_0.png";
        TILE_ASSET_SELECTION="assets/images/theme_1_step.png";
        STEPS_HORIZONTAL_ASSET_PATH= "assets/images/theme_1_step.png";
        STEPS_HORIZONTAL_END_ASSET_PATH= "assets/images/theme_1_step.png";
        STEPS_VERTICAL_ASSET_PATH= "assets/images/theme_1_step.png";
        STEPS_VERTICAL_END_ASSET_PATH= "assets/images/theme_1_step.png";
    }

    public void updateTheme(){
        int aTheme = theme;
        theme = Configuration.getThemeIndex();
        if(aTheme != theme){
            loadThemeFileNames();
            loadAssets();
        }
    }

    public void loadThemeFileNames(){
        switch (theme){
            case 0:
                loadThemeSimpleFileNames();
                break;
            case 1:
                loadThemePixelartFileNames();
                break;
            default:
                loadThemeSimpleFileNames();
        }
    }

    public AnimationMove getAnimationMove(){
        return animationMove;
    }

    public void setHintMarkCoords(Coordinate coords){
        hintMarkCoords = coords;
    }

}
