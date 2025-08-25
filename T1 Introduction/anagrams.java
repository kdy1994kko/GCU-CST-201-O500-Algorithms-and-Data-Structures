import java.util.HashMap;
import java.util.Scanner;

public class anagrams {
    // Function to check if two words are anagrams
    public static boolean areAnagrams(String word1, String word2) {
        // Convert both words to lowercase for case-insensitive comparison
        word1 = word1.toLowerCase();
        word2 = word2.toLowerCase();

        // Step 1: Check lengths first for efficiency
        if (word1.length() != word2.length()) {
            return false; // If lengths differ, words cannot be anagrams
        }

        // Step 2: Create a HashMap to count characters of the first word
        HashMap<Character, Integer> charCount = new HashMap<>();

        // Count characters from word1
        for (char c : word1.toCharArray()) {
            charCount.put(c, charCount.getOrDefault(c, 0) + 1);
        }

        // Step 3: Process word2 to decrement counts
        for (char c : word2.toCharArray()) {
            if (!charCount.containsKey(c)) {
                return false; // Character not found, so not an anagram
            }
            charCount.put(c, charCount.get(c) - 1);
            if (charCount.get(c) < 0) {
                return false; // More occurrences of a character in word2
            }
        }

        // Step 4: All counts matched correctly, words are anagrams
        return true;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Prompt and read first word
        System.out.print("Enter first word: ");
        String word1 = scanner.nextLine();

        // Prompt and read second word
        System.out.print("Enter second word: ");
        String word2 = scanner.nextLine();

        // Call function to check for anagrams
        boolean result = areAnagrams(word1, word2);

        // Print true or false based on result
        System.out.println(result);

        scanner.close();
    }
}
