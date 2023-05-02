import java.io.*;
import java.util.Scanner;

public class Game {
    boolean attacker;

    boolean defenderAI;
    boolean attackerAI;
    boolean isFirstMove = true;

    int depth = 3;

    AIRandom aleatronDefender;
    AIRandom aleatronAttacker;

    Scanner scanner = new Scanner(System.in);
    GameController gameController;


    public Game(){
        reset();
    }

    public void reset (){
        attacker = true;
        defenderAI = true;
        attackerAI = true;
        gameController = new GameController();
        aleatronDefender = new AIRandom(gameController, PieceType.DEFENDER);
        aleatronAttacker = new AIRandom(gameController, PieceType.ATTACKER);
    }

    public void playGame(){
        while(!gameController.isEndGame()){
            playTurn();
        }
    }

    public void endGame(ResultGame result){
        if(result == ResultGame.ATTACKER_WIN) System.out.println("Attacker win !");
        else System.out.println("Defender win !");
        gameController.print();
    }

    public void playTurn(){
        gameController.print();

        if(attacker) playTurnAttacker();
        else playTurnDefender();

        attacker = !attacker;
        
    }

    public Piece movePlayer(){

        Piece current = null;

        Coup coupPlayer = new Coup(new Coordinate(0,0), new Coordinate(0,0));

        while (current == null) {
            System.out.println("Coordonnées de la pièce que vous souhaitez déplacer (ligne colonne) : ");
            coupPlayer.init.setRow(scanner.nextInt());
            coupPlayer.init.setCol(scanner.nextInt());


            System.out.println("Coordonnées de la case où vous souhaitez déplacer la pièce (ligne colonne) : ");
            coupPlayer.dest.setRow(scanner.nextInt());
            coupPlayer.dest.setCol(scanner.nextInt());

            ReturnValue returnValue = gameController.move(coupPlayer);
            current = returnValue.getPiece();

            if(current == null){
                switch(returnValue.getValue()){
                    case 1:
                        System.out.println("Coordonnées invalides (en dehors de la grille), veuillez saisir à nouveau.");
                        break;

                    case 2:
                        System.out.println("Coordonnées invalides (pas un pion), veuillez saisir à nouveau.");
                        break;

                    case 3:
                        System.out.println("Case de destination invalide, veuillez saisir à nouveau.");
                        break;
                    case 4:
                        System.out.println("Case de destination invalide (château), veuillez saisir à nouveau.");
                        break;
                    case 5:
                        System.out.println("Case de destination invalide (même position), veuillez saisir à nouveau.");
                        break;
                    case 6:
                        System.out.println("La pièce sélectionnée ne peut pas se déplacer sur la case de destination, veuillez saisir à nouveau.");
                        break;
                    default:
                        System.out.println("Erreur valeur de retour");
                        break;
                }
            }


            if (current != null && ( (current.isAttacker() && !attacker) || (current.isDefenderOrKing() && attacker) ) ){
                System.out.println("Ce pion n'est pas le votre !");
                current = null;
            }

        }

        return current;
    }

    public void playTurnDefender(){
        System.out.println("Défenseur, à vous de jouer !");


        Piece current = null;

        if(defenderAI){
            while(current == null) {
                /*Coup coupAI = aleatronDefender.playMove();
                ReturnValue returnValue = gameController.move(coupAI);
                current = returnValue.getPiece();*/

                MyAI ai = new MyAI();
                Node aiNode = ai.minimax(gameController.grid.board,gameController.king.c,depth,PieceType.DEFENDER);
                System.out.println("Defender AI heuristic :" + aiNode.move.heuristic);
                ReturnValue returnValue = gameController.move(aiNode.move);
                current = returnValue.getPiece();

            }
        }
        else {
            current = movePlayer();
        }

        int kill = gameController.attack(current);
        System.out.println("nb kill : " + kill);

        if(gameController.isDefenderWinConfiguration()) endGame(ResultGame.DEFENDER_WIN);
    }

    public void playTurnAttacker(){
        System.out.println("Attaquant, à vous de jouer !");

        Piece current = null;

        if(attackerAI){
            while(current == null) {


                /*Node aiNode = aiAttacker.minimax(gameController.grid.cloneGrid(),gameController.king.c,3,PieceType.ATTACKER);
                Coup coupAI = aiNode.move;
                System.out.println("heuristic :" + coupAI.heuristic);*/

                if(isFirstMove){
                    Coup coupAI = aleatronAttacker.playMove();
                    ReturnValue returnValue = gameController.move(coupAI);
                    current = returnValue.getPiece();
                    isFirstMove = false;
                }else{
                    MyAI ai = new MyAI();
                    Node aiNode = ai.minimax(gameController.grid.board,gameController.king.c,depth,PieceType.ATTACKER);
                    System.out.println("Attacker AI heuristic :" + aiNode.move.heuristic);
                    ReturnValue returnValue = gameController.move(aiNode.move);
                    current = returnValue.getPiece();
                }





                //System.exit(0);


            }
        }
        else {
            current = movePlayer();
        }

        //Check if the king has been captured while on or next to the throne
        gameController.capture();

        int kill = gameController.attack(current);
        System.out.println("nb kill : " + kill);


        if(gameController.isAttackerWinConfiguration()) endGame(ResultGame.ATTACKER_WIN);
    }


    public void loadFromFile(String filePath){
        try {
            FileInputStream fis = new FileInputStream(filePath);
            ObjectInputStream ois = new ObjectInputStream(fis);

            gameController = (GameController) ois.readObject();

            ois.close();

        } catch (IOException e) {
            System.out.println(e.toString());
        } catch (ClassNotFoundException e){
            System.out.println(e.toString());
        }
    }

    public void saveInFile(String filePath){
        try {
            FileOutputStream fos = new FileOutputStream(filePath);
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            oos.writeObject(gameController);

            oos.close();

        } catch(IOException e) {
            System.out.println(e.toString());
        }
    }

}