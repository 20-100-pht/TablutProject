package Global;

import Model.Game;
import View.Interface;
import View.InterfacePage;

import java.io.*;

public class Configuration implements Serializable {
    static Configuration instance = null;
    static int themeIndex = 1;
    static boolean animationActived = true;
    final static String FILE_NAME = "config.cfg";

    public static Configuration instance() {
        if (instance == null)
            instance = new Configuration();
        return instance;
    }

    public Configuration(){
        File f = new File(FILE_NAME);
        if(f.isFile()){
            load();
        }
    }

    public static void save(){
        File saveFile = new File(FILE_NAME);
        try {
            FileOutputStream fos = new FileOutputStream(saveFile);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(themeIndex);
            oos.writeObject(animationActived);

            oos.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void load(){
        File loadFile = new File(FILE_NAME);
        try {
            FileInputStream is = new FileInputStream(loadFile);
            ObjectInputStream ois = new ObjectInputStream(is);

            themeIndex = (int) ois.readObject();
            animationActived = (boolean) ois.readObject();

            ois.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static int getThemeIndex(){
        return instance.themeIndex;
    }

    public static void setThemeIndex(int themeIndex){
        instance.themeIndex = themeIndex;
    }

    public static boolean isAnimationActived(){
        return instance.animationActived;
    }

    public static void setAnimationActived(boolean isAnimationActived){
        instance.animationActived = isAnimationActived;
    }
}
