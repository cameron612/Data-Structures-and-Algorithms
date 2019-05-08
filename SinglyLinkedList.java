/**
 * Your implementation of a circular singly linked list.
 *
 * @author Cameron Allotey
 * @userid callotey3
 * @GTID 903218636
 * @version 1.0
 */
public class SinglyLinkedList<T> {
    // Do not add new instance variables or modify existing ones.
    private LinkedListNode<T> head;
    private int size;

    /**
     * Adds the element to the index specified.
     *
     * Adding to indices 0 and {@code size} should be O(1), all other cases are
     * O(n).
     *
     * @param index the requested index for the new element
     * @param data the data for the new element
     * @throws java.lang.IndexOutOfBoundsException if index is negative or
     * index > size
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addAtIndex(int index, T data) {
        if (data == null) {
            throw new java.lang.IllegalArgumentException(
                "Data cannot be null!");
        } else if (index < 0 || index > size) {
            throw new java.lang.IndexOutOfBoundsException(
                "Index cannot be < 0 or > size!");
        } else if (index == 0) {
            this.addToFront(data);
        } else if (index == size) {
            this.addToBack(data);
        } else {
            LinkedListNode<T> curr = head;
            for (int i = 0; i < index - 1; i++) {
                curr = curr.getNext();
            }
            curr.setNext(new LinkedListNode<T>(data, curr.getNext()));
            size++;
        }
    }

    /**
     * Adds the element to the front of the list.
     *
     * Must be O(1) for all cases.
     *
     * @param data the data for the new element
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToFront(T data) {
        if (data == null) {
            throw new java.lang.IllegalArgumentException(
                "Data cannot be null!");
        } else if (this.isEmpty()) {
            head = new LinkedListNode<T>(data, null);
            head.setNext(head);
            size++;
        } else {
            LinkedListNode<T> newNode = new LinkedListNode<T>(head.getData(),
                head.getNext());
            head.setData(data);
            head.setNext(newNode);
            size++;
        }
    }

    /**
     * Adds the element to the back of the list.
     *
     * Must be O(1) for all cases.
     *
     * @param data the data for the new element
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToBack(T data) {
        if (data == null) {
            throw new java.lang.IllegalArgumentException(
                "Data cannot be null!");
        } else if (this.isEmpty()) {
            head = new LinkedListNode<T>(data, null);
            head.setNext(head);
            size++;
        } else {
            LinkedListNode<T> newNode = new LinkedListNode<T>(head.getData(),
                head.getNext());
            head.setData(data);
            head.setNext(newNode);
            head = newNode;
            size++;
        }
    }

    /**
     * Removes and returns the element from the index specified.
     *
     * Removing from index 0 should be O(1), all other cases are O(n).
     *
     * @param index the requested index to be removed
     * @return the data formerly located at index
     * @throws java.lang.IndexOutOfBoundsException if index is negative or
     * index >= size
     */
    public T removeAtIndex(int index) {
        if (index < 0 || index >= size) {
            throw new java.lang.IndexOutOfBoundsException(
                "Index cannot be < 0 or >= size!");
        } else if (this.isEmpty()) {
            return null;
        } else {
            LinkedListNode<T> temp = head;
            if (size == 1) {
                head = null;
                size--;
                return temp.getData();
            } else if (index == 0) {
                return this.removeFromFront();
            } else if (index == size) {
                return this.removeFromBack();
            } else {
                for (int i = 0; i < index - 1; i++) {
                    temp = temp.getNext();
                }
                T ret = temp.getNext().getData();
                temp.setNext(temp.getNext().getNext());
                size--;
                return ret;
            }
        }
    }

