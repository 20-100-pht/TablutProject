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
    public GameFrame(Interface ui){
        super(ui);

        loadAssets();
    }

    public void build(){

        Border blacklineBorder = BorderFactory.createLineBorder(Color.BLACK);

        GridBagLayout gLayout = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();
        this.setLayout(gLayout);

        JPanel player1InfoPart = new JPanel(); //player1InfoPart.setBackground(Color.orange);
        c.gridx = 0;
        c.gridy = 1;
        c.weightx = 0.25;
        gLayout.setConstraints(player1InfoPart, c);
        this.add(player1InfoPart);

        //BoxLayout layoutPlayer1Info = new BoxLayout(player1InfoPart, BoxLayout.Y_AXIS);
        GridLayout layoutPlayer1Info = new GridLayout();
        layoutPlayer1Info.setColumns(1);
        layoutPlayer1Info.setRows(3);
        player1InfoPart.setLayout(layoutPlayer1Info);

        JPanel panelImagePlayer1 = new JPanel();
        panelImagePlayer1.setPreferredSize(new Dimension(104, 104));
        panelImagePlayer1.setBorder(blacklineBorder);
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

        JLabel labelPlayer1Name = new JLabel("Alexandre"); labelPlayer1Name.setBackground(Color.red);
        c2.gridx = 0;
        c2.gridy = 1;
        layoutImagePlayer1.setConstraints(labelPlayer1Name, c2);
        player1InfoPart.add(labelPlayer1Name);

        JLabel labelPlayer1Status = new JLabel("Ton tour...");
        c2.gridx = 0;
        c2.gridy = 2;
        layoutImagePlayer1.setConstraints(labelPlayer1Status, c2);
        player1InfoPart.add(labelPlayer1Status);



        gridPanel = new GridPanel(this);
        gridPanel.setPreferredSize(new Dimension(500, 500));
        //gridPanel.setBackground(Color.ORANGE);
        c.gridx = 1;
        c.gridy = 1;
        c.weightx = 0.5;
        gLayout.setConstraints(gridPanel, c);
        this.add(gridPanel);

        JPanel player2InfoPart = new JPanel();
        c.gridx = 2;
        c.gridy = 1;
        c.weightx = 0.25;
        gLayout.setConstraints(player2InfoPart, c);
        this.add(player2InfoPart);

        setEventHandlers();
        UpdateGridPanelSize();
    }

    void loadAssets(){
        try{
            imageRobot = new ImageIcon(ImageIO.read(new File("assets/human-robot.png")));
        } catch(IOException exp){
            exp.printStackTrace();
        }
    }

    void UpdateGridPanelSize(){
        if(this.getWidth() < 1200){
            gridPanel.setCaseSize(48);
        }
        else{
            gridPanel.setCaseSize(64);
        }
        gridPanel.setPreferredSize(new Dimension(gridPanel.getCaseSize()*gridPanel.GRID_SIZE, gridPanel.getCaseSize()*gridPanel.GRID_SIZE));
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
        int height = (int) (sizeScreen.height * 0.8);
        int width = (int) (sizeScreen.width * 0.8);
        window.setSize(width, height);
        window.setLocationRelativeTo(null);
    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
    }

    public void setGameInstance(Game game){
        this.game = game;
    }

    public Game GetGameInstance(){
        return game;
    }
}
