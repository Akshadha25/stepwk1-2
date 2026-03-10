import java.util.*;
import java.time.LocalDateTime;

public class Problem1_UsernameChecker {
    private static Map<String, String> usernameMap = new HashMap<>(); // username -> userId
    private static Map<String, Integer> attemptFrequency = new HashMap<>();
    private static Map<String, List<LocalDateTime>> attemptTimestamps = new HashMap<>();

    // Add a new username (used for registration)
    public static boolean addUsername(String username, String userId) {
        if (usernameMap.containsKey(username)) return false;
        usernameMap.put(username, userId);
        return true;
    }

    // Check availability
    public static boolean checkAvailability(String username) {
        attemptFrequency.put(username, attemptFrequency.getOrDefault(username, 0) + 1);
        attemptTimestamps.putIfAbsent(username, new ArrayList<>());
        attemptTimestamps.get(username).add(LocalDateTime.now());
        return !usernameMap.containsKey(username);
    }

    // Suggest alternatives
    public static List<String> suggestAlternatives(String username) {
        List<String> suggestions = new ArrayList<>();
        int i = 1;
        while (suggestions.size() < 3) {
            String newName = username + i;
            if (!usernameMap.containsKey(newName)) suggestions.add(newName);
            i++;
        }
        suggestions.add(username.replace("_", "."));
        suggestions.add(username + "_2026");
        return suggestions;
    }

    // Get most attempted username
    public static String getMostAttempted() {
        return attemptFrequency.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);
    }

    public static void main(String[] args) {
        addUsername("john_doe", "user123");
        addUsername("admin", "user000");

        System.out.println("Check john_doe: " + checkAvailability("john_doe"));
        System.out.println("Check jane_smith: " + checkAvailability("jane_smith"));
        System.out.println("Suggest for john_doe: " + suggestAlternatives("john_doe"));
        System.out.println("Most attempted: " + getMostAttempted());

        // Debug: print attempt timestamps
        System.out.println("Attempt logs:");
        attemptTimestamps.forEach((k, v) -> System.out.println(k + " -> " + v));
    }
}