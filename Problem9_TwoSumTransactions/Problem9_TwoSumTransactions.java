// Problem 9: Two-Sum Transactions
import java.util.HashMap;
import java.util.Map;

public class Problem9_TwoSumTransactions {

    // Find indices of two transactions that sum to the target
    public static int[] twoSum(int[] transactions, int target) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < transactions.length; i++) {
            int complement = target - transactions[i];
            if (map.containsKey(complement)) {
                return new int[]{map.get(complement), i};
            }
            map.put(transactions[i], i);
        }
        return new int[]{-1, -1}; // No solution found
    }

    public static void main(String[] args) {
        int[] transactions = {20, 35, 15, 40, 50};
        int target = 55;

        int[] result = twoSum(transactions, target);
        if (result[0] != -1) {
            System.out.println("Transactions at indices " + result[0] + " and " + result[1] + " sum to " + target);
        } else {
            System.out.println("No two transactions sum to " + target);
        }
    }
}