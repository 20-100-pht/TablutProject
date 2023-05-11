package View;

import Controller.MenuController;
import Model.Grid;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

import static java.awt.Font.BOLD;

public class MenuFrame extends Frame {

    MenuController menuController;
    Button bttnNewGame;
    Button bttnLoadGame;
    Button bttnStatistics;
    Button bttnOption;
    Image imageBackground;
    JLabel labelTitle;

    public MenuFrame(Interface ui){
        super(ui);

        menuController = new MenuController(this);

        loadAssets();
    }
    @Override
    public void build() {

        JFrame window = ui.getWindow();
        Font fontArial50 = new Font("Arial", BOLD, 50);

        GridBagLayout gLayout = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();

        labelTitle = new JLabel("Tablut");
        labelTitle.setFont(fontArial50);
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
        c.ipady = (int) ((double) window.getHeight()*0.15);
        gLayout.setConstraints(space, c);
        this.add(space);

        bttnNewGame = createClassicButton(gLayout, 1, 2, "Nouvelle partie");
        this.add(bttnNewGame);
        bttnLoadGame = createClassicButton(gLayout, 1, 3, "Charger une partie");
        this.add(bttnLoadGame);
        bttnStatistics = createClassicButton(gLayout, 1, 4, "Statistiques");
        this.add(bttnStatistics);
        bttnOption = createClassicButton(gLayout, 1, 5, "Options");
        this.add(bttnOption);

        setButtonHandlers();

        this.setLayout(gLayout);
    }

    Button createClassicButton(GridBagLayout layout, int posX, int posY, String text){
        Button bttn = new Button(text, true, this);
        bttn.setPreferredSize(new Dimension(275, 55));
        bttn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        bttn.setRoundValue(40);
        bttn.setFont(new Font(Font.DIALOG, Font.BOLD, 23));

        GridBagConstraints c = new GridBagConstraints();
        c.gridx = posX;
        c.gridy = posY;
        c.ipady = 0;
        c.insets = new Insets(20, 20, 20, 20);
        layout.setConstraints(bttn, c);

        return bttn;
    }

    public void setButtonHandlers(){
        bttnNewGame.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                ui.changePage(InterfacePage.NEWGAME);
            }
        });

        bttnLoadGame.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                menuController.loadGame();
            }
        });
    }

    @Override
    public void adaptWindow(){
        JFrame window = ui.getWindow();
        window.setMinimumSize(new Dimension(400, 600));

        Dimension sizeScreen = Toolkit.getDefaultToolkit().getScreenSize();
        int height = (int) (sizeScreen.height * 0.75);
        int width = (int) (sizeScreen.width * 0.6);
        int x = (int) (sizeScreen.width/2 - width/2);
        int y = (int) (sizeScreen.height/2 - height/2);

        window.setSize(width, height);
        window.setLocation(x, y);

        System.out.println(window.getWidth() + " " + window.getHeight());

        ui.getWindow().setJMenuBar(null);
    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);

        g.drawImage(imageBackground, 0, 0, this.getWidth(), this.getHeight(), null);
    }

    public void loadAssets(){
        try{
            imageBackground = ImageIO.read(new File("assets/images/backgroundMenu.jpg"));
        } catch(IOException exp){
            exp.printStackTrace();
        }
    }

    public File showLoadDialog(){
        File file = null;
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File("."));
        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            file = fileChooser.getSelectedFile();
        }
        return file;
    }
}
