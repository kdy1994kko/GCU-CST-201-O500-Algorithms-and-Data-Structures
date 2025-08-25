// Greedy Algorithm
import java.util.*;

// Class representing an item with weight, value, and value-to-weight ratio
class Item {
    int weight, value;
    double ratio;

    // Constructor to initialize item and calculate ratio
    Item(int w, int v) {
        weight = w;
        value = v;
        ratio = (double) v / w;
    }
}

public class GreedyKnapsack {
    public static void main(String[] args) {
        // Given weights and values of items
        int[] weights = {20, 30, 40, 60, 70, 90};
        int[] values =  {70, 80, 90,110,120,200};
        int capacity = 280; // Max capacity of knapsack

        List<Item> items = new ArrayList<>();

        // Create a list of items
        for (int i = 0; i < weights.length; i++) {
            items.add(new Item(weights[i], values[i]));
        }

        // Sort items in decreasing order of value-to-weight ratio
        items.sort((a, b) -> Double.compare(b.ratio, a.ratio));

        double totalValue = 0; // Total value carried in knapsack
        int steps = 0; // Count of operations/decisions made

        // Loop through the sorted items
        for (Item item : items) {
            if (capacity >= item.weight) {
                // If item fits fully, take it
                capacity -= item.weight;
                totalValue += item.value;
                steps++;
            } else {
                // Take fractional part of the item (if allowed)
                totalValue += item.ratio * capacity;
                steps++;
                break; // Knapsack is full
            }
        }

        // Output results
        System.out.println("Greedy Max Value: " + totalValue);
        System.out.println("Greedy Steps: " + steps);
    }
}
