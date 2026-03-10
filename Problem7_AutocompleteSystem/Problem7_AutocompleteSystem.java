import java.util.*;

public class Problem7_AutocompleteSystem {
    // Map to store word -> frequency
    private Map<String, Integer> counts;

    public Problem7_AutocompleteSystem() {
        counts = new HashMap<>();
    }

    // Add a word or update its count
    public void addWord(String word) {
        counts.put(word, counts.getOrDefault(word, 0) + 1);
    }

    // Get suggestions based on prefix, sorted by frequency descending
    public List<String> getSuggestions(String prefix) {
        List<String> res = new ArrayList<>();
        for (String word : counts.keySet()) {
            if (word.startsWith(prefix)) {
                res.add(word);
            }
        }
        res.sort((a, b) -> counts.get(b) - counts.get(a)); // Sort by count descending
        return res;
    }

    // For testing the autocomplete system
    public static void main(String[] args) {
        Problem7_AutocompleteSystem auto = new Problem7_AutocompleteSystem();

        // Adding words
        auto.addWord("hello");
        auto.addWord("hi");
        auto.addWord("hello");
        auto.addWord("hey");
        auto.addWord("hi");
        auto.addWord("hike");

        // Test suggestions
        System.out.println("Suggestions for 'h': " + auto.getSuggestions("h"));
        System.out.println("Suggestions for 'he': " + auto.getSuggestions("he"));
        System.out.println("Suggestions for 'hi': " + auto.getSuggestions("hi"));
    }
}
