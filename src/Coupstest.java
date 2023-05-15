import Model.Game;
import Model.Piece;
import Structure.Coordinate;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public class Coupstest {

    public static void main(String[] args) {
        File file = new File("sdfg");
        int possible=0;
        Coordinate c;
        ArrayList<Coordinate> A = new ArrayList<Coordinate>();
        try {
            FileInputStream is = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(is);

            Game game = (Game) ois.readObject();
            Piece[][] board = game.getLogicGrid().grid.board;
            for(int y = 0; y < board.length; y++){
                for(int x = 0; x < board.length; x++){
                    if(board[y][x]!=null && board[y][x].isAttacker()){
//                        possible += board[y][x].possibleMoves(board).size();
                        System.out.println("rowp " + y + "colp " + x);
                        for (int i =0 ; i<board[y][x].possibleMoves(board).size(); i++){
                            c = board[y][x].possibleMoves(board).get(i);
                            System.out.print("row " + c.getRow());
                            System.out.print("col " + c.getCol());
                            System.out.println("");
                        }
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

