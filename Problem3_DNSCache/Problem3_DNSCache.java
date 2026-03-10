 // Problem 3: DNS Cache with TTL
import java.util.*;
import java.util.concurrent.*;

class DNSEntry {
    String domain;
    String ipAddress;
    long expiryTime; // in milliseconds

    public DNSEntry(String domain, String ipAddress, long ttlSeconds) {
        this.domain = domain;
        this.ipAddress = ipAddress;
        this.expiryTime = System.currentTimeMillis() + ttlSeconds * 1000;
    }

    public boolean isExpired() {
        return System.currentTimeMillis() > expiryTime;
    }
}

public class Problem3_DNSCache {
    private final Map<String, DNSEntry> cache = new ConcurrentHashMap<>();
    private int cacheHits = 0;
    private int cacheMisses = 0;

    // Add entry to cache
    public void add(String domain, String ipAddress, long ttlSeconds) {
        cache.put(domain, new DNSEntry(domain, ipAddress, ttlSeconds));
    }

    // Resolve domain
    public String resolve(String domain) {
        DNSEntry entry = cache.get(domain);

        if (entry != null && !entry.isExpired()) {
            cacheHits++;
            return "Cache HIT ? " + entry.ipAddress;
        } else {
            cacheMisses++;
            // Simulate upstream DNS query
            String ipAddress = queryUpstreamDNS(domain);
            add(domain, ipAddress, 300); // TTL 5 minutes
            return "Cache MISS ? Query upstream ? " + ipAddress;
        }
    }

    // Simulated upstream DNS
    private String queryUpstreamDNS(String domain) {
        Random rand = new Random();
        return (rand.nextInt(256)) + "." + (rand.nextInt(256)) + "." + 
               (rand.nextInt(256)) + "." + (rand.nextInt(256));
    }

    // Remove expired entries
    public void cleanExpired() {
        cache.entrySet().removeIf(entry -> entry.getValue().isExpired());
    }

    // Show stats
    public void printStats() {
        int total = cacheHits + cacheMisses;
        double hitRate = total > 0 ? ((double) cacheHits / total) * 100 : 0;
        System.out.println("Cache HITs: " + cacheHits);
        System.out.println("Cache MISSes: " + cacheMisses);
        System.out.println("Hit Rate: " + String.format("%.2f", hitRate) + "%");
    }

    public static void main(String[] args) throws InterruptedException {
        Problem3_DNSCache dnsCache = new Problem3_DNSCache();

        System.out.println(dnsCache.resolve("google.com"));
        System.out.println(dnsCache.resolve("yahoo.com"));
        System.out.println(dnsCache.resolve("google.com")); // Should be a hit

        Thread.sleep(1000); // wait a bit
        dnsCache.cleanExpired();

        dnsCache.printStats();
    }
}
