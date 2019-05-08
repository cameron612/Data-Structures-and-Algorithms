import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Your implementations of various string searching algorithms.
 *
 * @author Cameron Allotey
 * @userid callotey3
 * @GTID 903218636
 * @version 1.0
 */
public class PatternMatching {

    /**
     * Knuth-Morris-Pratt (KMP) algorithm that relies on the failure table (also
     * called failure function). Works better with small alphabets.
     *
     * Make sure to implement the failure table before implementing this method.
     *
     * @throws IllegalArgumentException if the pattern is null or of length 0
     * @throws IllegalArgumentException if text or comparator is null
     * @param pattern the pattern you are searching for in a body of text
     * @param text the body of text where you search for pattern
     * @param comparator you MUST use this for checking character equality
     * @return list containing the starting index for each match found
     */
    public static List<Integer> kmp(CharSequence pattern, CharSequence text,
                                    CharacterComparator comparator) {
        if (pattern == null || pattern.length() == 0) {
            throw new IllegalArgumentException(
                "Pattern must be non-null and contain characters!");
        }
        if (comparator == null) {
            throw new IllegalArgumentException(
                "Comparator must be non-null!");
        }
        if (text == null) {
            throw new IllegalArgumentException(
                "Text must be non-null!");
        }
        List<Integer> list = new ArrayList<Integer>();
        if (pattern.length() <= text.length()) {
            int[] failTbl = buildFailureTable(pattern, comparator);
            int temp = 0;
            int i = 0;
            int j = 0;
            while (i <= text.length() - pattern.length()) {
                while (j < pattern.length() && comparator.compare(
                    text.charAt(i + j), pattern.charAt(j)) == 0) {
                    j++;
                }
                if (j == 0) {
                    i++;
                } else {
                    if (j == pattern.length()) {
                        list.add(i);
                    }
                    temp = failTbl[j - 1];
                    i += j - temp;
                    j = temp;
                }
            }
        }
        return list;
    }

    /**
     * Builds failure table that will be used to run the Knuth-Morris-Pratt
     * (KMP) algorithm.
     *
     * The table built should be the length of the input text.
     *
     * Note that a given index i will be the largest prefix of the pattern
     * indices [0..i] that is also a suffix of the pattern indices [1..i].
     * This means that index 0 of the returned table will always be equal to 0
     *
     * Ex. ababac
     *
     * table[0] = 0
     * table[1] = 0
     * table[2] = 1
     * table[3] = 2
     * table[4] = 3
     * table[5] = 0
     *
     * If the pattern is empty, return an empty array.
     *
     * @throws IllegalArgumentException if the pattern or comparator is null
     * @param pattern a {@code CharSequence} you're building a failure table for
     * @param comparator you MUST use this for checking character equality
     * @return integer array holding your failure table
     */
    public static int[] buildFailureTable(CharSequence pattern,
                                          CharacterComparator comparator) {
        if (pattern == null) {
            throw new IllegalArgumentException(
                "Pattern must be non-null and contain characters!");
        }
        if (comparator == null) {
            throw new IllegalArgumentException(
                "Comparator must be non-null!");
        }
        int[] arr = new int[pattern.length()];
        if (arr.length == 0) {
            return arr;
        }
        int i = 0;
        int j = 1;
        arr[0] = 0;
        while (j < pattern.length()) {
            if (comparator.compare(pattern.charAt(i), pattern.charAt(j)) == 0) {
                arr[j++] = ++i;
            } else if (i == 0) {
                arr[j++] = 0;
            } else {
                i = arr[i - 1];
            }
        }
        return arr;
    }

    /**
     * Boyer Moore algorithm that relies on last occurrence table. Works better
     * with large alphabets.
     *
     * Make sure to implement the last occurrence table before implementing this
     * method.
     *
     * Note: You may find the getOrDefault() method useful from Java's Map.
     *
     * @throws IllegalArgumentException if the pattern is null or of length 0
     * @throws IllegalArgumentException if text or comparator is null
     * @param pattern the pattern you are searching for in a body of text
     * @param text the body of text where you search for the pattern
     * @param comparator you MUST use this for checking character equality
     * @return list containing the starting index for each match found
     */
    public static List<Integer> boyerMoore(CharSequence pattern,
                                           CharSequence text,
                                           CharacterComparator comparator) {
        if (pattern == null || pattern.length() == 0) {
            throw new IllegalArgumentException(
                "Pattern must be non-null and contain characters!");
        }
        if (text == null || comparator == null) {
            throw new IllegalArgumentException(
                "Text and comparator must be non-null!");
        }
        List<Integer> list = new ArrayList<Integer>();
        Map<Character, Integer> lastTable = buildLastTable(pattern);
        int shiftedIndex = 0;
        int j = 0;
        int i = 0;
        while (i <= text.length() - pattern.length()) {
            j = pattern.length() - 1;
            while (j >= 0 && comparator.compare(
                text.charAt(i + j), pattern.charAt(j)) == 0) {
                j--;
            }
            if (j == -1) {
                list.add(i++);
            } else {
                shiftedIndex = lastTable.getOrDefault(text.charAt(i + j), -1);
                if (shiftedIndex < j) {
                    i = i + (j - shiftedIndex);
                } else {
                    i++;
                }
            }
        }
        return list;
    }

