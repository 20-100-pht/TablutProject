package View;

import Global.Configuration;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OptionsFrame extends JFrame {
    JRadioButton yesButton;
    JRadioButton noButton;
    JComboBox<String> themeComboBox;
    JButton okButton;
    JButton exitButton;
    Frame frame;
    public OptionsFrame(Frame frame) {
        this.frame = frame;

        setTitle("Options");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(300, 200);

        Dimension sizeScreen = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) (sizeScreen.width/2 - getWidth()/2);
        int y = (int) (sizeScreen.height/2 - getHeight()/2);
        setLocation(x, y);

        build();

        setVisible(true);
        setEventHandlersOptions();
    }

    public void build(){
        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        Font fontOptions = new Font("Arial", Font.BOLD, 13);

        JLabel animationLabel = new JLabel("Animation active:");
        animationLabel.setFont(fontOptions);
        animationLabel.setForeground(Color.BLACK);
        gbc.gridx = 0;
        gbc.gridy = 0;
        mainPanel.add(animationLabel, gbc);

        yesButton = new JRadioButton("Oui");
        yesButton.setFont(fontOptions);
        yesButton.setForeground(Color.BLACK);
        yesButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        noButton = new JRadioButton("Non");
        noButton.setFont(fontOptions);
        noButton.setForeground(Color.BLACK);
        noButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        ButtonGroup animationGroup = new ButtonGroup();
        animationGroup.add(yesButton);
        animationGroup.add(noButton);

        if(Configuration.isAnimationActived()){
            yesButton.setSelected(true);
        }
        else{
            noButton.setSelected(true);
        }

        JPanel animationPanel = new JPanel();
        animationPanel.add(yesButton);
        animationPanel.add(noButton);

        gbc.gridx = 1;
        gbc.gridy = 0;
        mainPanel.add(animationPanel, gbc);

        JLabel themeLabel = new JLabel("Changer de thème:");
        themeLabel.setFont(fontOptions);
        themeLabel.setForeground(Color.BLACK);
        gbc.gridx = 0;
        gbc.gridy = 1;
        mainPanel.add(themeLabel, gbc);

        String[] themes = {"Thème 1", "Thème 2"};
        themeComboBox = new JComboBox<>(themes);
        themeComboBox.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        themeComboBox.setSelectedIndex(Configuration.getThemeIndex());
        themeComboBox.setForeground(Color.BLACK);
        gbc.gridx = 1;
        gbc.gridy = 1;
        mainPanel.add(themeComboBox, gbc);

        okButton = new JButton("Sauvegarder");
        okButton.setBackground(new Color(0,180,0));
        okButton.setForeground(Color.white);
        okButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        exitButton = new JButton("Annuler");
        exitButton.setBackground(new Color(180,0,0));
        exitButton.setForeground(Color.white);
        exitButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(okButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(10,0)));
        buttonPanel.add(exitButton);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(buttonPanel, gbc);

        add(mainPanel);
        setResizable(false);
        setVisible(true);

        setEventHandlersOptions();
    }

    public void setEventHandlersOptions(){
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String themeValue = (String) themeComboBox.getSelectedItem();
                if(themeValue == "Thème 1"){
                    Configuration.setThemeIndex(0);
                }
                else if(themeValue == "Thème 2"){
                    Configuration.setThemeIndex(1);
                }
                Configuration.save();

                frame.updateTheme();

                if(yesButton.isSelected()){
                    Configuration.setAnimationActived(true);
                }
                else{
                    Configuration.setAnimationActived(false);
                }

                dispose();
            }
        });
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }

}
