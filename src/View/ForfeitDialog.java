package View;

import javax.swing.*;
import java.awt.*;

public class ForfeitDialog extends JDialog {
    public ForfeitDialog(Interface ui){
        JDialog dialog = new JDialog();
        dialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        dialog.setTitle("Confirmation");
        dialog.setModal(true);

        // JPanel that contains the text and buttons
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Text
        JLabel question = new JLabel("Êtes-vous sûr de vouloir abandonner ?");
        question.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        panel.add(question, BorderLayout.NORTH);

        // Buttons
        JButton yesButton = new JButton("Oui");
        yesButton.setBackground(new Color(0,220,0));
        yesButton.setForeground(Color.WHITE);
        yesButton.setPreferredSize(new Dimension(80, 30));
        yesButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        yesButton.addActionListener(e -> {
            System.out.println("Tu as abandonné.");
            ui.changePage(InterfacePage.MENU);
            dialog.dispose(); // dialog closes after clicking on the button
        });

        JButton noButton = new JButton("Non");
        noButton.setBackground(new Color(220,0,0));
        noButton.setForeground(Color.WHITE);
        noButton.setPreferredSize(new Dimension(80, 30));
        noButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        noButton.addActionListener(e -> {
            System.out.println("Tu continues.");
            dialog.dispose(); // dialog closes after clicking on the button
        });

        // Add buttons to a custom panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(yesButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(10,0)));
        buttonPanel.add(noButton);
        panel.add(buttonPanel, BorderLayout.CENTER);

        // Display the dialog
        dialog.add(panel);
        dialog.pack();
        dialog.setLocationRelativeTo(null);
        dialog.setResizable(false);
        dialog.setVisible(true);
    }

}
