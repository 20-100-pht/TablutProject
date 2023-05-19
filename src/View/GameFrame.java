package View;

import Animation.AnimationChrono;
import Controller.GameGraphicController;
import Global.Configuration;
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
    ImageIcon imageHuman;
    ImageIcon imageArrowLeft;
    ImageIcon imageArrowRight;
    ImageIcon imageHint;
    ImageIcon imageBook;
    ImageIcon imageMenu;
    JButton bttnMenu;
    JLabel bttnHelp;
    JLabel bttnHint;
    JPopupMenu menu;
    JMenuItem save, forfeit, options;
    WinMessagePanel winMessagePanel;
    OptionsFrame optionsFrame;
    ForfeitDialog forfeitDialog;
    Timer timerWinMessage;
    Button bttnBackMenu;
    Button bttnReplay;
    Button bttnUndo;
    Button bttnRedo;
    CapturedPiecesPanel capturedPiecesPanel1;
    CapturedPiecesPanel capturedPiecesPanel2;
    boolean frozen;
    JLabel labelIndexTurn;
    TextMessagePanel textMessagePanel;
    Timer timerTextMessage;
    Color COLOR_ATTACKER;
    Color COLOR_DEFENDER;
    int theme;
    JPanel panelImagePlayer1;
    JPanel panelImagePlayer2;
    TimerLabel chronoLabelDef;
    TimerLabel chronoLabelAtt;
    AnimationChrono animationChrono;
    Image imageBackground;
    JPanel gridBorderPanel;
    Button bttnStatusIa;
    JLabel labelPreviousTurn;
    JLabel labelNextTurn;
    JPanel rulesPanel;
    ImageIcon imageBulb;


    public GameFrame(Interface ui, Game game){
        super(ui);

        gameGraphicController = new GameGraphicController(this, game);
        this.game = game;

        frozen = false;
        theme = Configuration.getThemeIndex();

        if(game.isBlitzMode()) {
            animationChrono = new AnimationChrono(game, this);
            addAnimation(animationChrono);
        }

        loadAssets();
    }

    public void build(){

        Font fontDialog20 = new Font(Font.DIALOG, Font.PLAIN, 20);

        this.setLayout(new GridLayout());

        //Main Panel

        JPanel mainPanel = new JPanel();
        mainPanel.setOpaque(false);
        OverlayLayout oLayout = new OverlayLayout(mainPanel);
        mainPanel.setLayout(oLayout);
        this.add(mainPanel);

        //Panel background

        JPanel bgPanel = new JPanel();
        bgPanel.setOpaque(false);
        //bgPanel.setBackground(new Color(60,60,60));

        GridBagLayout gLayout = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();
        bgPanel.setLayout(gLayout);

        // MENU

        bttnMenu = new JButton(imageMenu);
        bttnMenu.setContentAreaFilled(false);
        bttnMenu.setBorderPainted(false);
        bttnMenu.setMargin(new Insets(0,0,0,0));
        c.gridx = 0;
        c.gridy = 0;
        c.anchor = GridBagConstraints.FIRST_LINE_START;
        c.insets = new Insets(10, 15, 0, 0);
        c.weighty = 0.15;
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

        //Buttons game ended

        JPanel bttnsAfterGamePanel = new JPanel();
        bttnsAfterGamePanel.setOpaque(false);
        bttnsAfterGamePanel.setLayout(new GridBagLayout());
        c.gridx = 1;
        c.gridy = 0;
        bgPanel.add(bttnsAfterGamePanel, c);

        bttnBackMenu = new Button("Retour au menu", false, this);
        bttnBackMenu.setPreferredSize(new Dimension(200, 35));
        bttnBackMenu.setFont(new Font(Font.DIALOG, Font.PLAIN, 20));
        bttnBackMenu.setRoundValue(35);
        bttnBackMenu.setBackgroundColorHov(Color.orange);
        c.gridx = 0;
        c.gridy = 0;
        c.insets = new Insets(12, 0, 0, 35);
        bttnsAfterGamePanel.add(bttnBackMenu, c);
        bttnBackMenu.setVisible(false);

        bttnReplay = new Button("Recommencer", false, this);
        bttnReplay.setPreferredSize(new Dimension(200, 35));
        bttnReplay.setFont(new Font(Font.DIALOG, Font.PLAIN, 20));
        bttnReplay.setRoundValue(35);
        bttnReplay.setBackgroundColorHov(Color.orange);
        c.gridx = 1;
        c.gridy = 0;
        c.insets = new Insets(12, 0, 0, 0);
        bttnsAfterGamePanel.add(bttnReplay, c);
        bttnReplay.setVisible(false);


        // btn regle-tuto

        JPanel panelTopRightIcons = new JPanel();
        panelTopRightIcons.setOpaque(false);
        c.gridx = 2;
        c.gridy = 0;
        c.anchor = GridBagConstraints.FIRST_LINE_END;
        c.insets = new Insets(10, 0, 0, 15);
        c.weighty = 0.15;
        c.weightx = 0.01;
        bgPanel.add(panelTopRightIcons, c);

        bttnHelp = new JLabel(imageBook);
        //gLayout.setConstraints(bttnHelp, c);
        panelTopRightIcons.add(bttnHelp);

        bttnHint = new JLabel(imageBulb);
        panelTopRightIcons.add(bttnHint);


        c.insets = new Insets(0, 0, 0, 0);
        c.anchor = GridBagConstraints.CENTER;


        //Infos player 1

        RoundPanel player1InfoPart = new RoundPanel(35);
        player1InfoPart.setOpaque(false);
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

        JLabel labelPlayer1Name = new JLabel(game.getDefenderName());
        labelPlayer1Name.setFont(new Font(Font.DIALOG, Font.BOLD, 14));
        c.gridx = 0;
        c.gridy = 0;
        c.insets = new Insets(12, 20, 0,20);
        layoutPlayer1Info.setConstraints(labelPlayer1Name, c);
        player1InfoPart.add(labelPlayer1Name);

        c.insets = new Insets(12, 0, 0,0);

        //Image player 1

        panelImagePlayer1 = new JPanel();
        panelImagePlayer1.setPreferredSize(new Dimension(104, 104));
        //panelImagePlayer1.setBorder(imagePlayer1Border);
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

        JLabel labelImagePlayer1;
        if(game.isDefenderAI()) labelImagePlayer1 = new JLabel(imageRobot);
        else labelImagePlayer1 = new JLabel(imageHuman);
        c2.gridx = 0;
        c2.gridy = 0;
        layoutImagePlayer1.setConstraints(labelImagePlayer1, c2);
        panelImagePlayer1.add(labelImagePlayer1);

        //Chrono défenseur
        chronoLabelDef = new TimerLabel(game.getDefTimeRemained());
        chronoLabelDef.setFont(new Font("Arial", Font.BOLD, 20));
        c.gridx = 0;
        c.gridy = 3;
        player1InfoPart.add(chronoLabelDef, c);

        //Pieces capturées par le joueur 1

        capturedPiecesPanel1 = new CapturedPiecesPanel(this, true);
        capturedPiecesPanel1.setPreferredSize(new Dimension(160, 80));
        capturedPiecesPanel1.setOpaque(false);
        c.gridx = 0;
        c.gridy = 4;
        layoutPlayer1Info.setConstraints(capturedPiecesPanel1, c);
        player1InfoPart.add(capturedPiecesPanel1);

        //Model.Grid

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BorderLayout());
        centerPanel.setOpaque(false);
        c.gridx = 1;
        c.gridy = 1;
        c.weightx = 0.5;
        c.weighty = 0.6;
        c.fill = GridBagConstraints.CENTER;
        gLayout.setConstraints(centerPanel, c);
        bgPanel.add(centerPanel);

        gridPanel = new GridPanel(this);

        GridPanelContainer gridPanelContainer = new GridPanelContainer(gridPanel, 35);
        gridPanelContainer.setLayout(new GridBagLayout());
        gridPanelContainer.setColor(new Color(236, 240, 241));
        centerPanel.add(gridPanelContainer);

        gridBorderPanel = new JPanel();
        gridBorderPanel.setLayout(new GridBagLayout());
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 0;
        c.weighty = 0;
        c.insets = new Insets(25, 25, 25, 25);
        gridPanelContainer.add(gridBorderPanel, c);

        c.insets = new Insets(5, 5, 5, 5);
        //c.insets = new Insets(0,0,0,0);
        gridBorderPanel.add(gridPanel, c);

        c.insets = new Insets(12, 0, 0, 0);

        //Infos player 2

        RoundPanel player2InfoPart = new RoundPanel(35);
        player2InfoPart.setOpaque(false);
        c.gridx = 2;
        c.gridy = 1;
        c.weightx = 0.25;
        gLayout.setConstraints(player2InfoPart, c);
        bgPanel.add(player2InfoPart);

        GridBagLayout layoutPlayer2Info = new GridBagLayout();
        player2InfoPart.setLayout(layoutPlayer2Info);

        //Nom joueur 2

        c.weightx = 0;

        JLabel labelPlayer2Name = new JLabel(game.getAttackerName());
        labelPlayer2Name.setFont(new Font(Font.DIALOG, Font.BOLD, 14));
        c.gridx = 0;
        c.gridy = 0;
        c.insets = new Insets(12, 20, 0,20);
        layoutPlayer2Info.setConstraints(labelPlayer2Name, c);
        player2InfoPart.add(labelPlayer2Name);

        c.insets = new Insets(12, 0, 0,0);

        //Image player 2

        panelImagePlayer2 = new JPanel();
        panelImagePlayer2.setPreferredSize(new Dimension(104, 104));
        //panelImagePlayer2.setBorder(imagePlayer2Border);
        c.gridx = 0;
        c.gridy = 1;
        layoutPlayer2Info.setConstraints(panelImagePlayer2, c);
        player2InfoPart.add(panelImagePlayer2);

        GridBagLayout layoutImagePlayer2 = new GridBagLayout();
        panelImagePlayer2.setLayout(layoutImagePlayer2);

        c2.anchor = GridBagConstraints.CENTER;
        c2.weightx = 0;
        c2.fill = GridBagConstraints.NONE;

        JLabel labelImagePlayer2;
        if(game.isAttackerAI()) labelImagePlayer2 = new JLabel(imageRobot);
        else labelImagePlayer2 = new JLabel(imageHuman);
        c2.gridx = 0;
        c2.gridy = 0;
        layoutImagePlayer2.setConstraints(labelImagePlayer2, c2);
        panelImagePlayer2.add(labelImagePlayer2);

        //Chrono Attacker

        chronoLabelAtt = new TimerLabel(game.getAttTimeRemained());
        chronoLabelAtt.setFont(new Font("Arial", Font.BOLD, 20));
        c.gridx = 0;
        c.gridy = 3;
        player2InfoPart.add(chronoLabelAtt, c);

        capturedPiecesPanel2 = new CapturedPiecesPanel(this, false);
        capturedPiecesPanel2.setPreferredSize(new Dimension(160, 80));
        capturedPiecesPanel2.setOpaque(false);
        c.gridx = 0;
        c.gridy = 4;
        layoutPlayer2Info.setConstraints(capturedPiecesPanel2, c);
        player2InfoPart.add(capturedPiecesPanel2);

        //Button pause ia

        if(game.isAttackerAI() | game.isDefenderAI()) {
            bttnStatusIa = new Button("Stopper IAs", false, this);
            bttnStatusIa.setFont(new Font(Font.DIALOG, Font.PLAIN, 20));
            bttnStatusIa.setPreferredSize(new Dimension(160, 40));
            bttnStatusIa.setRoundValue(35);
            c.gridx = 0;
            c.gridy = 2;
            gLayout.setConstraints(bttnStatusIa, c);
            bgPanel.add(bttnStatusIa);
        }

        //Turns

        RoundPanel turnPanel = new RoundPanel(35);
        c.gridx = 1;
        c.gridy = 2;
        c.insets = new Insets(10, 15,20, 15);
        gLayout.setConstraints(turnPanel, c);
        bgPanel.add(turnPanel);

        labelPreviousTurn = new JLabel(imageArrowLeft);
        turnPanel.add(labelPreviousTurn);

        Border borderIndexTurn = BorderFactory.createEmptyBorder(0, 30, 0 ,30);

        labelIndexTurn = new JLabel("Tour 1");
        labelIndexTurn.setFont(fontDialog20);
        labelIndexTurn.setBorder(borderIndexTurn);
        turnPanel.add(labelIndexTurn);

        labelNextTurn = new JLabel(imageArrowRight);
        turnPanel.add(labelNextTurn);

        //Undo - Redo

        JPanel panelHistory = new JPanel();
        panelHistory.setOpaque(false);
        c.gridx = 2;
        c.gridy = 2;
        c.weighty = 0.15;
        gLayout.setConstraints(panelHistory, c);
        bgPanel.add(panelHistory);

        c.insets = new Insets(0, 0,0, 0);
        c.weighty = 0;

        GridBagLayout layoutPanelHistory = new GridBagLayout();
        panelHistory.setLayout(layoutPanelHistory);

        bttnUndo = new Button("Annuler", false, this);
        bttnUndo.setPreferredSize(new Dimension(160, 40));
        bttnUndo.setRoundValue(35);
        c.gridx = 0;
        c.gridy = 0;
        layoutPanelHistory.setConstraints(bttnUndo, c);
        panelHistory.add(bttnUndo);

        bttnRedo = new Button("Refaire", false, this);
        bttnRedo.setPreferredSize(new Dimension(160, 40));
        bttnRedo.setRoundValue(35);
        c.gridx = 0;
        c.gridy = 1;
        c.insets = new Insets(8, 0, 20, 0);
        layoutPanelHistory.setConstraints(bttnRedo, c);
        panelHistory.add(bttnRedo);

        // btn indice -> bttnHint

        //Panel foreground

        JPanel fgPanel = new JPanel(){
            public boolean isOptimizedDrawingEnabled() {
                return false;
            }
        };

        fgPanel.setOpaque(false);
        fgPanel.setLayout(null);

        createMessages(fgPanel);

        //Panel rules

        rulesPanel = new JPanel();
        rulesPanel.setOpaque(false);
        rulesPanel.setLayout(new GridBagLayout());
        rulesPanel.setVisible(false);

        RulesPanel rulesVPanel = new RulesPanel(this, 35);
        rulesVPanel.build();
        rulesVPanel.setBackground(Color.red);

        c.weightx = 0.5;
        c.weighty = 0.5;
        c.anchor = GridBagConstraints.CENTER;
        /*c.gridx = 1;
        c.gridy = 1;*/
        rulesPanel.add(rulesVPanel, c);


        mainPanel.add(rulesPanel);
        mainPanel.add(fgPanel);
        mainPanel.add(bgPanel);

        updateTheme();
        setEventHandlers();
    }

    public void createMessages(JPanel parent){
        winMessagePanel = new WinMessagePanel();
        winMessagePanel.setSize(new Dimension(750, 100));
        winMessagePanel.setVisible(false);
        parent.add(winMessagePanel);

        textMessagePanel = new TextMessagePanel("");
        textMessagePanel.setSize(new Dimension(750, 100));
        textMessagePanel.setVisible(false);
        parent.add(textMessagePanel);
    }

    void loadAssets(){
        try{
            imageRobot = new ImageIcon(ImageIO.read(new File("assets/images/robot.png")));
            imageHuman = new ImageIcon(ImageIO.read(new File("assets/images/human.png")));
            imageArrowLeft = new ImageIcon(ImageIO.read(new File("assets/images/arrow3_left.png")));
            imageArrowRight = new ImageIcon(ImageIO.read(new File("assets/images/arrow3_right.png")));
            imageBook = new ImageIcon(ImageIO.read(new File("assets/images/book.png")));
            imageBulb = new ImageIcon(ImageIO.read(new File("assets/images/bulb.png")));
            imageMenu = new ImageIcon(ImageIO.read(new File( "assets/images/menu.png")));
            imageBackground = ImageIO.read(new File( "assets/images/backgroundMenu.jpg"));
        } catch(IOException exp){
            exp.printStackTrace();
        }
    }


    public void setEventHandlers(){

        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                gridPanel.setPreferredSize(gridPanel.getPreferredSize());
                gridPanel.revalidate();
            }
        });

        bttnBackMenu.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                ui.changePage(InterfacePage.MENU);
            }
        });

        bttnReplay.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                gameGraphicController.bttnReplayClickHandler();
            }
        });

        bttnUndo.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if(frozen) return;
                gameGraphicController.bttnUndoClickHandler();
            }
        });

        bttnRedo.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if(frozen) return;
                gameGraphicController.bttnRedoClickHandler();
            }
        });

        if(bttnStatusIa != null){
            bttnStatusIa.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseReleased(MouseEvent e) {
                    super.mouseReleased(e);
                    gameGraphicController.bttnPauseIaClickHandler();
                }
            });
        }

        bttnHelp.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                rulesPanel.setVisible(true);
                setFrozen(true);
            }
        });

        bttnHint.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                gameGraphicController.bttnHintClickHandler();
            }
        });

        setMenuHandlers();
        setReviewModeHandlers();
    }

    void setReviewModeHandlers(){
        labelPreviousTurn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                if(frozen && !game.isEnded()) return;
                gameGraphicController.bttnPreviousTurnClickHandler();
            }
        });

        labelNextTurn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                if(frozen && !game.isEnded()) return;
                gameGraphicController.bttnNextTurnClickHandler();
            }
        });
    }

    void setMenuHandlers(){

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
        options.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openOptionsWindow();
            }
        });

        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameGraphicController.bttnSaveClickHandler();
            }
        });
    }

    @Override
    public void adaptWindow(){
        JFrame window = ui.getWindow();
        window.setMinimumSize(new Dimension(1000, 750));

        Dimension sizeScreen = Toolkit.getDefaultToolkit().getScreenSize();
        int height = (int) (sizeScreen.height);
        int width = (int) (sizeScreen.width);
        window.setSize(width, height);
        window.setExtendedState(JFrame.MAXIMIZED_BOTH);
        //window.setLocationRelativeTo(null);
        window.setLocation(0, 0);
    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);

        g.drawImage(imageBackground, 0, 0, this.getWidth(), this.getHeight(), null);
    }

    public void setGameInstance(Game game){
        this.game = game;
    }

    public GameGraphicController getGraphicGameController(){
        return gameGraphicController;
    }

    public void showEndGameButtons(){
        bttnBackMenu.setVisible(true);
        bttnReplay.setVisible(true);
    }

    public void hideEndGameButtons(){
        bttnBackMenu.setVisible(false);
        bttnReplay.setVisible(false);
    }

    public void showWinMessage(String winnerName){

        int x = ui.getWindow().getWidth()/2 - winMessagePanel.getWidth()/2;
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

    public void showTextMessage(String text, int delay){

        int x = ui.getWindow().getWidth()/2 - textMessagePanel.getWidth()/2;
        textMessagePanel.setLocation(new Point(x, 300));

        textMessagePanel.setText(text);
        textMessagePanel.setVisible(true);

        if(delay != -1) {
            timerTextMessage = new Timer(delay, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    hideTextMessage();
                }
            });
            timerTextMessage.start();
        }
    }

    public void hideTextMessage(){
        if(timerTextMessage != null) {
            timerTextMessage.stop();
        }
        textMessagePanel.setVisible(false);
    }

    public void setTextMessage(String text){
        textMessagePanel.setText(text);
    }

    public void openGiveupDialog(){ forfeitDialog = new ForfeitDialog(ui);
    }
    public void openOptionsWindow(){
        optionsFrame = new OptionsFrame(this);
    }
    public void hideWinMessage(){
        if(timerWinMessage != null){
            timerWinMessage.stop();
        }
        winMessagePanel.setVisible(false);
    }

    public void hideAllMessages(){
        hideWinMessage();
        hideTextMessage();
    }

    public File showSaveDialog(){
        File file = null;
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File("."));
        if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            file = fileChooser.getSelectedFile();
        }
        return file;
    }

    @Override
    public void display(){
        game.startGame();
    }

    public void setFrozen(boolean frozen){
        this.frozen = frozen;
        gridPanel.setFrozen(frozen);
        if(game.isBlitzMode()) {
            animationChrono.setPaused(frozen);
        }
    }

    public void setTurnLabelValue(String text){
        labelIndexTurn.setText(text);
    }

    public void updatePlayerStatus() {
        Color colorTurn;
        if(game.isAttackerTurn()) colorTurn = COLOR_ATTACKER;
        else colorTurn = COLOR_DEFENDER;

        gridPanel.setBorder(BorderFactory.createLineBorder(colorTurn, 2));
        gridBorderPanel.setBackground(colorTurn);
    }

    public GridPanel getGridPanelInstance(){
        return gridPanel;
    }

    @Override
    public void updateTheme(){
        theme = Configuration.getThemeIndex();
        loadTheme();

        if(gridPanel == null) return;

        gridPanel.updateTheme();
        capturedPiecesPanel1.updateTheme();
        capturedPiecesPanel2.updateTheme();

        panelImagePlayer1.setBorder(BorderFactory.createLineBorder(COLOR_DEFENDER, 5));
        panelImagePlayer2.setBorder(BorderFactory.createLineBorder(COLOR_ATTACKER, 5));

        updatePlayerStatus();
    }

    public void loadTheme(){
        switch (theme){
            case 1:
                COLOR_ATTACKER = new Color(98, 124, 160);
                COLOR_DEFENDER = new Color(214, 206, 71);
                break;
            default:
                COLOR_ATTACKER = Color.WHITE;
                COLOR_DEFENDER = Color.BLACK;
        }
    }

    public void updateChronoLabels() {
        chronoLabelDef.setDuration(game.getDefTimeRemained());
        chronoLabelDef.updateTextValue();
        chronoLabelAtt.setDuration(game.getAttTimeRemained());
        chronoLabelAtt.updateTextValue();
    }

    public void setBttnIaPauseText(String text){
        bttnStatusIa.setText(text);
    }

    public boolean isAnimationMoveTerminated(){
        return gridPanel.getAnimationMove() == null || gridPanel.getAnimationMove().isTerminated();
    }

    public void hideRulesPanel(){
        rulesPanel.setVisible(false);
        setFrozen(false);
    }
}