package View;

import Model.Grid;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.Vector;

public class RulesPanel extends RoundPanel {

    final int NPage = 5;

    int page = 0;
    JPanel page1;
    JPanel page2;
    JPanel page3;
    JPanel page4;
    JPanel page5;
    Vector<JPanel> pages;
    Vector<JLabel> lLabelPageIndex;
    ImageIcon imageMovePossibles;
    ImageIcon imageCaptureClassic;
    ImageIcon imageCaptureSeveral;
    ImageIcon imageCaptureWall;
    ImageIcon imageCaptureCastle;
    ImageIcon imageCaptureSuicide;
    ImageIcon imageCaptureThrone;
    ImageIcon imageCaptureKWall;
    ImageIcon imageCaptureKClassic;
    ImageIcon imageAttacker;
    ImageIcon imageDefender;
    ImageIcon imageKing;
    ImageIcon imageCross;
    ImageIcon imageGridFortress;
    JLabel labelImageCross;
    ImageIcon imageArrowLeft;
    ImageIcon imageArrowRight;
    JLabel labelPreviousPage;
    JLabel labelNextPage;
    ImageIcon imagePageIndexF;
    ImageIcon imagePageIndexNF;
    GameFrame gameFrame;

    public RulesPanel(GameFrame gameFrame, int roundValue) {
        super(roundValue);

        page = 0;
        this.gameFrame = gameFrame;

        loadAssets();
    }

    public void build(){

        pages = new Vector<JPanel>();
        lLabelPageIndex = new Vector<JLabel>();

        Font fontDialog30 = new Font(Font.DIALOG, Font.BOLD, 30);
        Font fontDialog15 = new Font(Font.DIALOG, Font.BOLD, 18);

        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        setBackground(Color.blue);

        /*JPanel leftPanel = new JPanel();
        leftPanel.setOpaque(false);
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 0.15;
        c.gridheight = 3;
        c.fill = GridBagConstraints.BOTH;
        add(leftPanel, c);

        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new GridBagLayout());
        rightPanel.setOpaque(false);
        c.gridx = 2;
        c.gridy = 0;
        c.weightx = 0.15;
        c.gridheight = 3;
        c.fill = GridBagConstraints.BOTH;
        add(rightPanel, c);*/

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridBagLayout());
        topPanel.setOpaque(false);
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1;
        c.fill = GridBagConstraints.BOTH;
        add(topPanel, c);

        c.gridheight = 1;
        c.weightx = 0;
        c.fill = GridBagConstraints.NONE;

        //Changement de page

        JPanel bottomPanel = new JPanel();
        bottomPanel.setOpaque(false);
        bottomPanel.setLayout(new GridBagLayout());
        c.gridx = 0;
        c.gridy = 2;
        c.weightx = 0.7;
        c.insets = new Insets(7, 0, 7, 0);
        c.fill = GridBagConstraints.HORIZONTAL;
        add(bottomPanel, c);

        c.weightx = 0;
        c.fill = GridBagConstraints.NONE;
        //c.weighty = 1;
        c.weightx = 0.3;

        //Flèche gauche

        labelPreviousPage = new JLabel(imageArrowLeft);
        c.gridx = 0;
        c.gridy = 0;
        c.insets = new Insets(0, 0, 0, 40);
        c.anchor = GridBagConstraints.LAST_LINE_END;
        bottomPanel.add(labelPreviousPage, c);


        //Flèche droite

        labelNextPage = new JLabel(imageArrowRight);
        c.gridx = 2;
        c.gridy = 0;
        c.insets = new Insets(0, 40, 0, 0);
        c.anchor = GridBagConstraints.LAST_LINE_START;
        bottomPanel.add(labelNextPage, c);

        c.anchor = GridBagConstraints.CENTER;
        c.weightx = 0;
        c.insets = new Insets(0, 0, 0, 0);

        //Page index

