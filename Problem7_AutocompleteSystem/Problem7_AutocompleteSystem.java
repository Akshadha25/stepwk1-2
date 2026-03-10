import java.util.*;

public class Problem7_AutocompleteSystem {
    private Map<String, Integer> counts;

    public Problem7_AutocompleteSystem() {
        counts = new HashMap<>();
    }

    public void addWord(String word) {
        counts.put(word, counts.getOrDefault(word, 0) + 1);
    }

    public List<String> getSuggestions(String prefix) {
        PriorityQueue<Map.Entry<String, Integer>> pq = new PriorityQueue<>(
                Map.Entry.comparingByValue()
        );

        for (Map.Entry<String, Integer> entry : counts.entrySet()) {
            if (entry.getKey().startsWith(prefix)) {
                pq.offer(entry);
                if (pq.size() > 10) pq.poll(); // keep top 10
            }
        }

        List<String> result = new ArrayList<>();
        while (!pq.isEmpty()) result.add(pq.poll().getKey());
        Collections.reverse(result);
        return result;
    }

    public static void main(String[] args) {
        Problem7_AutocompleteSystem auto = new Problem7_AutocompleteSystem();

        String[] words = {"hello","hi","hello","hey","hi","hike","hippo","hill","hint","hover","house","hero"};
        for (String w : words) auto.addWord(w);

        System.out.println("Suggestions for 'h': " + auto.getSuggestions("h"));
        System.out.println("Suggestions for 'he': " + auto.getSuggestions("he"));
        System.out.println("Suggestions for 'hi': " + auto.getSuggestions("hi"));
    }
}