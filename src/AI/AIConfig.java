package AI;

public class AIConfig {

    static double pieceRatio_D = 5;
    static double kingToCorner_D = 10;
    static double nextToKing_D = 100;
    static double pieceRatio_A = 1;
    static double kingToCorner_A = 80;
    static double kingToValuablePos_A = 60;
    static double nextToKing_A = 20;
    static double circleStrat_A = 50;


    public static double getPieceRatio_D() {
        return pieceRatio_D;
    }

    public static void setPieceRatio_D(double pieceRatio_D) {
        AIConfig.pieceRatio_D = pieceRatio_D;
    }

    public static double getKingToCorner_D() {
        return kingToCorner_D;
    }

    public static void setKingToCorner_D(double kingToCorner_D) {
        AIConfig.kingToCorner_D = kingToCorner_D;
    }

    public static double getNextToKing_D() {
        return nextToKing_D;
    }

    public static void setNextToKing_D(double nextToKing_D) {
        AIConfig.nextToKing_D = nextToKing_D;
    }
    public static double getPieceRatio_A() {
        return pieceRatio_A;
    }

    public static void setPieceRatio_A(double pieceRatio_A) {
        AIConfig.pieceRatio_A = pieceRatio_A;
    }

    public static double getKingToCorner_A() {
        return kingToCorner_A;
    }

    public static void setKingToCorner_A(double kingToCorner_A) {
        AIConfig.kingToCorner_A = kingToCorner_A;
    }

    public static double getNextToKing_A() {
        return nextToKing_A;
    }

    public static void setNextToKing_A(double nextToKing_A) {
        AIConfig.nextToKing_A = nextToKing_A;
    }

    public static double getCircleStrat_A() {
        return circleStrat_A;
    }

    public static void setCircleStrat_A(double circleStrat_A) {
        AIConfig.circleStrat_A = circleStrat_A;
    }

    public static double getKingToValuablePos_A() {
        return kingToValuablePos_A;
    }

    public static void setKingToValuablePos_A(double kingToValuablePos_A) {
        AIConfig.kingToValuablePos_A = kingToValuablePos_A;
    }
}
