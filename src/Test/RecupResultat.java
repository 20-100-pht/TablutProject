import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RecupResultat {

    private static int MAX_RESULT = 10000;
    static int nbResult;
    public static void main(String[] args) {
        String inputFilePath = ".txt";

        try {
            File inputFile = new File(inputFilePath);
            Scanner scanner = new Scanner(inputFile);

            nbResult = 0;
            StructResultat[] list = new StructResultat[MAX_RESULT];

            recovery(scanner, list);

            Double max = (double) 0;
            int indexMax = 0;
            StructResultat current;
            for(int i = 0; i < nbResult; i++) {
                current = list[i];
                if(current.AttackerWin.compareTo(max) == 1){
                    max = current.AttackerWin;
                    indexMax = i;
                }
            }

            System.out.println("Pourcentage max : " + list[indexMax] + "\n");

            scanner.close();
            System.out.println("Le traitement des résultats est terminé.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void recovery(Scanner scanner, StructResultat[] list){
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();

            if (line.startsWith("Résultats de l'expérience") && scanner.hasNextLine()) {
                list[nbResult] = new StructResultat();
                patternMatcher(scanner, list[nbResult].AttackerWin);
                patternMatcher(scanner, list[nbResult].DefenderWin);
                patternMatcher(scanner, list[nbResult].MaxTrun);
                patternMatcher(scanner, list[nbResult].TempsExec);
                patternMatcher(scanner, list[nbResult].CircleStrategy);
                patternMatcher(scanner, list[nbResult].PieceRatio);
                patternMatcher(scanner, list[nbResult].NextToKing);
                patternMatcher(scanner, list[nbResult].KingToCorner);

                nbResult += 1;
            }
        }
    }

    private static void patternMatcher(Scanner scanner, Double var) {

        String line = scanner.nextLine();

        Pattern pattern = Pattern.compile("\\d+\\.\\d+");
        Matcher matcher = pattern.matcher(line);

        if (matcher.find()) {
            String percentageString = matcher.group();
            var = Double.parseDouble(percentageString);
        }
    }

}