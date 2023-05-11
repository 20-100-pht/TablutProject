package View;

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
    public OptionsFrame() {
        setTitle("Options");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(300, 200);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel animationLabel = new JLabel("Animation active:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        mainPanel.add(animationLabel, gbc);

        yesButton = new JRadioButton("Oui");
        yesButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        yesButton.setSelected(true);

        noButton = new JRadioButton("Non");
        noButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        ButtonGroup animationGroup = new ButtonGroup();
        animationGroup.add(yesButton);
        animationGroup.add(noButton);

        JPanel animationPanel = new JPanel();
        animationPanel.add(yesButton);
        animationPanel.add(noButton);

        gbc.gridx = 1;
        gbc.gridy = 0;
        mainPanel.add(animationPanel, gbc);

        JLabel themeLabel = new JLabel("Changer de thème:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        mainPanel.add(themeLabel, gbc);

        String[] themes = {"Thème 1", "Thème 2"};
        themeComboBox = new JComboBox<>(themes);
        themeComboBox.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        gbc.gridx = 1;
        gbc.gridy = 1;
        mainPanel.add(themeComboBox, gbc);

        okButton = new JButton("Save");
        okButton.setBackground(new Color(0,220,0));
        okButton.setForeground(Color.white);
        okButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        exitButton = new JButton("Undo");
        exitButton.setBackground(new Color(220,0,0));
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
                // A COMPLETER
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
