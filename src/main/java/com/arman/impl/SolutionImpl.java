package com.arman.impl;

import com.arman.Solution;
import com.arman.proxy.async.Async;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

import static java.lang.System.out;

public class SolutionImpl implements Solution {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private static final Map<String, String[]> mappings = new HashMap<>() {{
        put("", new String[]{});
        put("1", new String[]{});
        put("2", new String[]{"a", "b", "c"});
        put("3", new String[]{"d", "e", "f"});
        put("4", new String[]{"g", "h", "i"});
        put("5", new String[]{"j", "k", "l"});
        put("6", new String[]{"m", "n", "o"});
        put("7", new String[]{"p", "q", "r", "s"});
        put("8", new String[]{"t", "u", "v"});
        put("9", new String[]{"w", "x", "y", "z"});
    }};

    @Override
    @Async
    public void eggs(int n) {
        int bigCart = n / 8;
        n = n % 8;

        while (n % 6 != 0 && bigCart > 0) {
            n = n + 8;
            bigCart--;
        }

        if (n % 6 == 0) {
            out.println("Big Cart : " + bigCart);
            out.println("Small Cart : " + n / 6);
        } else {
            out.println("No solution");
        }

    }

    @Override
    public int sundayCount(String from, String to) {
        LocalDate fromDate = LocalDate.parse(from, formatter);
        LocalDate toDate = LocalDate.parse(to, formatter);
        int skew = DayOfWeek.SUNDAY.getValue() - fromDate.getDayOfWeek().getValue();
        int days = Period.between(fromDate, toDate).getDays() - skew;
        return days % 7 > 0 ? days / 7 + 1 : days / 7;
    }

    @Override
    public int divide(int dividend, int divisor) {
        long n = dividend;
        long m = divisor;
        boolean negative = n < 0 ^ m < 0;

        n = Math.abs(n);
        m = Math.abs(m);

        long ans = 0;

        if (m != 1) {
            while (n - m >= 0) {
                n -= m;
                ans++;
            }
        } else {
            ans = n;
        }

        ans = negative ? -ans : ans;

        if (ans > Integer.MAX_VALUE) {
            return Integer.MAX_VALUE;
        } else if (ans < Integer.MIN_VALUE) {
            return Integer.MIN_VALUE;
        }

        return (int) ans;
    }

    @Override
    public int[] nextGreaterElement(int[] input) {
        int n = input.length;
        int[] result = new int[n];
        Deque<Integer> stack = new ArrayDeque<>();

        // Iterate from the end to the beginning
        for (int i = n - 1; i >= 0; i--) {
            // Pop elements from the stack that are less than or equal to the current element
            while (!stack.isEmpty() && stack.peek() <= input[i]) {
                stack.pop();
            }
            // If stack is empty, no greater element exists; otherwise, take the top of the stack
            result[i] = stack.isEmpty() ? -1 : stack.peek();
            // Push the current element onto the stack
            stack.push(input[i]);
        }

        return result;
    }

    @Override
    public String reverseWords(String s) {
        StringBuilder sb = new StringBuilder();
        String[] words = s.split(" ");
        int length = words.length - 1;
        for (int i = length; i > -1; i--) {
            String word = words[i].trim();
            if (word.isEmpty()) {
                continue;
            }
            if (i != length) {
                sb.append(" ");
            }
            sb.append(word);
        }
        return sb.toString();
    }

    @Override
    public List<List<Integer>> combine(int n, int k) {
        List<List<Integer>> res = new LinkedList<>();
        List<Integer> ds = new LinkedList<>();
        combine(res, 1, n, k, ds);
        return res;
    }

    @Override
    public List<String> wordWrap(String input, int maxLength) {
        List<String> res = new LinkedList<>();
        var sb = new StringBuilder();
        for (String s : input.split(" ")) {
            if (sb.length() + s.length() + 1 > maxLength) {
                sb.append(" ".repeat(maxLength - sb.length()));
                res.add(sb.toString());
                sb = new StringBuilder();
            }
            sb.append(s);
            sb.append(" ");
        }
        if (!sb.isEmpty()) {
            sb.append(" ".repeat(maxLength - sb.length()));
            res.add(sb.toString());
        }

        return res;
    }