        JPanel panelPageIndex = new JPanel();
        panelPageIndex.setOpaque(false);
        c.gridx = 1;
        c.gridy = 0;
        bottomPanel.add(panelPageIndex, c);

        for(int i = 0; i < NPage; i++){
            JLabel label = createLabelPageIndex(panelPageIndex, 0+i, 0);
            lLabelPageIndex.add(label);
        }

        lLabelPageIndex.elementAt(0).setIcon(imagePageIndexF);

        JLabel labelRulesTitle = new JLabel("Règles");
        labelRulesTitle.setFont(new Font(Font.DIALOG, Font.BOLD, 22));
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 0.5;
        c.anchor = GridBagConstraints.BASELINE_LEADING;
        c.insets = new Insets(16, 18, 0, 0);
        topPanel.add(labelRulesTitle, c);

        //Croix pour fermer

        labelImageCross = new JLabel(imageCross);
        c.gridx = 1;
        c.gridy = 0;
        c.weightx = 0.5;
        c.anchor = GridBagConstraints.BASELINE_TRAILING;
        c.insets = new Insets(13, 0, 13, 13);
        topPanel.add(labelImageCross, c);

        c.weighty = 0;
        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets(0, 0, 0, 0);
        c.fill = GridBagConstraints.NONE;


        //Page 1

        page1 = createPage(this, 0, 1);

        JLabel labelTitleMoves = new JLabel("Déplacements");
        labelTitleMoves.setFont(fontDialog30);
        c.gridx = 1;
        c.gridy = 0;
        page1.add(labelTitleMoves, c);

        //createSpaceY(page1, 1, 1, 0.2);

        JLabel labelImgMovePossibles = new JLabel(imageMovePossibles);
        labelImgMovePossibles.setPreferredSize(new Dimension(256, 256));
        c.gridx = 1;
        c.gridy = 2;
        c.insets = new Insets(50, 0, 20, 0);
        page1.add(labelImgMovePossibles, c);

        c.insets = new Insets(15, 0, 0, 0);

        JLabel labelMovePossiblesExp = new JLabel("Les pièces se déplacent orthogonalement dans les 4 directions, comme une tour aux échecs.");
        labelMovePossiblesExp.setFont(fontDialog15);
        c.gridx = 1;
        c.gridy = 3;
        page1.add(labelMovePossiblesExp, c);

        JLabel labelMovePossiblesExp2 = new JLabel("Elles peuvent bouger de plusieurs cases en une seule fois.");
        labelMovePossiblesExp2.setFont(fontDialog15);
        c.gridx = 1;
        c.gridy = 4;
        page1.add(labelMovePossiblesExp2, c);

        c.insets = new Insets(0, 0, 0, 0);

        //Page 2

        page2 = createPage(this, 0, 1);

        JLabel labelTitleCapturePawn = new JLabel("Capture de pions");
        labelTitleCapturePawn.setFont(fontDialog30);
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 5;
        c.insets = new Insets(0,0,65,0);
        page2.add(labelTitleCapturePawn, c);

        c.gridwidth = 1;
        c.insets = new Insets(0,0,0,0);

        JPanel centerP2Panel = new JPanel();
        centerP2Panel.setOpaque(false);
        c.gridx = 1;
        c.gridheight = 5;
        c.weightx = 0.2;
        page2.add(centerP2Panel, c);

        JPanel leftP2Panel = new JPanel();
        leftP2Panel.setOpaque(false);
        c.gridx = 0;
        c.gridheight = 5;
        c.weightx = 0.1;
        page2.add(leftP2Panel, c);

        JPanel rightP2Panel = new JPanel();
        rightP2Panel.setOpaque(false);
        c.gridx = 4;
        c.gridheight = 5;
        c.weightx = 0.2;
        page2.add(rightP2Panel, c);

        c.gridheight = 1;
        c.weightx = 0;


        JLabel labelImgCapturePawn1 = new JLabel(imageCaptureClassic);
        c.gridx = 1;
        c.gridy = 1;
        //c.insets = new Insets(50, 0, 20, 0);
        page2.add(labelImgCapturePawn1, c);