    /**
     * Removes and returns the element at the front of the list. If the list is
     * empty, return {@code null}.
     *
     * Must be O(1) for all cases.
     *
     * @return the data formerly located at the front, null if empty list
     */
    public T removeFromFront() {
        if (this.isEmpty()) {
            return null;
        } else {
            LinkedListNode<T> temp = head;
            if (size == 1) {
                head = null;
                size--;
                return temp.getData();
            } else if (size == 2) {
                head = head.getNext();
                head.setNext(head);
                size--;
                return temp.getData();
            } else {
                T ret = temp.getData();
                LinkedListNode<T> temp2 = head.getNext();
                head.setNext(temp2.getNext());
                head.setData(temp2.getData());
                size--;
                return ret;
            }
        }
    }

    /**
     * Removes and returns the element at the back of the list. If the list is
     * empty, return {@code null}.
     *
     * Must be O(n) for all cases.
     *
     * @return the data formerly located at the back, null if empty list
     */
    public T removeFromBack() {
        if (this.isEmpty()) {
            return null;
        } else {
            LinkedListNode<T> temp = head;
            if (size == 1) {
                head = null;
                size--;
                return temp.getData();
            } else {
                LinkedListNode<T> temp2 = null;
                for (int i = 0; i < size - 1; i++) {
                    temp2 = temp;
                    temp = temp.getNext();
                }
                temp2.setNext(head);
                size--;
                return temp.getData();
            }
        }
    }

    /**
     * Removes the last copy of the given data from the list.
     *
     * Must be O(n) for all cases.
     *
     * @param data the data to be removed from the list
     * @return the removed data occurrence from the list itself (not the data
     * passed in), null if no occurrence
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public T removeLastOccurrence(T data) {
        if (data == null) {
            throw new java.lang.IllegalArgumentException(
                "Data cannot be null!");
        } else if (this.isEmpty()) {
            return null;
        } else {
            LinkedListNode<T> temp = head;
            if (size == 1) {
                if (head.getData().equals(data)) {
                    head = null;
                    size--;
                    return temp.getData();
                } else {
                    return null;
                }
            } else {
                LinkedListNode<T> last = null;
                for (int i = 0; i < size; i++) {
                    if (temp.getData().equals(data)) {
                        last = temp;
                    }
                    temp = temp.getNext();
                }
                if (last != null) {
                    T ret = last.getData();
                    last.setData(last.getNext().getData());
                    last.setNext(last.getNext().getNext());
                    size--;
                    return ret;
                } else {
                    return null;
                }
            }
        }
    }

    /**
     * Returns the element at the specified index.
     *
     * Getting index 0 should be O(1), all other cases are O(n).
     *
     * @param index the index of the requested element
     * @return the object stored at index
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or
     * index >= size
     */
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new java.lang.IndexOutOfBoundsException(
                "Index cannot be < 0 or >= size!");
        } else if (this.isEmpty()) {
            return null;
        } else if (index == 0) {
            return head.getData();
        }
        LinkedListNode<T> curr = head;
        for (int i = 0; i < size; i++) {
            if (i == index) {
                return curr.getData();
            }
            curr = curr.getNext();
        }
        return null;
    }

    /**
     * Returns an array representation of the linked list.
     *
     * Must be O(n) for all cases.
     *
     * @return an array of length {@code size} holding all of the objects in
     * this list in the same order
     */
    public Object[] toArray() {
        LinkedListNode<T> curr = head;
        Object[] ans = new Object[size];
        for (int i = 0; i < size; i++) {
            ans[i] = curr.getData();
            curr = curr.getNext();
        }
        return ans;
    }

    /**
     * Returns a boolean value indicating if the list is empty.
     *
     * Must be O(1) for all cases.
     *
     * @return true if empty; false otherwise
     */
    public boolean isEmpty() {
        return head == null;
    }

    /**
     * Clears the list of all data.
     *
     * Must be O(1) for all cases.
     */
    public void clear() {
        head = null;
        size = 0;
    }

    /**
     * Returns the number of elements in the list.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the list
     */
    public int size() {
        // DO NOT MODIFY!
        return size;
    }

    /**
     * Returns the head node of the linked list.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return node at the head of the linked list
     */
    public LinkedListNode<T> getHead() {
        // DO NOT MODIFY!
        return head;
    }
}