    @Override
    public List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        List<Integer> ds = new LinkedList<>();
        permutation(nums, res, ds);
        return res;
    }

    @Override
    public List<String> permute(String input) {
        List<String> res = new LinkedList<>();
        permutation(input.toCharArray(), res, new StringBuilder(), new boolean[input.length()]);
        return res;
    }

    @Override
    public int reverse(int x) {
        boolean negative = x < 0;
        long res = 0;
        x = Math.abs(x);

        while (x != 0) {
            int mod = x % 10;
            res = res * 10 + mod;
            x /= 10;
        }

        res = negative ? -res : res;

        if (res > Integer.MAX_VALUE || res < Integer.MIN_VALUE) {
            return 0;
        }

        return (int) res;

    }

    @Override
    public int lengthOfLongestSubstring(String s) {
        int maxLength = 0;
        char[] chars = s.toCharArray();
        Set<Character> facedChars = new LinkedHashSet<>();
        for (char c : chars) {
            if (facedChars.contains(c)) {
                if (maxLength < facedChars.size()) {
                    maxLength = facedChars.size();
                }
                removeOrClear(facedChars, c);
            }
            facedChars.add(c);
        }

        return Math.max(facedChars.size(), maxLength);
    }

    @Override
    public int myAtoi(String s) {
        boolean digitStartIndex = false;
        boolean overFlow = false;
        boolean isNegative = false;

        char[] chars = s.trim().toCharArray();
        int res = 0;

        if (chars[0] == '-') {
            isNegative = true;
        } else {
            if (chars[0] != '+') {
                return res;
            }
        }


        for (int i = 1; i < s.length(); i++) {
            char c = chars[i];
            if (!isDigit(c)) {
                break;
            }

            if (c == '0' && !digitStartIndex) {
                continue;
            }

            if (!digitStartIndex) {
                digitStartIndex = true;
            }

            int numFromChar = (((int) c) - 48);

            if (((long) res * 10) + numFromChar > Integer.MAX_VALUE) {
                overFlow = true;
            }

            res *= 10;
            res += numFromChar;
        }
        if (overFlow) {
            return isNegative ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        }

        return isNegative ? -res : res;

    }

    @Override
    public String intToRoman(int num) {
        if (num > 9999) {
            throw new ArithmeticException("Can't convert " + num + " to Roman");
        }

        String[] mSeries = {"", "M", "MM", "MMM", "MMMM", "MMMMM", "MMMMMM", "MMMMMMM", "MMMMMMMM", "MMMMMMMMM"};
        String[] cSeries = {"", "C", "CC", "CCC", "CD", "D", "DC", "DCC", "DCCC", "CM"};
        String[] xSeries = {"", "X", "XX", "XXX", "XL", "L", "LX", "LXX", "LXXX", "XC"};
        String[] iSeries = {"", "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX"};

        return mSeries[num / 1000] + cSeries[(num = num % 1000) / 100] + xSeries[(num = num % 100) / 10] + iSeries[(num % 10)];
    }

    @Override
    public int romanToInt(String s) {
        int res = 0;
        int prevValue = 0;

        for (int i = s.length() - 1; i >= 0; i--) {
            int currentValue = getNumericalValue(s.charAt(i));
            if (currentValue < prevValue) {
                res -= currentValue;
            } else {
                res += currentValue;
            }

            prevValue = currentValue;
        }

        return res;
    }

