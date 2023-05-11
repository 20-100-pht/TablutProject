package View;

import Model.Grid;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.io.File;
import java.io.IOException;


public class TextMessagePanel extends JPanel {

    JLabel textLabel;
    public TextMessagePanel(String text){

        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        textLabel = new JLabel(text);
        textLabel.setFont(new Font(Font.DIALOG, Font.BOLD, 25));
        c.insets = new Insets(0, 30, 0, 30);
        add(textLabel, c);

        c.insets = new Insets(0, 0, 0 ,0);

        Border border = BorderFactory.createLineBorder(Color.BLACK, 5);
        setBorder(border);
    }

    public void setText(String text){
        textLabel.setText(text);
    }
}
