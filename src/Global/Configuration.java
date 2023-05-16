package Global;

import java.io.*;

public class Configuration implements Serializable {
    static Configuration instance = null;
    static boolean coupHumanByList = false;
    static int themeIndex = 1;
    static boolean animationActived = true;
    final static String FILE_NAME = "config.cfg";

    final static String TEST_FILE_PATH = "src/Test/LoadGameTestObject/";
    final static String FILE_LIST_NAME = TEST_FILE_PATH + "KillAttacker.txt";
    final static String FILE_LOAD_NAME = TEST_FILE_PATH + "KillAttacker";

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

    public static void setCoupHumanByList(boolean b){coupHumanByList = b;}

    public static String getFileListName(){return FILE_LIST_NAME;}

    public static String getFileLoadName(){return FILE_LOAD_NAME;}

    public static boolean isListCoupHumanGame(){return coupHumanByList;}

    public static void setAnimationActived(boolean isAnimationActived){
        instance.animationActived = isAnimationActived;
    }
}
