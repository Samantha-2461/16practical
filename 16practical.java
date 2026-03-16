// I acknowledge the use of AI called Deepseek to assist me with understanding what the practical is asking for, to debug my code, to understand more about heap sort
import java.io.*;
import java.util.*;

public class tryHeapsort {
    
    public static void main(String[] args) {
        System.out.println("=".repeat(70));
        System.out.println("CSC 211 - Fundamental Algorithms and Data Structures");
        System.out.println("Term 1 Practical 6: Heap Sort Implementation");
        System.out.println("=".repeat(70));
        
        try {
            
            testWithShortArray();
            
            // Read and clean words from file
            System.out.println("\n--- Reading words from " + filename + " ---");
            String[] words = readWordsFromFile(joyce1922_ulysses.text);
            
            if (words.length == 0) {
                System.out.println("No words found in the file.");
                return;
            }
            
            System.out.println("Total words read: " + words.length);
            System.out.println("First 20 words from file:");
            displayArraySample(words, 20);
            
            // Part (d) and (e): Compare timings for both methods
            compareHeapConstructionMethods(words);
            
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
            System.out.println("Current working directory: " + System.getProperty("user.dir"));
        }
    }
    
    /**
     * Part (c): Test with a short array of not more than 20 words
     */
    private static void testWithShortArray() {
        System.out.println("\n" + "-".repeat(50));
        System.out.println("PART (c): TESTING WITH SHORT ARRAY");
        System.out.println("-".repeat(50));
        
        // Create a short array of 15 words
        String[] testWords = {
            "dog", "cat", "bird", "fish", "ant", 
            "zebra", "lion", "elephant", "tiger", "bear",
            "monkey", "giraffe", "horse", "sheep", "goat"
        };
        
        System.out.println("Original array (" + testWords.length + " words):");
        System.out.println(Arrays.toString(testWords));
        System.out.println();
        
        // Test bottom-up heap construction and sort
        System.out.println("BOTTOM-UP APPROACH:");
        String[] bottomUpCopy = testWords.clone();
        
        // Build heap bottom-up
        buildHeapBottomUp(bottomUpCopy);
        System.out.println("After bottom-up heap construction:");
        System.out.println(Arrays.toString(bottomUpCopy));
        
        // Verify heap property
        System.out.println("Heap property valid: " + verifyHeapProperty(bottomUpCopy));
        
        // Sort using heap sort
        heapSort(bottomUpCopy);
        System.out.println("After heap sort (alphabetical order):");
        System.out.println(Arrays.toString(bottomUpCopy));
        System.out.println("Sorted correctly: " + verifySorted(bottomUpCopy));
        System.out.println();
        
        // Test top-down heap construction and sort
        System.out.println("TOP-DOWN APPROACH:");
        String[] topDownCopy = testWords.clone();
        
        // Build heap top-down
        buildHeapTopDown(topDownCopy);
        System.out.println("After top-down heap construction:");
        System.out.println(Arrays.toString(topDownCopy));
        
        // Verify heap property
        System.out.println("Heap property valid: " + verifyHeapProperty(topDownCopy));
        
        // Sort using heap sort
        heapSort(topDownCopy);
        System.out.println("After heap sort (alphabetical order):");
        System.out.println(Arrays.toString(topDownCopy));
        System.out.println("Sorted correctly: " + verifySorted(topDownCopy));
        
        // Verify both methods produce the same sorted result
        boolean sameResult = Arrays.equals(bottomUpCopy, topDownCopy);
        System.out.println("\nBoth methods produce the same sorted result: " + sameResult);
    }
    
