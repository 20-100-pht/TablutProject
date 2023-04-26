import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static java.awt.Font.BOLD;
import static javax.swing.BoxLayout.Y_AXIS;

public class MenuFrame extends JComponent {

    Interface ui;
    Menu menu;
    Image selectorImage;
    boolean selectorDisplayed;
    Position selectorPos;

    public MenuFrame(Interface ui){
        this.ui = ui;
        menu = new Menu();
        loadAssets();

        selectorDisplayed = false;
    }

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
        c.ipady = (int) ((double) window.getWidth()*1);
        gLayout.setConstraints(space, c);
        this.add(space);

        c.ipady = 0;
        c.insets = new Insets(20, 20, 20, 20);

        Button bttnNewGame = new Button("Nouvelle partie", true, ui);
        //c.fill = GridBagConstraints.NONE;
        bttnNewGame.setBorder(new RoundBtn(10));
        c.gridx = 1;
        c.gridy = 2;
        gLayout.setConstraints(bttnNewGame, c);
        this.add(bttnNewGame);

        Button bttnLoadGame = new Button("Charger une partie", true, ui);
        //c.fill = GridBagConstraints.NONE;
        bttnNewGame.setBorder(new RoundBtn(10));
        c.gridx = 1;
        c.gridy = 3;
        gLayout.setConstraints(bttnLoadGame, c);
        this.add(bttnLoadGame);

        Button bttnStatistics = new Button("Statistiques", true, ui);
        // c.fill = GridBagConstraints.NONE;
        bttnNewGame.setBorder(new RoundBtn(10));
        c.gridx = 1;
        c.gridy = 4;
        gLayout.setConstraints(bttnStatistics, c);
        this.add(bttnStatistics);

        Button bttnOption = new Button("Options", true, ui);
        //c.fill = GridBagConstraints.NONE;
        bttnNewGame.setBorder(new RoundBtn(10));
        c.gridx = 1;
        c.gridy = 5;
        gLayout.setConstraints(bttnOption, c);
        this.add(bttnOption);

        this.setLayout(gLayout);
    }

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

        if(selectorDisplayed){
            g.drawImage(selectorImage, selectorPos.GetX(), selectorPos.GetY(), null);
        }
    }

    public void loadAssets(){
        try{
            selectorImage = ImageIO.read(new File("assets/arrow.png"));
            selectorImage= GraphicUtils.resizeImage(selectorImage, 16, 16);
        } catch(IOException exp){
            exp.printStackTrace();
        }
    }

    public void setVisibleSelector(boolean isVisible){
        selectorDisplayed = isVisible;
    }

    public void setSelectorPos(Position pos){
        selectorPos = pos;
    }

    // Rounding buttons
    class RoundBtn implements Border {
        private int r;
        RoundBtn(int r) {
            this.r = r;
        }
        public Insets getBorderInsets(Component c) {
            return new Insets(this.r+1, this.r+1, this.r+2, this.r);
        }
        public boolean isBorderOpaque() {
            return true;
        }
        public void paintBorder(Component c, Graphics g, int x, int y,
                                int width, int height) {
            g.drawRoundRect(x, y, width-1, height-1, r, r);
        }
    }
}
