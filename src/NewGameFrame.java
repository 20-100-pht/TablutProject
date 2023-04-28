import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;

public class NewGameFrame extends Frame {

    JCheckBox cbAttPartIA;
    JCheckBox cbDefPartIA;
    JTextField tfNamePlayer1;
    JTextField tfNamePlayer2;
    JButton bttnStart;
    JPanel centralPart;
    public NewGameFrame(Interface ui){
        super(ui);
    }

    @Override
    public void adaptWindow(){
        
    }

    @Override
    public void build(){

        JFrame window = ui.getWindow();

        Font fontArial25 = new Font("Arial", Font.BOLD, 25);
        Font fontArial20 = new Font("Arial", Font.BOLD, 20);

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
        c.gridx = 0;
        c.gridy = 5;
        c.gridwidth = 2;
        c.insets = new Insets(60, 0, 20, 0);
        gLayoutCP.setConstraints(labelDifficultyIA, c);
        centralPart.add(labelDifficultyIA);

        c.gridwidth = 1;
        c.insets = new Insets(0, 0, 0, 0);

        ButtonGroup bttnGrpAttIA = new ButtonGroup();

        JRadioButton rdoEasyAttPart = new JRadioButton("Facile");
        bttnGrpAttIA.add(rdoEasyAttPart);
        c.gridx = 0;
        c.gridy = 6;
        gLayoutCP.setConstraints(rdoEasyAttPart, c);
        centralPart.add(rdoEasyAttPart);

        JRadioButton rdoMediumAttPart = new JRadioButton("Moyen");
        bttnGrpAttIA.add(rdoMediumAttPart);
        c.gridx = 0;
        c.gridy = 7;
        gLayoutCP.setConstraints(rdoMediumAttPart, c);
        centralPart.add(rdoMediumAttPart);

        JRadioButton rdoDifficultAttPart = new JRadioButton("Difficile");
        bttnGrpAttIA.add(rdoDifficultAttPart);
        c.gridx = 0;
        c.gridy = 8;
        gLayoutCP.setConstraints(rdoDifficultAttPart, c);
        centralPart.add(rdoDifficultAttPart);


        ButtonGroup bttnGrpDefIA = new ButtonGroup();

        JRadioButton rdoEasyDefPart = new JRadioButton("Facile");
        bttnGrpDefIA.add(rdoEasyDefPart);
        c.gridx = 1;
        c.gridy = 6;
        gLayoutCP.setConstraints(rdoEasyDefPart, c);
        centralPart.add(rdoEasyDefPart);

        JRadioButton rdoMediumDefPart = new JRadioButton("Moyen");
        bttnGrpDefIA.add(rdoMediumDefPart);
        c.gridx = 1;
        c.gridy = 7;
        gLayoutCP.setConstraints(rdoMediumDefPart, c);
        centralPart.add(rdoMediumDefPart);

        JRadioButton rdoDifficultDefPart = new JRadioButton("Difficile");
        bttnGrpDefIA.add(rdoDifficultDefPart);
        c.gridx = 1;
        c.gridy = 8;
        gLayoutCP.setConstraints(rdoDifficultDefPart, c);
        centralPart.add(rdoDifficultDefPart);

        bttnStart = new JButton("Lancer la partie");
        c.gridx = 1;
        c.gridy = 2;
        //c.anchor = GridBagConstraints.LAST_LINE_END;
        c.insets = new Insets(50, 0, 0, 0);
        gLayout.setConstraints(bttnStart, c);
        this.add(bttnStart);

        setHandlers();
    }

    public void setHandlers(){
        cbAttPartIA.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(cbAttPartIA.isSelected());
                tfNamePlayer1.setEnabled(!cbAttPartIA.isSelected());
            }
        });

        cbDefPartIA.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tfNamePlayer2.setEnabled(!cbDefPartIA.isSelected());
            }
        });

        bttnStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ui.getGameFrame().setGameInstance(new Game());
                ui.changePage(InterfacePage.GAME);
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
