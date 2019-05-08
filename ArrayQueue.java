/**
 * Your implementation of an array-backed queue.
 *
 * @author Cameron Allotey
 * @userid callotey3
 * @GTID 903218636
 * @version 1.0
 */
public class ArrayQueue<T> {

    // Do not add new instance variables.
    private T[] backingArray;
    private int front;
    private int size;

    /**
     * The initial capacity of a queue with fixed-size backing storage.
     */
    public static final int INITIAL_CAPACITY = 9;

    /**
     * Constructs a new ArrayQueue.
     */
    public ArrayQueue() {
        this.backingArray = (T[]) new Object[INITIAL_CAPACITY];
        this.size = 0;
        this.front = 0;
    }

    /**
     * Adds the given data to the queue.
     *
     * If sufficient space is not available in the backing array, you should
     * resize it to double the current length. If a resize is necessary,
     * you should copy elements to the front of the new array and reset
     * front to 0.
     *
     * This method should be implemented in amortized O(1) time.
     *
     * @param data the data to add
     * @throws IllegalArgumentException if data is null
     */
    public void enqueue(T data) {
        if (data == null) {
            throw new java.lang.IllegalArgumentException(
                "Cannot enqueue null data");
        } else if (size == 0) { //adding to an empty array
            backingArray[0] = data;
            ++size;
        } else if (size < backingArray.length) { 
            //adding without doubling backing length
            backingArray[(front + size++) % (backingArray.length)] = data;
        } else { //double backing array length first
            T[] temp = backingArray;
            backingArray = (T[]) new Object[2 * temp.length];
            for (int i = 0; i < size; ++i) {
                backingArray[i] = temp[(front + i) % size];
            }
            front = 0;
            backingArray[size++ % backingArray.length] = data;
        }
    }

    /**
     * Removes the data from the front of the queue.
     *
     * Do not shrink the backing array. If the queue becomes empty as a result
     * of this call, you should explicitly reset front to 0.
     *
     * You should replace any spots that you dequeue from with null. Failure to
     * do so can result in a loss of points.
     *
     * This method should be implemented in O(1) time.
     *
     * See the homework pdf for more information on implementation details.
     *
     * @return the data from the front of the queue
     * @throws java.util.NoSuchElementException if the queue is empty
     */
    public T dequeue() {
        if (size > 0) {
            T temp =  backingArray[front];
            backingArray[front] = null;
            --size;
            front = (front + 1) % backingArray.length;
            if (size == 0) {
                front = 0;
            }
            return temp;
        } else {
            throw new java.util.NoSuchElementException(
                "No data to return, queue is empty");
        }
    }

    /**
     * Retrieves the next data to be dequeued without removing it.
     *
     * This method should be implemented in O(1) time.
     *
     * @return the next data or null if the queue is empty
     */
    public T peek() {
        return backingArray[front];
    }

    /**
     * Returns the size of the queue.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return number of items in the queue
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }

    /**
     * Returns the backing array of the queue.
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
}