import java.util.*;

public class LectureScheduler {

    static final int N = 7; // Total number of lectures

    // Adjacency matrix representing which lectures share students
    static int[][] A = {
        {0, 1, 1, 0, 1, 0, 0},
        {1, 0, 1, 1, 0, 1, 0},
        {1, 1, 0, 1, 0, 1, 1},
        {0, 1, 1, 0, 0, 1, 1},
        {1, 0, 0, 0, 0, 1, 1},
        {0, 1, 1, 1, 1, 0, 0},
        {0, 0, 1, 1, 1, 0, 0}
    };

    /**
     * Greedy graph coloring algorithm to assign minimum lecture time slots
     * @param graph Adjacency matrix
     * @return Minimum number of time slots (colors) used
     */
    static int greedyColoring(int[][] graph) {
        int[] result = new int[N];  // result[i] stores the time slot for lecture i
        Arrays.fill(result, -1);    // Initialize all lectures as unassigned
        result[0] = 0;              // Assign first lecture to slot 0

        boolean[] available = new boolean[N]; // Keeps track of available slots

        // Assign slots to remaining lectures
        for (int u = 1; u < N; u++) {
            Arrays.fill(available, true); // Reset availability before each assignment

            // Check all adjacent lectures to mark their slots as unavailable
            for (int v = 0; v < N; v++) {
                if (graph[u][v] == 1 && result[v] != -1) {
                    available[result[v]] = false;
                }
            }

            // Find the first available slot
            int cr;
            for (cr = 0; cr < N; cr++) {
                if (available[cr]) break;
            }

            result[u] = cr; // Assign the found slot
        }

        // Find the highest slot number used
        int maxColor = 0;
        for (int c : result) {
            maxColor = Math.max(maxColor, c);
        }

        // Print time slot assignment for each lecture
        System.out.println("Lecture time slots assigned:");
        for (int i = 0; i < N; i++) {
            System.out.println("Lecture " + i + " => Time Slot " + result[i]);
        }

        return maxColor + 1; // +1 because slots are 0-indexed
    }

    /**
     * Counts the number of unique conflicting student pairs (edges in the graph)
     * @param graph Adjacency matrix
     * @return Number of unique student conflicts
     */
    static int countConflicts(int[][] graph) {
        Set<String> conflicts = new HashSet<>();
        for (int i = 0; i < N; i++) {
            for (int j = i + 1; j < N; j++) {
                if (graph[i][j] == 1) {
                    // Use a string like "2-5" to represent the conflict between lecture 2 and 5
                    conflicts.add(i + "-" + j);
                }
            }
        }
        return conflicts.size(); // Return total unique conflict pairs
    }

    // Main method to run the scheduling logic
    public static void main(String[] args) {
        // Part 1: Calculate minimum time slots using greedy coloring
        int minTimeSlots = greedyColoring(A);

        // Part 2: Count unique student conflicts (edges)
        int conflictStudents = countConflicts(A);

        // Final output
        System.out.println("\nMinimum number of separate times for lectures: " + minTimeSlots);
        System.out.println("Minimum number of different students with conflicts: " + conflictStudents);
    }
}

