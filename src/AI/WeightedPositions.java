package AI;

import java.util.ArrayList;

public class WeightedPositions {

    int A = 0;
    int B = 5;
    int C = B*10;
    int D = -1;
    int E = C*2;

    public ArrayList<int[][]> getWeights(){
        ArrayList<int[][]> weights = new ArrayList<>();
        weights.add(left);
        weights.add(right);
        weights.add(top);
        weights.add(bottom);
        weights.add(center);
        weights.add(top_left);
        weights.add(top_right);
        weights.add(bottom_left);
        weights.add(bottom_right);

        return weights;
    }

    int[][] center = {
            { A, A, A, A, B, A, A, A, A},
            { A, A, A, B, A, B, A, A, A},
            { A, A, B, A, A, A, B, A, A},
            { A, B, A, A, A, A, A, B, A},
            { B, A, A, A, A, A, A, A, B},
            { A, B, A, A, A, A, A, B, A},
            { A, A, B, A, A, A, B, A, A},
            { A, A, A, B, A, B, A, A, A},
            { A, A, A, A, B, A, A, A, A}
    };

    int[][] left = {
            { A, A, A, A, C, A, A, A, A},
            { A, A, A, C, A, B, A, A, A},
            { A, A, C, A, A, A, B, A, A},
            { A, C, A, A, A, A, A, B, A},
            { C, A, A, A, A, A, A, A, B},
            { A, C, A, A, A, A, A, B, A},
            { A, A, C, A, A, A, B, A, A},
            { A, A, A, C, A, B, A, A, A},
            { A, A, A, A, C, A, A, A, A}
    };

    int[][] right = {
            { A, A, A, A, C, A, A, A, A},
            { A, A, A, B, A, C, A, A, A},
            { A, A, B, A, A, A, C, A, A},
            { A, B, A, A, A, A, A, C, A},
            { B, A, A, A, A, A, A, A, C},
            { A, B, A, A, A, A, A, C, A},
            { A, A, B, A, A, A, C, A, A},
            { A, A, A, B, A, C, A, A, A},
            { A, A, A, A, C, A, A, A, A}
    };

    int[][] top = {
            { A, A, A, A, C, A, A, A, A},
            { A, A, A, C, A, C, A, A, A},
            { A, A, C, A, A, A, C, A, A},
            { A, C, A, A, A, A, A, C, A},
            { C, A, A, A, A, A, A, A, C},
            { A, B, A, A, A, A, A, B, A},
            { A, A, B, A, A, A, B, A, A},
            { A, A, A, B, A, B, A, A, A},
            { A, A, A, A, B, A, A, A, A}
    };

    int[][] bottom = {
            { A, A, A, A, B, A, A, A, A},
            { A, A, A, B, A, B, A, A, A},
            { A, A, B, A, A, A, B, A, A},
            { A, B, A, A, A, A, A, B, A},
            { C, A, A, A, A, A, A, A, C},
            { A, C, A, A, A, A, A, C, A},
            { A, A, C, A, A, A, C, A, A},
            { A, A, A, C, A, C, A, A, A},
            { A, A, A, A, C, A, A, A, A}
    };

    int[][] top_left = {
            { A, A, A, A, C, A, A, A, A},
            { A, A, A, C, A, B, A, A, A},
            { A, A, C, A, A, A, B, A, A},
            { A, C, A, A, A, A, A, B, A},
            { C, A, A, A, A, A, A, A, B},
            { A, B, A, A, A, A, A, B, A},
            { A, A, B, A, A, A, B, A, A},
            { A, A, A, B, A, B, A, A, A},
            { A, A, A, A, B, A, A, A, A}
    };

    int[][] top_right = {
            { A, A, A, A, C, A, A, A, A},
            { A, A, A, B, A, C, A, A, A},
            { A, A, B, A, A, A, C, A, A},
            { A, B, A, A, A, A, A, C, A},
            { B, A, A, A, A, A, A, A, C},
            { A, B, A, A, A, A, A, B, A},
            { A, A, B, A, A, A, B, A, A},
            { A, A, A, B, A, B, A, A, A},
            { A, A, A, A, B, A, A, A, A}
    };

    int[][] bottom_left = {
            { A, A, A, A, B, A, A, A, A},
            { A, A, A, B, A, B, A, A, A},
            { A, A, B, A, A, A, B, A, A},
            { A, B, A, A, A, A, A, B, A},
            { C, A, A, A, A, A, A, A, B},
            { A, C, A, A, A, A, A, B, A},
            { A, A, C, A, A, A, B, A, A},
            { A, A, A, C, A, B, A, A, A},
            { A, A, A, A, C, A, A, A, A}
    };

    int[][] bottom_right = {
            { A, A, A, A, B, A, A, A, A},
            { A, A, A, B, A, B, A, A, A},
            { A, A, B, A, A, A, B, A, A},
            { A, B, A, A, A, A, A, B, A},
            { B, A, A, A, A, A, A, A, C},
            { A, B, A, A, A, A, A, C, A},
            { A, A, B, A, A, A, C, A, A},
            { A, A, A, B, A, C, A, A, A},
            { A, A, A, A, C, A, A, A, A}
    };


}
