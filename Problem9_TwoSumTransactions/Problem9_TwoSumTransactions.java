import java.util.*;
import java.time.*;

class Transaction {
    int id;
    double amount;
    String merchant;
    LocalDateTime timestamp;

    Transaction(int id, double amount, String merchant, LocalDateTime timestamp) {
        this.id = id;
        this.amount = amount;
        this.merchant = merchant;
        this.timestamp = timestamp;
    }
}

public class Problem9_TwoSumTransactions {
    // Classic two-sum
    public static List<int[]> findTwoSum(List<Transaction> transactions, double target) {
        Map<Double, Transaction> map = new HashMap<>();
        List<int[]> results = new ArrayList<>();
        for (Transaction t : transactions) {
            double complement = target - t.amount;
            if (map.containsKey(complement)) {
                results.add(new int[]{map.get(complement).id, t.id});
            }
            map.put(t.amount, t);
        }
        return results;
    }

    // Duplicate detection (same amount + merchant, different IDs)
    public static List<List<Integer>> detectDuplicates(List<Transaction> transactions) {
        Map<String, List<Integer>> map = new HashMap<>();
        for (Transaction t : transactions) {
            String key = t.amount + "_" + t.merchant;
            map.putIfAbsent(key, new ArrayList<>());
            map.get(key).add(t.id);
        }

        List<List<Integer>> duplicates = new ArrayList<>();
        for (List<Integer> ids : map.values()) {
            if (ids.size() > 1) duplicates.add(ids);
        }
        return duplicates;
    }

    public static void main(String[] args) {
        List<Transaction> transactions = Arrays.asList(
                new Transaction(1, 500, "StoreA", LocalDateTime.of(2026,3,10,10,0)),
                new Transaction(2, 300, "StoreB", LocalDateTime.of(2026,3,10,10,15)),
                new Transaction(3, 200, "StoreC", LocalDateTime.of(2026,3,10,10,30)),
                new Transaction(4, 500, "StoreA", LocalDateTime.of(2026,3,10,11,0)),
                new Transaction(5, 150, "StoreB", LocalDateTime.of(2026,3,10,11,15))
        );

        double target = 500;
        List<int[]> pairs = findTwoSum(transactions, target);
        System.out.println("Two-Sum pairs for target " + target + ":");
        for (int[] pair : pairs) {
            System.out.println(Arrays.toString(pair));
        }

        List<List<Integer>> duplicates = detectDuplicates(transactions);
        System.out.println("\nDetected duplicates:");
        for (List<Integer> dup : duplicates) {
            System.out.println(dup);
        }
    }
}