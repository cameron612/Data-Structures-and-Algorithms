/**
 * Your implementation of an ArrayList.
 *
 * @author Cameron Allotey
 * @userid callotey3
 * @GTID 903218636
 * @version 1.0
 */
public class ArrayList<T> {

    // Do not add new instance variables.
    private T[] backingArray;
    private int size;

    /**
     * The initial capacity of the array list.
     *
     * DO NOT CHANGE THIS VARIABLE.
     */
    public static final int INITIAL_CAPACITY = 9;

    /**
     * Constructs a new ArrayList.
     *
     * Java does not allow for regular generic array creation, so you will have
     * to cast an Object array to T[] to get the generic typing.
     */
    public ArrayList() {
        this.backingArray = (T[]) new Object[INITIAL_CAPACITY];
        this.size = 0;
    }

    /**
     * Adds the element to the index specified.
     *
     * Remember that this add may require elements to be shifted.
     *
     * Adding to index {@code size} should be amortized O(1),
     * all other adds are O(n).
     *
     * @param index the index where you want the new element
     * @param data the data to add to the list
     * @throws java.lang.IndexOutOfBoundsException if index is negative
     * or index > size
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addAtIndex(int index, T data) {
        if (data == null) {
            throw new java.lang.IllegalArgumentException();
        } else if (index > size || index < 0) { 
            throw new java.lang.IndexOutOfBoundsException();
        } else if (index == size) {
            this.addToBack(data);
        } else if (size < backingArray.length) {
            this.shift(index);
            backingArray[index] = data;
            ++size;
        } else { //double backing array length first
            boolean shifted = false;
            T[] temp = backingArray;
            backingArray = (T[]) new Object[2 * temp.length];
            for (int i = 0; i < temp.length; ++i) {
                if (i == index) {
                    backingArray[i] = data;
                    shifted = true;
                } else {
                    if (shifted) {
                        backingArray[i + 1] = temp[i];
                    } else {
                        backingArray[i] = temp[i];
                    }
                }
            }
            ++size;
        }
    }

    /**
     * Adds the given data to the front of your array list.
     *
     * Remember that this add may require elements to be shifted.
     * 
     * Must be O(n).
     *
     * @param data the data to add to the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToFront(T data) {
        if (data == null) {
            throw new java.lang.IllegalArgumentException();
        } else if (this.isEmpty()) { //adding to an empty array
            backingArray[0] = data;
            ++size;
        } else if (size < backingArray.length) { 
            //adding without doubling backing length
            this.shift(0);
            backingArray[0] = data;
            ++size;
        } else { //double backing array length first
            T[] temp = backingArray;
            backingArray = (T[]) new Object[2 * temp.length];
            backingArray[0] = data;
            for (int i = 0; i < temp.length; ++i) {
                backingArray[i + 1] = temp[i];
            }
            ++size;
        }
    }

    /**
     * Adds the given data to the back of your array list.
     *
     * Must be amortized O(1).
     *
     * @param data the data to add to the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToBack(T data) {
        if (data == null) {
            throw new java.lang.IllegalArgumentException("Cannot insert null data into data structure");
        } else if (this.size() == backingArray.length) {
            this.dblLength();
            backingArray[size++] = data;
        } else {
            backingArray[size++] = data;
        }
    }

    /**
     * Removes and returns the element at {@code index}.
     *
     * Remember that this remove may require elements to be shifted.
     *
     * This method should be O(1) for index {@code size - 1} and O(n) in 
     * all other cases.
     *
     * @param index the index of the element
     * @return the object that was formerly at that index
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or
     * index >= size
     */
    public T removeAtIndex(int index) {
        if (index < 0 || index >= size) {
            throw new java.lang.IndexOutOfBoundsException();
        } else if (size > 0) {
            T temp = backingArray[index];
            backingArray[index] = null;
            --size;
            this.shiftUp(index);
            return temp;
        } else {
            return null;
        }
    }

    /**
     * Removes and returns the first element in the list.
     *
     * Remember that this remove may require elements to be shifted.
     *
     * Must be O(n).
     *
     * @return the data from the front of the list or null if the list is empty
     */
    public T removeFromFront() {
        if (size > 0) {
            T temp =  backingArray[0];
            backingArray[0] = null;
            --size;
            this.shiftUp(0);
            return temp;
        } else {
            return null;
        }
    }

    /**
     * Removes and returns the last element in the list.
     * 
     * Must be O(1).
     *
     * @return the data from the back of the list or null if the list is empty
     */
    public T removeFromBack() {
        if (size > 0) {
            T temp =  backingArray[size - 1];
            backingArray[(size--) - 1] = null;
            return temp;
        } else {
            return null;
        }
    }

    /**
     * Returns the element at the given index.
     *
     * Must be O(1).
     *
     * @param index the index of the element
     * @return the data stored at that index
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or
     * index >= size
     */
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new java.lang.IndexOutOfBoundsException();
        } else {
            return backingArray[index];
        }
    }

    /**
     * Finds the index at which the given data is located in the ArrayList.
     *
     * If there are multiple instances of the data in the ArrayList, then return
     * the index of the last instance.
     *
     * Be sure to think carefully about whether value or reference equality
     * should be used.
     *
     * Must be O(n), but consider which end of the ArrayList to start from.
     *
     * @param data the data to find the last index of
     * @return the last index of the data or -1 if the data is not in the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public int lastIndexOf(T data) {
        int count = 0;
        int idx = -1;
        if (data == null) {
            throw new java.lang.IllegalArgumentException();
        } else {
            for (T a : backingArray) {
                if (a != null && a.equals(data)) {
                    idx = count;
                }
                ++count;
            }
            return idx;
        }
    }

    /**
     * Returns a boolean value representing whether or not the list is empty.
     *
     * Must be O(1).
     *
     * @return true if empty; false otherwise
     */
    public boolean isEmpty() {
        return this.size() == 0;
    }

    /**
     * Clears the list. Resets the backing array to a new array of the initial
     * capacity.
     *
     * Must be O(1).
     */
    public void clear() {
        size = 0;
        backingArray = (T[]) new Object[INITIAL_CAPACITY];
    }

    /**
     * Returns the size of the list as an integer.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the list
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }

    /**
     * Returns the backing array for this list.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the backing array for this list
     */
    public Object[] getBackingArray() {
        // DO NOT MODIFY THIS METHOD!
        return backingArray;
    }


    /**
     * helper to shift backing array down by 1
     * @param idx index to shift from
     */
    private void shift(int idx) {
        for (int i = this.size(); i > idx; i--) { 
            backingArray[i] = backingArray[i - 1]; 
        }
    }

    /**
     * helper to shift backing array up by 1
     * @param idx index to shift to
     */
    private void shiftUp(int idx) {
        for (int i = idx; i < backingArray.length - 1; i++) { 
            backingArray[i] = backingArray[i + 1]; 
        }
        backingArray[size] = null;
    }

    /**
     * helper to double backing array length
     */
    private void dblLength() {
        T[] temp = backingArray;
        backingArray = (T[]) new Object[2 * temp.length];
        for (int i = 0; i < temp.length; ++i) {
            backingArray[i] = temp[i];
        }
    }
}
