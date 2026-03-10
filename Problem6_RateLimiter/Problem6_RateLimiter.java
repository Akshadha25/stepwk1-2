import java.util.*;
import java.util.concurrent.*;
import java.time.*;

public class Problem6_RateLimiter {
    static class TokenBucket {
        int maxTokens;
        int tokens;
        long lastRefillTime;
        long refillIntervalMillis;

        TokenBucket(int maxTokens, long intervalSeconds) {
            this.maxTokens = maxTokens;
            this.tokens = maxTokens;
            this.lastRefillTime = System.currentTimeMillis();
            this.refillIntervalMillis = intervalSeconds * 1000;
        }

        synchronized boolean allowRequest() {
            refill();
            if (tokens > 0) {
                tokens--;
                return true;
            }
            return false;
        }

        synchronized void refill() {
            long now = System.currentTimeMillis();
            if (now - lastRefillTime > refillIntervalMillis) {
                tokens = maxTokens;
                lastRefillTime = now;
            }
        }

        synchronized int getRemaining() {
            refill();
            return tokens;
        }
    }

    private Map<String, TokenBucket> clientBuckets = new ConcurrentHashMap<>();
    private int maxRequests;
    private long intervalSeconds;

    public Problem6_RateLimiter(int maxRequests, long intervalSeconds) {
        this.maxRequests = maxRequests;
        this.intervalSeconds = intervalSeconds;
    }

    public boolean checkRequest(String clientId) {
        clientBuckets.putIfAbsent(clientId, new TokenBucket(maxRequests, intervalSeconds));
        return clientBuckets.get(clientId).allowRequest();
    }

    public int getRemaining(String clientId) {
        return clientBuckets.getOrDefault(clientId, new TokenBucket(maxRequests, intervalSeconds)).getRemaining();
    }

    public static void main(String[] args) throws InterruptedException {
        Problem6_RateLimiter limiter = new Problem6_RateLimiter(5, 10); // 5 req per 10 sec
        String clientId = "client123";

        for (int i = 1; i <= 10; i++) {
            if (limiter.checkRequest(clientId)) {
                System.out.println("Request " + i + " allowed at " + LocalTime.now() + " (Remaining: " + limiter.getRemaining(clientId) + ")");
            } else {
                System.out.println("Request " + i + " blocked at " + LocalTime.now());
            }
            Thread.sleep(1000);
        }
    }
}