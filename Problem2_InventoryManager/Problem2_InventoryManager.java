// Problem2: E-commerce Flash Sale Inventory Manager
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class Problem2_InventoryManager {

    // Product stock: productId -> available units
    private static ConcurrentHashMap<String, AtomicInteger> stock = new ConcurrentHashMap<>();

    // Waiting list for each product (FIFO)
    private static ConcurrentHashMap<String, Queue<Integer>> waitingList = new ConcurrentHashMap<>();

    public static void main(String[] args) {
        // Initialize stock
        stock.put("IPHONE15_256GB", new AtomicInteger(100));
        waitingList.put("IPHONE15_256GB", new ConcurrentLinkedQueue<>());

        // Simulate purchases
        System.out.println(purchaseItem("IPHONE15_256GB", 12345));
        System.out.println(purchaseItem("IPHONE15_256GB", 67890));

        // Check stock
        System.out.println("Stock remaining: " + checkStock("IPHONE15_256GB") + " units");

        // Add users to waiting list if stock runs out
        for(int i = 0; i < 100; i++) {
            purchaseItem("IPHONE15_256GB", 1000+i);
        }

        int waitingUser = 99999;
        System.out.println(purchaseItem("IPHONE15_256GB", waitingUser));
        System.out.println("Waiting list position: " + getWaitingListPosition("IPHONE15_256GB", waitingUser));
    }

    // Check current stock
    public static int checkStock(String productId) {
        AtomicInteger count = stock.get(productId);
        return count == null ? 0 : count.get();
    }

    // Attempt to purchase a product
    public static String purchaseItem(String productId, int userId) {
        stock.putIfAbsent(productId, new AtomicInteger(0));
        waitingList.putIfAbsent(productId, new ConcurrentLinkedQueue<>());

        AtomicInteger count = stock.get(productId);
        Queue<Integer> queue = waitingList.get(productId);

        // Atomically decrement stock if available
        while (true) {
            int available = count.get();
            if (available > 0) {
                if (count.compareAndSet(available, available - 1)) {
                    return "Success: " + (available - 1) + " units remaining";
                }
            } else {
                // Add to waiting list
                queue.add(userId);
                return "Added to waiting list, position #" + queue.size();
            }
        }
    }

    // Get waiting list position for a user
    public static int getWaitingListPosition(String productId, int userId) {
        Queue<Integer> queue = waitingList.get(productId);
        if (queue == null) return -1;
        int pos = 1;
        for (int id : queue) {
            if (id == userId) {
                return pos;
            }
            pos++;
        }
        return -1;
    }
}