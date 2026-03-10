public class Problem2_InventoryManager {
    public static void main(String[] args) {
        System.out.println("Hello Problem 2: Inventory Manager");

        // Example usage:
        InventoryManager manager = new InventoryManager();
        manager.addItem(1, "Apple", 50);
        manager.addItem(2, "Banana", 30);

        System.out.println("Stock for Apple: " + manager.checkStock(1));
        manager.purchaseItem(1, 10);
        System.out.println("Stock for Apple after purchase: " + manager.checkStock(1));
    }
}

// Placeholder InventoryManager class
class InventoryManager {
    private java.util.Map<Integer, String> items = new java.util.HashMap<>();
    private java.util.Map<Integer, Integer> stock = new java.util.HashMap<>();

    public void addItem(int id, String name, int quantity) {
        items.put(id, name);
        stock.put(id, quantity);
    }

    public int checkStock(int id) {
        return stock.getOrDefault(id, 0);
    }

    public void purchaseItem(int id, int qty) {
        int current = stock.getOrDefault(id, 0);
        if (current >= qty) {
            stock.put(id, current - qty);
            System.out.println(qty + " units purchased for item " + items.get(id));
        } else {
            System.out.println("Not enough stock for item " + items.get(id));
        }
    }
}
