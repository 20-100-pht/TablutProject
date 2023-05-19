package AI;

public class AIConfig {


    static double pieceRatio_A = 1;
    static double kingToCorner_A = 40;
    static double kingToValuablePos_A = 30;
    static double nextToKing_A = 10;
    static double circleStrat_A = 6;



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
