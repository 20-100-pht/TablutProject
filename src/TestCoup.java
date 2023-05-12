import Controller.GameConsoleController;
import Model.Game;
import Model.Piece;
import View.Interface;
import View.InterfacePage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class TestCoup {

    public static void main(String[] args) {
        File file = new File("scratch");
        int possible=0;
        try {
            FileInputStream is = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(is);

            Game game = (Game) ois.readObject();
            Piece[][] board = game.getLogicGrid().grid.board;
            for(int y = 0; y < board.length; y++){
                for(int x = 0; x < board.length; x++){
                    if(board[y][x]!=null && board[y][x].isDefender()){
                            possible += board[y][x].possibleMoves(board).size();
                        }
                    }
                }


            ois.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println(possible);
    }
}
