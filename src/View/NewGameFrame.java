package View;

import AI.AIDifficulty;
import Global.Utils;
import Model.Game;
import Model.Grid;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

public class NewGameFrame extends Frame {

    JTextField tfNameAtt;
    JTextField tfNameDef;
    Button bttnStart;
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
    Image imageNewGame;
    JPanel bttnDefIAPanel;
    JPanel bttnAttIAPanel;
    JTextField tfTime;
    ImageIcon imageInfo;

    public NewGameFrame(Interface ui){
        super(ui);

        loadAssets();
    }

    @Override
    public void adaptWindow(){
        JFrame window = ui.getWindow();
        window.setMinimumSize(new Dimension(650, 750));

        Dimension sizeScreen = Toolkit.getDefaultToolkit().getScreenSize();
        int height = (int) (sizeScreen.height * 0.75);
        int width = (int) (sizeScreen.width * 0.6);
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

        RoundPanel mainPanel = new RoundPanel(35);
        c.fill = GridBagConstraints.BOTH;
        c.insets = new Insets(0, 0, 0, 0);
        this.add(mainPanel, c);

        c.fill = GridBagConstraints.CENTER;

        GridBagLayout gLayout = new GridBagLayout();
        mainPanel.setLayout(gLayout);
        mainPanel.setOpaque(false);
        JLabel labelNewGame = new JLabel("Nouvelle partie");
        labelNewGame.setFont(fontArial25);
        labelNewGame.setForeground(Color.BLACK);
        c.gridx = 1;
        c.gridy = 0;
        c.insets = new Insets(20, 0, 30, 0);
        gLayout.setConstraints(labelNewGame, c);
        mainPanel.add(labelNewGame);

        c.insets = new Insets(0, 0, 0, 0);

        centralPanel = new JPanel();
        GridBagLayout gLayoutCP = new GridBagLayout();
        centralPanel.setLayout(gLayoutCP);
        centralPanel.setOpaque(false);

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
        labelDefPart.setForeground(Color.BLACK);
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 0.5;
        gLayoutCP.setConstraints(labelDefPart, c);
        centralPanel.add(labelDefPart);

        JLabel labelAttPart = new JLabel("Attaquant");
        labelAttPart.setFont(fontArial20);
        labelAttPart.setForeground(Color.BLACK);
        c.gridx = 1;
        c.gridy = 0;
        c.weightx = 0.5;
        gLayoutCP.setConstraints(labelAttPart, c);
        centralPanel.add(labelAttPart);

        c.insets = new Insets(0, 0, 0, 0);

        JLabel labelNames = new JLabel("Nom des joueurs");
        labelNames.setFont(fontDialog15);
        labelNames.setForeground(Color.BLACK);
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
        tfNameDef.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.BLACK),
                BorderFactory.createEmptyBorder(0, 3, 0, 0)));
        tfNameDef.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if (tfNameDef.getText().length() >= 20)
                    e.consume();
            }
        });
        tfNameDef.setPreferredSize(new Dimension(115, 20));
        c.gridx = 0;
        c.gridy = 4;
        gLayoutCP.setConstraints(tfNameDef, c);
        centralPanel.add(tfNameDef);

        tfNameAtt = new JTextField(20);
        tfNameAtt.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.BLACK),
                BorderFactory.createEmptyBorder(0, 3, 0, 0)));
        tfNameAtt.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if (tfNameAtt.getText().length() >= 20)
                    e.consume();
            }
        });
        tfNameAtt.setPreferredSize(new Dimension(115, 20));
        c.gridx = 1;
        c.gridy = 4;
        gLayoutCP.setConstraints(tfNameAtt, c);
        centralPanel.add(tfNameAtt);

        c.fill = GridBagConstraints.CENTER;

        JLabel labelDifficultyIA = new JLabel("Paramètres joueurs");
        labelDifficultyIA.setFont(fontDialog15);
        labelDifficultyIA.setForeground(Color.BLACK);
        c.gridx = 0;
        c.gridy = 5;
        c.gridwidth = 2;
        c.insets = new Insets(40, 0, 20, 0);
        gLayoutCP.setConstraints(labelDifficultyIA, c);
        centralPanel.add(labelDifficultyIA);

        c.gridwidth = 1;
        c.insets = new Insets(0, 0, 0, 0);

        buildIaParamsPart();

        JPanel panelBlitzTitle = new JPanel();
        panelBlitzTitle.setOpaque(false);
        panelBlitzTitle.setToolTipText("Les joueurs doivent jouer rapidement car lorsque l'un deux n'a plus de temps il a perdu !");
        c.gridx = 0;
        c.gridy = 7;
        c.gridwidth = 2;
        c.insets = new Insets(40, 0, 20, 0);
        gLayoutCP.setConstraints(panelBlitzTitle, c);
        centralPanel.add(panelBlitzTitle);

        JLabel labelBlitzTitle = new JLabel("Mode blitz");
        labelBlitzTitle.setFont(fontDialog15);
        labelBlitzTitle.setForeground(Color.BLACK);
        panelBlitzTitle.add(labelBlitzTitle);

        JLabel labelBlitzInfo = new JLabel(imageInfo);
        panelBlitzTitle.add(labelBlitzInfo);

        //c.gridwidth = 2;

        buildBlitzParamsPart();

        bttnStart = createBttnStart(mainPanel);

        returnImageButton = new JLabel(returnImage);
        returnImageButton.setPreferredSize(new Dimension(32, 32));
        c.gridx = 1;
        c.gridy = 2;
        c.anchor = GridBagConstraints.LINE_START;
        gLayout.setConstraints(returnImageButton, c);
        mainPanel.add(returnImageButton);

        setEventHandlers();
    }

    public void buildBlitzParamsPart(){
        GridBagConstraints c = new GridBagConstraints();
        JPanel blitzPanel = new JPanel();
        blitzPanel.setOpaque((false));
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

        tfTime = new JTextField();
        tfTime.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.BLACK),
                BorderFactory.createEmptyBorder(0, 3, 0, 0)));
        tfTime.setPreferredSize(new Dimension(65, 20));
        c.gridx = 1;
        c.insets = new Insets(0, 15, 0, 10);
        blitzPanel.add(tfTime, c);

        c.insets = new Insets(0, 0, 0, 0);

        JLabel labelInSeconds = new JLabel("(En secondes)");
        labelInSeconds.setForeground(Color.BLACK);
        c.gridx = 2;
        blitzPanel.add(labelInSeconds, c);

    }

    public void buildIaParamsPart(){
        bttnDefIAPanel = createRadioBttnPanel(centralPanel, 0, 6);

        ButtonGroup bttnGrpDefIA = new ButtonGroup();
        rdoHumanDefPart = createRadioButton(bttnDefIAPanel, bttnGrpDefIA, "Humain", 0, 0);
        rdoHumanDefPart.setOpaque(false);
        rdoEasyDefPart = createRadioButton(bttnDefIAPanel, bttnGrpDefIA, "IA Facile", 0, 1);
        rdoEasyDefPart.setOpaque(false);
        rdoMediumDefPart = createRadioButton(bttnDefIAPanel, bttnGrpDefIA, "IA Moyen", 0, 2);
        rdoMediumDefPart.setOpaque(false);
        rdoDifficultDefPart = createRadioButton(bttnDefIAPanel, bttnGrpDefIA, "IA Difficile", 0, 3);
        rdoDifficultDefPart.setOpaque(false);

        bttnAttIAPanel = createRadioBttnPanel(centralPanel, 1, 6);

        ButtonGroup bttnGrpAttIA = new ButtonGroup();
        rdoHumanAttPart = createRadioButton(bttnAttIAPanel, bttnGrpAttIA, "Humain", 0, 0);
        rdoHumanAttPart.setOpaque(false);
        rdoEasyAttPart = createRadioButton(bttnAttIAPanel, bttnGrpAttIA, "IA Facile", 0, 1);
        rdoEasyAttPart.setOpaque(false);
        rdoMediumAttPart = createRadioButton(bttnAttIAPanel, bttnGrpAttIA, "IA Moyen", 0, 2);
        rdoMediumAttPart.setOpaque(false);
        rdoDifficultAttPart = createRadioButton(bttnAttIAPanel, bttnGrpAttIA, "IA Difficile", 0, 3);
        rdoDifficultAttPart.setOpaque(false);

        rdoHumanDefPart.setSelected(true);
        rdoHumanAttPart.setSelected(true);
    }
    
    public JPanel createRadioBttnPanel(JPanel parent, int posX, int posY){
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setOpaque(false);
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = posX;
        c.gridy = posY;

        parent.add(panel, c);
        return panel;
    }

    public JPanel createSpacePanel(JPanel parent, int posX, int posY){
        JPanel spacePanel = new JPanel();
        spacePanel.setOpaque(false);
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
        bttn.setForeground(Color.BLACK);
        group.add(bttn);

        GridBagConstraints c = new GridBagConstraints();
        c.gridx = posX;
        c.gridy = posY;
        c.anchor = GridBagConstraints.BELOW_BASELINE_LEADING;
        parent.add(bttn, c);

        return bttn;
    }

    public Button createBttnStart(JPanel parent){
        Button bttn = new Button("Lancer la partie", true, this);
        GridBagConstraints c = new GridBagConstraints();
        bttn.setPreferredSize(new Dimension(250, 55));
        bttn.setFont(new Font(Font.DIALOG, Font.BOLD, 23));
        bttn.setRoundValue(40);
        c.gridx = 1;
        c.gridy = 2;
        //c.anchor = GridBagConstraints.LAST_LINE_END;
        c.insets = new Insets(50, 0, 20, 0);

        parent.add(bttn, c);
        return bttn;
    }

    public void setEventHandlers() {

        bttnStart.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                AIDifficulty defDiff = AIDifficulty.HUMAN;
                if(rdoEasyDefPart.isSelected()){
                    defDiff = AIDifficulty.EASY;
                }
                else if(rdoMediumDefPart.isSelected()){
                    defDiff = AIDifficulty.MID;
                }
                else if(rdoDifficultDefPart.isSelected()){
                    defDiff = AIDifficulty.HARD;
                }
                AIDifficulty attDiff = AIDifficulty.HUMAN;
                if(rdoEasyAttPart.isSelected()){
                    attDiff = AIDifficulty.EASY;
                }
                else if(rdoMediumAttPart.isSelected()){
                    attDiff = AIDifficulty.MID;
                }else if(rdoDifficultAttPart.isSelected()){
                    attDiff = AIDifficulty.HARD;
                }

                int blitzTime;
                if(!Utils.isNumeric(tfTime.getText())) blitzTime = 0;
                else blitzTime = Integer.parseInt(tfTime.getText());

                ui.createGameFrame(new Game(tfNameDef.getText(), tfNameAtt.getText(), defDiff, attDiff, blitzTime));
                ui.changePage(InterfacePage.GAME);
            }
        });

        returnImageButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                ui.changePage(InterfacePage.MENU);
            }
        });

        ActionListener mA = new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                updateBlitzTextfield();
            }
        };

        rdoHumanAttPart.addActionListener(mA);
        rdoEasyAttPart.addActionListener(mA);
        rdoMediumAttPart.addActionListener(mA);
        rdoDifficultAttPart.addActionListener(mA);
        rdoHumanDefPart.addActionListener(mA);
        rdoEasyDefPart.addActionListener(mA);
        rdoMediumDefPart.addActionListener(mA);
        rdoDifficultDefPart.addActionListener(mA);
    }

    public void updateBlitzTextfield(){
        if(rdoEasyAttPart.isSelected() || rdoMediumAttPart.isSelected() || rdoDifficultAttPart.isSelected()) {
            tfNameAtt.setEnabled(false);
            tfNameAtt.setBackground(Color.LIGHT_GRAY);
            tfTime.setEnabled(false);
            tfTime.setBackground(Color.LIGHT_GRAY);
            if (tfNameAtt.getText().length() <= 20)
                tfNameAtt.setText("");
        } else {
            tfNameAtt.setEnabled(true);
            tfNameAtt.setBackground(Color.WHITE);
            tfTime.setEnabled(true);
            tfTime.setBackground(Color.WHITE);
        }
        if(rdoEasyDefPart.isSelected() || rdoMediumDefPart.isSelected() || rdoDifficultDefPart.isSelected()) {
            tfNameDef.setEnabled(false);
            tfNameDef.setBackground(Color.LIGHT_GRAY);
            tfTime.setEnabled(false);
            tfTime.setBackground(Color.LIGHT_GRAY);
            if (tfNameDef.getText().length() <= 20)
                tfNameDef.setText("");
        } else {
            if(rdoHumanDefPart.isSelected()) {
                tfNameDef.setBackground(Color.WHITE);
                tfNameDef.setEnabled(true);
            } else {
                tfNameDef.setBackground(Color.LIGHT_GRAY);
                tfTime.setEnabled(true);
            }
        }
    }
    public void loadAssets(){
        try{
            imageChrono = ImageIO.read(new File("assets/images/chrono.png"));
            imageChrono = imageChrono.getScaledInstance(32, 32, Image.SCALE_DEFAULT);
            imageNewGame = ImageIO.read(new File("assets/images/backgroundMenu.jpg"));
            returnImage = new ImageIcon(ImageIO.read(new File("assets/images/arrow2.png")));
            imageInfo = new ImageIcon(ImageIO.read(new File("assets/images/info.png")));
        } catch(IOException exp){
            exp.printStackTrace();
        }
    }
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawImage(imageNewGame, 0, 0, this.getWidth(), this.getHeight(), null);
    }

}
