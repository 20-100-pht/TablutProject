import Controller.GameConsoleController;
import Model.ResultGame;

public class AITraining {
    private static int AIGAMES = 1;
    private static boolean AITRAINING = true;
    private static boolean LoadBar = false;

    public static void main(String[] args) {
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

        ResultGame Res;
        double nbVictoryAttacker = 0, nbVictoryDefender = 0, nbMaxTurnEncountered = 0;
        long tpsAverage = 0;
        long start, end;
        for (int i = 0; i < AIGAMES; i++) {
            GameConsoleController gcc = new GameConsoleController();
            gcc.setPrintTerminal(true);
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

        }

        System.out.println("\nResultat Game : " + ((nbVictoryAttacker/AIGAMES)*100) + "% AttackerWin");
        System.out.println("                " + ((nbVictoryDefender/AIGAMES)*100) + "% DefenderWin");
        System.out.println("                " + ((nbMaxTurnEncountered/AIGAMES)*100) + "% > MAX_TURN");
        System.out.println("Total time execution : " + tpsAverage/Math.pow(10,9) + "s");
        System.out.println("Average time execution by game : " + (tpsAverage/AIGAMES)/Math.pow(10,9) + "s");
        //affichage de stats et paramètres utilisés
    }
}
