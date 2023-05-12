package View;

import AI.AIDifficulty;
import Model.Game;
import Model.Grid;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

public class NewGameFrame extends Frame {

    JTextField tfNameAtt;
    JTextField tfNameDef;
    JButton bttnStart;
    JPanel centralPanel;
    ImageIcon returnImage;
    JLabel returnImageButton;
    JRadioButton rdoHumanAttPart;
    JRadioButton rdoEasyAttPart;
    JRadioButton rdoMediumAttPart;
    JRadioButton rdoDifficultAttPart;
    JRadioButton rdoHumanDefPart;
    JRadioButton rdoEasyDefPart;
    JRadioButton rdoMediumDefPart;
    JRadioButton rdoDifficultDefPart;
    Image imageChrono;
    public NewGameFrame(Interface ui){
        super(ui);

        loadAssets();
    }

    @Override
    public void adaptWindow(){
        JFrame window = ui.getWindow();
        window.setMinimumSize(new Dimension(400, 600));

        Dimension sizeScreen = Toolkit.getDefaultToolkit().getScreenSize();
        int height = (int) (sizeScreen.height * 0.6);
        int width = (int) (sizeScreen.width * 0.4);
        int x = (int) (sizeScreen.width/2 - width/2);
        int y = (int) (sizeScreen.height/2 - height/2);

        window.setSize(width, height);
        window.setLocation(x, y);

        ui.getWindow().setJMenuBar(null);
    }

    @Override
    public void build(){

        JFrame window = ui.getWindow();

        Font fontArial25 = new Font("Arial", Font.BOLD, 25);
        Font fontArial20 = new Font("Arial", Font.BOLD, 20);
        Font fontDialog15 = new Font(Font.DIALOG, Font.BOLD, 15);

        this.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        JPanel mainPanel = new JPanel();
        c.fill = GridBagConstraints.BOTH;
        this.add(mainPanel, c);

        c.fill = GridBagConstraints.CENTER;

        GridBagLayout gLayout = new GridBagLayout();
        mainPanel.setLayout(gLayout);

        JLabel labelNewGame = new JLabel("Nouvelle partie");
        labelNewGame.setFont(fontArial25);
        c.gridx = 1;
        c.gridy = 0;
        c.insets = new Insets(0, 0, 30, 0);
        gLayout.setConstraints(labelNewGame, c);
        mainPanel.add(labelNewGame);

        c.insets = new Insets(0, 0, 0, 0);

        centralPanel = new JPanel();
        GridBagLayout gLayoutCP = new GridBagLayout();
        centralPanel.setLayout(gLayoutCP);

        c.gridx = 1;
        c.gridy = 1;
        c.weightx = 0.4;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.CENTER;
        gLayout.setConstraints(centralPanel, c);
        mainPanel.add(centralPanel);

        JPanel spaceLeft = createSpacePanel(mainPanel, 0, 1);
        JPanel spaceRight = createSpacePanel(mainPanel, 2, 1);

        c.weightx = 1;
        c.fill = GridBagConstraints.NONE;
        c.insets = new Insets(0, 0, 30, 0);

        JLabel labelDefPart = new JLabel("Défenseur");
        labelDefPart.setFont(fontArial20);
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 0.5;
        gLayoutCP.setConstraints(labelDefPart, c);
        centralPanel.add(labelDefPart);

        JLabel labelAttPart = new JLabel("Attaquant");
        labelAttPart.setFont(fontArial20);
        c.gridx = 1;
        c.gridy = 0;
        c.weightx = 0.5;
        gLayoutCP.setConstraints(labelAttPart, c);
        centralPanel.add(labelAttPart);

        c.insets = new Insets(0, 0, 0, 0);

        JLabel labelNames = new JLabel("Nom des joueurs");
        labelNames.setFont(fontDialog15);
        c.gridx = 0;
        c.gridy = 3;
        c.gridwidth = 2;
        c.insets = new Insets(0, 0, 20, 0);
        gLayoutCP.setConstraints(labelNames, c);
        centralPanel.add(labelNames);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridwidth = 1;
        c.insets = new Insets(0, 30, 0, 30);

        tfNameDef = new JTextField(20);
        tfNameDef.setPreferredSize(new Dimension(115, 20));
        c.gridx = 0;
        c.gridy = 4;
        gLayoutCP.setConstraints(tfNameDef, c);
        centralPanel.add(tfNameDef);

        tfNameAtt = new JTextField(20);
        tfNameAtt.setPreferredSize(new Dimension(115, 20));
        c.gridx = 1;
        c.gridy = 4;
        gLayoutCP.setConstraints(tfNameAtt, c);
        centralPanel.add(tfNameAtt);

        c.fill = GridBagConstraints.CENTER;

        JLabel labelDifficultyIA = new JLabel("Paramètres IAs");
        labelDifficultyIA.setFont(fontDialog15);
        c.gridx = 0;
        c.gridy = 5;
        c.gridwidth = 2;
        c.insets = new Insets(40, 0, 20, 0);
        gLayoutCP.setConstraints(labelDifficultyIA, c);
        centralPanel.add(labelDifficultyIA);

        c.gridwidth = 1;
        c.insets = new Insets(0, 0, 0, 0);

        buildIaParamsPart();

        JLabel labelBlitzTitle = new JLabel("Paramètres blitz");
        labelBlitzTitle.setFont(fontDialog15);
        c.gridx = 0;
        c.gridy = 7;
        c.gridwidth = 2;
        c.insets = new Insets(40, 0, 20, 0);
        gLayoutCP.setConstraints(labelBlitzTitle, c);
        centralPanel.add(labelBlitzTitle);

        buildBlitzParamsPart();

        bttnStart = createBttnStart(mainPanel);

        returnImageButton = new JLabel(returnImage);
        returnImageButton.setPreferredSize(new Dimension(32, 32));
        c.gridx = 1;
        c.gridy = 2;
        c.anchor = GridBagConstraints.LAST_LINE_START;
        returnImageButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        gLayout.setConstraints(returnImageButton, c);
        mainPanel.add(returnImageButton);

        setEventHandlers();
    }

