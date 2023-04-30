import javax.imageio.ImageIO;
import javax.swing.*;
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
            returnImage = new ImageIcon(ImageIO.read(new File("assets/arrow2.png")));
            //selectorImage= GraphicUtils.resizeImage(selectorImage, 16, 16);
        } catch(IOException exp){
            exp.printStackTrace();
        }
    }

    @Override
    public void adaptWindow(){
        
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

        tfNamePlayer1 = new JTextField();
        c.gridx = 0;
        c.gridy = 4;
        gLayoutCP.setConstraints(tfNamePlayer1, c);
        centralPart.add(tfNamePlayer1);

        tfNamePlayer2 = new JTextField();
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
        gLayout.setConstraints(bttnStart, c);
        this.add(bttnStart);

        returnImageButton = new JLabel(returnImage);
        returnImageButton.setPreferredSize(new Dimension(32, 32));
        //returnImageButton.setBackground(Color.CYAN);
        c.gridx = 1;
        c.gridy = 2;
        c.anchor = GridBagConstraints.LAST_LINE_START;
        gLayout.setConstraints(returnImageButton, c);
        this.add(returnImageButton);

        setHandlers();
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

    public void setHandlers() {
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
                ui.getGameFrame().setGameInstance(new Game());
                ui.changePage(InterfacePage.GAME);
            }
        });

        returnImageButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("Yeah boy");
                ui.changePage(InterfacePage.MENU);
            }
        });
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
    }

    //Update size and margin of components
    @Override
    public void updateMargins(){

    }
}
