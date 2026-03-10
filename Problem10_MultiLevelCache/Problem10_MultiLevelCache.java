// Problem 10: Multi-Level Cache
import java.util.*;

public class Problem10_MultiLevelCache {

    // Simulate a simple two-level cache: L1 (fast, small) and L2 (slower, bigger)
    private Map<String, String> L1;
    private Map<String, String> L2;
    private int L1Capacity;

    public Problem10_MultiLevelCache(int L1Capacity) {
        this.L1Capacity = L1Capacity;
        L1 = new LinkedHashMap<>(L1Capacity, 0.75f, true); // access-order LRU
        L2 = new HashMap<>();
    }

    // Get value from cache
    public String get(String key) {
        if (L1.containsKey(key)) {
            return L1.get(key); // L1 hit
        }
        if (L2.containsKey(key)) {
            String value = L2.get(key);
            putInL1(key, value); // promote to L1
            return value;
        }
        return null; // cache miss
    }

    // Put value in cache
    public void put(String key, String value) {
        putInL1(key, value);
        L2.put(key, value); // always keep in L2
    }

    private void putInL1(String key, String value) {
        if (L1.size() >= L1Capacity) {
            String eldestKey = L1.keySet().iterator().next();
            L1.remove(eldestKey); // remove LRU
        }
        L1.put(key, value);
    }

    // For testing
    public static void main(String[] args) {
        Problem10_MultiLevelCache cache = new Problem10_MultiLevelCache(2);

        cache.put("A", "Apple");
        cache.put("B", "Banana");
        System.out.println("Get A: " + cache.get("A")); // L1 hit
        cache.put("C", "Cherry"); // evict least recently used (B)
        System.out.println("Get B: " + cache.get("B")); // L2 hit, promote to L1
        System.out.println("Get C: " + cache.get("C")); // L1 hit
        System.out.println("Get A: " + cache.get("A")); // L1 hit or L2 depending on eviction
    }
}