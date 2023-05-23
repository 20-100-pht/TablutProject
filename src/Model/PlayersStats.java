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
        if(!file.exists()) {
            playersStats = new Vector<PlayerStats>();
        }
        else {
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

    public void addPlayer(PlayerStats playerStats){
        playersStats.add(playerStats);
    }

    public PlayerStats getPlayerStats(int index){
        if(index >= playersStats.size()) return null;
        else return playersStats.get(index);
    }

    public PlayerStats getPlayerStatsFromName(String name){
        for(int i = 0; i < playersStats.size(); i++){
            PlayerStats pS = playersStats.get(i);
            if(pS.getName().equals(name)){
                return pS;
            }
        }
        return null;
    }

    public int getNPlayer(){
        return playersStats.size();
    }
}
