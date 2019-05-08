/**
 * Your implementation of a linked stack. It should NOT be circular.
 *
 * @author Cameron Allotey
 * @userid callotey3
 * @GTID 903218636
 * @version 1.0
 */
public class LinkedStack<T> {

    // Do not add new instance variables.
    private LinkedNode<T> head;
    private int size;

    /**
     * Adds the given data onto the stack. The given element becomes the
     * top-most element of the stack.
     *
     * This method should be implemented in O(1) time.
     *
     * @param data the data to add
     * @throws IllegalArgumentException if data is null
     */
    public void push(T data) {
        if (data == null) {
            throw new java.lang.IllegalArgumentException(
                "Null data cannot be pushed to the stack");
        } else if (size == 0) {
            head = new LinkedNode<T>(data, null);
            size++;
        } else {
            LinkedNode<T> newNode = new LinkedNode<T>(data,
                head);
            head = newNode;
            size++;
        }
    }

    /**
     * Removes and returns the top-most element on the stack.
     *
     * This method should be implemented in O(1) time.
     *
     * @return the data from the top of the stack
     * @throws java.util.NoSuchElementException if the stack is empty
     */
    public T pop() {
        if (size == 0) {
            throw new java.util.NoSuchElementException(
                "No data to return, stack is empty");
        } else {
            LinkedNode<T> temp = head;
            if (size == 1) {
                head = null;
                size--;
                return temp.getData();
            } else if (size == 2) {
                head = head.getNext();
                head.setNext(null);
                size--;
                return temp.getData();
            } else {
                T ret = temp.getData();
                head = head.getNext();
                size--;
                return ret;
            }
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
        if (head == null) {
            return null;
        } else {
            return head.getData();
        }
    }

    /**
     * Return the size of the stack.
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
     * Returns the head node of the stack.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the head node
     */
    public LinkedNode<T> getHead() {
        // DO NOT MODIFY THIS METHOD!
        return head;
    }
}