        //c.insets = new Insets(0, 0, 50, 0);

        JLabel labelCapturePos1 = new JLabel("Tenaille classique");
        labelCapturePos1.setFont(fontDialog15);
        c.gridx = 1;
        c.gridy = 2;
        c.insets = new Insets(7, 0, 55, 0);
        page2.add(labelCapturePos1, c);

        c.insets = new Insets(0, 0, 0, 0);

        JLabel labelImgCapturePawn3 = new JLabel(imageCaptureWall);
        c.gridx = 3;
        c.gridy = 1;
        c.insets = new Insets(0, 0, 0, 0);
        page2.add(labelImgCapturePawn3, c);

        //c.insets = new Insets(0, 0, 50, 0);

        JLabel labelCapturePos3 = new JLabel("Tenaille contre un mur");
        labelCapturePos3.setFont(fontDialog15);
        c.gridx = 3;
        c.gridy = 2;
        c.insets = new Insets(7, 0, 55, 0);
        page2.add(labelCapturePos3, c);

        JLabel labelImgCaptureThrone = new JLabel(imageCaptureThrone);
        labelImgCaptureThrone.setOpaque(true);
        c.gridx = 1;
        c.gridy = 3;
        c.insets = new Insets(0, 0, 0, 0);
        //c.insets = new Insets(80, 0, 0, 0);
        page2.add(labelImgCaptureThrone, c);

        c.insets = new Insets(0, 0, 0, 0);

        JLabel labelCaptureThrone = new JLabel("Tenaille contre le trône");
        labelCaptureThrone.setFont(fontDialog15);
        c.gridx = 1;
        c.gridy = 4;
        c.insets = new Insets(7, 0, 0, 0);
        page2.add(labelCaptureThrone, c);



        JLabel labelImgCapturePawn4 = new JLabel(imageCaptureCastle);
        labelImgCapturePawn4.setOpaque(true);
        c.gridx = 3;
        c.gridy = 3;
        page2.add(labelImgCapturePawn4, c);

        JLabel labelCapturePos4 = new JLabel("Tenaille contre une forteresse");
        labelCapturePos4.setFont(fontDialog15);
        c.gridx = 3;
        c.gridy = 4;
        c.insets = new Insets(7, 0, 0, 0);
        page2.add(labelCapturePos4, c);


        //Page 3

        page3 = createPage(this, 0, 1);

        JLabel labelTitleCaptureKing = new JLabel("Capture du roi");
        labelTitleCaptureKing.setFont(fontDialog30);
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 5;
        c.insets = new Insets(0,0,40,0);
        page3.add(labelTitleCaptureKing, c);

        c.gridwidth = 1;

        JPanel centerP3Panel = new JPanel();
        centerP3Panel.setOpaque(false);
        c.gridx = 1;
        c.gridheight = 2;
        c.weightx = 0.1;
        page3.add(centerP3Panel, c);

        JPanel leftP3Panel = new JPanel();
        leftP3Panel.setOpaque(false);
        c.gridx = 0;
        c.gridheight = 2;
        c.weightx = 0.4;
        page3.add(leftP3Panel, c);

        JPanel rightP3Panel = new JPanel();
        rightP3Panel.setOpaque(false);
        c.gridx = 4;
        c.gridheight = 2;
        c.weightx = 0.4;
        page3.add(rightP3Panel, c);

        c.gridheight = 1;
        c.weightx = 0;

        JLabel labelImgCaptureKClassic = new JLabel(imageCaptureKClassic);
        c.gridx = 1;
        c.gridy = 1;
        c.weightx = 0.5;
        c.insets = new Insets(0, 0, 7, 0);
        page3.add(labelImgCaptureKClassic, c);

        //c.insets = new Insets(0, 0, 50, 0);

