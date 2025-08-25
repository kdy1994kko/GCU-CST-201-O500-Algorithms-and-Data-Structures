import java.util.*;

public class GraphCycleFinder {

    // Define infinity as a large constant
    static int INF = Integer.MAX_VALUE;

    // Store the minimum weight found
    static int minWeight = INF;

    // Store all cycles that have the minimum weight
    static List<List<Integer>> minCycles = new ArrayList<>();

    public static void main(String[] args) {
        // Define the weight matrix representing the graph
        int[][] W = {
            {0, 3, INF, INF, 1},
            {INF, 0, 6, INF, 3},
            {1, INF, 0, INF, INF},
            {-4, INF, 5, 0, INF},
            {INF, INF, 2, 2, 0}
        };

        int n = W.length; // Number of vertices

        // Run DFS from each vertex to find all simple cycles
        for (int i = 0; i < n; i++) {
            dfs(W, i, i,
                new HashSet<>(Set.of(i)), // Initially visited node is itself
                new HashSet<>(),          // No visited edges yet
                new ArrayList<>(List.of(i)), // Start path with current node
                0); // Initial weight is 0
        }

        // Output the result
        System.out.println("Minimum Weight: " + minWeight);
        System.out.println("Cycles with Minimum Weight:");
        for (List<Integer> cycle : minCycles) {
            System.out.println(cycle);
        }
    }

    // Depth-First Search to find cycles
    static void dfs(int[][] graph, int start, int node,
                    Set<Integer> visitedNodes,
                    Set<String> visitedEdges,
                    List<Integer> path,
                    int weight) {

        // If we returned to start and path has more than one node, it's a valid cycle
        if (node == start && path.size() > 1) {
            if (weight < minWeight) {
                minWeight = weight;
                minCycles.clear(); // Found a better cycle, discard previous
                minCycles.add(new ArrayList<>(path));
            } else if (weight == minWeight) {
                minCycles.add(new ArrayList<>(path)); // Same weight, keep both
            }
            return;
        }

        int n = graph.length;
        for (int neighbor = 0; neighbor < n; neighbor++) {
            if (graph[node][neighbor] != INF) {
                String edge = node + "," + neighbor;

                // Skip already visited edges (no repeats)
                if (visitedEdges.contains(edge)) continue;

                // Skip visiting the same node again unless returning to start
                if (visitedNodes.contains(neighbor) && neighbor != start) continue;

                // Mark edge and node as visited
                visitedNodes.add(neighbor);
                visitedEdges.add(edge);
                path.add(neighbor);

                // Recursive call with updated weight
                dfs(graph, start, neighbor, visitedNodes, visitedEdges, path,
                    weight + graph[node][neighbor]);

                // Backtrack
                path.remove(path.size() - 1);
                visitedEdges.remove(edge);
                visitedNodes.remove(neighbor);
            }
        }
    }
}
