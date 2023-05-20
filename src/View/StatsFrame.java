package View;

import Model.Grid;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

public class StatsFrame extends Frame {

    Interface ui;
    Button bttnBackToMenu;
    ImageIcon imageLeftArrow;
    ImageIcon imageRightArrow;
    JLabel labelLeftArrow;
    JLabel labelRightArrow;
    JLabel labelPlayerName;

    public StatsFrame(Interface ui){
        super(ui);
        this.ui = ui;

        loadAssets();
    }

    private void loadAssets() {
        try{
            imageLeftArrow = new ImageIcon(ImageIO.read(new File("assets/images/arrow4_left.png")));
            imageRightArrow = new ImageIcon(ImageIO.read(new File("assets/images/arrow4_right.png")));
        } catch(IOException exp){
            exp.printStackTrace();
        }
    }

    public void build(){

        Font fontDialog25 = new Font(Font.DIALOG, Font.BOLD, 30);
        Font fontDialog20 = new Font(Font.DIALOG, Font.BOLD, 20);

        JFrame window = ui.getWindow();

        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        setOpaque(false);

        c.gridheight = 3;
        c.weightx = 0.3;
        c.fill = GridBagConstraints.BOTH;

        JPanel leftSpaceP = new JPanel();
        leftSpaceP.setOpaque(false);
        c.gridx = 0;
        c.gridy = 0;
        add(leftSpaceP, c);

        JPanel rightSpaceP = new JPanel();
        rightSpaceP.setOpaque(false);
        c.gridx = 2;
        c.gridy = 0;
        add(rightSpaceP, c);

        c.gridheight = 1;
        c.weightx = 0;

        RoundPanel centerPanel = new RoundPanel(35);
        centerPanel.setLayout(new GridBagLayout());
        centerPanel.setOpaque(false);
        centerPanel.setBackground(Color.red);
        c.gridx = 1;
        c.gridy = 0;
        c.weightx = 0.4;
        c.fill = GridBagConstraints.BOTH;
        add(centerPanel, c);

        c.fill = GridBagConstraints.NONE;

        JLabel labelTitle = new JLabel("Statistiques");
        labelTitle.setFont(fontDialog25);
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 2;
        c.insets = new Insets(30, 0, 25, 0);
        centerPanel.add(labelTitle, c);

        c.gridwidth = 1;

        //Values panel

        JPanel val1Panel = new JPanel();
        val1Panel.setLayout(new GridBagLayout());
        val1Panel.setOpaque(false);
        c.gridx = 0;
        c.gridy = 1;
        c.anchor = GridBagConstraints.BASELINE_TRAILING;
        c.insets = new Insets(0, 0, 0, 20);
        centerPanel.add(val1Panel, c);

        c.insets = new Insets(0, 0, 0, 0);

        c.anchor = GridBagConstraints.BASELINE_LEADING;

        JLabel labelTWin = new JLabel("Nombre de partie gagnées :");
        labelTWin.setFont(fontDialog20);
        c.gridy = 0;
        val1Panel.add(labelTWin, c);

        JLabel labelTLoose = new JLabel("Nombre de partie perdues :");
        labelTLoose.setFont(fontDialog20);
        c.gridy = 1;
        val1Panel.add(labelTLoose, c);

        JLabel labelTTurn = new JLabel("Nombre de tour moyen :");
        labelTTurn.setFont(fontDialog20);
        c.gridy = 2;
        val1Panel.add(labelTTurn, c);

        JPanel val2Panel = new JPanel();
        val2Panel.setLayout(new GridBagLayout());
        val2Panel.setOpaque(false);
        c.gridx = 1;
        c.gridy = 1;
        c.anchor = GridBagConstraints.BASELINE_LEADING;
        centerPanel.add(val2Panel, c);

        JLabel labelVWin = new JLabel("0");
        labelVWin.setFont(fontDialog20);
        c.gridy = 0;
        val2Panel.add(labelVWin, c);

        JLabel labelVLoose = new JLabel("0");
        labelVLoose.setFont(fontDialog20);
        c.gridy = 1;
        val2Panel.add(labelVLoose, c);

        JLabel labelVTurn = new JLabel("0");
        labelVTurn.setFont(fontDialog20);
        c.gridy = 2;
        val2Panel.add(labelVTurn, c);

        c.anchor = GridBagConstraints.CENTER;

        //Nav Panel

        JPanel navPanel = new JPanel();
        navPanel.setPreferredSize(new Dimension(400, 64));
        navPanel.setLayout(new GridBagLayout());
        navPanel.setOpaque(false);
        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 2;
        c.weightx = 0.5;
        c.insets = new Insets(20, 0, 20, 0);
        centerPanel.add(navPanel, c);

        c.gridwidth = 1;
        c.weightx = 0;
        c.insets = new Insets(0, 0, 0, 0);

        labelLeftArrow = new JLabel(imageLeftArrow);
        c.gridx = 0;
        c.gridy = 0;
        c.anchor = GridBagConstraints.BASELINE_LEADING;
        c.weightx = 0.2;
        navPanel.add(labelLeftArrow, c);

        labelPlayerName = new JLabel("Aucun joueur");
        labelPlayerName.setFont(fontDialog20);
        c.gridx = 1;
        c.gridy = 0;
        c.weightx = 0.6;
        c.anchor = GridBagConstraints.CENTER;
        navPanel.add(labelPlayerName, c);

        labelRightArrow = new JLabel(imageRightArrow);
        c.gridx = 2;
        c.gridy = 0;
        c.weightx = 0.2;
        c.anchor = GridBagConstraints.BASELINE_TRAILING;
        navPanel.add(labelRightArrow, c);

        c.anchor = GridBagConstraints.CENTER;
        c.weightx = 0;

        bttnBackToMenu = new Button("Retour au menu", false, this);
        bttnBackToMenu.setRoundValue(200);
        bttnBackToMenu.setPreferredSize(new Dimension(400, 45));
        c.gridx = 1;
        c.gridy = 1;
        c.insets = new Insets(30, 0, 0, 0);
        add(bttnBackToMenu, c);

        setEventHandlers();
    }

    public void adaptWindow(){

    }

    public void setEventHandlers(){
        bttnBackToMenu.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                ui.changePage(InterfacePage.MENU);
            }
        });
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.setColor(new Color(155, 89, 182));
        g.fillRect(0, 0, ui.getWindow().getWidth(), ui.getWindow().getHeight());
    }
}