        JLabel labelCaptureKClassic = new JLabel("Tenaille classique");
        labelCaptureKClassic.setFont(fontDialog15);
        c.gridx = 1;
        c.gridy = 2;
        c.insets = new Insets(0, 0, 0, 0);
        page3.add(labelCaptureKClassic, c);

        c.insets = new Insets(0, 0, 0, 0);

        JLabel labelImgCaptureKWall = new JLabel(imageCaptureKWall);
        c.gridx = 3;
        c.gridy = 1;
        c.weightx = 0.5;
        c.insets = new Insets(0, 0, 7, 0);
        page3.add(labelImgCaptureKWall, c);

        //c.insets = new Insets(0, 0, 50, 0);

        JLabel labelCaptureKWall = new JLabel("Tenaille contre un mur");
        labelCaptureKWall.setFont(fontDialog15);
        c.gridx = 3;
        c.gridy = 2;
        c.insets = new Insets(0, 0, 0, 0);
        page3.add(labelCaptureKWall, c);

        JLabel labelCaptureKExp = new JLabel("Comme pour les pions, il peut être capturé contre les forteresses et le trône.");
        labelCaptureKExp.setFont(fontDialog15);
        c.gridx = 0;
        c.gridy = 3;
        c.gridwidth = 5;
        c.insets = new Insets(35, 0, 0, 0);
        page3.add(labelCaptureKExp, c);

        c.gridwidth = 1;

        //Page 4

        page4 = createPage(this, 0, 1);

        JLabel labelTitleGoodToKnow = new JLabel("Bon à savoir");
        labelTitleGoodToKnow.setFont(fontDialog30);
        c.gridx = 0;
        c.gridy = 1;
        //c.gridwidth = 5;
        c.insets = new Insets(0,0,32,0);
        page4.add(labelTitleGoodToKnow, c);

        JLabel labelImgSuicide = new JLabel(imageCaptureSuicide);
        c.gridx = 0;
        c.gridy = 2;
        c.insets = new Insets(0,0,0,0);
        page4.add(labelImgSuicide, c);

        JLabel labelCaptureSuicide = new JLabel("1) Une pièce qui se \"suicide\" n'est pas capturée.");
        labelCaptureSuicide.setFont(fontDialog15);
        c.gridx = 0;
        c.gridy = 3;
        c.insets = new Insets(7, 0, 40, 0);
        page4.add(labelCaptureSuicide, c);

        JLabel labelImgCaptureSeveral = new JLabel(imageCaptureSeveral);
        c.gridx = 0;
        c.gridy = 4;
        c.insets = new Insets(0,0,0,0);
        page4.add(labelImgCaptureSeveral, c);

        JLabel labelCaptureSeveral = new JLabel("2) Plusieurs pièces peuvent être capturées en un seul coup.");
        labelCaptureSeveral.setFont(fontDialog15);
        c.gridx = 0;
        c.gridy = 5;
        c.insets = new Insets(7, 0, 0, 0);
        page4.add(labelCaptureSeveral, c);

        JLabel labelCaptureKingExc = new JLabel("3) Le roi ne peut pas capturer");
        labelCaptureKingExc.setFont(fontDialog15);
        c.gridx = 0;
        c.gridy = 6;
        c.insets = new Insets(13, 0, 0, 0);
        page4.add(labelCaptureKingExc, c);



        //Page 5

        page5 = createPage(this, 0, 1);

        JLabel labelTitleGoal = new JLabel("But du jeu");
        labelTitleGoal.setFont(fontDialog30);
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 2;
        c.insets = new Insets(0,0,25,0);
        page5.add(labelTitleGoal, c);

        c.gridwidth = 1;

        JLabel labelAttacker = new JLabel("Le roi doit parvenir à se cacher dans une de ses forteresses.");
        labelAttacker.setFont(fontDialog15);
        c.gridx = 1;
        c.gridy = 1;
        c.insets = new Insets(7, 0, 0, 0);
        page5.add(labelAttacker, c);

