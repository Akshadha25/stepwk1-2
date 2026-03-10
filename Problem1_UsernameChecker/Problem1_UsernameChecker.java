import java.util.*;

public class Problem1_UsernameChecker {
    private static Map<String, String> usernameMap = new HashMap<>(); // username -> userId
    private static Map<String, Integer> attemptFrequency = new HashMap<>();

    public static boolean checkAvailability(String username) {
        attemptFrequency.put(username, attemptFrequency.getOrDefault(username, 0) + 1);
        return !usernameMap.containsKey(username);
    }

    public static List<String> suggestAlternatives(String username) {
        List<String> suggestions = new ArrayList<>();
        int i = 1;
        while (suggestions.size() < 3) {
            String newName = username + i;
            if (!usernameMap.containsKey(newName)) suggestions.add(newName);
            i++;
        }
        suggestions.add(username.replace("_", "."));
        return suggestions;
    }

    public static String getMostAttempted() {
        return attemptFrequency.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);
    }

    public static void main(String[] args) {
        usernameMap.put("john_doe", "user123");
        usernameMap.put("admin", "user000");

        System.out.println("Check john_doe: " + checkAvailability("john_doe"));
        System.out.println("Check jane_smith: " + checkAvailability("jane_smith"));
        System.out.println("Suggest for john_doe: " + suggestAlternatives("john_doe"));
        System.out.println("Most attempted: " + getMostAttempted());
    }
}
