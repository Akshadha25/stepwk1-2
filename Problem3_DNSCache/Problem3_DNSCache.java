import java.util.*;
import java.util.concurrent.*;

class DNSRecord {
    String ip;
    long expiryTime;

    DNSRecord(String ip, long ttlMillis) {
        this.ip = ip;
        this.expiryTime = System.currentTimeMillis() + ttlMillis;
    }

    boolean isExpired() {
        return System.currentTimeMillis() > expiryTime;
    }
}

public class Problem3_DNSCache {
    private final int MAX_CACHE_SIZE = 1000;
    private Map<String, DNSRecord> cache = new LinkedHashMap<>(16, 0.75f, true);
    private int hits = 0, misses = 0;

    public synchronized void add(String domain, String ip, long ttlMillis) {
        if (cache.size() >= MAX_CACHE_SIZE) {
            Iterator<String> it = cache.keySet().iterator();
            if (it.hasNext()) {
                String oldest = it.next();
                it.remove(); // LRU eviction
            }
        }
        cache.put(domain, new DNSRecord(ip, ttlMillis));
    }

    public synchronized String lookup(String domain) {
        long start = System.nanoTime();
        DNSRecord record = cache.get(domain);
        if (record == null || record.isExpired()) {
            if (record != null) cache.remove(domain);
            misses++;
            long time = System.nanoTime() - start;
            System.out.println("Cache MISS for " + domain + " (" + time/1e6 + " ms)");
            return null;
        }
        hits++;
        long time = System.nanoTime() - start;
        System.out.println("Cache HIT for " + domain + " (" + time/1e6 + " ms)");
        return record.ip;
    }

    public synchronized void printStats() {
        int total = hits + misses;
        double hitRate = total == 0 ? 0 : ((double) hits / total) * 100;
        System.out.println("Cache Stats -> Hits: " + hits + ", Misses: " + misses + ", Hit Rate: " + String.format("%.2f", hitRate) + "%");
    }

    public static void main(String[] args) throws InterruptedException {
        Problem3_DNSCache dnsCache = new Problem3_DNSCache();
        dnsCache.add("example.com", "93.184.216.34", 5000);
        dnsCache.add("openai.com", "104.22.1.46", 10000);

        System.out.println(dnsCache.lookup("example.com"));
        System.out.println(dnsCache.lookup("openai.com"));

        Thread.sleep(6000);
        System.out.println(dnsCache.lookup("example.com"));
        System.out.println(dnsCache.lookup("openai.com"));

        dnsCache.printStats();
    }
}