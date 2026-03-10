import java.util.*;

public class Problem5_AnalyticsDashboard {

    static class PageEvent {
        String url, source;
        String userId;

        PageEvent(String url, String userId, String source) {
            this.url = url;
            this.userId = userId;
            this.source = source;
        }
    }

    private static Map<String, Integer> pageViews = new HashMap<>();
    private static Map<String, Set<String>> uniqueVisitors = new HashMap<>();
    private static Map<String, Integer> trafficSource = new HashMap<>();

    public static void processEvent(PageEvent event) {
        pageViews.put(event.url, pageViews.getOrDefault(event.url, 0) + 1);
        uniqueVisitors.putIfAbsent(event.url, new HashSet<>());
        uniqueVisitors.get(event.url).add(event.userId);
        trafficSource.put(event.source, trafficSource.getOrDefault(event.source, 0) + 1);
    }

    public static void printDashboard() {
        System.out.println("--- Top Pages ---");
        pageViews.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .limit(10)
                .forEach(e -> System.out.println(e.getKey() + " - " + e.getValue() + " views (" + uniqueVisitors.get(e.getKey()).size() + " unique)"));

        System.out.println("\n--- Traffic Sources ---");
        int total = trafficSource.values().stream().mapToInt(i -> i).sum();
        trafficSource.forEach((src, count) -> System.out.println(src + ": " + (count * 100 / total) + "%"));
    }

    public static void main(String[] args) {
        List<PageEvent> events = Arrays.asList(
                new PageEvent("/article/news", "user1", "google"),
                new PageEvent("/article/news", "user2", "facebook"),
                new PageEvent("/sports/championship", "user3", "direct"),
                new PageEvent("/article/news", "user1", "google"),
                new PageEvent("/sports/championship", "user4", "google")
        );

        for(PageEvent e : events) processEvent(e);

        printDashboard();
    }
}