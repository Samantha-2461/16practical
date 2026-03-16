// I acknowledge the use of AI called Deepseek to assist me with understanding what the practical is asking for, to debug my code, to understand more about heap sort
import java.io.*;
import java.util.*;

public class BuildHeapBottomUp {
    
    public static void main(String[] args) {
        System.out.println("=== TASK 1: BUILD HEAP FROM BOTTOM UP (IN SITU) ===");
        
        try {
            // Part (c): Test with a short array first (not more than 20 words)
            testWithShortArray();
            
            // Then work with Ulysses words - using the supplied text version
            String filename = "ulysses.txt"; 
            System.out.println("\n--- Working with Ulysses words from " + filename + " ---");
            
            String[] words = readWordsFromFile(joyce1922_ulysses.text);
            
            if (words.length > 0) {
                System.out.println("Total words: " + words.length);
                System.out.println("First 20 words from Ulysses:");
                displayArraySample(words, 20);
                
                // Part (a): Build heap from the bottom up
                System.out.println("\n--- Part (a): Building heap from the bottom up ---");
                long startTime = System.nanoTime();
                
                // Build heap directly in the original array (in situ)
                buildHeapBottomUp(words);
                
                long endTime = System.nanoTime();
                double buildTimeMs = (endTime - startTime) / 1_000_000.0;
                
                System.out.println("Time to build heap bottom-up: " + buildTimeMs + " ms");
                
                // Display heap array sample
                System.out.println("\nHeap array after bottom-up construction (first 20 elements):");
                displayArraySample(words, 20);
                
                // Verify heap property
                boolean isValid = verifyHeapProperty(words);
                System.out.println("Heap property valid: " + isValid);
                
                // From this heap make a list of words sorted into alphabetical order
                System.out.println("\n--- Sorting the heap to get alphabetical order ---");
                startTime = System.nanoTime();
                heapSort(words);
                endTime = System.nanoTime();
                double sortTimeMs = (endTime - startTime) / 1_000_000.0;
                
                System.out.println("Time to sort: " + sortTimeMs + " ms");
                System.out.println("\nSorted array (first 20 words in alphabetical order):");
                displayArraySample(words, 20);
                
                // Verify sorting
                boolean isSorted = verifySorted(words);
                System.out.println("Array is correctly sorted: " + isSorted);
                System.out.println("\nTotal time (build + sort): " + (buildTimeMs + sortTimeMs) + " ms");
            } else {
                System.out.println("No words found in " + filename);
            }
            
        } catch (FileNotFoundException e) {
            System.out.println("ERROR: ulysses.txt not found!");
            System.out.println("Please make sure the Ulysses text file is in the current directory.");
            System.out.println("Current working directory: " + System.getProperty("user.dir"));
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }
    
    /**
     * Part (c): Test with a short array of not more than 20 words
     */
    private static void testWithShortArray() {
        System.out.println("\n" + "-".repeat(50));
        System.out.println("PART (c): TESTING WITH SHORT ARRAY (20 words)");
        System.out.println("-".repeat(50));
        
        // Create a short array of exactly 20 words
        String[] testWords = {
            "dog", "cat", "bird", "fish", "ant", "zebra", "lion", "elephant", "tiger", "bear",
            "monkey", "giraffe", "horse", "sheep", "goat", "cow", "duck", "frog", "bee", "owl"
        };
        
        System.out.println("Original array (" + testWords.length + " words):");
        System.out.println(Arrays.toString(testWords));
        
        // Make a copy for testing
        String[] testCopy = testWords.clone();
        
        // Build heap bottom-up
        buildHeapBottomUp(testCopy);
        System.out.println("\nAfter bottom-up heap construction:");
        System.out.println(Arrays.toString(testCopy));
        
        // Verify heap property
        System.out.println("Heap property valid: " + verifyHeapProperty(testCopy));
        
        // Sort the heap
        heapSort(testCopy);
        System.out.println("\nAfter heap sort (alphabetical order):");
        System.out.println(Arrays.toString(testCopy));
        
        // Verify sorting
        System.out.println("Array sorted correctly: " + verifySorted(testCopy));
        System.out.println();
    }
    
    /**
     * Part (a): Build heap from the bottom up (Floyd's algorithm)
     * Using the code for buildUp as a basis (as mentioned in instructions)
     */
    public static void buildHeapBottomUp(String[] array) {
        int n = array.length;
        
        // Start from the last non-leaf node and heapify down
        // Last non-leaf node is at index (n/2 - 1)
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapifyDown(array, n, i);
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
     * From this heap make a list of words sorted into alphabetical order
     */
    public static void heapSort(String[] array) {
        int n = array.length;
        
        // Note: The array should already be a heap when this method is called
        
        // Extract elements from heap one by one
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
     * Read and clean words from the Ulysses text file
     * Using the cleaned words from last week's practical
     */
    private static String[] readWordsFromFile(String filename) throws IOException {
        ArrayList<String> wordsList = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String line;
        int lineCount = 0;
        
        System.out.println("Reading and cleaning words from Ulysses...");
        
        while ((line = reader.readLine()) != null) {
            lineCount++;
            // Clean the words (remove punctuation, convert to lowercase)
            // This uses the same cleaning process from last week
            String[] words = line.toLowerCase().split("[^a-zA-Z]+");
            for (String word : words) {
                if (!word.isEmpty()) {
                    wordsList.add(word);
                }
            }
            
            // Progress indicator for large file
            if (lineCount % 1000 == 0) {
                System.out.print(".");
            }
        }
        reader.close();
        
        System.out.println("\nProcessed " + lineCount + " lines");
        return wordsList.toArray(new String[0]);
    }
}
