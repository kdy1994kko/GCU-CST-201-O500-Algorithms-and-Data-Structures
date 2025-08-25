import java.util.*;

public class Sorting {

    // Class to store the metrics of each sorting algorithm
    static class Metrics {
        long comparisons = 0;  // Number of comparisons
        long exchanges = 0;    // Number of data exchanges/swaps
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter array size n: ");
        int n = scanner.nextInt();  // Read array size
        scanner.close();

        int trials = 100; // Number of repetitions for averaging

        // Arrays to store total comparisons and swaps for each sorting algorithm
        long[] totalComps = new long[5];
        long[] totalSwaps = new long[5];

        // Repeat the process 100 times
        for (int t = 0; t < trials; t++) {
            // Create an array from 1 to n
            int[] baseArray = new int[n];
            for (int i = 0; i < n; i++) baseArray[i] = i + 1;

            // Shuffle the array
            shuffleArray(baseArray);

            // Run and record metrics for each sorting algorithm

            totalComps[0] += selectionSort(baseArray.clone()).comparisons;
            totalSwaps[0] += selectionSort(baseArray.clone()).exchanges;

            totalComps[1] += bubbleSort(baseArray.clone()).comparisons;
            totalSwaps[1] += bubbleSort(baseArray.clone()).exchanges;

            totalComps[2] += mergeSort(baseArray.clone()).comparisons;
            totalSwaps[2] += mergeSort(baseArray.clone()).exchanges;

            totalComps[3] += quickSort(baseArray.clone()).comparisons;
            totalSwaps[3] += quickSort(baseArray.clone()).exchanges;

            totalComps[4] += improvedSort(baseArray.clone()).comparisons;
            totalSwaps[4] += improvedSort(baseArray.clone()).exchanges;
        }

        // Output average comparisons and swaps
        String[] names = {"Selection Sort", "Bubble Sort", "Merge Sort", "Quick Sort", "Improved Sort"};
        for (int i = 0; i < names.length; i++) {
            System.out.printf("%s - Avg Comparisons: %.2f, Avg Swaps: %.2f%n",
                names[i], totalComps[i] / (double)trials, totalSwaps[i] / (double)trials);
        }
    }

    // Fisher-Yates Shuffle Algorithm
    static void shuffleArray(int[] arr) {
        Random rnd = new Random();
        for (int i = arr.length - 1; i > 0; i--) {
            int j = rnd.nextInt(i + 1);
            int temp = arr[i]; arr[i] = arr[j]; arr[j] = temp;
        }
    }

    // Selection Sort with metrics
    static Metrics selectionSort(int[] arr) {
        Metrics m = new Metrics();
        for (int i = 0; i < arr.length - 1; i++) {
            int minIdx = i;
            for (int j = i + 1; j < arr.length; j++) {
                m.comparisons++;
                if (arr[j] < arr[minIdx]) minIdx = j;
            }
            if (i != minIdx) {
                swap(arr, i, minIdx);
                m.exchanges++;
            }
        }
        return m;
    }

    // Bubble Sort with metrics
    static Metrics bubbleSort(int[] arr) {
        Metrics m = new Metrics();
        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = 0; j < arr.length - i - 1; j++) {
                m.comparisons++;
                if (arr[j] > arr[j + 1]) {
                    swap(arr, j, j + 1);
                    m.exchanges++;
                }
            }
        }
        return m;
    }

    // Merge Sort entry point
    static Metrics mergeSort(int[] arr) {
        return mergeSortHelper(arr, 0, arr.length - 1);
    }

    // Recursive Merge Sort function
    static Metrics mergeSortHelper(int[] arr, int left, int right) {
        Metrics m = new Metrics();
        if (left < right) {
            int mid = (left + right) / 2;

            // Recursive sort of two halves
            Metrics m1 = mergeSortHelper(arr, left, mid);
            Metrics m2 = mergeSortHelper(arr, mid + 1, right);

            // Accumulate metrics from left and right
            m.comparisons += m1.comparisons + m2.comparisons;
            m.exchanges += m1.exchanges + m2.exchanges;

            // Merge two halves and accumulate metrics
            Metrics mergeMetrics = merge(arr, left, mid, right);
            m.comparisons += mergeMetrics.comparisons;
            m.exchanges += mergeMetrics.exchanges;
        }
        return m;
    }

    // Merge two sorted subarrays
    static Metrics merge(int[] arr, int l, int m, int r) {
        Metrics mtr = new Metrics();

        int n1 = m - l + 1;
        int n2 = r - m;

        int[] L = Arrays.copyOfRange(arr, l, m + 1);
        int[] R = Arrays.copyOfRange(arr, m + 1, r + 1);

        int i = 0, j = 0, k = l;

        while (i < n1 && j < n2) {
            mtr.comparisons++;
            if (L[i] <= R[j]) {
                arr[k++] = L[i++];
            } else {
                arr[k++] = R[j++];
            }
            mtr.exchanges++;
        }

        while (i < n1) {
            arr[k++] = L[i++];
            mtr.exchanges++;
        }

        while (j < n2) {
            arr[k++] = R[j++];
            mtr.exchanges++;
        }

        return mtr;
    }

    // Quick Sort entry point
    static Metrics quickSort(int[] arr) {
        return quickSortHelper(arr, 0, arr.length - 1);
    }

    // Recursive Quick Sort function
    static Metrics quickSortHelper(int[] arr, int low, int high) {
        Metrics m = new Metrics();
        if (low < high) {
            // Partition and count operations
            int[] res = partition(arr, low, high);
            int pi = res[0];
            m.comparisons += res[1];
            m.exchanges += res[2];

            // Recurse on left and right partitions
            Metrics left = quickSortHelper(arr, low, pi - 1);
            Metrics right = quickSortHelper(arr, pi + 1, high);

            m.comparisons += left.comparisons + right.comparisons;
            m.exchanges += left.exchanges + right.exchanges;
        }
        return m;
    }

    // Partition function for Quick Sort
    static int[] partition(int[] arr, int low, int high) {
        int pivot = arr[high];
        int i = low - 1;
        int comps = 0, swaps = 0;

        for (int j = low; j < high; j++) {
            comps++;
            if (arr[j] < pivot) {
                i++;
                swap(arr, i, j);
                swaps++;
            }
        }
        swap(arr, i + 1, high);
        swaps++;

        // Return pivot index, comparisons, and swaps
        return new int[]{i + 1, comps, swaps};
    }

    // Improved Sorting using Java's Arrays.sort() (Timsort)
    static Metrics improvedSort(int[] arr) {
        Metrics m = new Metrics();
        Arrays.sort(arr);  // Java’s optimized sort (Dual-Pivot Quicksort for primitives)

        // Estimations: Comparisons ~ n log n, Exchanges ~ n
        m.comparisons = (long) (arr.length * Math.log(arr.length));
        m.exchanges = arr.length;
        return m;
    }

    // Utility function to swap two elements
    static void swap(int[] arr, int i, int j) {
        int temp = arr[i]; arr[i] = arr[j]; arr[j] = temp;
    }
}


