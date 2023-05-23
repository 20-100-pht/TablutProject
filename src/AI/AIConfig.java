package AI;

public class AIConfig {

    static double pieceRatio_D = 5;
    static double kingToCorner_D = 20;
    static double nextToKing_D = 10;
    static double pieceRatio_A = 2;
    static double kingToCorner_A = 10;
    static double nextToKing_A = 6;
    static double circleStrat_A = 3;


    public static double getPieceRatio_D() {return pieceRatio_D;}

    public static double getKingToCorner_D() {return kingToCorner_D;}

    public static double getNextToKing_D() {
        return nextToKing_D;
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

}
