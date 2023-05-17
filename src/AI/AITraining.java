package AI;

import AI.AIDifficulty;
import Controller.GameConsoleController;
import Model.Game;
import Model.PieceType;
import Model.ResultGame;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;


public class AITraining {
    private static final int AIGAMES = 5;
    private static final int NB_EXPERIENCES = 40;
    private static final boolean PRINT = false;
    private static final boolean LoadBar = true;
    private static final boolean WRITE_TO_FILE = true;
    private static final PieceType AiTested = PieceType.ATTACKER;
    private static final AIDifficulty AiAttack = AIDifficulty.MID;
    private static final AIDifficulty AiDef = AIDifficulty.MID;

    public static void main(String[] args) throws IOException {
        System.out.println("Tablut");

        BufferedWriter writer = null;
        Date date = new Date();

        ResultGame Res;
        double nbVictoryAttacker = 0, nbVictoryDefender = 0, nbMaxTurnEncountered = 0;
        long tpsAverage = 0, tpsTotal = 0;
        long turnTotal = 0;
        int attackers = 0, defenders = 0;
        long start, end;

        SimpleDateFormat formatter = new SimpleDateFormat("dd_HH_mm_ss");
        String fileName = "Res" + AiTested + "_" + formatter.format(date) + ".txt";

        /*if (WRITE_TO_FILE) {
            try {

                writer = new BufferedWriter(new FileWriter(fileName));

                writer.write("Résultats des expériences sur l'IA ATTACKER de difficulté " + AiAttack + " vs l'IA DEFENDER de difficulté" + AiDef + "\n");//
                writer.write("Paramètres : Nombre d'expériences = " + NB_EXPERIENCES + ", nombre de parties par expérience = " + AIGAMES + "\n");
                writer.write("On teste l'IA " + AiTested + "\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }*/

        if(LoadBar){
            System.out.print("\rProgression Totale: [                    ] " + 0 + "%");
            System.out.print(" - Progression: [                    ] " + 0 + "%");
        }

        for (int j = 0; j < NB_EXPERIENCES; j++) {
            for (int i = 0; i < AIGAMES; i++) {

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
                turnTotal += game.getTurnIndex();

                attackers+= game.getLogicGrid().getNbPieceAttackerOnGrid();
                defenders+= game.getLogicGrid().getNbPieceDefenderOnGrid();

                if (LoadBar) {
                    double progress = (double) (i + (j*AIGAMES)) / (AIGAMES * NB_EXPERIENCES);
                    double progressExp = (double) i / (AIGAMES+1);

                    StringBuilder loadBarTotal = createLoadBar(progress);
                    StringBuilder loadBarExp = createLoadBar(progressExp);
                    System.out.print("\rProgression Totale: [" + loadBarTotal.toString() + "] " + (int) (progress * 100) + "%");
                    System.out.print(" - Progression: [" + loadBarExp.toString() + "] " + (int) (progressExp * 100) + "%");
                }
            }

            String weights = getWeights();
            randomizeWeights();

            if (WRITE_TO_FILE) {
                try {
                    writer = new BufferedWriter(new FileWriter(fileName,true));
                    writer.write("\n\n");
                    writer.write("Résultats de l'expérience n°" + j + " :\n");
                    writer.write(((nbVictoryAttacker / (AIGAMES * NB_EXPERIENCES)) * 100) + "% AttackerWin\n");
                    writer.write(((nbVictoryDefender / (AIGAMES * NB_EXPERIENCES)) * 100) + "% DefenderWin\n");
                    writer.write(((nbMaxTurnEncountered / (AIGAMES * NB_EXPERIENCES)) * 100) + "% > MAX_TURN\n");
                    writer.write("Temps d'éxécution: " + tpsAverage / Math.pow(10, 9) + "s\n");
                    writer.write("Average turns : " + turnTotal / (AIGAMES* NB_EXPERIENCES) + "turns\n");
                    writer.write("Attackers : " + attackers / (AIGAMES* NB_EXPERIENCES) + "\n");
                    writer.write("Defenders : " + defenders / (AIGAMES* NB_EXPERIENCES) + "\n");
                    writer.write(weights);
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            tpsTotal += tpsAverage;
            tpsAverage = 0;

        }

        if (WRITE_TO_FILE) {
            try {
                writer = new BufferedWriter(new FileWriter(fileName));
                writer.write("\n");
                writer.write("Total time execution : " + tpsTotal / Math.pow(10, 9) + "s");
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("\nResultat Game : " + ((nbVictoryAttacker / (AIGAMES * NB_EXPERIENCES)) * 100) + "% AttackerWin");
            System.out.println("                " + ((nbVictoryDefender / (AIGAMES* NB_EXPERIENCES)) * 100) + "% DefenderWin");
            System.out.println("                " + ((nbMaxTurnEncountered / (AIGAMES* NB_EXPERIENCES)) * 100) + "% > MAX_TURN");
            System.out.println("Average turns : " + turnTotal / (AIGAMES* NB_EXPERIENCES) + "turns");
            System.out.println("Total time execution : " + tpsTotal / Math.pow(10, 9) + "s");
            System.out.println("Average time execution by game : " + (tpsTotal / (AIGAMES* NB_EXPERIENCES)) / Math.pow(10, 9) + "s");

        }

    }

    private static StringBuilder createLoadBar(double progress){
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

        return bar;
    }

    private static void randomizeWeights(){

        Random r = new Random();

        AIConfig.setCircleStrat_A(r.nextInt(101));
        AIConfig.setPieceRatio_A(r.nextInt(101));
        AIConfig.setNextToKing_A(r.nextInt(101));
        AIConfig.setKingToCorner_A(r.nextInt(101));
    }

    private static String getWeights(){

        String weights = "";

        weights += "Circle Strategy : ";
        weights += AIConfig.getCircleStrat_A();
        weights += "\nPiece Ratio : ";
        weights += AIConfig.getPieceRatio_A();
        weights += "\nNext to King : ";
        weights += AIConfig.getNextToKing_A();
        weights += "\nKing to corner : ";
        weights += AIConfig.getKingToCorner_A();
        weights += "\n";

        return weights;
    }



}
