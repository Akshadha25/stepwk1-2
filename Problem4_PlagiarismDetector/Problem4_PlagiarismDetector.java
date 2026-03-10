import java.util.*;

public class Problem4_PlagiarismDetector {

    public static void main(String[] args) {
        String[] submissions = {
            "public class A { public static void main(String[] args){ System.out.println(\"Hello\"); } }",
            "public class B { public static void main(String[] args){ System.out.println(\"Hello\"); } }",
            "public class C { public static void main(String[] args){ System.out.println(\"Hi\"); } }"
        };

        int n = submissions.length;
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                if (submissions[i].equals(submissions[j])) {
                    System.out.println("Plagiarism detected between submission " + i + " and " + j);
                }
            }
        }
    }
}
