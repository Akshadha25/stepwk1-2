// Problem 6: Distributed Rate Limiter
import java.util.concurrent.*;
import java.time.*;

public class Problem6_RateLimiter {
    private final int maxRequests;
    private final long intervalMillis;
    private int requestCount;
    private long windowStart;

    public Problem6_RateLimiter(int maxRequests, long intervalSeconds) {
        this.maxRequests = maxRequests;
        this.intervalMillis = intervalSeconds * 1000;
        this.requestCount = 0;
        this.windowStart = System.currentTimeMillis();
    }

    public synchronized boolean allowRequest() {
        long now = System.currentTimeMillis();
        if (now - windowStart > intervalMillis) {
            // Reset window
            windowStart = now;
            requestCount = 0;
        }
        if (requestCount < maxRequests) {
            requestCount++;
            return true;
        }
        return false;
    }

    public static void main(String[] args) throws InterruptedException {
        Problem6_RateLimiter limiter = new Problem6_RateLimiter(5, 10); // 5 requests per 10 seconds

        for (int i = 1; i <= 10; i++) {
            if (limiter.allowRequest()) {
                System.out.println("Request " + i + " allowed at " + LocalTime.now());
            } else {
                System.out.println("Request " + i + " blocked at " + LocalTime.now());
            }
            Thread.sleep(1000); // 1 second between requests
        }
    }
}