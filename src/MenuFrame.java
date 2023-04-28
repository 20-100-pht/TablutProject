import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static java.awt.Font.BOLD;
import static javax.swing.BoxLayout.Y_AXIS;

public class MenuFrame extends Frame {

    Menu menu;
    Button bttnNewGame;
    Button bttnLoadGame;
    Button bttnStatistics;
    Button bttnOption;

    public MenuFrame(Interface ui){
        super(ui);

        menu = new Menu();
        loadAssets();
    }
    @Override
    public void build() {

        JFrame window = ui.getWindow();
        Font fontArial45 = new Font("Arial", BOLD, 45);

        GridBagLayout gLayout = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();

        JLabel labelTitle = new JLabel("Tablut");
        labelTitle.setFont(fontArial45);

        c.fill = GridBagConstraints.NONE;
        c.gridx = 1;
        c.gridy = 0;
        c.gridwidth = GridBagConstraints.REMAINDER;
        gLayout.setConstraints(labelTitle, c);
        this.add(labelTitle);

        JLabel space = new JLabel("");
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 1;
        c.gridy = 1;
        c.ipady = (int) ((double) window.getWidth()*0.7);
        gLayout.setConstraints(space, c);
        this.add(space);

        c.ipady = 0;
        c.insets = new Insets(20, 20, 20, 20);

        bttnNewGame = new Button("Nouvelle partie", true, this);
        //c.fill = GridBagConstraints.NONE;
        bttnNewGame.setBorder(new ButtonRoundBorder(10));
        c.gridx = 1;
        c.gridy = 2;
        gLayout.setConstraints(bttnNewGame, c);
        this.add(bttnNewGame);

        bttnLoadGame = new Button("Charger une partie", true, this);
        //c.fill = GridBagConstraints.NONE;
        bttnLoadGame.setBorder(new ButtonRoundBorder(10));
        c.gridx = 1;
        c.gridy = 3;
        gLayout.setConstraints(bttnLoadGame, c);
        this.add(bttnLoadGame);

        bttnStatistics = new Button("Statistiques", true, this);
        // c.fill = GridBagConstraints.NONE;
        bttnStatistics.setBorder(new ButtonRoundBorder(10));
        c.gridx = 1;
        c.gridy = 4;
        gLayout.setConstraints(bttnStatistics, c);
        this.add(bttnStatistics);

        bttnOption = new Button("Options", true, this);
        //c.fill = GridBagConstraints.NONE;
        bttnOption.setBorder(new ButtonRoundBorder(10));
        c.gridx = 1;
        c.gridy = 5;
        gLayout.setConstraints(bttnOption, c);
        this.add(bttnOption);

        setButtonHandlers();

        this.setLayout(gLayout);
    }

    public void setButtonHandlers(){
        bttnNewGame.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                ui.changePage(InterfacePage.NEWGAME);
            }
        });
    }

    @Override
    public void adaptWindow(){
        JFrame window = ui.getWindow();

        Dimension sizeScreen = Toolkit.getDefaultToolkit().getScreenSize();
        int height = (int) (sizeScreen.height * 0.6);
        int width = (int) (sizeScreen.width * 0.4);
        window.setSize(width, height);
        window.setLocationRelativeTo(null);
    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
    }

    public void loadAssets(){

    }
}
