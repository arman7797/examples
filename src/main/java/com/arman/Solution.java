package com.arman;

import com.arman.proxy.async.Async;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface Solution {

    @Async
    void eggs(int n);

    int sundayCount(String from, String to);

    int divide(int dividend, int divisor);

    int[] nextGreaterElement(int[] input);

    String reverseWords(String s);

    List<List<Integer>> combine(int n, int k);

    List<String> wordWrap(String input, int maxLength);

    List<List<Integer>> permute(int[] nums);

    List<String> permute(String input);

    int reverse(int x);

    int lengthOfLongestSubstring(String s);

    int myAtoi(String s);

    default boolean isDigit(char c) {
        return c > 47 && c < 58;
    }

    String intToRoman(int num);

    int romanToInt(String s);

    List<String> letterCombinations(String digits);

    List<List<Integer>> threeSum(int[] nums);

    boolean search(int[] nums, int target);

    void setZeroes(int[][] matrix);

    List<Integer> spiralOrder(int[][] matrix);

    boolean searchMatrix(int[][] matrix, int target);

    int binarySearch(int[] nums, int target);

    int binaryVerticalSearch(int[][] nums, int target, int col);

    String findPath(int[][] matrix);

    @Async
    CompletableFuture<List<String>> letterCombinationsAsync(String digits);
}
