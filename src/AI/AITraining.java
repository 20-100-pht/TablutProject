package AI;

import AI.AIDifficulty;
import Controller.GameConsoleController;
import Model.Game;
import Model.PieceType;
import Model.ResultGame;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;


public class AITraining {
    private static final int AIGAMES = 100;
    private static final int NB_EXPERIENCES = 1;
    private static final boolean PRINT = false;
    private static final boolean PRINT_LAST_BOARD = false;
    private static final boolean LoadBar = true;
    private static final boolean RANDOMIZE_WEIGHT = false;
    private static final boolean WRITE_TO_FILE = false;
    private static final PieceType AiTested = PieceType.ATTACKER;
    private static final AIDifficulty AiAttack = AIDifficulty.HARD;
    private static final AIDifficulty AiDef = AIDifficulty.HARD;

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
        String fileName = "src/Test/OutputAI/" + AiTested + "_" + formatter.format(date) + ".txt";

        if(LoadBar){
            System.out.print("\rProgression Totale: [                    ] " + 0 + "%");
            System.out.print(" - Progression: [                    ] " + 0 + "%");
        }

        for (int j = 0; j < NB_EXPERIENCES; j++) {

            float nbAttWin = 0;
            float nbDefWin = 0;
            float nbMaxTurn = 0;
            float nbAtt = 0;
            float nbDef = 0;

            for (int i = 0; i < AIGAMES; i++) {

                //setWeights();

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
                        nbAttWin++;
                        break;
                    case DEFENDER_WIN:
                        nbVictoryDefender++;
                        nbDefWin++;
                        break;
                    default:
                        nbMaxTurnEncountered++;
                        nbMaxTurn++;
                        break;
                }
                tpsAverage += end - start;
                turnTotal += game.getTurnIndex();

                nbAtt+= game.getLogicGrid().getNbPieceAttackerOnGrid();
                nbDef+= game.getLogicGrid().getNbPieceDefenderOnGrid();

                if (LoadBar) {
                    double progress = (double) (i + (j*AIGAMES)) / (AIGAMES * NB_EXPERIENCES);
                    double progressExp = (double) i / (AIGAMES+1);

                    StringBuilder loadBarTotal = createLoadBar(progress);
                    StringBuilder loadBarExp = createLoadBar(progressExp);
                    System.out.print("\rProgression Totale: [" + loadBarTotal.toString() + "] " + (int) (progress * 100) + "%");
                    System.out.print(" - Progression: [" + loadBarExp.toString() + "] " + (int) (progressExp * 100) + "%");
                }

                if(PRINT_LAST_BOARD){
                    game.getLogicGrid().print();
                }
            }

            String weights = getWeights();
            if(RANDOMIZE_WEIGHT) randomizeWeights();

            if (WRITE_TO_FILE) {
                try {
                    writer = new BufferedWriter(new FileWriter(fileName,true));
                    writer.write("\n\n");
                    writer.write("Résultats de l'expérience n°" + j + " :\n");
                    writer.write(((nbAttWin / (AIGAMES)) * 100) + "% AttackerWin\n");
                    writer.write(((nbDefWin / (AIGAMES)) * 100) + "% DefenderWin\n");
                    writer.write(((nbMaxTurn / (AIGAMES)) * 100) + "% > MAX_TURN\n");
                    writer.write("Temps d'éxécution: " + tpsAverage / Math.pow(10, 9) + "s\n");
                    writer.write("Average turns : " + turnTotal / (AIGAMES) + "turns\n");
                    writer.write("Attackers : " + nbAtt / (AIGAMES) + "\n");
                    writer.write("Defenders : " + nbDef / (AIGAMES) + "\n");
                    writer.write(weights);
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            tpsTotal += tpsAverage;
            tpsAverage = 0;
            attackers += nbAtt;
            defenders += nbDef;
            turnTotal = 0;

        }

        if (WRITE_TO_FILE) {
            try {
                writer = new BufferedWriter(new FileWriter(fileName,true));
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

    private static void setWeights(){
        AIConfig.setCircleStrat_A(7);
        AIConfig.setPieceRatio_A(70);
        AIConfig.setNextToKing_A(51);
        AIConfig.setKingToCorner_A(82);
    }



}