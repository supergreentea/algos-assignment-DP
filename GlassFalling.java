import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Glass Falling
 * Author: Hui Lin
 */
public class GlassFalling {

    // Do not change the parameters!
    public int glassFallingRecur(int floors, int sheets) {
        // Fill in here and change the return

        // If number of floors is 0, 0 trials needed.
        if (floors == 0) return 0;

        // If number of floors is 1, 1 trial is needed.
        if (floors == 1) return 1;

        // If we have one sheet, we'd have to try all floors.
        if (sheets == 1) return floors;

        int minTrials = Integer.MAX_VALUE;
        int trials;

        for (int i = 1; i <= floors; i++) {
            // If the sheet breaks at the ith floor, the problem is reduced to the case with
            // i - 1 floors and sheets - 1 sheets. If the sheet does not break, the problem is
            // reduced to the case with floors - i floors and same number of sheets.
            // We add 1 for the current trial.
            trials = 1 + Math.max(glassFallingRecur(i - 1, sheets - 1), glassFallingRecur(floors - i, sheets));
            minTrials = Math.min(minTrials, trials);
        }

        return minTrials;
    }

    // Optional:
    // Pick whatever parameters you want to, just make sure to return an int.
    public int glassFallingMemoized(int floors, int sheets) {
        // Fill in here and change the return

        // Initialize 2d memo array.
        ArrayList<ArrayList<Integer>> memo = new ArrayList<>();

        // Fill memo with Integer.MAX_VALUE to mark sub-problem as unsolved.
        for (int i = 0; i < sheets + 1; i++) {
            memo.add(new ArrayList<>());
            for (int j = 0; j < floors + 1; j++) {
                memo.get(i).add(Integer.MAX_VALUE);
            }
        }

        // Return result from auxiliary function.
        return glassFallingMemoizedAux(floors, sheets, memo);
    }

    // Auxiliary function for glassFallingMemoized
    public int glassFallingMemoizedAux(int floors, int sheets, ArrayList<ArrayList<Integer>> memo) {
        // If value in array is less than Integer.MAX_VALUE then sub-problem was solved, so return value.
        if (memo.get(sheets).get(floors) < Integer.MAX_VALUE) return memo.get(sheets).get(floors);

        // Boundary conditions.
        if (floors == 0 || floors == 1 || sheets == 1) {
            memo.get(sheets).set(floors, floors);
            return floors;
        }

        int minTrials = Integer.MAX_VALUE;
        int trials;

        // Get worst case for each floor, and find floor with minimal trials.
        for (int i = 1; i <= floors; i++) {
            // As described in write-up, sub-problems for when sheet breaks or does not break. Get the worse case.
            trials = 1 + Math.max(glassFallingMemoizedAux(i - 1, sheets - 1, memo),
                                  glassFallingMemoizedAux(floors - i, sheets, memo));
            minTrials = Math.min(minTrials, trials); // Find minimum trials from testing all floors.
        }

        memo.get(sheets).set(floors, minTrials); // Store calculation in memo array.
        return minTrials;
    }

    // Do not change the parameters!
    public int glassFallingBottomUp(int floors, int sheets) {
        // Fill in here and change the return
        int[][] memo = new int[sheets + 1][floors + 1];
        int trials;

        // One trial for a floor and 0 trials for 0 floors.
        for (int i = 1; i <= sheets; i++) {
            memo[i][1] = 1;
            memo[i][0] = 0;
        }

        // One sheet with j floors requires j trials.
        for (int j = 1; j <= floors; j++) {
            memo[1][j] = j;
        }

        for (int i = 2; i <= sheets; i++) {
            for (int j = 2; j <= floors; j++) {
                memo[i][j] = Integer.MAX_VALUE;
                for (int x = 1; x <= j; x++) {
                    // As described in write-up, sub-problems for when sheet breaks or does not break. Get the worse case.
                    trials = 1 + Math.max(memo[i - 1][x - 1], memo[i][j - x]);
                    memo[i][j] = Math.min(memo[i][j], trials); // Store result from floor with minimal trials in array.
                }
            }
        }

        return memo[sheets][floors];
    }


    public static void main(String args[]){
        GlassFalling gf = new GlassFalling();
        // Do not touch the below lines of code, and make sure
        // in your final turned-in copy, these are the only things printed
        int minTrials1Recur = gf.glassFallingMemoized(27, 2);
        int minTrials1Bottom = gf.glassFallingBottomUp(27, 2);
        int minTrials2Recur = gf.glassFallingMemoized(100, 3);
        int minTrials2Bottom = gf.glassFallingBottomUp(100, 3);
        System.out.println(minTrials1Recur + " " + minTrials1Bottom);
        System.out.println(minTrials2Recur + " " + minTrials2Bottom);
    }
}