    /**
     * Part (d) and (e): Compare timings for bottom-up vs top-down heap construction
     */
    private static void compareHeapConstructionMethods(String[] words) {
        System.out.println("\n" + "-".repeat(50));
        System.out.println("PART (d) & (e): TIMING COMPARISON");
        System.out.println("-".repeat(50));
        System.out.println("Running multiple tests for accurate timing...\n");
        
        int numTests = 5;
        long bottomUpTotalTime = 0;
        long topDownTotalTime = 0;
        long bottomUpSortTotalTime = 0;
        long topDownSortTotalTime = 0;
        
        for (int test = 1; test <= numTests; test++) {
            System.out.println("Test " + test + " of " + numTests + ":");
            
            // Create fresh copies for each test
            String[] wordsForBottomUp = words.clone();
            String[] wordsForTopDown = words.clone();
            
            // --- BOTTOM-UP APPROACH ---
            // Time just the heap construction
            long startTime = System.nanoTime();
            buildHeapBottomUp(wordsForBottomUp);
            long endTime = System.nanoTime();
            long bottomUpTime = endTime - startTime;
            bottomUpTotalTime += bottomUpTime;
            
            // Time the sorting phase (same for both methods)
            startTime = System.nanoTime();
            heapSort(wordsForBottomUp);
            endTime = System.nanoTime();
            long bottomUpSortTime = endTime - startTime;
            bottomUpSortTotalTime += bottomUpSortTime;
            
            System.out.printf("  Bottom-up: Construction = %.3f ms, Sort = %.3f ms%n", 
                bottomUpTime / 1_000_000.0, bottomUpSortTime / 1_000_000.0);
            
            // --- TOP-DOWN APPROACH ---
            // Time just the heap construction
            startTime = System.nanoTime();
            buildHeapTopDown(wordsForTopDown);
            endTime = System.nanoTime();
            long topDownTime = endTime - startTime;
            topDownTotalTime += topDownTime;
            
            // Time the sorting phase (same as bottom-up)
            startTime = System.nanoTime();
            heapSort(wordsForTopDown);
            endTime = System.nanoTime();
            long topDownSortTime = endTime - startTime;
            topDownSortTotalTime += topDownSortTime;
            
            System.out.printf("  Top-down:    Construction = %.3f ms, Sort = %.3f ms%n%n", 
                topDownTime / 1_000_000.0, topDownSortTime / 1_000_000.0);
            
            // Verify both methods produce correct sorting
            if (test == 1) {
                boolean bottomUpCorrect = verifySorted(wordsForBottomUp);
                boolean topDownCorrect = verifySorted(wordsForTopDown);
                System.out.println("  First test verification:");
                System.out.println("    Bottom-up sorted correctly: " + bottomUpCorrect);
                System.out.println("    Top-down sorted correctly: " + topDownCorrect);
                System.out.println();
            }
        }
        
        // Calculate averages
        double avgBottomUpConst = bottomUpTotalTime / (numTests * 1_000_000.0);
        double avgTopDownConst = topDownTotalTime / (numTests * 1_000_000.0);
        double avgBottomUpSort = bottomUpSortTotalTime / (numTests * 1_000_000.0);
        double avgTopDownSort = topDownSortTotalTime / (numTests * 1_000_000.0);
        double avgBottomUpTotal = avgBottomUpConst + avgBottomUpSort;
        double avgTopDownTotal = avgTopDownConst + avgTopDownSort;
        
        // Display final timing comparison
        System.out.println("\n" + "=".repeat(60));
        System.out.println("FINAL TIMING COMPARISON (Averages over " + numTests + " tests)");
        System.out.println("=".repeat(60));
        System.out.printf("%-25s %15s %15s %15s%n", "Method", "Construction", "Sort Phase", "Total Time");
        System.out.println("-".repeat(60));
        System.out.printf("%-25s %15.3f ms %15.3f ms %15.3f ms%n", 
            "Bottom-up", avgBottomUpConst, avgBottomUpSort, avgBottomUpTotal);
        System.out.printf("%-25s %15.3f ms %15.3f ms %15.3f ms%n", 
            "Top-down", avgTopDownConst, avgTopDownSort, avgTopDownTotal);
        System.out.println("-".repeat(60));
        
        double ratio = avgTopDownConst / avgBottomUpConst;
        System.out.printf("\nTop-down construction is %.2f times slower than bottom-up construction%n", ratio);
        
        if (avgBottomUpSort > 0 && avgTopDownSort > 0) {
            double sortRatio = Math.max(avgBottomUpSort, avgTopDownSort) / 
                              Math.min(avgBottomUpSort, avgTopDownSort);
            System.out.printf("Sort phase times are very similar (ratio: %.2f:1)%n", sortRatio);
        }
        
        System.out.println("\nNote: The sort phase is identical for both methods,");
        System.out.println("as once the heap is built, the sorting algorithm is the same.");
    }
    