    /**
     * Builds last occurrence table that will be used to run the Boyer Moore
     * algorithm.
     *
     * Note that each char x will have an entry at table.get(x).
     * Each entry should be the last index of x where x is a particular
     * character in your pattern.
     * If x is not in the pattern, then the table will not contain the key x,
     * and you will have to check for that in your Boyer Moore implementation.
     *
     * Ex. octocat
     *
     * table.get(o) = 3
     * table.get(c) = 4
     * table.get(t) = 6
     * table.get(a) = 5
     * table.get(everything else) = null, which you will interpret in
     * Boyer-Moore as -1
     *
     * If the pattern is empty, return an empty map.
     *
     * @throws IllegalArgumentException if the pattern is null
     * @param pattern a {@code CharSequence} you are building last table for
     * @return a Map with keys of all of the characters in the pattern mapping
     *         to their last occurrence in the pattern
     */
    public static Map<Character, Integer> buildLastTable(CharSequence pattern) {
        if (pattern == null) {
            throw new IllegalArgumentException(
                "Pattern must be non-null!");
        }
        Map<Character, Integer> myLastTable = new HashMap<Character, Integer>();
        for (int i = 0; i < pattern.length(); i++) {
            myLastTable.put(pattern.charAt(i), i);
        }
        return myLastTable;
    }

    /**
     * Prime base used for Rabin-Karp hashing.
     * DO NOT EDIT!
     */
    private static final int BASE = 101;

    /**
     * Runs the Rabin-Karp algorithm. This algorithms generates hashes for the
     * pattern and compares this hash to substrings of the text before doing
     * character by character comparisons.
     *
     * When the hashes are equal and you do character comparisons, compare
     * starting from the beginning of the pattern to the end, not from the end
     * to the beginning.
     *
     * You must use the Rabin-Karp Rolling Hash for this implementation. The
     * formula for it is:
     *
     * sum of: c * BASE ^ (pattern.length - 1 - i), where c is the integer
     * value of the current character, and i is the index of the character
     *
     * Note that if you were dealing with very large numbers here, your hash
     * will likely overflow. However, you will not need to handle this case.
     * You may assume there will be no overflow.
     *
     * For example: Hashing "bunn" as a substring of "bunny" with base 101 hash
     * = b * 101 ^ 3 + u * 101 ^ 2 + n * 101 ^ 1 + n * 101 ^ 0 = 98 * 101 ^ 3 +
     * 117 * 101 ^ 2 + 110 * 101 ^ 1 + 110 * 101 ^ 0 = 102174235
     *
     * Another key step for this algorithm is that updating the hashcode from
     * one substring to the next one must be O(1). To update the hash:
     *
     * remove the oldChar times BASE raised to the length - 1, multiply by
     * BASE, and add the newChar.
     *
     * For example: Shifting from "bunn" to "unny" in "bunny" with base 101
     * hash("unny") = (hash("bunn") - b * 101 ^ 3) * 101 + y =
     * (102174235 - 98 * 101 ^ 3) * 101 + 121 = 121678558
     *
     * Keep in mind that calculating exponents is not O(1) in general, so you'll
     * need to keep track of what BASE^{m - 1} is for updating the hash.
     *
     * Do NOT use Math.pow() for this method.
     *
     * @throws IllegalArgumentException if the pattern is null or of length 0
     * @throws IllegalArgumentException if text or comparator is null
     * @param pattern a string you're searching for in a body of text
     * @param text the body of text where you search for pattern
     * @param comparator the comparator to use when checking character equality
     * @return list containing the starting index for each match found
     */
    public static List<Integer> rabinKarp(CharSequence pattern,
                                          CharSequence text,
                                          CharacterComparator comparator) {
        if (pattern == null || pattern.length() == 0) {
            throw new IllegalArgumentException(
                "Pattern must be non-null and contain characters!");
        }
        if (text == null || comparator == null) {
            throw new IllegalArgumentException(
                "Text and comparator must be non-null!");
        }
        List<Integer> list = new ArrayList<Integer>();
        if (pattern.length() <= text.length()) {
            int patternHash = 0;
            int textHash = 0;
            int pow = 1;
            for (int i = pattern.length() - 1; i >= 0; i--) {
                textHash += text.charAt(i) * pow;
                patternHash += pattern.charAt(i) * pow;
                if (i != 0) {
                    pow *= BASE;
                }
            }
            int i = 0;
            while (i <= text.length() - pattern.length()) {
                if (patternHash == textHash) {
                    int j = 0;
                    while (j < pattern.length()
                        && comparator.compare(
                            text.charAt(i + j), pattern.charAt(j)) == 0) {
                        j++;
                    }
                    if (j == pattern.length()) {
                        list.add(i);
                    }
                }
                if (++i <= text.length() - pattern.length()) {
                    textHash = ((textHash - text.charAt(i - 1) * pow)//curr
                        * BASE + text.charAt(i - 1 + pattern.length()));
                }
            }
        }
        return list;
    }

}
