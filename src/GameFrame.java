import javax.swing.border.Border;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryNotEmptyException;

public class GameFrame extends Frame {

    Game game;
    GridPanel gridPanel;
    ImageIcon imageRobot;
    ImageIcon imageArrowLeft;
    ImageIcon imageArrowRight;
    ImageIcon imageBook;
    JMenuBar menuBar;
    public GameFrame(Interface ui){
        super(ui);

        loadAssets();
    }

    public void build(){

        Border blacklineBorder = BorderFactory.createLineBorder(Color.BLACK);

        Font fontDialog20 = new Font(Font.DIALOG, Font.PLAIN, 20);

        GridBagLayout gLayout = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();
        this.setLayout(gLayout);

        this.setBackground(Color.red);
        this.setOpaque(true);

        // TUTO

        JLabel labelTuto = new JLabel(imageBook);
        c.gridx = 0;
        c.gridy = 0;
        c.anchor = GridBagConstraints.FIRST_LINE_START;
        c.insets = new Insets(10, 15, 0, 0);
        c.weighty = 0.15;
        gLayout.setConstraints(labelTuto, c);
        this.add(labelTuto);

        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets(0, 0, 0, 0);
        c.weighty = 0;

        //Infos player 1

        JPanel player1InfoPart = new JPanel(); //player1InfoPart.setBackground(Color.orange);
        c.gridx = 0;
        c.gridy = 1;
        c.weightx = 0.25;
        gLayout.setConstraints(player1InfoPart, c);
        this.add(player1InfoPart);

        GridBagLayout layoutPlayer1Info = new GridBagLayout();
        player1InfoPart.setLayout(layoutPlayer1Info);

        //Nom player 1

        c.weightx = 0;

        JLabel labelPlayer1Name = new JLabel("Alexandre"); labelPlayer1Name.setBackground(Color.red);
        c.gridx = 0;
        c.gridy = 0;
        layoutPlayer1Info.setConstraints(labelPlayer1Name, c);
        player1InfoPart.add(labelPlayer1Name);

        //Image player 1

        JPanel panelImagePlayer1 = new JPanel();
        panelImagePlayer1.setPreferredSize(new Dimension(104, 104));
        panelImagePlayer1.setBorder(blacklineBorder);
        c.gridx = 0;
        c.gridy = 1;
        layoutPlayer1Info.setConstraints(panelImagePlayer1, c);
        player1InfoPart.add(panelImagePlayer1);

        GridBagLayout layoutImagePlayer1 = new GridBagLayout();
        GridBagConstraints c2 = new GridBagConstraints();
        panelImagePlayer1.setLayout(layoutImagePlayer1);

        c2.anchor = GridBagConstraints.CENTER;
        c2.weightx = 0;
        c2.fill = GridBagConstraints.NONE;

        JLabel labelImagePlayer1 = new JLabel(imageRobot);
        c2.gridx = 0;
        c2.gridy = 0;
        layoutImagePlayer1.setConstraints(labelImagePlayer1, c2);
        panelImagePlayer1.add(labelImagePlayer1);

        //Status joueur 1

        JLabel labelPlayer1Status = new JLabel("Ton tour...");
        c.gridx = 0;
        c.gridy = 2;
        layoutPlayer1Info.setConstraints(labelPlayer1Status, c);
        player1InfoPart.add(labelPlayer1Status);

        //Grid

        gridPanel = new GridPanel(this);
        c.gridx = 1;
        c.gridy = 1;
        c.weightx = 0.5;
        c.weighty = 0.7;
        gLayout.setConstraints(gridPanel, c);
        this.add(gridPanel);

        c.weighty = 0;

        //Infos player 2

        JPanel player2InfoPart = new JPanel();
        c.gridx = 2;
        c.gridy = 1;
        c.weightx = 0.25;
        gLayout.setConstraints(player2InfoPart, c);
        this.add(player2InfoPart);

        GridBagLayout layoutPlayer2Info = new GridBagLayout();
        player2InfoPart.setLayout(layoutPlayer2Info);

        //Nom joueur 2

        c.weightx = 0;

        JLabel labelPlayer2Name = new JLabel("Philippe");
        c.gridx = 0;
        c.gridy = 0;
        layoutPlayer2Info.setConstraints(labelPlayer2Name, c);
        player2InfoPart.add(labelPlayer2Name);

        //Image player 2

        JPanel panelImagePlayer2 = new JPanel();
        panelImagePlayer2.setPreferredSize(new Dimension(104, 104));
        panelImagePlayer2.setBorder(blacklineBorder);
        c.gridx = 0;
        c.gridy = 1;
        layoutPlayer2Info.setConstraints(panelImagePlayer2, c);
        player2InfoPart.add(panelImagePlayer2);

        GridBagLayout layoutImagePlayer2 = new GridBagLayout();
        panelImagePlayer2.setLayout(layoutImagePlayer2);

        c2.anchor = GridBagConstraints.CENTER;
        c2.weightx = 0;
        c2.fill = GridBagConstraints.NONE;

        JLabel labelImagePlayer2 = new JLabel(imageRobot);
        c2.gridx = 0;
        c2.gridy = 0;
        layoutImagePlayer2.setConstraints(labelImagePlayer2, c2);
        panelImagePlayer2.add(labelImagePlayer2);

        //Status joueur 2

        JLabel labelPlayer2Status = new JLabel("Observe...");
        c.gridx = 0;
        c.gridy = 2;
        layoutPlayer2Info.setConstraints(labelPlayer2Status, c);
        player2InfoPart.add(labelPlayer2Status);

        //Turns

        JPanel turnPanel = new JPanel();
        c.gridx = 1;
        c.gridy = 2;
        c.insets = new Insets(30, 0,20, 0);
        gLayout.setConstraints(turnPanel, c);
        this.add(turnPanel);

        BoxLayout layoutTurnPanel = new BoxLayout(turnPanel, BoxLayout.X_AXIS);

        JLabel labelPreviousTurn = new JLabel(imageArrowLeft);
        turnPanel.add(labelPreviousTurn);

        Border borderIndexTurn = BorderFactory.createEmptyBorder(0, 30, 0 ,30);

        JLabel labelIndexTurn = new JLabel("Tour 1");
        labelIndexTurn.setFont(fontDialog20);
        labelIndexTurn.setBorder(borderIndexTurn);
        turnPanel.add(labelIndexTurn);

        JLabel labelNextTurn = new JLabel(imageArrowRight);
        turnPanel.add(labelNextTurn);

        //Undo - Redo

        JPanel panelHistory = new JPanel();
        c.gridx = 2;
        c.gridy = 2;
        c.weighty = 0.15;
        gLayout.setConstraints(panelHistory, c);
        this.add(panelHistory);

        c.insets = new Insets(0, 0,0, 0);
        c.weighty = 0;

        GridBagLayout layoutPanelHistory = new GridBagLayout();
        panelHistory.setLayout(layoutPanelHistory);

        JButton bttnUndo = new JButton("Annuler");
        bttnUndo.setPreferredSize(new Dimension(110, 30));
        bttnUndo.setBorder(new ButtonRoundBorder(15));
        c.gridx = 0;
        c.gridy = 0;
        layoutPanelHistory.setConstraints(bttnUndo, c);
        panelHistory.add(bttnUndo);

        JButton bttnRedo = new JButton("Refaire");
        bttnRedo.setPreferredSize(new Dimension(110, 30));
        bttnRedo.setBorder(new ButtonRoundBorder(15));
        c.gridx = 0;
        c.gridy = 1;
        c.insets = new Insets(8, 0, 20, 0);
        layoutPanelHistory.setConstraints(bttnRedo, c);
        panelHistory.add(bttnRedo);

        setEventHandlers();
        createMenuBar();
        UpdateGridPanelSize();
    }

