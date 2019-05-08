import java.util.ArrayList;
/**
 * Your implementation of a max heap.
 *
 * @author Cameron Allotey
 * @userid callotey3
 * @GTID 903218636
 * @version 1.0
 */
public class MaxHeap<T extends Comparable<? super T>> {

    // DO NOT ADD OR MODIFY THESE INSTANCE/CLASS VARIABLES.
    public static final int INITIAL_CAPACITY = 13;

    private T[] backingArray;
    private int size;

    /**
     * Creates a Heap with an initial capacity of INITIAL_CAPACITY
     * for the backing array.
     *
     * Use the constant field provided. Do not use magic numbers!
     */
    public MaxHeap() {
        backingArray = (T[]) new Comparable[INITIAL_CAPACITY];
        backingArray[0] = null;
        size = 0;
    }

    /**
     * Creates a properly ordered heap from a set of initial values.
     *
     * You must use the BuildHeap algorithm that was taught in lecture! Simply
     * adding the data one by one using the add method will not get any credit.
     * As a reminder, this is the algorithm that involves building the heap
     * from the bottom up by repeated use of downHeap operations.
     *
     * The data in the backingArray should be in the same order as it appears
     * in the passed in ArrayList before you start the Build Heap Algorithm.
     *
     * The backingArray should have capacity 2n + 1 where n is the
     * number of data in the passed in ArrayList (not INITIAL_CAPACITY from
     * the interface). Index 0 should remain empty, indices 1 to n should
     * contain the data in proper order, and the rest of the indices should
     * be empty.
     *
     * @param data a list of data to initialize the heap with
     * @throws IllegalArgumentException if data or any element in data is null
     */
    public MaxHeap(ArrayList<T> data) {
        if (data == null) {
            throw new IllegalArgumentException(
                "Given arrayList is null!");
        }
        size = data.size();
        backingArray = (T[]) new Comparable[2 * size + 1];
        backingArray[0] = null;
        int count = 1;
        for (T x : data) {
            if (x == null) {
                throw new IllegalArgumentException(
                    "Given data is null!");
            }
            backingArray[count] = x;
            count++;
        }
        for (int i = size / 2; i > 0; i--) {
            //downheap (check if each element is null first)
            heapifyDown(i);
        }
    }

    /**
     * Adds an item to the heap. If the backing array is full and you're trying
     * to add a new item, then double its capacity.
     *
     * @throws IllegalArgumentException if the item is null
     * @param item the item to be added to the heap
     */
    public void add(T item) {
        if (item == null) {
            throw new IllegalArgumentException(
                "Null data cannot be added!");
        }
        if (size < backingArray.length - 1) {
            backingArray[size + 1] = item;
        } else {
            //resize
            T[] tempArr = (T[]) new Comparable[2 * backingArray.length];
            int count = 0;
            for (T x : backingArray) {
                tempArr[count] = backingArray[count];
                count++;
            }
            tempArr[size + 1] = item;
            backingArray = tempArr;
        }
        //heapify up
        size++;
        heapifyUp(size);
    }


    /**
     * Private helper to check parents and swap if necessary
     *
     * @param idx index to start at
     */
    private void heapifyUp(int idx) {
        while (idx > 1) {
            if (backingArray[idx].compareTo(backingArray[idx / 2]) > 0) {
                T x = backingArray[idx];
                backingArray[idx] = backingArray[idx / 2];
                backingArray[idx / 2] = x;
            }
            idx = idx / 2;
        }
    }

    /**
     * Removes and returns the max item of the heap. As usual for array-backed
     * structures, be sure to null out spots as you remove. Do not decrease the
     * capacity of the backing array.
     *
     * @throws java.util.NoSuchElementException if the heap is empty
     * @return the removed item
     */
    public T remove() {
        if (size == 0) {
            throw new java.util.NoSuchElementException(
                "Cannot remove from empty heap!");
        }

        T temp = backingArray[1];
        backingArray[1] = backingArray[size];
        backingArray[size] = null;
        size--;
        //heapify down
        heapifyDown(1);
        return temp;
    }

    /**
     * Private helper to check children and swap if necessary
     * @param idx index to heapify down from
     */
    private void heapifyDown(int idx) {
        int right;
        int left;
        while (2 * idx < size + 1) {
            left = 2 * idx;
            right = left + 1;
            T x = backingArray[idx];
            if (right < size + 1 && backingArray[right].compareTo(
                backingArray[left]) > 0) {
                if (backingArray[idx].compareTo(backingArray[right]) < 0) {
                    backingArray[idx] = backingArray[right];
                    backingArray[right] = x;
                }
                idx = right;
            } else {
                if (backingArray[idx].compareTo(backingArray[left]) < 0) {
                    backingArray[idx] = backingArray[left];
                    backingArray[left] = x;
                }
                idx = left;
            }
        }
    }

    /**
     * Returns the maximum element in the heap.
     *
     * @return the maximum element, null if the heap is empty
     */
    public T getMax() {
        return backingArray[1];
    }

    /**
     * Returns if the heap is empty or not.
     *
     * @return true if the heap is empty, false otherwise
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Clears the heap and rests the backing array to a new array of capacity
     * {@code INITIAL_CAPACITY}.
     */
    public void clear() {
        backingArray = (T[]) new Comparable[INITIAL_CAPACITY];
        backingArray[0] = null;
        size = 0;
    }

    /**
     * Returns the size of the heap.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return number of items in the heap
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }

    /**
     * Returns the backing array of the heap.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the backing array of the heap
     */
    public Object[] getBackingArray() {
        // DO NOT MODIFY THIS METHOD!
        return backingArray;
    }

}