package View;

import Controller.MenuController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import static java.awt.Font.BOLD;

public class MenuFrame extends Frame {

    MenuController menuController;
    Button bttnNewGame;
    Button bttnLoadGame;
    Button bttnStatistics;
    Button bttnOption;

    public MenuFrame(Interface ui){
        super(ui);

        menuController = new MenuController(this);

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
        c.ipady = (int) ((double) window.getHeight()*0.15);
        gLayout.setConstraints(space, c);
        this.add(space);

        c.ipady = 0;
        c.insets = new Insets(20, 20, 20, 20);

        bttnNewGame = new Button("Nouvelle partie", true, this);
        //c.fill = GridBagConstraints.NONE;
        bttnNewGame.setBorder(new ButtonRoundBorder(10));
        c.gridx = 1;
        c.gridy = 2;
        bttnNewGame.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        gLayout.setConstraints(bttnNewGame, c);
        this.add(bttnNewGame);

        bttnLoadGame = new Button("Charger une partie", true, this);
        //c.fill = GridBagConstraints.NONE;
        bttnLoadGame.setBorder(new ButtonRoundBorder(10));
        c.gridx = 1;
        c.gridy = 3;
        bttnLoadGame.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        gLayout.setConstraints(bttnLoadGame, c);
        this.add(bttnLoadGame);

        bttnStatistics = new Button("Statistiques", true, this);
        // c.fill = GridBagConstraints.NONE;
        bttnStatistics.setBorder(new ButtonRoundBorder(10));
        c.gridx = 1;
        c.gridy = 4;
        bttnStatistics.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        gLayout.setConstraints(bttnStatistics, c);
        this.add(bttnStatistics);

        bttnOption = new Button("Options", true, this);
        //c.fill = GridBagConstraints.NONE;
        bttnOption.setBorder(new ButtonRoundBorder(10));
        c.gridx = 1;
        c.gridy = 5;
        bttnOption.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        gLayout.setConstraints(bttnOption, c);
        this.add(bttnOption);

        setButtonHandlers();

        this.setLayout(gLayout);
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
        int height = (int) (sizeScreen.height * 0.6);
        int width = (int) (sizeScreen.width * 0.4);
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
    }

    public void loadAssets(){

    }

    public File showLoadDialog(){
        File file = null;
        JFileChooser fileChooser = new JFileChooser();
        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            file = fileChooser.getSelectedFile();
        }
        return file;
    }
}
/**import java.awt.BorderLayout;
 import java.awt.Dimension;
 import java.awt.GridBagConstraints;
 import java.awt.GridBagLayout;
 import java.awt.Insets;
 import java.awt.event.ActionEvent;
 import java.awt.event.ActionListener;
 import javax.swing.Box;
 import javax.swing.JButton;
 import javax.swing.JFrame;
 import javax.swing.JLabel;
 import javax.swing.JPanel;

 public class TablutMenu extends JFrame implements ActionListener {
 private static final long serialVersionUID = 1L;

 public TablutMenu() {
 super("Controller.Menu principal");

 // Créer les boutons pour chaque option du menu
 JButton newGameButton = new JButton("Nouvelle partie");
 JButton loadGameButton = new JButton("Charger partie");
 JButton statsButton = new JButton("Statistiques");
 JButton optionsButton = new JButton("Options");

 // Ajouter un écouteur d'événements pour chaque bouton
 newGameButton.addActionListener(this);
 loadGameButton.addActionListener(this);
 statsButton.addActionListener(this);
 optionsButton.addActionListener(this);

 // Créer le panneau pour les boutons du menu
 JPanel menuPanel = new JPanel(new GridBagLayout());
 GridBagConstraints c = new GridBagConstraints();
 c.insets = new Insets(5, 0, 5, 0);
 c.gridx = 0;
 c.gridy = 0;
 menuPanel.add(Box.createRigidArea(new Dimension(0, 10))); // espace vide en haut
 menuPanel.add(newGameButton, c);
 c.gridy++;
 menuPanel.add(Box.createRigidArea(new Dimension(0, 10))); // espace vide entre les boutons
 menuPanel.add(loadGameButton, c);
 c.gridy++;
 menuPanel.add(Box.createRigidArea(new Dimension(0, 10))); // espace vide entre les boutons
 menuPanel.add(statsButton, c);
 c.gridy++;
 menuPanel.add(Box.createRigidArea(new Dimension(0, 10))); // espace vide entre les boutons
 menuPanel.add(optionsButton, c);
 menuPanel.add(Box.createRigidArea(new Dimension(0, 10))); // espace vide en bas

 // Créer le panneau pour le titre du jeu
 JPanel titlePanel = new JPanel();
 titlePanel.add(new JLabel("Tablut"));

 // Ajouter les panneaux au cadre principal
 add(titlePanel, BorderLayout.NORTH);
 add(menuPanel, BorderLayout.CENTER);

 // Configurer le cadre principal
 setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 pack();
 setLocationRelativeTo(null);
 setVisible(true);
 }

 @Override
 public void actionPerformed(ActionEvent e) {
 String command = e.getActionCommand();

 switch (command) {
 case "Nouvelle partie":
 startNewGame();
 break;
 case "Charger partie":
 loadSavedGame();
 break;
 case "Statistiques":
 displayStats();
 break;
 case "Options":
 showOptions();
 break;
 }
 }

 private void startNewGame() {
 // Code pour commencer une nouvelle partie
 System.out.println("Nouvelle partie démarrée.");
 }

 private void loadSavedGame() {
 // Code pour charger une partie sauvegardée
 System.out.println("Partie chargée.");
 }

 private void displayStats() {
 // Code pour afficher les statistiques
 System.out.println("Statistiques affichées.");
 }

 private void showOptions() {
 // Code pour afficher les options
 System.out.println("Options affichées.");
 }

 public static void main(String[] args) {
 TablutMenu menu = new TablutMenu();
 }
 }

**/