    /**
     * Part (a): Build heap from the bottom up (Floyd's algorithm)
     * Time complexity: O(n)
     */
    public static void buildHeapBottomUp(String[] array) {
        int n = array.length;
        
        // Start from the last non-leaf node and heapify down
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapifyDown(array, n, i);
        }
    }
    
    /**
     * Part (b): Build heap from the top down by repeated insertions
     * Time complexity: O(n log n)
     */
    public static void buildHeapTopDown(String[] array) {
        int n = array.length;
        
        // Treat the array as if we're inserting elements one by one
        for (int i = 1; i < n; i++) {
            // Element at index i is the new element being inserted
            int current = i;
            int parent = (current - 1) / 2;
            
            // Heapify up: bubble the new element up to maintain heap property
            while (current > 0 && array[current].compareTo(array[parent]) > 0) {
                swap(array, current, parent);
                current = parent;
                parent = (current - 1) / 2;
            }
        }
    }
    
    /**
     * Heapify down procedure for max heap
     */
    private static void heapifyDown(String[] array, int n, int i) {
        int largest = i;
        int left = 2 * i + 1;
        int right = 2 * i + 2;
        
        // Compare with left child
        if (left < n && array[left].compareTo(array[largest]) > 0) {
            largest = left;
        }
        
        // Compare with right child
        if (right < n && array[right].compareTo(array[largest]) > 0) {
            largest = right;
        }
        
        // If largest is not the root, swap and continue heapifying
        if (largest != i) {
            swap(array, i, largest);
            heapifyDown(array, n, largest);
        }
    }
    
    /**
     * Heap sort using the in-place heap
     * This is the shareable sorting algorithm mentioned in part (d)
     */
    public static void heapSort(String[] array) {
        int n = array.length;
        
        // Note: The array should already be a heap when this method is called
        // We extract elements one by one
        
        for (int i = n - 1; i > 0; i--) {
            // Move current root to end
            swap(array, 0, i);
            
            // Heapify the reduced heap
            heapifyDown(array, i, 0);
        }
    }
    
    /**
     * Swap two elements in an array
     */
    private static void swap(String[] array, int i, int j) {
        String temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }
    
    /**
     * Verify that the array satisfies the max heap property
     */
    private static boolean verifyHeapProperty(String[] array) {
        int n = array.length;
        
        for (int i = 0; i <= n/2 - 1; i++) {
            int left = 2 * i + 1;
            int right = 2 * i + 2;
            
            // Check left child
            if (left < n && array[i].compareTo(array[left]) < 0) {
                return false;
            }
            
            // Check right child
            if (right < n && array[i].compareTo(array[right]) < 0) {
                return false;
            }
        }
        
        return true;
    }
    
    /**
     * Verify that the array is sorted in ascending order
     */
    private static boolean verifySorted(String[] array) {
        for (int i = 0; i < array.length - 1; i++) {
            if (array[i].compareTo(array[i + 1]) > 0) {
                return false;
            }
        }
        return true;
    }
    
    /**
     * Display a sample of the array (first n elements)
     */
    private static void displayArraySample(String[] array, int n) {
        System.out.print("[");
        for (int i = 0; i < Math.min(n, array.length); i++) {
            System.out.print(array[i]);
            if (i < Math.min(n, array.length) - 1) {
                System.out.print(", ");
            }
        }
        if (array.length > n) {
            System.out.print(", ...");
        }
        System.out.println("] (" + array.length + " total words)");
    }
    
    /**
     * Read and clean words from a text file
     */
    private static String[] readWordsFromFile(String filename) throws IOException {
        ArrayList<String> wordsList = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String line;
        int lineCount = 0;
        
        System.out.println("Reading and cleaning words from file...");
        
        while ((line = reader.readLine()) != null) {
            lineCount++;
            // Clean the words (remove punctuation, convert to lowercase)
            String[] words = line.toLowerCase().split("[^a-zA-Z]+");
            for (String word : words) {
                if (!word.isEmpty()) {
                    wordsList.add(word);
                }
            }
            
            // Progress indicator for large files
            if (lineCount % 1000 == 0) {
                System.out.print(".");
            }
        }
        reader.close();
        
        System.out.println("\nProcessed " + lineCount + " lines");
        return wordsList.toArray(new String[0]);
    }
}
