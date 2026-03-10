
// Problem 2: E-commerce Flash Sale Inventory Manager
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Problem2_InventoryManager {

    // Stock map: productId -> available stock
    private static Map<String, AtomicInteger> stock = new HashMap<>();

    // Waiting list: productId -> Queue of userIds
    private static Map<String, Queue<Integer>> waitingList = new HashMap<>();

    public static void main(String[] args) {
        // Initialize products
        stock.put("IPHONE15_256GB", new AtomicInteger(100));
        waitingList.put("IPHONE15_256GB", new LinkedList<>());

        // Simulate purchases
        System.out.println(purchaseItem("IPHONE15_256GB", 12345));
        System.out.println(purchaseItem("IPHONE15_256GB", 67890));

        // Check stock
        System.out.println("Current stock: " + checkStock("IPHONE15_256GB"));

        // Fill stock to zero and test waiting list
        for (int i = 0; i < 100; i++) {
            purchaseItem("IPHONE15_256GB", i + 1000);
        }
        System.out.println(purchaseItem("IPHONE15_256GB", 99999)); // Goes to waiting list
        System.out.println("Waiting list position: " + getWaitingListPosition("IPHONE15_256GB", 99999));
    }

    // Check current stock
    public static int checkStock(String productId) {
        AtomicInteger count = stock.get(productId);
        return count != null ? count.get() : 0;
    }

    // Purchase item
    public static synchronized String purchaseItem(String productId, int userId) {
        AtomicInteger count = stock.get(productId);
        if (count == null) return "Product not found";

        if (count.get() > 0) {
            count.decrementAndGet();
            return "Success, " + count.get() + " units remaining";
        } else {
            Queue<Integer> queue = waitingList.get(productId);
            if (queue != null) {
                queue.add(userId);
                return "Added to waiting list, position #" + queue.size();
            } else {
                return "Product sold out";
            }
        }
    }

    // Get waiting list position
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