import java.util.Scanner;

public class TowerOfHanoi {

    // Method to calculate the number of moves made by ith largest disk
    public static long calculateMoves(int n, int i) {
        // Validate if i is within correct range
        if (i < 1 || i > n) {
            throw new IllegalArgumentException("Disk number i must be between 1 and n.");
        }

        // Number of moves = 2^(n - i)
        return (long) Math.pow(2, n - i);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Prompt user to enter total number of disks
        System.out.print("Enter total number of disks (n): ");
        int n = scanner.nextInt();

        // Prompt user to enter which disk to analyze
        System.out.print("Enter disk number (i) to count moves for: ");
        int i = scanner.nextInt();

        // Validate input and compute result
        if (i < 1 || i > n) {
            System.out.println("Invalid input. Disk number i must be between 1 and n.");
        } else {
            long moves = calculateMoves(n, i);

            // Display result
            System.out.println("Number of moves made by disk " + i + " : " + moves);
        }

        scanner.close();
    }
}


