import java.util.Collection;
import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Your implementation of a binary search tree.
 *
 * @author Cameron Allotey
 * @userid callotey3
 * @GTID 903218636
 * @version 1.0
 */
public class BST<T extends Comparable<? super T>> {
    // DO NOT ADD OR MODIFY INSTANCE VARIABLES.
    private BSTNode<T> root;
    private int size;

    /**
     * A no-argument constructor that should initialize an empty BST.
     *
     * Since instance variables are initialized to their default values, there
     * is no need to do anything for this constructor.
     */
    public BST() {
        // DO NOT IMPLEMENT THIS CONSTRUCTOR!
    }

    /**
     * Initializes the BST with the data in the Collection. The data
     * should be added in the same order it is in the Collection.
     *
     * Hint: Not all Collections are indexable like Lists, so a regular for loop
     * will not work here. However, all Collections are Iterable, so what type
     * of loop would work?
     *
     * @param data the data to add to the tree
     * @throws IllegalArgumentException if data or any element in data is null
     */
    public BST(Collection<T> data) {
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
     * Add the data as a leaf in the BST. Should traverse the tree to find the
     * appropriate location. If the data is already in the tree, then nothing
     * should be done (the duplicate shouldn't get added, and size should not be
     * incremented).
     * 
     * Should have a running time of O(log n) for a balanced tree, and a worst
     * case of O(n).
     *
     * @throws IllegalArgumentException if the data is null
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
    private BSTNode<T> rAdd(BSTNode<T> root, T data) {
        if (root == null) {
            root = new BSTNode<T>(data);
            size++;
        } else if (data.compareTo(root.getData()) > 0) {
            root.setRight(rAdd(root.getRight(), data));
        } else if (data.compareTo(root.getData()) < 0) {
            root.setLeft(rAdd(root.getLeft(), data));
        }
        return root;
    }

    /**
     * Removes the data from the tree. There are 3 cases to consider:
     *
     * 1: the data is a leaf (no children). In this case, simply remove it.
     * 2: the data has one child. In this case, simply replace it with its
     * child.
     * 3: the data has 2 children. Use the predecessor to replace the data.
     * You MUST use recursion to find and remove the predecessor (you will
     * likely need an additional helper method to handle this case efficiently).
     *
     * Should have a running time of O(log n) for a balanced tree, and a worst
     * case of O(n).
     *
     * @throws IllegalArgumentException if the data is null
     * @throws java.util.NoSuchElementException if the data is not found
     * @param data the data to remove from the tree.
     * @return the data removed from the tree. Do not return the same data
     * that was passed in. Return the data that was stored in the tree.
     */
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException(
                "Given data is null, cannot be removed from tree");
        } else {
            BSTNode<T> ret = new BSTNode<T>(null);
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
    private BSTNode<T> rRemove(BSTNode<T> root, BSTNode<T> ret, T data) {
        if (root == null) {
            return root;
        } else if (data.compareTo(root.getData()) > 0) {
            root.setRight(rRemove(root.getRight(), ret, data));
        } else if (data.compareTo(root.getData()) < 0) {
            root.setLeft(rRemove(root.getLeft(), ret, data));
        } else {
            size--;
            ret.setData(root.getData());
            if (root.getLeft() == null && root.getRight() == null) {
                return null;
            } else if (root.getLeft() == null) {
                return root.getRight();
            } else if (root.getRight() == null) {
                return root.getLeft();
            } else {
                BSTNode<T> temp = new BSTNode<T>(null);
                root.setLeft(rGetPred(null, root.getLeft(), temp));
                root.setData(temp.getData());
                return root;
            }
        }
        return root;
    }

    /**
     * Recursive helper method to appropriately remove data predicate
     * @param root recursive root starting to the left of node
     * @param ret node to hold return data
     * @param parent grandparent node in case that grandchildren are
     * left after removal
     * @return the predicate
     */
    private BSTNode<T> rGetPred(BSTNode<T> parent,
        BSTNode<T> root, BSTNode<T> ret) {
        //starts to the left
        if (root.getRight() != null) {
            root.setRight(rGetPred(root, root.getRight(), ret));
            return root;
        } else {
            ret.setData(root.getData());
            return root.getLeft();
        }
    }

    /**
     * Returns the data in the tree matching the parameter passed in (think
     * carefully: should you use value equality or reference equality?).
     *
     * Should have a running time of O(log n) for a balanced tree, and a worst
     * case of O(n).
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
            BSTNode<T> ans = rGet(root, data);
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
     * Should have a running time of O(log n) for a balanced tree, and a worst
     * case of O(n).
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
            BSTNode<T> ans = rGet(root, data);
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

    private BSTNode<T> rGet(BSTNode<T> root, T data) {
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
     * Should run in O(n).
     *
     * @return a preorder traversal of the tree
     */
    public List<T> preorder() {
        return preorder(root);
    }

    /**
     * Recursive helper method to get a traversal list
     * @param root recursive root of subtree
     * @return preordered list
     */
    private List<T> preorder(BSTNode<T> root) {
        if (root == null) {
            return new ArrayList<T>();
        }

        List<T> newList = new ArrayList<T>();
        newList.add(root.getData());
        newList.addAll(preorder(root.getLeft()));
        newList.addAll(preorder(root.getRight()));
        return newList;
    }

    /**
     * Should run in O(n).
     *
     * @return an inorder traversal of the tree
     */
    public List<T> inorder() {
        return inorder(root);
    }

    /**
     * Recursive helper method to get a traversal list
     * @param root recursive root of subtree
     * @return inordered list
     */
    private List<T> inorder(BSTNode<T> root) {
        if (root == null) {
            return new ArrayList<T>();
        }

        List<T> newList = new ArrayList<T>();
        newList.addAll(inorder(root.getLeft()));
        newList.add(root.getData());
        newList.addAll(inorder(root.getRight()));
        return newList;
    }

    /**
     * Should run in O(n).
     *
     * @return a postorder traversal of the tree
     */
    public List<T> postorder() {
        return postorder(root);
    }

    /**
     * Recursive helper method to get a traversal list
     * @param root recursive root of subtree
     * @return postordered list
     */
    private List<T> postorder(BSTNode<T> root) {
        if (root == null) {
            return new ArrayList<T>();
        }

        List<T> newList = new ArrayList<T>();
        newList.addAll(postorder(root.getLeft()));
        newList.addAll(postorder(root.getRight()));
        newList.add(root.getData());
        return newList;
    }

    /**
     * Generate a level-order traversal of the tree.
     *
     * To do this, add the root node to a queue. Then, while the queue isn't
     * empty, remove one node, add its data to the list being returned, and add
     * its left and right child nodes to the queue. If what you just removed is
     * {@code null}, ignore it and continue with the rest of the nodes.
     *
     * Should run in O(n). This does not need to be done recursively.
     *
     * @return a level order traversal of the tree
     */
    public List<T> levelorder() {
        LinkedList<BSTNode<T>> queue = new LinkedList<BSTNode<T>>();
        List<T> ret = new ArrayList<T>();
        if (root != null) {
            queue.add(root);
            while (queue.size() != 0) {
                BSTNode<T> x = queue.removeFirst();
                if (x != null) {
                    ret.add(x.getData());
                    if (x.getLeft() != null) {
                        queue.add(x.getLeft());
                    }
                    if (x.getRight() != null) {
                        queue.add(x.getRight());
                    }
                }
            }
        }
        return ret;
    }

    /**
     * This method checks whether a binary tree meets the criteria for being
     * a binary search tree.
     *
     * This method is a static method that takes in a BSTNode called
     * {@code treeRoot}, which is the root of the tree that you should check.
     *
     * You may assume that the tree passed in is a proper binary tree; that is,
     * there are no loops in the tree, the parent-child relationship is
     * correct, that there are no duplicates, and that every parent has at
     * most 2 children. So, what you will have to check is that the order
     * property of a BST is still satisfied.
     *
     * Should run in O(n). However, you should stop the check as soon as you
     * find evidence that the tree is not a BST rather than checking the rest
     * of the tree.
     *
     * @param <T> the generic typing
     * @param treeRoot the root of the binary tree to check
     * @return true if the binary tree is a BST, false otherwise
     */
    public static <T extends Comparable<? super T>> boolean isBST(
            BSTNode<T> treeRoot) {
        return risBST(treeRoot, null, null);
    }

    /**
     * Recursive helper method check BST validity
     * @param <T> the generic typing
     * @param treeRoot recursive root of subtree
     * @param min minimum bound
     * @param max maximum bound
     * @return whther or not tree is BST
     */
    private static <T extends Comparable<? super T>> boolean risBST(
            BSTNode<T> treeRoot, T min, T max) {
        if (treeRoot == null) {
            return true;
        }

        if (min == null && max != null) {
            if (treeRoot.getData().compareTo(max) > 0) {
                return false;
            }
        } else if (max == null && min != null) {
            if (treeRoot.getData().compareTo(min) < 0) {
                return false;
            }
        } else if (max != null && min != null) {
            if (treeRoot.getData().compareTo(min) < 0
                || treeRoot.getData().compareTo(max) > 0) {
                return false;
            }
        }
        return risBST(treeRoot.getLeft(), min, treeRoot.getData())
            && risBST(treeRoot.getRight(), treeRoot.getData(), max);
    }

    /**
     * Clears the tree.
     *
     * Should run in O(1).
     */
    public void clear() {
        this.root = null;
        this.size = 0;
    }

    /**
     * Calculate and return the height of the root of the tree. A node's
     * height is defined as {@code max(left.height, right.height) + 1}. A leaf
     * node has a height of 0 and a null child should be -1.
     *
     * Should be calculated in O(n).
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {
        return rHeight(root);
    }

    /**
     * Recursive helper method to get height
     * @param root recursive root of subtree
     * @return the height of the given root
     */
    private int rHeight(BSTNode<T> root) {
        if (root == null) {
            return -1;
        } else {
            return Math.max(rHeight(root.getLeft()),
                rHeight(root.getRight())) + 1;
        }
    }

    /**
     * Returns the size of the BST.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the number of elements in the tree
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }

    /**
     * Returns the root of the BST.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the root of the tree
     */
    public BSTNode<T> getRoot() {
        // DO NOT MODIFY THIS METHOD!
        return root;
    }
}