import java.util.*;

public class Problem4_PlagiarismDetector {

    private static int N_GRAM = 5; // 5-grams
    private static Map<String, Set<String>> ngramMap = new HashMap<>();

    public static void addDocument(String docId, String content) {
        String[] words = content.toLowerCase().split("\\s+");
        for (int i = 0; i <= words.length - N_GRAM; i++) {
            String ngram = String.join(" ", Arrays.copyOfRange(words, i, i + N_GRAM));
            ngramMap.putIfAbsent(ngram, new HashSet<>());
            ngramMap.get(ngram).add(docId);
        }
    }

    public static Map<String, Integer> analyzeDocument(String docId, String content) {
        Map<String, Integer> similarity = new HashMap<>();
        String[] words = content.toLowerCase().split("\\s+");
        for (int i = 0; i <= words.length - N_GRAM; i++) {
            String ngram = String.join(" ", Arrays.copyOfRange(words, i, i + N_GRAM));
            if (ngramMap.containsKey(ngram)) {
                for (String otherDoc : ngramMap.get(ngram)) {
                    if (!otherDoc.equals(docId)) {
                        similarity.put(otherDoc, similarity.getOrDefault(otherDoc, 0) + 1);
                    }
                }
            }
        }
        return similarity;
    }

    public static void main(String[] args) {
        addDocument("essay_089.txt", "This is a sample essay for testing plagiarism detection in documents");
        addDocument("essay_092.txt", "Another example essay to check plagiarism detection using n-grams");

        String newDocId = "essay_123.txt";
        String newContent = "This is a sample document to check plagiarism detection";

        Map<String, Integer> similarity = analyzeDocument(newDocId, newContent);
        similarity.forEach((doc, count) -> System.out.println("Matches with " + doc + ": " + count + " n-grams"));
    }
}