        JLabel labelImgGridFortress = new JLabel(imageGridFortress);
        c.gridx = 1;
        c.gridy = 2;
        c.insets = new Insets(10,0,10,0);
        page5.add(labelImgGridFortress, c);

        JLabel labelDefender = new JLabel("S'il réussit à s'échapper, les défenseurs gagnent.");
        labelDefender.setFont(fontDialog15);
        c.gridx = 1;
        c.gridy = 3;
        c.insets = new Insets(7, 0, 0, 0);
        page5.add(labelDefender, c);

        /*JLabel labelImgKing = new JLabel(imageKing);
        c.gridx = 0;
        c.gridy = 3;
        c.insets = new Insets(0,0,0,0);
        page5.add(labelImgKing, c);*/

        JLabel labelKing = new JLabel("S'il échoue et se fait capturer, les attaquants gagnent.");
        labelKing.setFont(fontDialog15);
        c.gridx = 1;
        c.gridy = 4;
        c.insets = new Insets(7, 0, 0, 0);
        page5.add(labelKing, c);

        pages.add(page5);
        pages.add(page1);
        pages.add(page2);
        pages.add(page3);
        pages.add(page4);


        page5.setVisible(true);

        setEventHandlers();
    }

    public void createSpaceY(JPanel parent, int gridx, int gridy, double weighty){
        GridBagConstraints c = new GridBagConstraints();
        JLabel space = new JLabel(" ");
        c.gridx = gridx;
        c.gridy = gridy;
        c.weighty = weighty;
        parent.add(space, c);
    }

    public JPanel createPage(JPanel parent, int x, int y){
        GridBagConstraints c = new GridBagConstraints();

        JPanel page = new JPanel();
        page.setOpaque(true);
        page.setLayout(new GridBagLayout());
        c.gridx = x;
        c.gridy = y;
        c.weightx = 0.7;
        c.weighty = 1;
        c.gridwidth = 3;
        c.fill = GridBagConstraints.BOTH;
        parent.add(page, c);
        page.setVisible(false);

        return page;
    }

    public JLabel createLabelPageIndex(JPanel parent, int gridX, int gridY){
        GridBagConstraints c = new GridBagConstraints();

        JLabel label = new JLabel(imagePageIndexNF);
        c.gridx = gridX;
        c.gridy = gridY;
        parent.add(label, c);

        return label;
    }

    public void setEventHandlers(){

        RulesPanel rulesPanel = this;

        labelImageCross.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                gameFrame.hideRulesPanel();
            }
        });

        labelPreviousPage.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                previous();
            }
        });

        labelNextPage.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                next();
            }
        });
    }

    public void loadAssets(){
        try{
            Image imageMovePossiblesT = ImageIO.read(new File("assets/images/movePossibles.png"));
            imageMovePossibles = new ImageIcon(imageMovePossiblesT.getScaledInstance(256, 256, Image.SCALE_DEFAULT));
            Image imageCaptureClassicT = ImageIO.read(new File("assets/images/kill_1.png"));
            imageCaptureClassic = new ImageIcon(imageCaptureClassicT.getScaledInstance(330, 90, Image.SCALE_DEFAULT));
            Image imageCaptureSeveralT = ImageIO.read(new File("assets/images/kill_2.png"));
            imageCaptureSeveral = new ImageIcon(imageCaptureSeveralT.getScaledInstance(256, 173, Image.SCALE_DEFAULT));
            Image imageCaptureWallT = ImageIO.read(new File("assets/images/kill_3.png"));
            imageCaptureWall = new ImageIcon(imageCaptureWallT.getScaledInstance(330, 90, Image.SCALE_DEFAULT));
            Image imageCaptureCastleT = ImageIO.read(new File("assets/images/kill_4.png"));
            imageCaptureCastle = new ImageIcon(imageCaptureCastleT.getScaledInstance(330, 90, Image.SCALE_DEFAULT));
            Image imageCaptureSuicideT = ImageIO.read(new File("assets/images/suicide.png"));
            imageCaptureSuicide = new ImageIcon(imageCaptureSuicideT.getScaledInstance(216, 140, Image.SCALE_DEFAULT));
            Image imageCaptureThroneT = ImageIO.read(new File("assets/images/kill_5.png"));
            imageCaptureThrone = new ImageIcon(imageCaptureThroneT.getScaledInstance(330, 90, Image.SCALE_DEFAULT));
            Image imageGridFortressT = ImageIO.read(new File("assets/images/fortressRules.png"));
            imageGridFortress = new ImageIcon(imageGridFortressT.getScaledInstance(350, 350, Image.SCALE_DEFAULT));

            Image imageCaptureKClassicT = ImageIO.read(new File("assets/images/kill_king1.png"));
            imageCaptureKClassic = new ImageIcon(imageCaptureKClassicT.getScaledInstance(200, 200, Image.SCALE_DEFAULT));
            Image imageCaptureKWallT = ImageIO.read(new File("assets/images/kill_king3.png"));
            imageCaptureKWall = new ImageIcon(imageCaptureKWallT.getScaledInstance(200, 200, Image.SCALE_DEFAULT));

            Image imageDefenderT = ImageIO.read(new File("assets/images/defender2.png"));
            imageDefender = new ImageIcon(imageDefenderT.getScaledInstance(128, 128, Image.SCALE_DEFAULT));
            Image imageAttackerT = ImageIO.read(new File("assets/images/attacker2.png"));
            imageAttacker = new ImageIcon(imageAttackerT.getScaledInstance(128, 128, Image.SCALE_DEFAULT));
            Image imageKingT = ImageIO.read(new File("assets/images/attacker2.png"));
            imageKing = new ImageIcon(imageKingT.getScaledInstance(128, 128, Image.SCALE_DEFAULT));

            Image imageCrossT = ImageIO.read(new File("assets/images/cross.png"));
            imageCross = new ImageIcon(imageCrossT.getScaledInstance(32, 32, Image.SCALE_DEFAULT));
            imageArrowLeft = new ImageIcon(ImageIO.read(new File("assets/images/arrow4_left.png")));
            imageArrowRight = new ImageIcon(ImageIO.read(new File("assets/images/arrow4_right.png")));
            imagePageIndexF = new ImageIcon(ImageIO.read(new File("assets/images/pageIndexF.png")));
            imagePageIndexNF = new ImageIcon(ImageIO.read(new File("assets/images/pageIndexNF.png")));
        } catch(IOException exp){
            exp.printStackTrace();
        }
    }

    public void previous(){
        int nI = page;
        if(page == 0){
            nI = pages.size()-1;
        }
        else{
            nI--;
        }
        changePage(nI);
    }

    public void next(){
        int nI = page;
        if(page == pages.size()-1){
            nI = 0;
        }
        else{
            nI++;
        }
        changePage(nI);
    }

    public void changePage(int newPage){
        System.out.print(newPage);

        int nPage = pages.size();
        for(int i = 0; i < nPage; i++){
            pages.elementAt(i).setVisible(false);
            lLabelPageIndex.elementAt(i).setIcon(imagePageIndexNF);
        }

        lLabelPageIndex.elementAt(newPage).setIcon(imagePageIndexF);
        pages.elementAt(newPage).setVisible(true);

        page = newPage;
    }

    public Dimension getPreferredSize(){
        int windowWidth = gameFrame.getInterface().getWindow().getWidth();
        int windowHeight = gameFrame.getInterface().getWindow().getHeight();

        int width;
        int height;
        if(windowWidth > 1200){
            width = (int) (windowWidth/1.4);
        }
        else{
            width = (int) (windowWidth/1.2);
        }

        if(windowHeight > 1050){
            height = (int) (windowHeight/1.5);
        }
        else{
            height = (int) (windowHeight/1.1);
        }

        return new Dimension(width, height);
    }
}
