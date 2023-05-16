package AI;

import AI.AIDifficulty;
import Controller.GameConsoleController;
import Model.Game;
import Model.PieceType;
import Model.ResultGame;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class AITraining {
    private static final int AIGAMES = 10;
    private static final int NB_EXPERIENCES = 2;
    private static final boolean PRINT = false;
    private static final boolean LoadBar = true;
    private static final boolean WRITE_TO_FILE = true;
    private static final PieceType AiTested = PieceType.ATTACKER;
    private static final AIDifficulty AiAttack = AIDifficulty.MID;
    private static final AIDifficulty AiDef = AIDifficulty.RANDOM;

    public static void main(String[] args) throws IOException {
        System.out.println("Tablut");

        BufferedWriter writer = null;
        Date date = new Date();

        ResultGame Res;
        double nbVictoryAttacker = 0, nbVictoryDefender = 0, nbMaxTurnEncountered = 0;
        long tpsAverage = 0, tpsTotal = 0;
        long start, end;

        if (WRITE_TO_FILE) {
            try {
                SimpleDateFormat formatter = new SimpleDateFormat("dd_HH_mm_ss");
                writer = new BufferedWriter(new FileWriter( "Res" + AiTested + "_" + formatter.format(date) + ".txt"));

                writer.write("Résultats des expériences sur l'IA ATTACKER de difficulté " + AiAttack + " vs l'IA DEFENDER de difficulté" + AiDef + "\n");//
                writer.write("Paramètres : Nombre d'expériences = " + NB_EXPERIENCES + ", nombre de parties par expérience = " + AIGAMES + "\n");
                writer.write("On teste l'IA " + AiTested + "\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        for (int j = 0; j < NB_EXPERIENCES; j++) {
            for (int i = 0; i < AIGAMES; i++) {
                if (LoadBar) {
                    double progress = (double) (i * j + 1) / (AIGAMES * NB_EXPERIENCES);
                    int barLength = 20;
                    int filledLength = (int) (progress * barLength);
                    int emptyLength = barLength - filledLength;

                    StringBuilder bar = new StringBuilder();
                    for (int b = 0; b < filledLength; b++) {
                        bar.append("#");
                    }
                    for (int b = 0; b < emptyLength; b++) {
                        bar.append(" ");
                    }

                    System.out.print("\rProgression: [" + bar.toString() + "] " + (int) (progress * 100) + "%");
                }

                Game game = new Game("", "", AiDef, AiAttack, 100);

                GameConsoleController gcc = new GameConsoleController(game);
                game.setGameControllerInstance(gcc);
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
                tpsAverage += end - start;
            }
            if (WRITE_TO_FILE) {
                try {
                    writer.write("");
                    writer.write("Résultats de l'expérience n°" + j + " :\n");
                    if (AiTested == PieceType.ATTACKER) {
                        writer.write("                " + ((nbVictoryAttacker / AIGAMES) * 100) + "% AttackerWin\n");
                    } else {
                        writer.write("                " + ((nbVictoryDefender / AIGAMES) * 100) + "% DefenderWin\n");
                    }
                    writer.write("                " + ((nbMaxTurnEncountered / AIGAMES) * 100) + "% > MAX_TURN\n");
                    writer.write("                 Temps d'éxécution: " + tpsAverage / Math.pow(10, 9) + "s\n");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            tpsTotal += tpsAverage;
            tpsAverage = 0;

        }

        if (WRITE_TO_FILE) {
            try {
                writer.write("\n");
                writer.write("Total time execution : " + tpsTotal / Math.pow(10, 9) + "s");
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("\nResultat Game : " + ((nbVictoryAttacker / AIGAMES) * 100) + "% AttackerWin");
            System.out.println("                " + ((nbVictoryDefender / AIGAMES) * 100) + "% DefenderWin");
            System.out.println("                " + ((nbMaxTurnEncountered / AIGAMES) * 100) + "% > MAX_TURN");
            System.out.println("Total time execution : " + tpsTotal / Math.pow(10, 9) + "s");
            System.out.println("Average time execution by game : " + (tpsTotal / AIGAMES) / Math.pow(10, 9) + "s");

        }
    }

}
