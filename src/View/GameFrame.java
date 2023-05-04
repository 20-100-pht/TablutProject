package View;

import Controller.GameGraphicController;
import Model.Game;
import Model.Grid;

import javax.swing.border.Border;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

public class GameFrame extends Frame {

    Game game;
    GameGraphicController gameGraphicController;
    GridPanel gridPanel;
    ImageIcon imageRobot;
    ImageIcon imageArrowLeft;
    ImageIcon imageArrowRight;
    ImageIcon imageBook;
    ImageIcon imageMenu;
    JMenuBar menuBar;
    JButton bttnMenu;
    JPopupMenu menu;
    JMenuItem save, forfeit, options;
    WinMessagePanel winMessagePanel;
    Timer timerWinMessage;


    public GameFrame(Interface ui){
        super(ui);

        gameGraphicController = new GameGraphicController(this);

        loadAssets();
    }

    public void build(){

        Border blacklineBorder = BorderFactory.createLineBorder(Color.BLACK);

        Font fontDialog20 = new Font(Font.DIALOG, Font.PLAIN, 20);

        this.setLayout(new GridLayout());

        //Main Panel

        JPanel mainPanel = new JPanel();
        OverlayLayout oLayout = new OverlayLayout(mainPanel);
        mainPanel.setLayout(oLayout);
        this.add(mainPanel);

        //Panel background

        JPanel bgPanel = new JPanel();
        //mainPanel.add(bgPanel);

        GridBagLayout gLayout = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();
        bgPanel.setLayout(gLayout);


        // MENU
        bttnMenu = new JButton(imageMenu);
        bttnMenu.setContentAreaFilled(false);
        bttnMenu.setOpaque(true);
        bttnMenu.setBorderPainted(false);
        bttnMenu.setMargin(new Insets(0,0,0,0));
        c.gridx = 0;
        c.gridy = 0;
        c.anchor = GridBagConstraints.FIRST_LINE_START;
        c.insets = new Insets(10, 15, 0, 0);
        c.weighty = 0.15;
        bttnMenu.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        gLayout.setConstraints(bttnMenu, c);
        bgPanel.add(bttnMenu);

        menu = new JPopupMenu();
        save = new JMenuItem("Sauvegarder");
        forfeit = new JMenuItem("Abandonner");
        options = new JMenuItem("Options");
        menu.add(save);
        menu.add(forfeit);
        menu.add(options);

        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets(0, 0, 0, 0);
        c.weighty = 0;

        //Infos player 1

        JPanel player1InfoPart = new JPanel();
        //player1InfoPart.setBackground(Color.orange);
        c.gridx = 0;
        c.gridy = 1;
        c.weightx = 0.25;
        gLayout.setConstraints(player1InfoPart, c);
        bgPanel.add(player1InfoPart);

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

        //Model.Grid

        gridPanel = new GridPanel(this);
        c.gridx = 1;
        c.gridy = 1;
        c.weightx = 0.5;
        c.weighty = 0.7;
        gLayout.setConstraints(gridPanel, c);
        bgPanel.add(gridPanel);

        c.weighty = 0;

        //Infos player 2

        JPanel player2InfoPart = new JPanel();
        c.gridx = 2;
        c.gridy = 1;
        c.weightx = 0.25;
        gLayout.setConstraints(player2InfoPart, c);
        bgPanel.add(player2InfoPart);

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
        bgPanel.add(turnPanel);

        BoxLayout layoutTurnPanel = new BoxLayout(turnPanel, BoxLayout.X_AXIS);

        JLabel labelPreviousTurn = new JLabel(imageArrowLeft);
        labelPreviousTurn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        turnPanel.add(labelPreviousTurn);

        Border borderIndexTurn = BorderFactory.createEmptyBorder(0, 30, 0 ,30);

        JLabel labelIndexTurn = new JLabel("Tour 1");
        labelIndexTurn.setFont(fontDialog20);
        labelIndexTurn.setBorder(borderIndexTurn);
        turnPanel.add(labelIndexTurn);

        JLabel labelNextTurn = new JLabel(imageArrowRight);
        labelNextTurn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        turnPanel.add(labelNextTurn);

        //Undo - Redo

        JPanel panelHistory = new JPanel();
        c.gridx = 2;
        c.gridy = 2;
        c.weighty = 0.15;
        gLayout.setConstraints(panelHistory, c);
        bgPanel.add(panelHistory);

        c.insets = new Insets(0, 0,0, 0);
        c.weighty = 0;

        GridBagLayout layoutPanelHistory = new GridBagLayout();
        panelHistory.setLayout(layoutPanelHistory);

        JButton bttnUndo = new JButton("Annuler");
        bttnUndo.setPreferredSize(new Dimension(110, 30));
        bttnUndo.setBorder(new ButtonRoundBorder(15));
        c.gridx = 0;
        c.gridy = 0;
        bttnUndo.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        layoutPanelHistory.setConstraints(bttnUndo, c);
        panelHistory.add(bttnUndo);

        JButton bttnRedo = new JButton("Refaire");
        bttnRedo.setPreferredSize(new Dimension(110, 30));
        bttnRedo.setBorder(new ButtonRoundBorder(15));
        c.gridx = 0;
        c.gridy = 1;
        c.insets = new Insets(8, 0, 20, 0);
        bttnRedo.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        layoutPanelHistory.setConstraints(bttnRedo, c);
        panelHistory.add(bttnRedo);

        //Panel background

        JPanel fgPanel = new JPanel(){
            public boolean isOptimizedDrawingEnabled() {
                return false;
            }
        };

        fgPanel.setOpaque(false);
        fgPanel.setLayout(null);

        winMessagePanel = new WinMessagePanel();
        winMessagePanel.setSize(new Dimension(600, 100));
        winMessagePanel.setOpaque(true);
        winMessagePanel.setVisible(false);
        fgPanel.add(winMessagePanel);

        mainPanel.add(fgPanel);
        mainPanel.add(bgPanel);

        setEventHandlers();
    }
    void loadAssets(){
        try{
            imageRobot = new ImageIcon(ImageIO.read(new File("assets/images/human-robot.png")));
            imageArrowLeft = new ImageIcon(ImageIO.read(new File("assets/images/arrow3_left.png")));
            imageArrowRight = new ImageIcon(ImageIO.read(new File("assets/images/arrow3_right.png")));
            imageBook = new ImageIcon(ImageIO.read(new File("assets/images/book.png")));
            imageMenu = new ImageIcon(ImageIO.read(new File( "assets/images/menu.png")));
        } catch(IOException exp){
            exp.printStackTrace();
        }
    }


