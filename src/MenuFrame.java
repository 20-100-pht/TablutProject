import javax.swing.*;
import java.awt.*;

import static java.awt.Font.BOLD;
import static javax.swing.BoxLayout.Y_AXIS;

public class MenuFrame extends JComponent {

    Interface ui;
    Menu menu;

    public MenuFrame(Interface ui){
        this.ui = ui;
        menu = new Menu();
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

        JButton bttnNewGame = new JButton("Nouvelle partie");
        c.fill = GridBagConstraints.NONE;
        c.gridx = 1;
        c.gridy = 2;
        gLayout.setConstraints(bttnNewGame, c);
        this.add(bttnNewGame);

        JButton bttnLoadGame = new JButton("Charger une partie");
        c.fill = GridBagConstraints.NONE;
        c.gridx = 1;
        c.gridy = 3;
        gLayout.setConstraints(bttnLoadGame, c);
        this.add(bttnLoadGame);

        JButton bttnStatistics = new JButton("Statistiques");
        c.fill = GridBagConstraints.NONE;
        c.gridx = 1;
        c.gridy = 4;
        gLayout.setConstraints(bttnStatistics, c);
        this.add(bttnStatistics);

        JButton bttnOption = new JButton("Options");
        c.fill = GridBagConstraints.NONE;
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
    }

    @Override
    protected void paintComponent(Graphics g){

    }
}