    public void buildBlitzParamsPart(){
        GridBagConstraints c = new GridBagConstraints();
        JPanel blitzPanel = new JPanel();
        c.gridx = 0;
        c.gridy = 8;
        c.gridwidth = 2;
        blitzPanel.setLayout(new GridBagLayout());
        centralPanel.add(blitzPanel, c);

        c.gridy = 0;
        c.gridwidth = 1;

        JLabel labelChronoImage = new JLabel(new ImageIcon(imageChrono));
        labelChronoImage.setPreferredSize(new Dimension(32, 32));
        c.gridx = 0;
        blitzPanel.add(labelChronoImage, c);

        JTextField tfTime = new JTextField();
        tfTime.setPreferredSize(new Dimension(65, 20));
        c.gridx = 1;
        c.insets = new Insets(0, 10, 0, 15);
        blitzPanel.add(tfTime, c);

        c.insets = new Insets(0, 0, 0, 0);

        JLabel labelInSeconds = new JLabel("(En secondes)");
        c.gridx = 2;
        blitzPanel.add(labelInSeconds, c);

    }

    public void buildIaParamsPart(){
        JPanel bttnDefIAPanel = createRadioBttnPanel(centralPanel, 0, 6);

        ButtonGroup bttnGrpDefIA = new ButtonGroup();
        rdoHumanDefPart = createRadioButton(bttnDefIAPanel, bttnGrpDefIA, "Humain", 0, 0);
        rdoEasyDefPart = createRadioButton(bttnDefIAPanel, bttnGrpDefIA, "Facile", 0, 1);
        rdoMediumDefPart = createRadioButton(bttnDefIAPanel, bttnGrpDefIA, "Moyen", 0, 2);
        rdoDifficultDefPart = createRadioButton(bttnDefIAPanel, bttnGrpDefIA, "Difficile", 0, 3);

        JPanel bttnAttIAPanel = createRadioBttnPanel(centralPanel, 1, 6);

        ButtonGroup bttnGrpAttIA = new ButtonGroup();
        rdoHumanAttPart = createRadioButton(bttnAttIAPanel, bttnGrpAttIA, "Humain", 0, 0);
        rdoEasyAttPart = createRadioButton(bttnAttIAPanel, bttnGrpAttIA, "Facile", 0, 1);
        rdoMediumAttPart = createRadioButton(bttnAttIAPanel, bttnGrpAttIA, "Moyen", 0, 2);
        rdoDifficultAttPart = createRadioButton(bttnAttIAPanel, bttnGrpAttIA, "Difficile", 0, 3);

        rdoHumanDefPart.setSelected(true);
        rdoHumanAttPart.setSelected(true);
    }
    
    public JPanel createRadioBttnPanel(JPanel parent, int posX, int posY){
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = posX;
        c.gridy = posY;

        parent.add(panel, c);
        return panel;
    }

    public JPanel createSpacePanel(JPanel parent, int posX, int posY){
        JPanel spacePanel = new JPanel();
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = posX;
        c.gridy = posY;
        c.weightx = 0.3;
        c.fill = GridBagConstraints.BOTH;
        parent.add(spacePanel, c);
        return spacePanel;
    }

    public JRadioButton createRadioButton(JPanel parent, ButtonGroup group, String text, int posX, int posY){
        JRadioButton bttn = new JRadioButton(text);
        group.add(bttn);

        GridBagConstraints c = new GridBagConstraints();
        c.gridx = posX;
        c.gridy = posY;
        c.anchor = GridBagConstraints.BELOW_BASELINE_LEADING;
        parent.add(bttn, c);

        return bttn;
    }

    public JButton createBttnStart(JPanel parent){
        JButton bttn = new JButton("Lancer la partie");
        bttn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 1;
        c.gridy = 2;
        //c.anchor = GridBagConstraints.LAST_LINE_END;
        c.insets = new Insets(50, 0, 0, 0);

        parent.add(bttn, c);
        return bttn;
    }

    public void setEventHandlers() {

        bttnStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AIDifficulty defDiff = AIDifficulty.HUMAN;
                if(rdoEasyDefPart.isSelected()){
                    defDiff = AIDifficulty.RANDOM;
                }
                else if(rdoMediumDefPart.isSelected()){
                    defDiff = AIDifficulty.MID;
                }
                AIDifficulty attDiff = AIDifficulty.HUMAN;
                if(rdoEasyAttPart.isSelected()){
                    attDiff = AIDifficulty.RANDOM;
                }
                else if(rdoMediumAttPart.isSelected()){
                    attDiff = AIDifficulty.MID;
                }
                ui.createGameFrame(new Game(tfNameDef.getText(), tfNameAtt.getText(), defDiff, attDiff));
                ui.changePage(InterfacePage.GAME);
            }
        });

        returnImageButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                ui.changePage(InterfacePage.MENU);
            }
        });
    }

    public void loadAssets(){
        try{
            imageChrono = ImageIO.read(new File("assets/images/chrono.png"));
            imageChrono = imageChrono.getScaledInstance(32, 32, Image.SCALE_DEFAULT);
            returnImage = new ImageIcon(ImageIO.read(new File("assets/images/arrow2.png")));
        } catch(IOException exp){
            exp.printStackTrace();
        }
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
    }

}
