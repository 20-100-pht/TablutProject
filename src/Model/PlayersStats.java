package Model;

import View.Interface;
import View.InterfacePage;

import java.io.*;
import java.util.Vector;

public class PlayersStats {

    final String FILE_NAME = "stats.txt";

    Vector<PlayerStats> playersStats;

    public PlayersStats(){
        File file = new File("assets/"+FILE_NAME);
        try {
            FileInputStream is = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(is);

            playersStats = (Vector<PlayerStats>) ois.readObject();

            ois.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void save(){
        File file = new File("assets/"+FILE_NAME);
        try {
            FileOutputStream os = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(os);

            oos.writeObject(playersStats);

            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
