import java.util.Collection;
import java.util.List;
import java.util.ArrayList;

/**
 * Your implementation of an AVL Tree.
 *
 * @author Cameron Allotey
 * @userid callotey3
 * @GTID 903218636
 * @version 1.0
 */
public class AVL<T extends Comparable<? super T>> {
    // DO NOT ADD OR MODIFY INSTANCE VARIABLES.
    private AVLNode<T> root;
    private int size;

    /**
     * A no-argument constructor that should initialize an empty AVL.
     *
     * Since instance variables are initialized to their default values, there
     * is no need to do anything for this constructor.
     */
    public AVL() {
        // DO NOT IMPLEMENT THIS CONSTRUCTOR!
    }

    /**
     * Initializes the AVL tree with the data in the Collection. The data
     * should be added in the same order it appears in the Collection.
     *
     * @param data the data to add to the tree
     * @throws IllegalArgumentException if data or any element in data is null
     */
    public AVL(Collection<T> data) {
        this();
        if (data == null) {
            throw new IllegalArgumentException(
                "Given Collection is null, cannot be put into a tree");
        }
        for (T x : data) {
            if (data == null) {
                throw new IllegalArgumentException(
                    "Collection element is null, cannot be put into a tree");
            } else {
                add(x);
            }
        }
    }

    /**
     * Adds the data to the AVL. Start by adding it as a leaf like in a regular
     * AVL and then rotate the tree as needed.
     *
     * If the data is already in the tree, then nothing should be done (the
     * duplicate shouldn't get added, and size should not be incremented).
     *
     * Remember to recalculate heights and balance factors going up the tree,
     * rebalancing if necessary.
     *
     * @throws java.lang.IllegalArgumentException if the data is null
     * @param data the data to be added
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException(
                "Given data is null, cannot be put into a tree");
        } else {
            root = rAdd(this.root, data);
        }
    }

    /**
     * Recursive helper method to appropriately add data
     * @param root recursive root of subtree
     * @param data data to be added
     * @return the original root
     */
    private AVLNode<T> rAdd(AVLNode<T> root, T data) {
        if (root == null) {
            root = new AVLNode<T>(data);
            size++;
        } else if (data.compareTo(root.getData()) > 0) {
            root.setRight(rAdd(root.getRight(), data));
        } else if (data.compareTo(root.getData()) < 0) {
            root.setLeft(rAdd(root.getLeft(), data));
        }
        update(root);
        root = balance(root);

        return root;
    }