//    @Override
//    public int romanToInt(String s) {
//        int res = 0;
//        char[] romanChars = s.toCharArray();
//        for (int i = romanChars.length - 1; i > -1; i--) {
//            char current = romanChars[i];
//            boolean checkPrev = i - 1 > -1;
//            int value = getNumericalValue(current);
//
//            if (checkPrev) {
//                char prevChar = romanChars[i - 1];
//                if ((current == 'V' || current == 'X') && prevChar == 'I') {
//                    value -= getNumericalValue(prevChar);
//                    --i;
//                }
//                if ((current == 'L' || current == 'C') && prevChar == 'X') {
//                    value -= getNumericalValue(prevChar);
//                    --i;
//                }
//                if ((current == 'D' || current == 'M') && prevChar == 'C') {
//                    value -= getNumericalValue(prevChar);
//                    --i;
//                }
//            }
//
//            res += value;
//        }
//        return res;
//    }

    @Override
    public List<String> letterCombinations(String digits) {
        List<String> res = new LinkedList<>();
        String[] chars = digits.split("");
        combinations(chars, 0, res, "");
        return res;
    }


    @Override
    public List<List<Integer>> threeSum(int[] nums) {
        Arrays.sort(nums);
        List<List<Integer>> res = new LinkedList<>();
        for (int i = 0; i < nums.length; i++) {
            if (i > 0 && nums[i] == nums[i - 1]) continue;
            int j = i + 1;
            int k = nums.length - 1;

            while (j < k) {
                int sum = nums[i] + nums[j] + nums[k];
                if (sum < 0) {
                    j++;
                } else if (sum > 0) {
                    k--;
                } else {
                    res.add(Arrays.asList(nums[i], nums[j], nums[k]));
                    k--;
                    j++;
                    while (j < k && nums[j] == nums[j - 1]) j++;
                    while (j < k && nums[k] == nums[k + 1]) k--;
                }
            }

        }
        return res;
    }

    @Override
    @Async
    public CompletableFuture<List<String>> letterCombinationsAsync(String digits) {
        System.out.printf("Executing in %s%n", Thread.currentThread().getName());
        return CompletableFuture.completedFuture(letterCombinations(digits));
    }

    @Override
    public boolean search(int[] nums, int target) {
        int lastValue = nums[nums.length - 1];
        boolean found = false;
        if (lastValue > target) {
            for (int i = nums.length - 1; i > -1; i--) {
                int cur = nums[i];
                if (cur == target) {
                    found = true;
                    break;
                }
                if (i - 1 >= 0 && cur < nums[i - 1]) {
                    break;
                }
            }
        } else if (lastValue < target) {
            for (int i = 0; i < nums.length; i++) {
                int cur = nums[i];
                if (cur == target) {
                    found = true;
                    break;
                }
                if (i + 1 < nums.length && cur > nums[i + 1]) {
                    break;
                }
            }
        } else {
            found = true;
        }
        return found;
    }

    @Override
    public void setZeroes(int[][] matrix) {
        int n = matrix[0].length;
        boolean[] zeros = new boolean[n];
        boolean fillRow;

        for (int[] ints : matrix) {
            fillRow = false;

            for (int col = 0; col < ints.length; col++) {
                if (ints[col] == 0) {
                    zeros[col] = true;
                    fillRow = true;
                }
            }

            if (fillRow) {
                Arrays.fill(ints, 0);
            }
        }

        for (int col = 0; col < zeros.length; col++) {
            if (zeros[col]) {
                for (int row = 0; row < matrix.length; row++) {
                    matrix[row][col] = 0;
                }
            }
        }
    }

    @Override
    public List<Integer> spiralOrder(int[][] matrix) {

        int m = matrix.length;
        int n = matrix[0].length;
        int cap = n * m;
        List<Integer> res = new ArrayList<>(cap);
        int delta = 0;

        while (res.size() < cap) {
            for (int i = delta; i < n - delta; i++) { // top ->
                res.add(matrix[delta][i]);
            }
            if (cap == res.size()) break;

            for (int i = delta + 1; i < m - delta; i++) { // right
                res.add(matrix[i][n - delta - 1]);
            }
            if (cap == res.size()) break;

            for (int i = n - delta - 2; i >= delta; i--) { // bottom <-
                res.add(matrix[m - delta - 1][i]);
            }
            if (cap == res.size()) break;

            for (int i = m - delta - 2; i > delta; i--) { // left ^
                res.add(matrix[i][delta]);
            }
            delta++;
        }

        return res;
    }

    @Override
    public boolean searchMatrix(int[][] matrix, int target) {
        int res = binaryVerticalSearch(matrix, target, 0);
        if (res < 0) {
            int expectedIndex = -(res + 1);
            if (expectedIndex == 0) {
                return false;
            }
            res = binarySearch(matrix[expectedIndex - 1], target);
        }

        return res >= 0;
    }

    @Override
    public int binarySearch(int[] nums, int target) {
        int low = 0, high = nums.length - 1;
        while (low <= high) {
            int mid = (low + high) >>> 1;
            int midVal = nums[mid];
            if (midVal < target) {
                low = mid + 1;
            } else if (midVal > target) {
                high = mid - 1;
            } else {
                return mid;
            }
        }
        return -(low + 1);
    }

    @Override
    public int binaryVerticalSearch(int[][] nums, int target, int col) {
        var low = 0;
        var high = nums.length - 1;
        while (low <= high) {
            var middle = (low + high) >>> 1;
            int midVal = nums[middle][col];
            if (midVal < target) {
                low = middle + 1;
            } else if (midVal > target) {
                high = middle - 1;
            } else {
                return middle;
            }
        }
        return -(low + 1);
    }

    @Override
    public String findPath(int[][] matrix) {
        int m, n;
        if ((m = matrix.length) == 0 || (n = matrix[0].length) == 0 ||
                matrix[0][0] == 0 || matrix[m - 1][n - 1] == 0) {
            return "No Path";
        }

        boolean found = false;
        char[] directions = {'L', 'R', 'U', 'D'};
        int[][] directionMatrix = {{0, -1}, {0, 1}, {-1, 0}, {1, 0}}; // Left, Right, Up, Down
        char[][] path = new char[m][n];
        boolean[][] visited = new boolean[m][n];

        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{0, 0});
        visited[0][0] = true;

        while (!queue.isEmpty()) {
            var cell = queue.poll();
            var x = cell[0];
            var y = cell[1];

            if (x == m - 1 && y == n - 1) {
                found = true;
                break;
            }

            for (int i = 0; i < directionMatrix.length; i++) {
                var step = directionMatrix[i];
                var nextX = x + step[0];
                var nextY = y + step[1];
                if (nextX >= 0 && nextX < m && nextY >= 0 && nextY < n &&
                        !visited[nextX][nextY] && matrix[nextX][nextY] != 0) {
                    queue.offer(new int[]{nextX, nextY});
                    path[nextX][nextY] = directions[i];
                    visited[nextX][nextY] = true;
                }
            }

        }

        if (found) {
            var i = m - 1;
            var j = n - 1;
            var builder = new StringBuilder();
            while (!(i == 0 && j == 0)) {
                var dir = path[i][j];
                builder.append(dir);
                builder.append(" ");

                switch (dir) {
                    case 'L' -> j++;
                    case 'R' -> j--;
                    case 'U' -> i++;
                    case 'D' -> i--;
                }
            }
            return builder.reverse().toString();
        }

        return "No Path";
    }

    private static int getNumericalValue(char romanChar) {
        return switch (romanChar) {
            case 'I' -> 1;
            case 'V' -> 5;
            case 'X' -> 10;
            case 'L' -> 50;
            case 'C' -> 100;
            case 'D' -> 500;
            case 'M' -> 1000;
            default -> 0;
        };
    }

    private static void combinations(String[] chars, int index, List<String> res, String ds) {
        if (index == chars.length) {
            res.add(ds);
            return;
        }
        String[] letters = mappings.get(chars[index]);
        for (String letter : letters) {
            combinations(chars, index + 1, res, ds + letter);
        }
    }

    private static void removeOrClear(Set<Character> facedChars, char c) {
        boolean found = false;
        Iterator<Character> iterator = facedChars.iterator();
        while (iterator.hasNext()) {
            Character ch = iterator.next();
            if (ch.equals(c)) {
                found = true;
                iterator.remove();
            } else if (!found) {
                iterator.remove();
            }
        }
    }

    private static void permutation(char[] chars, List<String> result, StringBuilder sb, boolean[] visited) {
        if (sb.length() == chars.length) {
            result.add(sb.toString());
            return;
        }
        for (int i = 0; i < chars.length; i++) {
            if (visited[i]) {
                continue;
            }
            visited[i] = true;
            sb.append(chars[i]);
            permutation(chars, result, sb, visited);
            sb.deleteCharAt(sb.length() - 1);
            visited[i] = false;
        }
    }

    private static void permutation(int[] nums, List<List<Integer>> res, List<Integer> ds) {
        if (ds.size() == nums.length) {
            res.add(new ArrayList<>(ds));
            return;
        }

        for (int num : nums) {
            if (ds.contains(num)) {
                continue;
            }
            ds.add(num);
            permutation(nums, res, ds);
            ds.removeLast();
        }

    }

    private static void combine(List<List<Integer>> res, int index, int n, int k, List<Integer> ds) {
        if (ds.size() == k) {
            res.add(new LinkedList<>(ds));
            return;
        }
        for (int i = index; i < n + 1; i++) {
            ds.add(i);
            combine(res, i + 1, n, k, ds);
            ds.removeLast();
        }
    }
}