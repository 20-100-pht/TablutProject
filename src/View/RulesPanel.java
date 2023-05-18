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

    final int NPage = 6;

    int page = 0;
    JPanel page1;
    JPanel page2;
    JPanel page3;
    JPanel page4;
    JPanel page5;
    JPanel page6;
    Vector<JPanel> pages;
    Vector<JLabel> lLabelPageIndex;
    ImageIcon imageMovePossibles;
    ImageIcon imageCaptureClassic;
    ImageIcon imageCaptureSeveral;
    ImageIcon imageCaptureWall;
    ImageIcon imageCaptureCastle;
    ImageIcon imageCaptureSuicide;
    ImageIcon imageCaptureThrone;
    ImageIcon imageCross;
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

        JPanel leftPanel = new JPanel();
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
        add(rightPanel, c);

        c.weightx = 0;
        c.gridheight = 1;
        c.fill = GridBagConstraints.NONE;

        //Changement de page

        JPanel bottomPanel = new JPanel();
        bottomPanel.setOpaque(false);
        bottomPanel.setLayout(new GridBagLayout());
        c.gridx = 1;
        c.gridy = 2;
        c.weightx = 0.7;
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
        c.insets = new Insets(0, 0, 15, 0);
        c.anchor = GridBagConstraints.LAST_LINE_START;
        bottomPanel.add(labelPreviousPage, c);


        //Flèche droite

        labelNextPage = new JLabel(imageArrowRight);
        c.gridx = 2;
        c.gridy = 0;
        c.insets = new Insets(0, 0, 15, 0);
        c.anchor = GridBagConstraints.LAST_LINE_END;
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

        //Croix pour fermer

        labelImageCross = new JLabel(imageCross);
        labelImageCross.setPreferredSize(new Dimension(32, 32));
        c.anchor = GridBagConstraints.FIRST_LINE_END;
        c.gridx = 0;
        c.gridy = 0;
        c.weighty = 1;
        c.insets = new Insets(10, 0, 0, 0);
        rightPanel.add(labelImageCross, c);

        c.weighty = 0;
        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets(0, 0, 0, 0);


        //Page 1

        page1 = page3 = createPage(this, 1, 1);

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

        page2 = createPage(this, 1, 1);

        JLabel labelTitleCapturePawn = new JLabel("Captures de pions");
        labelTitleCapturePawn.setFont(fontDialog30);
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 5;
        c.insets = new Insets(0,0,65,0);
        page2.add(labelTitleCapturePawn, c);

        c.gridwidth = 1;
        c.insets = new Insets(0,0,0,0);

        JPanel centerP2Panel = new JPanel();
        c.gridx = 1;
        c.gridheight = 5;
        c.weightx = 0.2;
        page2.add(centerP2Panel, c);

        JPanel leftP2Panel = new JPanel();
        c.gridx = 0;
        c.gridheight = 5;
        c.weightx = 0.1;
        page2.add(leftP2Panel, c);

        JPanel rightP2Panel = new JPanel();
        c.gridx = 4;
        c.gridheight = 5;
        c.weightx = 0.1;
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
        labelImgCaptureThrone.setBackground(Color.red);
        labelImgCaptureThrone.setOpaque(true);
        c.gridx = 1;
        c.gridy = 3;
        c.insets = new Insets(0, 0, 0, 0);
        //c.insets = new Insets(80, 0, 0, 0);
        page2.add(labelImgCaptureThrone, c);

        c.insets = new Insets(0, 0, 0, 0);

        JLabel labelCaptureThrone = new JLabel("Tenaille contre le throne");
        labelCaptureThrone.setFont(fontDialog15);
        c.gridx = 1;
        c.gridy = 4;
        c.insets = new Insets(7, 0, 0, 0);
        page2.add(labelCaptureThrone, c);



        JLabel labelImgCapturePawn4 = new JLabel(imageCaptureCastle);
        labelImgCapturePawn4.setBackground(Color.red);
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

        page3 = createPage(this, 1, 1);

        JLabel labelTitleCaptureKing = new JLabel("Capture du roi");
        labelTitleCaptureKing.setFont(fontDialog30);
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 5;
        c.insets = new Insets(0,0,65,0);
        page3.add(labelTitleCaptureKing, c);

        //Page 4

        page4 = createPage(this, 1, 1);

        JLabel labelTitleGoodToKnow = new JLabel("Bon à savoir");
        labelTitleGoodToKnow.setFont(fontDialog30);
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 5;
        c.insets = new Insets(0,0,65,0);
        page4.add(labelTitleGoodToKnow, c);

        //Page 5

        page5 = createPage(this, 1, 1);

        JLabel labelTitleGoal = new JLabel("Objectif");
        labelTitleGoal.setFont(fontDialog30);
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 5;
        c.insets = new Insets(0,0,65,0);
        page5.add(labelTitleGoal, c);

        page6 = createPage(this, 1, 1);

        pages.add(page1);
        pages.add(page2);
        pages.add(page3);
        pages.add(page4);
        pages.add(page5);
        pages.add(page6);


        page1.setVisible(true);

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
        page.setOpaque(false);
        page.setLayout(new GridBagLayout());
        c.gridx = x;
        c.gridy = y;
        c.weightx = 0.8;
        c.weighty = 1;
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
            width = (int) (windowWidth/1.6);
        }
        else{
            width = (int) (windowWidth/1.3);
        }

        if(windowHeight > 850){
            height = (int) (windowHeight/1.5);
        }
        else{
            height = (int) (windowHeight/1.2);
        }

        return new Dimension(width, height);
    }
}
