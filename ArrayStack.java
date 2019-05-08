/**
 * Your implementation of an array-backed stack.
 *
 * @author Cameron Allotey
 * @userid callotey3
 * @GTID 903218636
 * @version 1.0
 */
public class ArrayStack<T> {

    // Do not add new instance variables.
    private T[] backingArray;
    private int size;

    /**
     * The initial capacity of a stack with fixed-size backing storage.
     */
    public static final int INITIAL_CAPACITY = 9;

    /**
     * Constructs a new ArrayStack.
     */
    public ArrayStack() {
        this.backingArray = (T[]) new Object[INITIAL_CAPACITY];
        this.size = 0;
    }

    /**
     * Adds the given data onto the stack. The given element becomes the
     * top-most element of the stack.
     *
     * If sufficient space is not available in the backing array, you should
     * resize it to double the current length.
     *
     * This method should be implemented in amortized O(1) time.
     *
     * @param data the data to add
     * @throws IllegalArgumentException if data is null
     */
    public void push(T data) {
        if (data == null) {
            throw new java.lang.IllegalArgumentException(
                "Cannot push null data onto stack");
        } else if (this.size() == backingArray.length) {
            this.dblLength();
            backingArray[size++] = data;
        } else {
            backingArray[size++] = data;
        }
    }

    /**
     * Removes and returns the top-most element on the stack.
     *
     * Do not shrink the backing array.
     *
     * You should replace any spots that you pop from with null. Failure to do
     * so can result in a loss of points.
     *
     * This method should be implemented in O(1) time.
     *
     * @return the data from the top of the stack
     * @throws java.util.NoSuchElementException if the stack is empty
     */
    public T pop() {
        if (size > 0) {
            T temp =  backingArray[size - 1];
            backingArray[(size--) - 1] = null;
            return temp;
        } else {
            throw new java.util.NoSuchElementException(
                "No data to return, stack is empty");
        }
    }

    /**
     * Retrieves the next element to be popped without removing it.
     *
     * This method should be implemented in O(1) time.
     *
     * @return the next data or null if the stack is empty
     */
    public T peek() {
        if (size == 0) {
            return null;
        } else {
            return backingArray[size - 1];
        }
    }

    /**
     * Returns the size of the stack.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return number of items in the stack
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }

    /**
     * Returns the backing array of the stack.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the backing array
     */
    public Object[] getBackingArray() {
        // DO NOT MODIFY THIS METHOD!
        return backingArray;
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