import AI.AIConfig;
import Structure.StructResultat;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RecupResultat {

    static String PATH_FILE = "src/Test/OutputAI/";
    static int nbResult;
    public static void main(String[] args) {
        String inputFilePath = PATH_FILE + "ATTACKER_17_16_33_38.txt";

        try {
            File inputFile = new File(inputFilePath);
            Scanner scanner = new Scanner(inputFile);

            nbResult = 0;
            List<StructResultat> list = new ArrayList<>();

            recovery(scanner, list);

            List<StructResultat> listAux = new ArrayList<>();
            Double max = (double) 0;
            StructResultat current;
            for(int i = 0; i < nbResult; i++) {
                current = list.get(i);
                if (current.AttackerWin == max){
                    listAux.add(list.get(i));
                }
                if(current.AttackerWin > max){
                    max = current.AttackerWin;
                    listAux.clear();
                    listAux.add(list.get(i));
                }
            }

            for (StructResultat currentMax:listAux) {
                printStructResultat(currentMax);
            }

            scanner.close();
            System.out.println("Le traitement des résultats est terminé.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void recovery(Scanner scanner, List<StructResultat> list){
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            StructResultat current = new StructResultat();

            if (line.startsWith("Résultats de l'expérience") && scanner.hasNextLine()) {
                current.AttackerWin = patternMatcherDouble(scanner);
                current.DefenderWin = patternMatcherDouble(scanner);
                current.MaxTrun = patternMatcherDouble(scanner);
                current.TempsExec = patternMatcherDouble(scanner);
                current.AverageTurns = patternMatcherInt(scanner);
                current.Attackers = patternMatcherDouble(scanner);
                current.Defenders = patternMatcherDouble(scanner);
                current.CircleStrategy = patternMatcherDouble(scanner);
                current.PieceRatio = patternMatcherDouble(scanner);
                current.NextToKing = patternMatcherDouble(scanner);
                current.KingToCorner = patternMatcherDouble(scanner);

                list.add(current);
                nbResult += 1;
            }
        }
    }

    private static void printStructResultat(StructResultat res){
        System.out.print("\n\n");
        System.out.print("Résultats :\n");
        System.out.print(res.AttackerWin + "% AttackerWin\n");
        System.out.print(res.DefenderWin + "% DefenderWin\n");
        System.out.print(res.MaxTrun + "% > MAX_TURN\n");
        System.out.print("Temps d'éxécution: " + res.TempsExec + "s\n");
        System.out.print("Average turns : " + res.AverageTurns + "turns\n");
        System.out.print("Attackers : " + res.Attackers + "\n");
        System.out.print("Defenders : " + res.Defenders + "\n");
        System.out.print("Circle Strategy : " + res.CircleStrategy + "\n");
        System.out.print("Piece Ratio : " + res.PieceRatio + "\n");
        System.out.print("Next to King : " + res.NextToKing +"\n");
        System.out.print("King to corner : " + res.KingToCorner + "\n");
    }

    private static Double patternMatcherInt(Scanner scanner) {

        String line = scanner.nextLine();

        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(line);

        if (matcher.find()) {
            String percentageString = matcher.group();
            return (double) Integer.parseInt(percentageString);
        }
        return (double) -1;
    }

    private static Double patternMatcherDouble(Scanner scanner) {

        String line = scanner.nextLine();

        Pattern pattern = Pattern.compile("\\d+\\.\\d+");
        Matcher matcher = pattern.matcher(line);

        if (matcher.find()) {
            String percentageString = matcher.group();
            return Double.parseDouble(percentageString);
        }
        return (double) -1;
    }
}