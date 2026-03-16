import java.io.*;
import java.util.*;

public class BuildHeapBottomUp {
    
    public static void main(String[] args) {
        System.out.println("=== TASK 1: BUILD HEAP FROM BOTTOM UP ===");
        
        try {
            // Test with small array first
            testWithSmallArray();
            
            // Then test with Ulysses words
            System.out.println("\n--- Testing with Ulysses words ---");
            String[] words = readWordsFromFile("ulysses.txt");
            
            if (words.length > 0) {
                System.out.println("Total words: " + words.length);
                
                // Build heap bottom-up and time it
                long startTime = System.nanoTime();
                
                HeapBottomUp heap = new HeapBottomUp(words);
                heap.buildHeap();
                
                long endTime = System.nanoTime();
                double timeMs = (endTime - startTime) / 1_000_000.0;
                
                System.out.println("Time to build heap bottom-up: " + timeMs + " ms");
                System.out.println("Heap built successfully!");
                
                // Display first few elements of heap
                heap.displayFirstN(20);
            }
            
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    private static void testWithSmallArray() {
        System.out.println("--- Testing with small array ---");
        String[] testWords = {"dog", "cat", "bird", "fish", "ant", "zebra", "lion"};
        
        System.out.println("Original array: " + Arrays.toString(testWords));
        
        HeapBottomUp heap = new HeapBottomUp(testWords);
        heap.buildHeap();
        
        System.out.println("Heap built (first few elements):");
        heap.displayFirstN(testWords.length);
    }
    
    private static String[] readWordsFromFile(String filename) throws IOException {
        ArrayList<String> wordsList = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String line;
        
        while ((line = reader.readLine()) != null) {
            String[] words = line.toLowerCase().split("[^a-zA-Z]+");
            for (String word : words) {
                if (!word.isEmpty()) {
                    wordsList.add(word);
                }
            }
        }
        reader.close();
        
        return wordsList.toArray(new String[0]);
    }
}

class HeapBottomUp {
    private String[] heap;
    private int size;
    
    public HeapBottomUp(String[] array) {
        this.heap = array.clone();
        this.size = array.length;
    }
    
    public void buildHeap() {
        // Start from last non-leaf node and heapify down
        for (int i = (size / 2) - 1; i >= 0; i--) {
            heapifyDown(i);
        }
    }
    
    private void heapifyDown(int i) {
        int largest = i;
        int left = 2 * i + 1;
        int right = 2 * i + 2;
        
        if (left < size && heap[left].compareTo(heap[largest]) > 0) {
            largest = left;
        }
        
        if (right < size && heap[right].compareTo(heap[largest]) > 0) {
            largest = right;
        }
        
        if (largest != i) {
            swap(i, largest);
            heapifyDown(largest);
        }
    }
    
    private void swap(int i, int j) {
        String temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;
    }
    
    public void displayFirstN(int n) {
        System.out.print("Heap contents: [");
        for (int i = 0; i < Math.min(n, size); i++) {
            System.out.print(heap[i]);
            if (i < Math.min(n, size) - 1) System.out.print(", ");
        }
        if (size > n) System.out.print(", ...");
        System.out.println("]");
    }
    
    public String[] getHeap() {
        return heap;
    }
}
