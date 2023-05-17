package View;

import Model.Grid;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class RulesPanel extends RoundPanel {
    int page = 0;
    JPanel page1;
    JPanel page2;
    JPanel page3;
    ImageIcon imageMovePossibles;
    ImageIcon imageCross;
    JLabel labelImageCross;

    public RulesPanel(int roundValue) {
        super(roundValue);

        loadAssets();
    }

    public void build(){

        Font fontDialog30 = new Font(Font.DIALOG, Font.BOLD, 30);
        Font fontDialog15 = new Font(Font.DIALOG, Font.BOLD, 15);

        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        labelImageCross = new JLabel(imageCross);
        c.gridx = 2;
        c.gridy = 0;
        c.anchor = GridBagConstraints.FIRST_LINE_END;
        c.insets = new Insets(10, 0, 0, 0);
        c.weightx = 0.1;
        add(labelImageCross, c);

        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets(0, 0, 0, 0);

        JLabel labelSpace = new JLabel("");
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 0.1;
        add(labelSpace, c);

        c.weightx = 1;

        page1 = createPage(this, 1, 1);

        JLabel labelTitleMoves = new JLabel("Déplacements");
        labelTitleMoves.setFont(fontDialog30);
        c.gridx = 1;
        c.gridy = 0;
        page1.add(labelTitleMoves, c);

        JLabel labelImgMovePossibles = new JLabel(imageMovePossibles);
        labelImgMovePossibles.setPreferredSize(new Dimension(256, 256));
        c.gridx = 1;
        c.gridy = 1;
        page1.add(labelImgMovePossibles, c);

        JLabel labelMovePossiblesExp = new JLabel("Les pièces se déplacent orthogonalement dans les 4 directions, comme une tour aux échecs");
        labelMovePossiblesExp.setFont(fontDialog15);
        c.gridx = 1;
        c.gridy = 2;
        c.insets = new Insets(15, 0, 0, 0);
        page1.add(labelMovePossiblesExp, c);

        page2 = createPage(this, 1, 2);

        page3 = createPage(this, 1, 3);

        page1.setVisible(true);

        setEventHandlers();
    }

    public JPanel createPage(JPanel parent, int x, int y){
        GridBagConstraints c = new GridBagConstraints();

        JPanel page = new JPanel();
        page.setLayout(new GridBagLayout());
        c.gridx = x;
        c.gridy = y;
        c.weightx = 0.8;
        c.weighty = 1;
        parent.add(page, c);
        page.setVisible(false);

        return page;
    }

    public void setEventHandlers(){

    }

    public void loadAssets(){
        try{
            Image imageMovePossiblesT = ImageIO.read(new File("assets/images/movePossibles.png"));
            imageMovePossibles = new ImageIcon(imageMovePossiblesT.getScaledInstance(256, 256, Image.SCALE_DEFAULT));
            Image imageCrossT = ImageIO.read(new File("assets/images/cross.png"));
            imageCross = new ImageIcon(imageCrossT.getScaledInstance(32, 32, Image.SCALE_DEFAULT));
        } catch(IOException exp){
            exp.printStackTrace();
        }
    }

    public void changePage(int newPage){

    }
}
