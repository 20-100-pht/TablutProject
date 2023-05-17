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
        String inputFilePath = "ResATTACKER_17_13_38_14.txt";

        try {
            File inputFile = new File(inputFilePath);
            Scanner scanner = new Scanner(inputFile);

            nbResult = 0;
            Double[] list = new Double[MAX_RESULT];

            recovery(scanner, list);

            Double max = (double) 0;
            Double current;
            for(int i = 0; i < nbResult; i++) {
                current = list[i];
                if(current.compareTo(max) == 1){
                    max = current;
                }
            }

            System.out.println("Pourcentage max : " + max + "\n");

            scanner.close();
            System.out.println("Le traitement des résultats est terminé.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void recovery(Scanner scanner, Double[] list){
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();

            if (line.startsWith("Résultats de l'expérience") && scanner.hasNextLine()) {
                patternMatcher(scanner, list);
            }
        }
    }

    private static void patternMatcher(Scanner scanner, Double[] list) {

        String line = scanner.nextLine();

        // Pattern pour correspondre à un nombre décimal
        Pattern pattern = Pattern.compile("\\d+\\.\\d+");
        Matcher matcher = pattern.matcher(line);

        if (matcher.find()) {
            String percentageString = matcher.group();
            list[nbResult] = Double.parseDouble(percentageString);
            nbResult += 1;
        }
    }
}