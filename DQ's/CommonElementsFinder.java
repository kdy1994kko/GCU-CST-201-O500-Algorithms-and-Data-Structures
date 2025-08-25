
/**
 * Topic 1 DQ 1 Instructions and Questions
 * Design an algorithm to find all the common elements in two sorted lists of numbers. 
 * For example, for the lists 2, 5, 5, 5 and 2, 2, 3, 5, 5, 7, the output should be 2, 5, 5. 
 * What is the maximum number of comparisons your algorithm makes if the lengths of the two given lists are m and n, respectively?  
 * In the worst-case scenario, where there are no common elements between the two lists, the algorithm will make m+n comparisons. 
 * What other situations might cause the algorithm to make fewer comparisons? 
 * Can you think of any optimizations for reducing comparisons when the lists contain many duplicate elements or when the lists are very large? 
 * Would you consider using other data structures or techniques for optimization? 
 * How would that impact time complexity?
 */

import java.util.*;

public class CommonElementsFinder {

    /**
     * Finds common elements between two sorted lists, including duplicates.
     * Uses the two-pointer technique to compare elements in order.
     * 
     * @param list1 First sorted list of integers
     * @param list2 Second sorted list of integers
     * @return A list containing common elements (with duplicates)
     */
    public static List<Integer> findCommonElements(List<Integer> list1, List<Integer> list2) {
        List<Integer> result = new ArrayList<>(); // Stores the common elements found
        int i = 0, j = 0; // Initialize pointers for both lists

        // Traverse both lists until one pointer reaches the end
        while (i < list1.size() && j < list2.size()) {
            int a = list1.get(i);
            int b = list2.get(j);

            if (a == b) {
                // If both elements are equal, it's a common element
                result.add(a);
                i++; // Move both pointers forward
                j++;
            } else if (a < b) {
                // Move pointer i forward if element in list1 is smaller
                i++;
            } else {
                // Move pointer j forward if element in list2 is smaller
                j++;
            }
        }

        return result; // Return the list of common elements
    }

    public static void main(String[] args) {
        // Example input lists
        List<Integer> list1 = Arrays.asList(2, 5, 5, 5);
        List<Integer> list2 = Arrays.asList(2, 2, 3, 5, 5, 7);

        // Output should be [2, 5, 5]
        System.out.println(findCommonElements(list1, list2));
    }
}
