// Problem 5: Analytics Dashboard
import java.util.*;

public class Problem5_AnalyticsDashboard {

    static class User {
        String name;
        int visits;

        User(String name, int visits) {
            this.name = name;
            this.visits = visits;
        }
    }

    public static void main(String[] args) {
        // Sample data
        List<User> users = Arrays.asList(
                new User("Alice", 5),
                new User("Bob", 3),
                new User("Charlie", 7),
                new User("Diana", 2)
        );

        // Total visits
        int totalVisits = users.stream().mapToInt(u -> u.visits).sum();
        System.out.println("Total Visits: " + totalVisits);

        // Most active user
        User mostActive = users.stream().max(Comparator.comparingInt(u -> u.visits)).orElse(null);
        if (mostActive != null) {
            System.out.println("Most Active User: " + mostActive.name + " (" + mostActive.visits + " visits)");
        }

        // Average visits
        double average = totalVisits / (double) users.size();
        System.out.println("Average Visits: " + String.format("%.2f", average));

        // Simple dashboard display
        System.out.println("\n--- Dashboard ---");
        for (User u : users) {
            System.out.println(u.name + ": " + u.visits + " visits");
        }
    }
}