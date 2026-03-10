// Problem 3: DNS Cache with TTL
import java.util.HashMap;

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
    private HashMap<String, DNSRecord> cache = new HashMap<>();

    // Add a DNS entry with TTL (milliseconds)
    public void add(String domain, String ip, long ttlMillis) {
        cache.put(domain, new DNSRecord(ip, ttlMillis));
    }

    // Lookup DNS entry
    public String lookup(String domain) {
        DNSRecord record = cache.get(domain);
        if (record == null || record.isExpired()) {
            cache.remove(domain); // remove expired entry
            return null;
        }
        return record.ip;
    }

    public static void main(String[] args) {
        Problem3_DNSCache dnsCache = new Problem3_DNSCache();

        // Add DNS entries
        dnsCache.add("example.com", "93.184.216.34", 5000); // 5 sec TTL
        dnsCache.add("openai.com", "104.22.1.46", 10000);   // 10 sec TTL

        System.out.println("Lookup example.com: " + dnsCache.lookup("example.com"));
        System.out.println("Lookup openai.com: " + dnsCache.lookup("openai.com"));

        try { Thread.sleep(6000); } catch (InterruptedException e) {}
        System.out.println("After 6 sec, lookup example.com: " + dnsCache.lookup("example.com"));
        System.out.println("Lookup openai.com: " + dnsCache.lookup("openai.com"));
    }
}