    /**
     * Removes the data from the tree. There are 3 cases to consider:
     *
     * 1: the data is a leaf. In this case, simply remove it.
     * 2: the data has one child. In this case, simply replace it with its
     * child.
     * 3: the data has 2 children. Use the successor to replace the data,
     * not the predecessor. As a reminder, rotations can occur after removing
     * the successor node.
     *
     * Remember to recalculate heights going up the tree, rebalancing if
     * necessary.
     *
     * @throws IllegalArgumentException if the data is null
     * @throws java.util.NoSuchElementException if the data is not found
     * @param data the data to remove from the tree.
     * @return the data removed from the tree. Do not return the same data
     * that was passed in.  Return the data that was stored in the tree.
     */
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException(
                "Given data is null, cannot be removed from tree");
        } else {
            AVLNode<T> ret = new AVLNode<T>(null);
            root = rRemove(this.root, ret, data);
            if (ret.getData() == null) {
                throw new java.util.NoSuchElementException(
                    "Element not found in tree");
            } else {
                return ret.getData();
            }
        }
    }

    /**
     * Recursive helper method to appropriately remove data
     * @param root recursive root of subtree
     * @param ret node for pointer reinforcement
     * @param data data to be removed
     * @return the original root
     */
    private AVLNode<T> rRemove(AVLNode<T> root, AVLNode<T> ret, T data) {
        if (root == null) {
            return null;
        } else if (data.compareTo(root.getData()) > 0) {
            root.setRight(rRemove(root.getRight(), ret, data));
        } else if (data.compareTo(root.getData()) < 0) {
            root.setLeft(rRemove(root.getLeft(), ret, data));
        } else {
            size--;
            ret.setData(root.getData());
            if (root.getLeft() == null && root.getRight() == null) {
                root = null;
            } else if (root.getLeft() == null) {
                root = root.getRight();
            } else if (root.getRight() == null) {
                root = root.getLeft();
            } else {
                AVLNode<T> temp = new AVLNode<T>(null);
                root.setRight(rGetSucc(null, root.getRight(), temp));
                root.setData(temp.getData());
            }
            update(root);
            return balance(root);
        }
        update(root);
        return balance(root);
    }

    /**
     * Recursive helper method to appropriately remove data predicate
     * @param root recursive root starting to the left of node
     * @param ret node to hold return data
     * @param parent grandparent node in case that grandchildren are
     * left after removal
     * @return the predicate
     */
    private AVLNode<T> rGetSucc(AVLNode<T> parent,
        AVLNode<T> root, AVLNode<T> ret) {
        //starts to the right
        if (root.getLeft() != null) {
            root.setLeft(rGetSucc(root, root.getLeft(), ret));
            update(root);
            return balance(root);
        } else {
            ret.setData(root.getData());
            return root.getRight();
        }
    }

    /**
     * Returns the data in the tree matching the parameter passed in (think
     * carefully: should you use value equality or reference equality?).
     *
     * @throws IllegalArgumentException if the data is null
     * @throws java.util.NoSuchElementException if the data is not found
     * @param data the data to search for in the tree.
     * @return the data in the tree equal to the parameter. Do not return the
     * same data that was passed in.  Return the data that was stored in the
     * tree.
     */
    public T get(T data) {
        if (data == null) {
            throw new IllegalArgumentException(
                "Cannot search for null data");
        } else {
            AVLNode<T> ans = rGet(root, data);
            if (ans == null) {
                throw new java.util.NoSuchElementException(
                    "Element not found in tree");
            }
            return ans.getData();
        }
    }

    /**
     * Returns whether or not data equivalent to the given parameter is
     * contained within the tree. The same type of equality should be used as
     * in the get method.
     *
     * @throws IllegalArgumentException if the data is null
     * @param data the data to search for in the tree.
     * @return whether or not the parameter is contained within the tree.
     */
    public boolean contains(T data) {
        if (data == null) {
            throw new IllegalArgumentException(
                "Cannot search for null data");
        } else {
            AVLNode<T> ans = rGet(root, data);
            if (ans == null) {
                return false;
            }
            return true;
        }
    }

    /**
     * Recursive helper method to get a node based on data
     * @param root recursive root of subtree
     * @param data data to be found
     * @return the found data
     */

    private AVLNode<T> rGet(AVLNode<T> root, T data) {
        if (root == null) {
            return null;
        }
        if (root.getData().compareTo(data) == 0) {
            return root;
        } else if (data.compareTo(root.getData()) > 0) {
            return rGet(root.getRight(), data);
        } else {
            return rGet(root.getLeft(), data);
        }
    }

    /**
     * Returns the data on branches of the tree with the maximum depth. If you
     * encounter multiple branches of maximum depth while traversing, then you
     * should list the remaining data from the left branch first, then the
     * remaining data in the right branch. This is essentially a preorder
     * traversal of the tree, but only of the branches of maximum depth.
     *
     * Your list should not duplicate data, and the data of a branch should be
     * listed in order going from the root to the leaf of that branch.
     *
     * Should run in worst case O(n), but you should not explore branches that
     * do not have maximum depth. You should also not need to traverse branches
     * more than once.
     *
     * Hint: How can you take advantage of the balancing information stored in
     * AVL nodes to discern deep branches?
     *
     * Example Tree:
     *                           10
     *                       /        \
     *                      5          15
     *                    /   \      /    \
     *                   2     7    13    20
     *                  / \   / \     \  / \
     *                 1   4 6   8   14 17  25
     *                /           \          \
     *               0             9         30
     *
     * Returns: [10, 5, 2, 1, 0, 7, 8, 9, 15, 20, 25, 30]
     *
     * @return the list of data in branches of maximum depth in preorder
     * traversal order
     */
    public List<T> deepestBranches() {
        ArrayList<T> myList = new ArrayList<T>();
        deepestBranches(this.root, myList);
        return myList;
    }

    /**
     * Private helper method to recursively add deepest branches
     * to a list input
     * @param root recursive root
     * @param myList list to be added to
     */
    private void deepestBranches(AVLNode<T> root, ArrayList<T> myList) {
        if (root == null) {
            return;
        } else {
            myList.add(root.getData());
        }

        if (root.getLeft() != null) {
            if (root.getRight() == null) {
                deepestBranches(root.getLeft(), myList);
            } else if (root.getLeft().getHeight()
                > root.getRight().getHeight()) {
                deepestBranches(root.getLeft(), myList);
            } else if (root.getLeft().getHeight()
                < root.getRight().getHeight()) {
                deepestBranches(root.getRight(), myList);
            } else if (root.getLeft().getHeight()
                == root.getRight().getHeight()) {
                deepestBranches(root.getLeft(), myList);
                deepestBranches(root.getRight(), myList);
            }
        } else {
            if (root.getRight() != null) {
                deepestBranches(root.getRight(), myList);
            }
        }
    }

    /**
     * Returns a sorted list of data that are within the threshold bounds of
     * data1 and data2. That is, the data should be > data1 and < data2.
     *
     * Should run in worst case O(n), but this is heavily dependent on the
     * threshold data. You should not explore branches of the tree that do not
     * satisfy the threshold.
     *
     * Example Tree:
     *                           10
     *                       /        \
     *                      5          15
     *                    /   \      /    \
     *                   2     7    13    20
     *                  / \   / \     \  / \
     *                 1   4 6   8   14 17  25
     *                /           \          \
     *               0             9         30
     *
     * sortedInBetween(7, 14) returns [8, 9, 10, 13]
     * sortedInBetween(3, 8) returns [4, 5, 6, 7]
     * sortedInBetween(8, 8) returns []
     *
     * @param data1 the smaller data in the threshold
     * @param data2 the larger data in the threshold
     * @throws java.lang.IllegalArgumentException if data1 or data2 are null
     * or if data1 > data2
     * @return a sorted list of data that is > data1 and < data2
     */
    public List<T> sortedInBetween(T data1, T data2) {
        if (data1 == null || data2 == null) {
            throw new IllegalArgumentException(
                "Thresholds cannot be null");
        }
        if (data1.compareTo(data2) > 0) {
            throw new IllegalArgumentException(
                "Lower limit must be given first");
        }
        ArrayList<T> myList = new ArrayList<T>();
        //get to starting node first
        AVLNode<T> start = betweenHelp(this.root, data1, data2);
        sortedInBetween(start, myList, data1, data2);
        return myList;
    }

    /**
     * Private helper method to recursively get to the values in range
     * if the tree root is not in range
     * @param root recursive root
     * @param data1 lower threshold
     * @param data2 upper threshold
     * @return the first found node in range
     */
    private AVLNode<T> betweenHelp(AVLNode<T> root, T data1, T data2) {
        if (root.getData().compareTo(data1) > 0
            && root.getData().compareTo(data2) < 0) {
            return root;
        } else {
            if (root.getData().compareTo(data2) > 0) {
                return betweenHelp(root.getLeft(), data1, data2);
            } else if (root.getData().compareTo(data1) < 0) {
                return betweenHelp(root.getRight(), data1, data2);
            } else {
                //will return empty list
                return root;
            }
        }
    }

    /**
     * Private helper method to recursively get the values in range
     * and add to input list
     * @param root recursive root
     * @param myList list to be added to
     * @param data1 lower threshold
     * @param data2 upper threshold
     */
    private void sortedInBetween(AVLNode<T> root, ArrayList<T> myList,
        T data1, T data2) {
        //if child out of range but parent in range need to check children
        if (root.getLeft() != null) {
            if (root.getLeft().getData().compareTo(data1) > 0
                && root.getLeft().getData().compareTo(data2) < 0) {
                sortedInBetween(root.getLeft(), myList, data1, data2);
            } else if (root.getLeft().getRight() != null) {
                sortedInBetween(root.getLeft().getRight(),
                    myList, data1, data2);
            }
        }
        if (root != null) {
            if (root.getData().compareTo(data1) > 0
                && root.getData().compareTo(data2) < 0) {
                myList.add(root.getData());
            }
        }

        if (root.getRight() != null) {
            if (root.getRight().getData().compareTo(data1) > 0
                && root.getRight().getData().compareTo(data2) < 0) {
                sortedInBetween(root.getRight(), myList, data1, data2);
            } else if (root.getRight().getLeft() != null) {
                sortedInBetween(root.getRight().getLeft(),
                    myList, data1, data2);
            }
        }
        
    }

    /**
     * Private helper method to update height and bf of input node
     *
     * @param root input node
     */
    private void update(AVLNode<T> root) {
        //update height and bf for given node
        if (root == null) {
            return;
        }
        int lc = -1; //height of left child
        int rc = -1; //height of right child
        if (root.getLeft() != null) {
            lc = root.getLeft().getHeight(); //0 unless previously updated
        }
        if (root.getRight() != null) {
            rc = root.getRight().getHeight(); //0 unless previously updated
        }
        root.setHeight(Math.max(lc, rc) + 1);
        root.setBalanceFactor(lc - rc);
    }

    /**
     * Private helper method to determine rotation type of input
     * subtree
     * @param root input subtree root
     * @return new subtree root
     */
    private AVLNode<T> balance(AVLNode<T> root) {
        if (root == null) {
            return null;
        }
        //rotate right
        if (root.getBalanceFactor() > 1) {
            if (root.getLeft() != null
                && root.getLeft().getBalanceFactor() < 0) {
                //left-right
                root.setLeft(leftRot(root.getLeft()));
            }
            root = rightRot(root);
            update(root);
        }
        if (root.getBalanceFactor() < -1) {
            //rotate left
            if (root.getRight() != null
                && root.getRight().getBalanceFactor() > 0) {
                //right - left
                root.setRight(rightRot(root.getRight()));
            }
            root = leftRot(root);
            update(root);
        }
        return root;
    }

    /**
     * Private helper method to perform right rotation on input
     * subtree
     * @param root input subtree root
     * @return new subtree root
     */
    private AVLNode<T> rightRot(AVLNode<T> root) {
        AVLNode<T> tempLeft = root.getLeft();
        AVLNode<T> tempChild = tempLeft.getRight();

        tempLeft.setRight(root);
        root.setLeft(tempChild);
        update(root);

        return tempLeft;
    }

    /**
     * Private helper method to perform left rotation on input
     * subtree
     * @param root input subtree root
     * @return new subtree root
     */
    private AVLNode<T> leftRot(AVLNode<T> root) {
        AVLNode<T> tempRight = root.getRight();
        AVLNode<T> tempChild = tempRight.getLeft();

        tempRight.setLeft(root);
        root.setRight(tempChild);
        update(root);

        return tempRight;
    }

    /**
     * Clears the tree.
     */
    public void clear() {
        this.root = null;
        this.size = 0;
    }

    /**
     * Returns the height of the root of the tree.
     *
     * Since this is an AVL, this method does not need to traverse the tree
     * and should be O(1)
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {
        if (root == null) {
            return -1;
        }
        return root.getHeight();
    }

    /**
     * Returns the size of the AVL tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return number of items in the AVL tree
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD
        return size;
    }

    /**
     * Returns the root of the AVL tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the root of the AVL tree
     */
    public AVLNode<T> getRoot() {
        // DO NOT MODIFY THIS METHOD!
        return root;
    }
}