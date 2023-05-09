package View;

import AI.AIDifficulty;
import Model.Game;

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

    JCheckBox cbAttPartIA;
    JCheckBox cbDefPartIA;
    JTextField tfNamePlayer1;
    JTextField tfNamePlayer2;
    JButton bttnStart;
    JPanel centralPart;
    ImageIcon returnImage;
    JLabel returnImageButton;
    JRadioButton rdoEasyAttPart;
    JRadioButton rdoMediumAttPart;
    JRadioButton rdoDifficultAttPart;
    JRadioButton rdoEasyDefPart;
    JRadioButton rdoMediumDefPart;
    JRadioButton rdoDifficultDefPart;
    public NewGameFrame(Interface ui){
        super(ui);

        try{
            returnImage = new ImageIcon(ImageIO.read(new File("assets/images/arrow2.png")));
            //selectorImage= Global.GraphicUtils.resizeImage(selectorImage, 16, 16);
        } catch(IOException exp){
            exp.printStackTrace();
        }
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

        System.out.println(javax.swing.UIManager.getDefaults().getFont("Label.font"));

        Font fontArial25 = new Font("Arial", Font.BOLD, 25);
        Font fontArial20 = new Font("Arial", Font.BOLD, 20);
        Font fontDialog15 = new Font(Font.DIALOG, Font.BOLD, 15);

        GridBagLayout gLayout = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();
        this.setLayout(gLayout);

        JLabel labelNewGame = new JLabel("Nouvelle partie");
        labelNewGame.setFont(fontArial25);
        c.gridx = 1;
        c.gridy = 0;
        c.insets = new Insets(0, 0, 30, 0);
        gLayout.setConstraints(labelNewGame, c);
        this.add(labelNewGame);

        c.insets = new Insets(0, 0, 0, 0);

        centralPart = new JPanel();
        GridBagLayout gLayoutCP = new GridBagLayout();
        centralPart.setLayout(gLayoutCP);

        c.gridx = 1;
        c.gridy = 1;
        c.weightx = 0.4;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.CENTER;
        gLayout.setConstraints(centralPart, c);
        this.add(centralPart);

        c.weightx = 0.3;

        JPanel spaceLeft = new JPanel();
        c.gridx = 0;
        c.gridy = 1;
        c.fill = GridBagConstraints.BOTH;
        gLayout.setConstraints(spaceLeft, c);
        this.add(spaceLeft);

        JPanel spaceRight = new JPanel();
        c.gridx = 2;
        c.gridy = 1;
        c.fill = GridBagConstraints.BOTH;
        gLayout.setConstraints(spaceRight, c);
        this.add(spaceRight);

        c.weightx = 1;
        c.fill = GridBagConstraints.NONE;
        c.insets = new Insets(0, 0, 30, 0);

        JLabel labelAttPart = new JLabel("Attaquant");
        labelAttPart.setFont(fontArial20);
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 0.5;
        gLayoutCP.setConstraints(labelAttPart, c);
        centralPart.add(labelAttPart);

        JLabel labelDefPart = new JLabel("Défenseur");
        labelDefPart.setFont(fontArial20);
        c.gridx = 1;
        c.gridy = 0;
        c.weightx = 0.5;
        //c.gridwidth = GridBagConstraints.REMAINDER;
        gLayoutCP.setConstraints(labelDefPart, c);
        centralPart.add(labelDefPart);

        c.insets = new Insets(0, 0, 0, 0);

        cbAttPartIA = new JCheckBox("Activate IA");
        c.gridx = 0;
        c.gridy = 1;
        gLayoutCP.setConstraints(cbAttPartIA, c);
        centralPart.add(cbAttPartIA);

        cbDefPartIA = new JCheckBox("Activate IA");
        c.gridx = 1;
        c.gridy = 1;
        gLayoutCP.setConstraints(cbDefPartIA, c);
        centralPart.add(cbDefPartIA);

        JLabel labelNames = new JLabel("Nom des joueurs");
        labelNames.setFont(fontDialog15);
        c.gridx = 0;
        c.gridy = 3;
        c.gridwidth = 2;
        c.insets = new Insets(50, 0, 20, 0);
        gLayoutCP.setConstraints(labelNames, c);
        centralPart.add(labelNames);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridwidth = 1;
        c.insets = new Insets(0, 30, 0, 30);

        tfNamePlayer1 = new JTextField(20);
        tfNamePlayer1.setPreferredSize(new Dimension(115, 20));
        c.gridx = 0;
        c.gridy = 4;
        gLayoutCP.setConstraints(tfNamePlayer1, c);
        centralPart.add(tfNamePlayer1);

        tfNamePlayer2 = new JTextField(20);
        tfNamePlayer2.setPreferredSize(new Dimension(115, 20));
        c.gridx = 1;
        c.gridy = 4;
        gLayoutCP.setConstraints(tfNamePlayer2, c);
        centralPart.add(tfNamePlayer2);

        c.fill = GridBagConstraints.CENTER;

        JLabel labelDifficultyIA = new JLabel("Paramètres IAs");
        labelDifficultyIA.setFont(fontDialog15);
        c.gridx = 0;
        c.gridy = 5;
        c.gridwidth = 2;
        c.insets = new Insets(60, 0, 20, 0);
        gLayoutCP.setConstraints(labelDifficultyIA, c);
        centralPart.add(labelDifficultyIA);

        c.gridwidth = 1;
        c.insets = new Insets(0, 0, 0, 0);

        ButtonGroup bttnGrpAttIA = new ButtonGroup();

        rdoEasyAttPart = new JRadioButton("Facile");
        bttnGrpAttIA.add(rdoEasyAttPart);
        c.gridx = 0;
        c.gridy = 6;
        gLayoutCP.setConstraints(rdoEasyAttPart, c);
        centralPart.add(rdoEasyAttPart);

        rdoMediumAttPart = new JRadioButton("Moyen");
        bttnGrpAttIA.add(rdoMediumAttPart);
        c.gridx = 0;
        c.gridy = 7;
        gLayoutCP.setConstraints(rdoMediumAttPart, c);
        centralPart.add(rdoMediumAttPart);

        rdoDifficultAttPart = new JRadioButton("Difficile");
        bttnGrpAttIA.add(rdoDifficultAttPart);
        c.gridx = 0;
        c.gridy = 8;
        gLayoutCP.setConstraints(rdoDifficultAttPart, c);
        centralPart.add(rdoDifficultAttPart);


        ButtonGroup bttnGrpDefIA = new ButtonGroup();

        rdoEasyDefPart = new JRadioButton("Facile");
        bttnGrpDefIA.add(rdoEasyDefPart);
        c.gridx = 1;
        c.gridy = 6;
        gLayoutCP.setConstraints(rdoEasyDefPart, c);
        centralPart.add(rdoEasyDefPart);

        rdoMediumDefPart = new JRadioButton("Moyen");
        bttnGrpDefIA.add(rdoMediumDefPart);
        c.gridx = 1;
        c.gridy = 7;
        gLayoutCP.setConstraints(rdoMediumDefPart, c);
        centralPart.add(rdoMediumDefPart);

        rdoDifficultDefPart = new JRadioButton("Difficile");
        bttnGrpDefIA.add(rdoDifficultDefPart);
        c.gridx = 1;
        c.gridy = 8;
        gLayoutCP.setConstraints(rdoDifficultDefPart, c);
        centralPart.add(rdoDifficultDefPart);

        setAttDifficultyButtonsEnabled(false);
        setDefDifficultyButtonsEnabled(false);

        bttnStart = new JButton("Lancer la partie");
        c.gridx = 1;
        c.gridy = 2;
        //c.anchor = GridBagConstraints.LAST_LINE_END;
        c.insets = new Insets(50, 0, 0, 0);
        bttnStart.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        gLayout.setConstraints(bttnStart, c);
        this.add(bttnStart);

        returnImageButton = new JLabel(returnImage);
        returnImageButton.setPreferredSize(new Dimension(32, 32));
        c.gridx = 1;
        c.gridy = 2;
        c.anchor = GridBagConstraints.LAST_LINE_START;
        returnImageButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        gLayout.setConstraints(returnImageButton, c);
        this.add(returnImageButton);

        setEventHandlers();
    }

    public void setAttDifficultyButtonsEnabled(boolean b){
        rdoEasyAttPart.setEnabled(b);
        rdoMediumAttPart.setEnabled(b);
        rdoDifficultAttPart.setEnabled(b);
    }

    public void setDefDifficultyButtonsEnabled(boolean b){
        rdoEasyDefPart.setEnabled(b);
        rdoMediumDefPart.setEnabled(b);
        rdoDifficultDefPart.setEnabled(b);
    }

    public void setEventHandlers() {
        cbAttPartIA.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tfNamePlayer1.setEnabled(!cbAttPartIA.isSelected());
                setAttDifficultyButtonsEnabled(cbAttPartIA.isSelected());
            }
        });

        cbDefPartIA.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tfNamePlayer2.setEnabled(!cbDefPartIA.isSelected());
                setDefDifficultyButtonsEnabled(cbDefPartIA.isSelected());
            }
        });

        bttnStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AIDifficulty defDiff = AIDifficulty.HUMAN;
                if(rdoEasyDefPart.isSelected()){
                    defDiff = AIDifficulty.RANDOM;
                }
                AIDifficulty attDiff = AIDifficulty.HUMAN;
                if(rdoEasyAttPart.isSelected()){
                    attDiff = AIDifficulty.RANDOM;
                }
                ui.createGameFrame(new Game(tfNamePlayer1.getText(), tfNamePlayer2.getText(), defDiff, attDiff));
                ui.changePage(InterfacePage.GAME);
            }
        });

        returnImageButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                ui.changePage(InterfacePage.MENU);
            }
        });

        DocumentListener docListenerForTf = new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                textFieldEventHandler(e);
            }
            @Override
            public void removeUpdate(DocumentEvent e) {
                textFieldEventHandler(e);
            }
            @Override
            public void changedUpdate(DocumentEvent e) {
                textFieldEventHandler(e);
            }
        };

        tfNamePlayer1.getDocument().addDocumentListener(docListenerForTf);
        tfNamePlayer2.getDocument().addDocumentListener(docListenerForTf);
    }

    public void textFieldEventHandler(DocumentEvent e){
        //tfNamePlayer1.requestFocusInWindow();
        tfNamePlayer1.setCaretPosition(tfNamePlayer1.getDocument().getLength());
        tfNamePlayer2.setCaretPosition(tfNamePlayer2.getDocument().getLength());
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
    }

}
