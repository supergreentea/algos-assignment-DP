/**
 * Rod cutting problem described in Chapter 15 of textbook
 */
public class RodCutting {

  // Do not change the parameters!
  public int rodCuttingRecur(int rodLength, int[] lengthPrices) {
      // Initialize array to store solutions to sub-problems.
      int[] memo = new int[rodLength + 1];

      for (int i = 1; i <= rodLength; i++) {
          int q = -1;
          for (int k = 1; k <= i; k++) {
              q = Math.max(q, lengthPrices[k - 1] + memo[i - k]);
          }
          memo[i] = q;
      }
      return memo[rodLength];
  }

  // Do not change the parameters!
  public int rodCuttingBottomUp(int rodLength, int[] lengthPrices) {
      // Initialize array to store solutions to sub-problems.
      int[] memo = new int[rodLength + 1];

      // Mark unsolved sub-problems with -1.
      for (int i = 0; i < rodLength + 1; i++) memo[i] = -1;

      // Call auxiliary function to solve.
      return rodCuttingBottomUpAux(lengthPrices, rodLength, memo);
  }

  public int rodCuttingBottomUpAux(int[] lengthPrices, int n, int[] memo) {
      // Positive value means sub-problem was solved.
      if (memo[n] >= 0) return memo[n];

      // Initialize value of solution to sub-problem to -1.
      int q = -1;

      // Rod of length 0 earns no revenue.
      if (n == 0) q = 0;
      else {
          for (int i = 1; i <= n; i++) {
              // We check index i - 1 of lengthPrices since array is 0-indexed.
              q = Math.max(q, lengthPrices[i - 1] + rodCuttingBottomUpAux(lengthPrices, n - i, memo));
          }
      }

      // Store computed solution.
      memo[n] = q;

      return q;
  }


  public static void main(String args[]){
      RodCutting rc = new RodCutting();

      // In your turned in copy, do not touch the below lines of code.
      // Make sure below is your only output.
      int length1 = 7;
      int[] prices1 = {1, 4, 7, 3, 19, 5, 12};
      int length2 = 14;
      int[] prices2 = {2, 5, 1, 6, 11, 15, 17, 12, 13, 9, 10, 22, 18, 26};
      int maxSell1Recur = rc.rodCuttingRecur(length1, prices1);
      int maxSell1Bottom = rc.rodCuttingBottomUp(length1, prices1);
      int maxSell2Recur = rc.rodCuttingRecur(length2, prices2);
      int maxSell2Bottom = rc.rodCuttingBottomUp(length2, prices2);
      System.out.println(maxSell1Recur + " " + maxSell1Bottom);
      System.out.println(maxSell2Recur + " " + maxSell2Bottom);
  }
}
