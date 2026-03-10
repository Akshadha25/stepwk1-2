// Problem 4: Plagiarism Detector
import java.util.*;

public class Problem4_PlagiarismDetector {

    // Method to calculate similarity percentage
    public static double calculateSimilarity(String text1, String text2) {
        Set<String> words1 = new HashSet<>(Arrays.asList(text1.toLowerCase().split("\\s+")));
        Set<String> words2 = new HashSet<>(Arrays.asList(text2.toLowerCase().split("\\s+")));

        Set<String> intersection = new HashSet<>(words1);
        intersection.retainAll(words2);

        Set<String> union = new HashSet<>(words1);
        union.addAll(words2);

        return ((double) intersection.size() / union.size()) * 100;
    }

    public static void main(String[] args) {
        String doc1 = "This is a sample document to check plagiarism detection";
        String doc2 = "This document is a sample to detect plagiarism";

        double similarity = calculateSimilarity(doc1, doc2);
        System.out.println("Similarity: " + String.format("%.2f", similarity) + "%");

        // Example: Flag if similarity > 50%
        if (similarity > 50) {
            System.out.println("Plagiarism likely detected!");
        } else {
            System.out.println("Documents are sufficiently different.");
        }
    }
}