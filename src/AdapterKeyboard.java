import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class AdapterKeyboard extends KeyAdapter {

    Interface ui;

    AdapterKeyboard(Interface ui) {
        this.ui = ui;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        System.out.println("Key pressed");
    }
}
