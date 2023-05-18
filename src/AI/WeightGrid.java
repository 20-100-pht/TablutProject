package AI;

public class WeightGrid {
    private static final int SIZE = 9;
    private static final int KING_WEIGHT = 8;
    private static final int CORNER_WEIGHT = 5;
    private static final int ADJACENT_WEIGHT = 2;

    public int[][] calculateWeights(int kingRow, int kingCol) {
        int[][] weights = new int[SIZE][SIZE];

        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                // proximité position  rois
                int proximityWeight = Math.max(Math.abs(kingRow - row), Math.abs(kingCol - col));
                int weight = KING_WEIGHT - proximityWeight;

                // coins poids (utilisé par le rois)
                if ((row == 0 || row == SIZE - 1) && (col == 0 || col == SIZE - 1)) weight += CORNER_WEIGHT;

                // position adjacente
                if (Math.abs(kingRow - row) <= 1 && Math.abs(kingCol - col) <= 1) weight += ADJACENT_WEIGHT;

                int distanceFromCenter = Math.max(Math.abs(row - SIZE / 2), Math.abs(col - SIZE / 2));
                int centerWeight = 2 * distanceFromCenter;
                weight += centerWeight;

                // encerclement
                int encirclementWeight = calculateEncirclementWeight(row, col, kingRow, kingCol);
                weight += encirclementWeight;

                weights[row][col] = weight;
            }
        }

        return weights;
    }

    private int calculateEncirclementWeight(int row, int col, int kingRow, int kingCol) {
        int encirclementWeight = 0;
        if (Math.abs(kingRow - row) <= 1 && Math.abs(kingCol - col) <= 1) {
            if (kingRow == row || kingCol == col) {
                if ((kingRow == 0 && row == SIZE - 1) || (kingRow == SIZE - 1 && row == 0) || (kingCol == 0 && col == SIZE - 1) || (kingCol == SIZE - 1 && col == 0)) encirclementWeight += 10;
            }
        }
        return encirclementWeight;
    }
}