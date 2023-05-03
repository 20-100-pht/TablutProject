package Controller;

import Structure.Coordinate;
import Structure.Coup;

import java.util.Scanner;

public class UserController {
    Scanner scanner;

    UserController(){
        scanner = new Scanner(System.in);
    }

    public Coup getCoupUser(){
        Coup coupPlayer = new Coup(new Coordinate(0,0), new Coordinate(0,0));

        System.out.println("Coordonnées de la pièce que vous souhaitez déplacer (ligne colonne) : ");
        coupPlayer.getInit().setRowCoord(scanner.nextInt());
        coupPlayer.getInit().setColCoord(scanner.nextInt());


        System.out.println("Coordonnées de la case où vous souhaitez déplacer la pièce (ligne colonne) : ");
        coupPlayer.getDest().setRowCoord(scanner.nextInt());
        coupPlayer.getDest().setColCoord(scanner.nextInt());

        return coupPlayer;
    }
}