    void createMenuBar(){
        menuBar = new JMenuBar();
        ui.getWindow().setJMenuBar(menuBar);

        JButton bttnSave = new JButton("Sauvegarder");
        menuBar.add(bttnSave);

        JButton bttnGiveup = new JButton("Abandonner");
        menuBar.add(bttnGiveup);
    }

    void loadAssets(){
        try{
            imageRobot = new ImageIcon(ImageIO.read(new File("assets/human-robot.png")));
            imageArrowLeft = new ImageIcon(ImageIO.read(new File("assets/arrow3_left.png")));
            imageArrowRight = new ImageIcon(ImageIO.read(new File("assets/arrow3_right.png")));
            imageBook = new ImageIcon(ImageIO.read(new File("assets/book.png")));
        } catch(IOException exp){
            exp.printStackTrace();
        }
    }

    public void UpdateGridPanelSize(){
        System.out.println(this.getWidth() + "-" + this.getHeight());
        if(this.getWidth() < 1000 || this.getHeight() < 800){
            gridPanel.setCaseSize(48);
        }
        else{
            gridPanel.setCaseSize(64);
        }
        //gridPanel.setPreferredSize(new Dimension(gridPanel.getCaseSize()*gridPanel.GRID_SIZE+1, gridPanel.getCaseSize()*gridPanel.GRID_SIZE+1));

        System.out.println(gridPanel.getWidth() + "--" + gridPanel.getHeight());
    }

    public void setEventHandlers(){
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                UpdateGridPanelSize();
            }
        });
    }

    @Override
    public void adaptWindow(){
        JFrame window = ui.getWindow();

        Dimension sizeScreen = Toolkit.getDefaultToolkit().getScreenSize();
        int height = (int) (sizeScreen.height * 0.85);
        int width = (int) (sizeScreen.width * 0.8);
        window.setSize(width, height);
        window.setLocationRelativeTo(null);

        createMenuBar();
    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);

        g.setFont(new Font("Arial", Font.BOLD, 15));

        int xN = gridPanel.getX()-22;
        int yI = gridPanel.getY() + gridPanel.getCaseSize()/2;
        for(int i = 0; i < GridPanel.GRID_SIZE; i++){
            int y = yI + i*gridPanel.getCaseSize();
            g.drawString(Integer.toString(i+1), xN, y);
        }

        String letters = "ABCDEFGHIJK";
        int xI = gridPanel.getX() + gridPanel.getCaseSize()/2;
        int y = gridPanel.getY() + gridPanel.getHeight() + 20;
        for(int i = 0; i < GridPanel.GRID_SIZE; i++){
            int xL = xI + i*gridPanel.getCaseSize();
            g.drawString(Character.toString(letters.charAt(i)), xL, y);
        }
    }

    public void setGameInstance(Game game){
        this.game = game;
    }

    public Game GetGameInstance(){
        return game;
    }

    @Override
    public void updateFrame() {
        UpdateGridPanelSize();
    }
}