    public void setEventHandlers(){
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {

            }
        });

        bttnMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menu.show(bttnMenu,0, bttnMenu.getHeight());
            }
        });
        forfeit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                openGiveupDialog();
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
        window.setMinimumSize(new Dimension(1000, 750));
    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);

        g.setFont(new Font("Arial", Font.BOLD, 15));

        int xN = gridPanel.getX()-22;
        int yI = gridPanel.getY() + gridPanel.getCaseSize()/2;
        for(int i = 0; i < GridPanel.GRID_SIZE; i++){
            int y = yI + i*gridPanel.getCaseSize();
            g.drawString(Integer.toString(9-i), xN, y);
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

    public GameGraphicController getGraphicGameController(){
        return gameGraphicController;
    }

    @Override
    public void updateFrame() {

    }

    public void showWinMessage(String winnerName){

        int x = ui.getWindow().getWidth()/2 - winMessagePanel.getWidth()/2;
        System.out.println(ui.getWindow().getWidth());
        winMessagePanel.setLocation(new Point(x, 300));

        winMessagePanel.setWinnerName(winnerName);
        winMessagePanel.setVisible(true);

        timerWinMessage = new Timer(4000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                hideWinMessage();
            }
        });
        timerWinMessage.start();

    }
    public void openGiveupDialog(){
        JDialog dialog = new JDialog();
        dialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        dialog.setTitle("Confirmation");
        dialog.setModal(true);

        // JPanel that contains the text and buttons
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Text
        JLabel question = new JLabel("Êtes-vous sûr de vouloir abandonner ?");
        question.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        panel.add(question, BorderLayout.NORTH);

        // Buttons
        JButton yesButton = new JButton("Oui");
        yesButton.setBackground(Color.GREEN);
        yesButton.setForeground(Color.WHITE);
        yesButton.setPreferredSize(new Dimension(80, 30));
        yesButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        yesButton.addActionListener(e -> {
            System.out.println("Tu as abandonné.");
            ui.changePage(InterfacePage.MENU);
            dialog.dispose(); // dialog closes after clicking on the button
        });

        JButton noButton = new JButton("Non");
        noButton.setBackground(Color.RED);
        noButton.setForeground(Color.WHITE);
        noButton.setPreferredSize(new Dimension(80, 30));
        noButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        noButton.addActionListener(e -> {
            System.out.println("Tu continues.");
            dialog.dispose(); // dialog closes after clicking on the button
        });

        // Add buttons to a custom panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(yesButton);
        buttonPanel.add(noButton);
        panel.add(buttonPanel, BorderLayout.CENTER);

        // Display the dialog
        dialog.add(panel);
        dialog.pack();
        dialog.setLocationRelativeTo(null);
        dialog.setResizable(false);
        dialog.setVisible(true);
    }

    public void hideWinMessage(){
        winMessagePanel.setVisible(false);
    }
}