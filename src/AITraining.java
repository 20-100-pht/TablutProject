import Controller.GameConsoleController;
import Model.ResultGame;

public class AITraining {
    private static int AIGAMES = 20;
    private static boolean AITRAINING = true;

    public static void main(String[] args) {
        System.out.println("Tablut");

        //Choix param
        //envoie param aux fonc
        ResultGame Res;
        double nbVictoryAttacker = 0, nbVictoryDefender = 0, nbMaxTurnEncountered = 0;
        long tpsAverage = 0;
        long start, end;
        for (int i = 0; i < AIGAMES; i++) {
            GameConsoleController gcc = new GameConsoleController();
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

        }
        System.out.println("Resultat Game : " + ((nbVictoryAttacker/AIGAMES)*100) + "% AttackerWin");
        System.out.println("                " + ((nbVictoryDefender/AIGAMES)*100) + "% DefenderWin");
        System.out.println("                " + ((nbMaxTurnEncountered/AIGAMES)*100) + "% Encountered");
        System.out.println("Total time execution : " + tpsAverage/Math.pow(10,9) + "s");
        System.out.println("Average time execution by game : " + (tpsAverage/AIGAMES)/Math.pow(10,9) + "s");
        //affichage de stats et paramètres utilisés
    }
}
