package AI;

import AI.AIDifficulty;
import Controller.GameConsoleController;
import Model.Game;
import Model.ResultGame;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.text.SimpleDateFormat;


public class AITraining {
    private static final  int AIGAMES = 50;
    private static final boolean PRINT = false;
    private static final boolean LoadBar = true;
    private static final boolean WRITE_TO_FILE = false;

    public static void main(String[] args) throws IOException {
        System.out.println("Tablut");

        Date date = new Date();

        ResultGame Res;
        double nbVictoryAttacker = 0, nbVictoryDefender = 0, nbMaxTurnEncountered = 0;
        long tpsAverage = 0;
        long start, end;
        String lastGamesStates = "";
        for (int i = 0; i < AIGAMES; i++) {
            Game game = new Game("", "", AIDifficulty.MID, AIDifficulty.MID,100);
            GameConsoleController gcc = new GameConsoleController(game);
            game.setGameControllerInstance(gcc);
            gcc.setPrintTerminal(PRINT);
            start = System.nanoTime();
            Res = gcc.playGame();

            lastGamesStates += "\nAttackers :" + gcc.getGame().getLogicGrid().getNbPieceAttackerOnGrid() + "\n";
            lastGamesStates += "Defenders :" + gcc.getGame().getLogicGrid().getNbPieceDefenderOnGrid();
            lastGamesStates += gcc.getGame().getLogicGrid().toString();

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
                writeToFile(nbVictoryAttacker, nbVictoryDefender, nbMaxTurnEncountered, tpsAverage, date, i);
            }

        }

        System.out.println(lastGamesStates);

        System.out.println("\nResultat Game : " + ((nbVictoryAttacker/AIGAMES)*100) + "% AttackerWin");
        System.out.println("                " + ((nbVictoryDefender/AIGAMES)*100) + "% DefenderWin");
        System.out.println("                " + ((nbMaxTurnEncountered/AIGAMES)*100) + "% > MAX_TURN");
        System.out.println("Total time execution : " + tpsAverage/Math.pow(10,9) + "s");
        System.out.println("Average time execution by game : " + (tpsAverage/AIGAMES)/Math.pow(10,9) + "s");

        if(WRITE_TO_FILE){
            writeToFile(nbVictoryAttacker, nbVictoryDefender, nbMaxTurnEncountered, tpsAverage, date, AIGAMES);
        }

        //affichage de stats et paramètres utilisés

    }

    /**
     * Writes result of tests in files
     *
     * @param nbVictoryAttacker nb of times attackers won
     * @param nbVictoryDefender nb of times defenders won
     * @param nbMaxTurnEncountered nb of games skipped
     * @param tpsAverage average time
     * @param date current date
     * @param nbGames nb of games played
     */
    public static void writeToFile(double nbVictoryAttacker, double nbVictoryDefender, double nbMaxTurnEncountered, double tpsAverage, Date date, int nbGames){
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("dd_MM_yyyy__HH_mm_ss");
            BufferedWriter writer = new BufferedWriter(new FileWriter("ResultatFinalIA"+ formatter.format(date) +".txt"));
            writer.write("Resultat Game : " + ((nbVictoryAttacker/nbGames)*100) + "% AttackerWin\n");
            writer.write("                " + ((nbVictoryDefender/nbGames)*100) + "% DefenderWin\n");
            writer.write("                " + ((nbMaxTurnEncountered/nbGames)*100) + "% > MAX_TURN\n");
            writer.write("Total time execution : " + tpsAverage/Math.pow(10,9) + "s\n");
            writer.write("Average time execution by game : " + (tpsAverage/nbGames)/Math.pow(10,9) + "s\n");
            writer.write("Number of games : " + nbGames + "\n");
            writer.close();
        }catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
