package Controller;

import Global.Configuration;
import Structure.Coordinate;
import Structure.Coup;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Stack;

public class CoupHumanList {


    Stack<Coup> coup;

    CoupHumanList() {
        coup = initCoup();
    }

    private Stack<Coup> initCoup() {
        String filePath = Configuration.getFileListName();
        Stack<Coup> coups = new Stack<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Traitement de chaque ligne
                String[] coordinates = line.split(" -> ");
                String[] coord1 = coordinates[0].split(":");
                String[] coord2 = coordinates[1].split(":");

                Coordinate init = new Coordinate(Integer.parseInt(coord1[0]), Integer.parseInt(coord1[1]));
                Coordinate dest = new Coordinate(Integer.parseInt(coord2[0]), Integer.parseInt(coord2[1]));
                Coup coup = new Coup(init, dest);
                coups.add(coup);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return reverseStack(coups);
    }

    private Stack<Coup> reverseStack(Stack<Coup> coup){
        int size = coup.size();
        Stack<Coup> clone = new Stack<>();
        for(int i = 0; i < size; i++) {
            clone.add(coup.pop());
        }
        return clone;
    }

    public Coup getCoup(){
        if (coup.isEmpty()){
            System.out.println("Fin de la sequence de coup prédéfinis\n");
            System.exit(0);
        }

        return coup.pop();
    }
}
