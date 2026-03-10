import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class Problem2_InventoryManager {

    private static ConcurrentHashMap<String, AtomicInteger> stock = new ConcurrentHashMap<>();
    private static ConcurrentHashMap<String, Queue<Integer>> waitingList = new ConcurrentHashMap<>();
    private static Map<Integer, List<String>> userPurchases = new HashMap<>();

    public static void main(String[] args) {
        initializeStock("IPHONE15_256GB", 100);

        // Simulate purchases
        simulatePurchase("IPHONE15_256GB", 12345);
        simulatePurchase("IPHONE15_256GB", 67890);

        // Simulate bulk purchases
        for (int i = 0; i < 100; i++) simulatePurchase("IPHONE15_256GB", 1000 + i);

        int waitingUser = 99999;
        simulatePurchase("IPHONE15_256GB", waitingUser);

        System.out.println("Final stock: " + checkStock("IPHONE15_256GB"));
        System.out.println("Waiting list for IPHONE15_256GB: " + waitingList.get("IPHONE15_256GB"));
        System.out.println("User purchases: " + userPurchases);
    }

    private static void initializeStock(String productId, int quantity) {
        stock.put(productId, new AtomicInteger(quantity));
        waitingList.put(productId, new ConcurrentLinkedQueue<>());
    }

    private static void simulatePurchase(String productId, int userId) {
        String result = purchaseItem(productId, userId);
        System.out.println("User " + userId + ": " + result);
        if (!result.contains("waiting list")) {
            userPurchases.putIfAbsent(userId, new ArrayList<>());
            userPurchases.get(userId).add(productId);
        }
    }

    public static int checkStock(String productId) {
        AtomicInteger count = stock.get(productId);
        return count == null ? 0 : count.get();
    }

    public static String purchaseItem(String productId, int userId) {
        stock.putIfAbsent(productId, new AtomicInteger(0));
        waitingList.putIfAbsent(productId, new ConcurrentLinkedQueue<>());
        AtomicInteger count = stock.get(productId);
        Queue<Integer> queue = waitingList.get(productId);

        while (true) {
            int available = count.get();
            if (available > 0) {
                if (count.compareAndSet(available, available - 1)) {
                    return "Success: " + (available - 1) + " units remaining";
                }
            } else {
                queue.add(userId);
                return "Added to waiting list, position #" + queue.size();
            }
        }
    }

    public static int getWaitingListPosition(String productId, int userId) {
        Queue<Integer> queue = waitingList.get(productId);
        if (queue == null) return -1;
        int pos = 1;
        for (int id : queue) {
            if (id == userId) return pos;
            pos++;
        }
        return -1;
    }
}