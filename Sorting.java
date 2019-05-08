import java.util.Comparator;
import java.util.Random;
import java.util.LinkedList;

/**
 * Your implementation of various sorting algorithms.
 *
 * @author Cameron Allotey
 * @userid callotey3
 * @GTID 903218636
 * @version 1.0
 */
public class Sorting {

    /**
     * Implement insertion sort.
     *
     * It should be:
     *  in-place
     *  stable
     *
     * Have a worst case running time of:
     *  O(n^2)
     *
     * And a best case running time of:
     *  O(n)
     *
     * @throws IllegalArgumentException if the array or comparator is null
     * @param <T> data type to sort
     * @param arr the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     */
    public static <T> void insertionSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException(
                "Array and comparator must be non-null");
        }

        for (int i = 1; i < arr.length; i++) {
            T x = arr[i];
            int j = i - 1;
            while (j >= 0 && comparator.compare(arr[j], x) > 0) {
                arr[j + 1] = arr[j];
                j--;
            }
            arr[j + 1] = x;
        }
    }

    /**
     * Implement selection sort.
     *
     * It should be:
     *  in-place
     *  unstable
     *
     * Have a worst case running time of:
     *  O(n^2)
     *
     * And a best case running time of:
     *  O(n^2)
     *
     * @throws IllegalArgumentException if the array or comparator is null
     * @param <T> data type to sort
     * @param arr the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     */
    public static <T> void selectionSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException(
                "Array and comparator must be non-null");
        }
        for (int i = 0; i < arr.length - 1; i++) {
            int min = i;
            for (int j = i + 1; j < arr.length; j++) {
                if (comparator.compare(arr[j], arr[min]) < 0) {
                    min = j;
                }
            }
            if (min != i) {
                T temp = arr[min];
                arr[min] = arr[i];
                arr[i] = temp;
            }
        }
    }

    /**
     * Implement merge sort.
     *
     * It should be:
     *  out-of-place
     *  stable
     *
     * Have a worst case running time of:
     *  O(n log n)
     *
     * And a best case running time of:
     *  O(n log n)
     *
     * You can create more arrays to run mergesort, but at the end, everything
     * should be merged back into the original T[] which was passed in.
     *
     * When splitting the array, if there is an odd number of elements, put the
     * extra data on the right side.
     *
     * @throws IllegalArgumentException if the array or comparator is null
     * @param <T> data type to sort
     * @param arr the array to be sorted
     * @param comparator the Comparator used to compare the data in arr
     */
    public static <T> void mergeSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException(
                "Array and comparator must be non-null");
        }
        int length = arr.length;
        int midIdx = length / 2;
        T[] left = (T[]) new Object[(length) / 2];
        T[] right = (T[]) new Object[length - left.length];
        System.arraycopy(arr, 0, left, 0, left.length);
        System.arraycopy(arr, left.length, right, 0, right.length);
        if (length != 1) {
            mergeSort(left, comparator);
            mergeSort(right, comparator);
        }
        int leftIdx = 0;
        int rightIdx = 0;
        int curr = 0;

        while (leftIdx < midIdx && rightIdx < length - midIdx) {
            if (comparator.compare(left[leftIdx], right[rightIdx]) <= 0) {
                arr[curr] = left[leftIdx++];
            } else {
                arr[curr] = right[rightIdx++];
            }
            curr++;
        }

        while (leftIdx < midIdx) {
            arr[curr] = left[leftIdx++];
            curr++;
        }

        while (rightIdx < length - midIdx) {
            arr[curr] = right[rightIdx++];
            curr++;
        }

    }

    /**
     * Implement quick sort.
     *
     * Use the provided random object to select your pivots. For example if you
     * need a pivot between a (inclusive) and b (exclusive) where b > a, use
     * the following code:
     *
     * int pivotIndex = rand.nextInt(b - a) + a;
     *
     * If your recursion uses an inclusive b instead of an exclusive one,
     * the formula changes by adding 1 to the nextInt() call:
     *
     * int pivotIndex = rand.nextInt(b - a + 1) + a;
     *
     * It should be:
     *  in-place
     *  unstable
     *
     * Have a worst case running time of:
     *  O(n^2)
     *
     * And a best case running time of:
     *  O(n log n)
     *
     * Make sure you code the algorithm as you have been taught it in class.
     * There are several versions of this algorithm and you may not receive
     * credit if you do not use the one we have taught you!
     *
     * @throws IllegalArgumentException if the array or comparator or rand is
     * null
     * @param <T> data type to sort
     * @param arr the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @param rand the Random object used to select pivots
     */
    public static <T> void quickSort(T[] arr, Comparator<T> comparator,
                                     Random rand) {
        if (arr == null || comparator == null || rand == null) {
            throw new IllegalArgumentException(
                "Array, comparator, and randomizer must be non-null");
        }
        quickSort(arr, comparator, rand, 0, arr.length);
    }

    /**
     * @param <T> data type to sort
     * @param arr the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @param rand the Random object used to select pivots
     * @param left lower bound
     * @param right upper bound
     */
    private static <T> void quickSort(T[] arr, Comparator<T> comparator,
                                     Random rand, int left, int right) {
        //get random idx between left and right
        if (right - left > 1) {
            int pivotIndex = rand.nextInt(right - left) + left;
            T pivot = arr[pivotIndex];
            T temp = arr[pivotIndex];
            arr[pivotIndex] = arr[left];
            arr[left] = temp;
            int leftIdx = left + 1;
            int rightIdx = right - 1;
            while (leftIdx <= rightIdx) {
                int compcnt = 0;
                while (leftIdx <= rightIdx
                    && comparator.compare(arr[leftIdx], pivot) <= 0) {
                    leftIdx++;
                }
                while (leftIdx <= rightIdx
                    && comparator.compare(arr[rightIdx], pivot) >= 0) {
                    rightIdx--;
                }
                if (leftIdx <= rightIdx) {
                    T temp2 = arr[leftIdx];
                    arr[leftIdx] = arr[rightIdx];
                    arr[rightIdx] = temp2;
                    leftIdx++;
                    rightIdx--;
                }
            }
            T temp3 = arr[left];
            arr[left] = arr[rightIdx];
            arr[rightIdx] = temp3;
            quickSort(arr, comparator, rand, left, rightIdx);
            quickSort(arr, comparator, rand, rightIdx + 1, right);
        }
    }


    /**
     * Implement LSD (least significant digit) radix sort.
     *
     * Make sure you code the algorithm as you have been taught it in class.
     * There are several versions of this algorithm and you may not get full
     * credit if you do not implement the one we have taught you!
     *
     * Remember you CANNOT convert the ints to strings at any point in your
     * code! Doing so may result in a 0 for the implementation.
     *
     * It should be:
     *  out-of-place
     *  stable
     *
     * Have a worst case running time of:
     *  O(kn)
     *
     * And a best case running time of:
     *  O(kn)
     *
     * You are allowed to make an initial O(n) passthrough of the array to
     * determine the number of iterations you need.
     *
     * Refer to the PDF for more information on LSD Radix Sort.
     *
     * You may use {@code java.util.ArrayList} or {@code java.util.LinkedList}
     * if you wish, but it may only be used inside radix sort and any radix sort
     * helpers. Do NOT use these classes with other sorts.
     *
     * Do NOT use anything from the Math class except Math.abs().
     *
     * @throws IllegalArgumentException if the array is null
     * @param arr the array to be sorted
     */
    public static void lsdRadixSort(int[] arr) {
        if (arr == null) {
            throw new IllegalArgumentException(
                "array must be non-null");
        }
        LinkedList<Integer>[] counter = (
            LinkedList<Integer>[]) new LinkedList[19];
        for (int i = 0; i < 19; i++) {
            counter[i] = new LinkedList<Integer>();
        }
        int mod = 10;
        int div = 1;
        boolean cont = true;
        while (cont) {
            cont = false;
            for (int x : arr) {
                int divided = x / div;
                if (Math.abs(divided / 10) > 0) {
                    cont = true;
                }
                if (counter[divided % mod + 9] == null) {
                    counter[divided % mod + 9] = new LinkedList<Integer>();
                }
                counter[divided % mod + 9].add(x);
            }
            int arrIdx = 0;
            for (int i = 0; i < counter.length; i++) {
                if (counter[i] != null) {
                    while (counter[i].size() != 0) {
                        arr[arrIdx++] = counter[i].remove();
                    }
                }
            }
            div *= 10; 
        }
    }
}