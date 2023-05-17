package View;

import Model.Grid;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

public class RulesPanel extends RoundPanel {

    final int NPage = 3;

    int page = 0;
    JPanel page1;
    JPanel page2;
    JPanel page3;
    ImageIcon imageMovePossibles;
    ImageIcon imageCross;
    JLabel labelImageCross;
    ImageIcon imageArrowLeft;
    ImageIcon imageArrowRight;
    JLabel labelPreviousPage;
    JLabel labelNextPage;
    ImageIcon imagePageIndexF;
    ImageIcon imagePageIndexNF;
    JLabel labelPage1Index;
    JLabel labelPage2Index;
    JLabel labelPage3Index;
    GameFrame gameFrame;

    public RulesPanel(GameFrame gameFrame, int roundValue) {
        super(roundValue);

        page = 0;
        this.gameFrame = gameFrame;

        loadAssets();
    }

    public void build(){

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
        c.insets = new Insets(0, 15, 15, 0);
        c.anchor = GridBagConstraints.LAST_LINE_START;
        bottomPanel.add(labelPreviousPage, c);


        //Flèche droite

        labelNextPage = new JLabel(imageArrowRight);
        c.gridx = 2;
        c.gridy = 0;
        c.insets = new Insets(0, 0, 15, 15);
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

        labelPage1Index = createLabelPageIndex(panelPageIndex, 0, 0);
        labelPage2Index = createLabelPageIndex(panelPageIndex, 1, 0);
        labelPage3Index = createLabelPageIndex(panelPageIndex, 2, 0);
        labelPage1Index.setIcon(imagePageIndexF);

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

        page1 = createPage(this, 1, 1);

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

        page2 = createPage(this, 1, 1);

        page3 = createPage(this, 1, 1);


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

            labelImageCross.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseReleased(MouseEvent e) {
                    super.mouseReleased(e);
                    setVisible(false);
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
            nI = NPage-1;
        }
        else{
            nI--;
        }
        changePage(nI);
    }

    public void next(){
        int nI = page;
        if(page == NPage-1){
            nI = 0;
        }
        else{
            nI++;
        }
        changePage(nI);
    }

    public void changePage(int newPage){
        System.out.print(newPage);

        labelPage1Index.setIcon(imagePageIndexNF);
        labelPage2Index.setIcon(imagePageIndexNF);
        labelPage3Index.setIcon(imagePageIndexNF);
        page1.setVisible(false);
        page2.setVisible(false);
        page3.setVisible(false);

        if(newPage == 0){
            labelPage1Index.setIcon(imagePageIndexF);
            page1.setVisible(true);
        }
        else if(newPage == 1){
            labelPage2Index.setIcon(imagePageIndexF);
            page2.setVisible(true);
        }
        else if(newPage == 2){
            labelPage3Index.setIcon(imagePageIndexF);
            page3.setVisible(true);
        }
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
            width = (int) (windowWidth/1.4);
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
