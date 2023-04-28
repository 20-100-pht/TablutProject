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
    public NewGameFrame(Interface ui){
        super(ui);
    }

    @Override
    public void adaptWindow(){
        
    }

    @Override
    public void build(){

        JFrame window = ui.getWindow();

        GridBagLayout gLayout = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();

        JLabel labelAttPart = new JLabel("Attaquant");
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 0.5;
        gLayout.setConstraints(labelAttPart, c);
        this.add(labelAttPart);

        JLabel labelDefPart = new JLabel("Défenseur");
        c.gridx = 1;
        c.gridy = 0;
        c.weightx = 0.5;
        //c.gridwidth = GridBagConstraints.REMAINDER;
        gLayout.setConstraints(labelDefPart, c);
        this.add(labelDefPart);

        cbAttPartIA = new JCheckBox("Activate IA");
        c.gridx = 0;
        c.gridy = 1;
        gLayout.setConstraints(cbAttPartIA, c);
        this.add(cbAttPartIA);

        cbDefPartIA = new JCheckBox("Activate IA");
        c.gridx = 1;
        c.gridy = 1;
        gLayout.setConstraints(cbDefPartIA, c);
        this.add(cbDefPartIA);

        JLabel labelNames = new JLabel("Nom des joueurs");
        c.gridx = 0;
        c.gridy = 3;
        c.gridwidth = 2;
        c.insets = new Insets(50, 0, 20, 0);
        gLayout.setConstraints(labelNames, c);
        this.add(labelNames);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridwidth = 1;
        c.insets = new Insets(0, 0, 0, 0);

        tfNamePlayer1 = new JTextField();
        c.gridx = 0;
        c.gridy = 4;
        gLayout.setConstraints(tfNamePlayer1, c);
        this.add(tfNamePlayer1);

        tfNamePlayer2 = new JTextField();
        c.gridx = 1;
        c.gridy = 4;
        gLayout.setConstraints(tfNamePlayer2, c);
        this.add(tfNamePlayer2);

        c.fill = GridBagConstraints.CENTER;

        JLabel labelDifficultyIA = new JLabel("Paramètres IAs");
        c.gridx = 0;
        c.gridy = 5;
        c.gridwidth = 2;
        c.insets = new Insets(60, 0, 20, 0);
        gLayout.setConstraints(labelDifficultyIA, c);
        this.add(labelDifficultyIA);

        c.gridwidth = 1;
        c.insets = new Insets(0, 0, 0, 0);

        ButtonGroup bttnGrpAttIA = new ButtonGroup();

        JRadioButton rdoEasyAttPart = new JRadioButton("Facile");
        bttnGrpAttIA.add(rdoEasyAttPart);
        c.gridx = 0;
        c.gridy = 6;
        gLayout.setConstraints(rdoEasyAttPart, c);
        this.add(rdoEasyAttPart);

        JRadioButton rdoMediumAttPart = new JRadioButton("Moyen");
        bttnGrpAttIA.add(rdoMediumAttPart);
        c.gridx = 0;
        c.gridy = 7;
        gLayout.setConstraints(rdoMediumAttPart, c);
        this.add(rdoMediumAttPart);

        JRadioButton rdoDifficultAttPart = new JRadioButton("Difficile");
        bttnGrpAttIA.add(rdoDifficultAttPart);
        c.gridx = 0;
        c.gridy = 8;
        gLayout.setConstraints(rdoDifficultAttPart, c);
        this.add(rdoDifficultAttPart);


        ButtonGroup bttnGrpDefIA = new ButtonGroup();

        JRadioButton rdoEasyDefPart = new JRadioButton("Facile");
        bttnGrpDefIA.add(rdoEasyDefPart);
        c.gridx = 1;
        c.gridy = 6;
        gLayout.setConstraints(rdoEasyDefPart, c);
        this.add(rdoEasyDefPart);

        JRadioButton rdoMediumDefPart = new JRadioButton("Moyen");
        bttnGrpDefIA.add(rdoMediumDefPart);
        c.gridx = 1;
        c.gridy = 7;
        gLayout.setConstraints(rdoMediumDefPart, c);
        this.add(rdoMediumDefPart);

        JRadioButton rdoDifficultDefPart = new JRadioButton("Difficile");
        bttnGrpDefIA.add(rdoDifficultDefPart);
        c.gridx = 1;
        c.gridy = 8;
        gLayout.setConstraints(rdoDifficultDefPart, c);
        this.add(rdoDifficultDefPart);

        bttnStart = new JButton("Lancer la partie");
        c.gridx = 1;
        c.gridy = 9;
        c.anchor = GridBagConstraints.LAST_LINE_END;
        c.insets = new Insets((int)(window.getHeight()*2), 0, 30, 40);
        gLayout.setConstraints(bttnStart, c);
        this.add(bttnStart);

        this.setLayout(gLayout);

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
    public void update(){

    }
}
