package View;

import Model.Grid;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.io.File;
import java.io.IOException;


public class WinMessagePanel extends JPanel {

    String winnerName;
    JLabel textLabel;
    ImageIcon imageVictory;
    public WinMessagePanel(){
        winnerName = "Philippe";

        loadImages();

        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        JLabel imageLeft = new JLabel(imageVictory);
        imageLeft.setPreferredSize(new Dimension(64, 64));
        add(imageLeft);

        textLabel = new JLabel(winnerName);
        textLabel.setFont(new Font(Font.DIALOG, Font.BOLD, 25));
        c.insets = new Insets(0, 30, 0, 30);
        add(textLabel, c);

        c.insets = new Insets(0, 0, 0 ,0);

        JLabel imageRight = new JLabel(imageVictory);
        imageRight.setPreferredSize(new Dimension(64, 64));
        add(imageRight);

        Border border = BorderFactory.createLineBorder(Color.BLACK, 5);
        setBorder(border);
    }

    public void setWinnerName(String name){
        winnerName = name;
        textLabel.setText("C'est " + name + " qui gagne !");
    }

    public void loadImages(){
        try {
            imageVictory = new ImageIcon(ImageIO.read(new File("assets/images/victory.png")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
