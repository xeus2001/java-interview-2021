package com.here.wikvaya.java.interview;

import java.util.List;
import java.util.Map;

/**
 * Tasks to be implemented.
 */
public interface Tasks {

  /**
   * Given a list of integers with incomplete pairs, find the missing integer.
   * <p>
   * Assume a list like {@code [3, 4, 5, 6, 3, 5, 6]} is given, the method should return {@code 4}.
   * @param integers A list of integer pairs, with one element missing, e.g. {@code [3, 4, 5, 6, 3, 5, 6]}.
   * @return the missing integer, for example {@code 4}.
   * @throws IllegalArgumentException if the given argument is invalid.
   */
  int findMissing(int[] integers);

  /**
   * Before the search can be implemented you should generate a sorted int-array. The array should contain random values between min and max, but no value must be contained multiple times.
   * <p>
   * BONUS: If min is bigger than max, reverse the sort order from ascending to descending!
   *
   * @param min  the minimal integer value (inclusive).
   * @param max  the maximal integer value (inclusive).
   * @param size the size of the array to be generated.
   * @return the sorted int-array.
   */
  int[] generateRandomData(final int min, final int max, final int size, final boolean ascending);

  /**
   * Count how often code points are repeating. For example the input string "aaaabbbcca" should produce an output like {code}[{"a":4}, {"b":3}, {"c":2}, {"a":1}]{code}. The implementation should be able to return comparable results for all input strings in all languages.
   *
   * @param s the input string.
   * @return a list where for each repeating code point one map is contained where the key is the code point of character and the value is the number of times it repeats.
   */
  List<Map<Integer, Integer>> count(final String s);

  /**
   * A method that should return the index of needle in haystack or -1, if needle is not contained in haystack. It is guaranteed that haystack is ordered. Please use the fastest possible method to find the index and do NOT use any library call.
   * <p>
   * BONUS: Automatically detect if haystack is ordered ascending or descending and operate with both sort orders!
   *
   * @param haystack the ascending ordered array of integers to search in.
   * @param needle   the value to search for.
   * @return the index of needle in haystack or -1 if needle is not part of haystack.
   * @see [https://en.wikipedia.org/wiki/Binary_search_algorithm]
   */
  int indexOf(final int[] haystack, final int needle);

  /**
   * A helper method for debugging the {@link #count(String)} implementation.
   *
   * @param list the list to stringify.
   * @return the serialized list.
   */
  default String stringify(final List<Map<Character, Integer>> list) {
    String r = "";
    r += '[';
    boolean first = true;
    for (final Map<Character, Integer> element : list) {
      if (!first) {
        r += ", ";
      } else {
        first = false;
      }
      r += "{";
      for (final Map.Entry<Character, Integer> entry : element.entrySet()) {
        r += "'" + entry.getKey() + "':" + entry.getValue();
      }
      r += "}";
    }
    r += "]\n";
    return r;
  }
}