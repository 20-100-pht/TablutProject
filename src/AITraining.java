import Controller.GameConsoleController;
import Model.ResultGame;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.text.SimpleDateFormat;


public class AITraining {
    private static int AIGAMES = 100;
    private static boolean PRINT = false;
    private static boolean AITRAINING = true;
    private static boolean LoadBar = true;

    private static boolean WRITE_TO_FILE = false;

    public static void main(String[] args) throws IOException {
        System.out.println("Tablut");
/**
 * Poids
 * ranger ai, ptere changer des trucs
 * heuristique
 * opti
 *
 */


        //Choix param
        //envoie param aux fonc
        int WAttackerOnTot, WDefenderOnTot, WDistanceKingToCorner;
        //WAttackerOnTot = 10*Math.random() /*10 est val random*/
        //

        SimpleDateFormat formatter = new SimpleDateFormat("dd_MM_yyyy__HH_mm_ss");
        Date date = new Date();

        ResultGame Res;
        double nbVictoryAttacker = 0, nbVictoryDefender = 0, nbMaxTurnEncountered = 0;
        long tpsAverage = 0;
        long start, end;
        for (int i = 0; i < AIGAMES; i++) {
            GameConsoleController gcc = new GameConsoleController();
            gcc.setPrintTerminal(PRINT);
            start = System.nanoTime();
            Res = gcc.playGame();
            end = System.nanoTime();
            switch (Res) {
                case ATTACKER_WIN:
                    nbVictoryAttacker++;
                    break;
                case DEFENDER_WIN:
                    nbVictoryDefender++;
                    break;
                default:
                    nbMaxTurnEncountered++;
                    break;
            }
            tpsAverage += end-start;

            if(LoadBar){
                double progress = (double) i / AIGAMES;
                int barLength = 20;
                int filledLength = (int) (progress * barLength);
                int emptyLength = barLength - filledLength;

                StringBuilder bar = new StringBuilder();
                for (int j = 0; j < filledLength; j++) {
                    bar.append("#");
                }
                for (int j = 0; j < emptyLength; j++) {
                    bar.append(" ");
                }

                System.out.print("\rProgression: [" + bar.toString() + "] " + (int)(progress * 100) + "%");
            }

            if(WRITE_TO_FILE && i%100 == 99 && i != 0){
                BufferedWriter writer = new BufferedWriter(new FileWriter("ResultatIA_"+ i + "Games__" + formatter.format(date) +".txt"));
                writer.write("For " + i + " games :\n\n");
                writer.write("Resultat Game : " + ((nbVictoryAttacker/i)*100) + "% AttackerWin\n");
                writer.write("                " + ((nbVictoryDefender/i)*100) + "% DefenderWin\n");
                writer.write("                " + ((nbMaxTurnEncountered/i)*100) + "% > MAX_TURN\n");
                writer.write("Total time execution : " + tpsAverage/Math.pow(10,9) + "s\n");
                writer.write("Average time execution by game : " + (tpsAverage/i)/Math.pow(10,9) + "s\n");
                writer.write("Number of games : " + i + "\n");
                writer.close();
            }

        }

        System.out.println("\nResultat Game : " + ((nbVictoryAttacker/AIGAMES)*100) + "% AttackerWin");
        System.out.println("                " + ((nbVictoryDefender/AIGAMES)*100) + "% DefenderWin");
        System.out.println("                " + ((nbMaxTurnEncountered/AIGAMES)*100) + "% > MAX_TURN");
        System.out.println("Total time execution : " + tpsAverage/Math.pow(10,9) + "s");
        System.out.println("Average time execution by game : " + (tpsAverage/AIGAMES)/Math.pow(10,9) + "s");

        if(WRITE_TO_FILE){
            BufferedWriter writer = new BufferedWriter(new FileWriter("ResultatFinalIA"+ formatter.format(date) +".txt"));
            writer.write("Resultat Game : " + ((nbVictoryAttacker/AIGAMES)*100) + "% AttackerWin\n");
            writer.write("                " + ((nbVictoryDefender/AIGAMES)*100) + "% DefenderWin\n");
            writer.write("                " + ((nbMaxTurnEncountered/AIGAMES)*100) + "% > MAX_TURN\n");
            writer.write("Total time execution : " + tpsAverage/Math.pow(10,9) + "s\n");
            writer.write("Average time execution by game : " + (tpsAverage/AIGAMES)/Math.pow(10,9) + "s\n");
            writer.write("Number of games : " + AIGAMES + "\n");
            writer.close();
        }

        //affichage de stats et paramètres utilisés
    }
}
