import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class AdapterMouse extends MouseAdapter {

    Interface ui;

    AdapterMouse(Interface ui) {
        this.ui = ui;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        System.out.println("Mouse pressed");
    }

}
