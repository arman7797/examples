package com.arman;

import com.arman.impl.LoggerInterceptor;
import com.arman.impl.SolutionImpl;
import com.arman.proxy.ProxyFactory;
import com.arman.proxy.ProxyProperties;
import com.arman.proxy.annotation.ProxyConfig;
import lombok.SneakyThrows;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Stack;

import static java.lang.System.out;

@ProxyConfig(exposeProxy = true, asyncProcessingEnabled = true)
public class Main {
    private static final ProxyFactory proxyFactory = new ProxyFactory(ProxyProperties.create(Main.class));
    private static final Solution SOLUTION = (Solution) proxyFactory.interceptorProxy(new SolutionImpl(), new LoggerInterceptor());

    @SneakyThrows
    public static void main(String[] args) {
        var graph = getStringGraph();
        var visited = new LinkedHashSet<String>();
        graph.depthFirst("Arman", visited::add);
        println(visited);

        println("Arman connections are: " + graph.successors("Arman"));
        println("Tigran connections are: " + graph.successors("Tigran"));
        println("Hakob Len connections are: " + graph.successors("Hakob Len"));
        println("Hakob Boy connections are: " + graph.successors("Hakob Boy"));
        println("Geyush connections are: " + graph.successors("Geyush"));
        println("Armine connections are: " + graph.successors("Armine"));
        println();

        println();
        var graphNum = getIntegerGraph();
        var dfs = new LinkedHashSet<Integer>();
        graphNum.depthFirst(40, dfs::add);
        println(dfs);

        println();
        var bfs = new LinkedHashSet<Integer>();
        graphNum.breadthFirst(40, bfs::add);
        println(bfs);

        out.println("Barev Vahram");

        ListNode l1 = new ListNode(9);
        ListNode l2 = new ListNode(1, new ListNode(9, new ListNode(9, new ListNode(9, new ListNode(9, new ListNode(9, new ListNode(9, new ListNode(9, new ListNode(9, new ListNode(9))))))))));


        ListNode.addTwoNumbers(l1, l2);

        char[][] board = {
                {'a', '5', 'q'},
                {'b', '6', 't'}
        };
        int rows = board.length;
        int cols = board[0].length;
        println(rows + " X " + cols);

        String input = """
                Lorem ipsum, or lipsum as it is sometimes known, 
                is dummy text used in laying out print, graphic or web designs. 
                The passage is attributed to an unknown typesetter in the 15th century who is thought
                to have scrambled parts of Cicero's De Finibus Bonorum et Malorum for use in a type 
                specimen book. It usually begins with:
                """;


        int[][] matrix = {
                {1, 0, 1, 0},
                {1, 0, 0, 1},
                {1, 1, 1, 1},
                {1, 1, 0, 1},
        };

        SOLUTION.findPath(matrix);


        int[][] spiral = {
                {1, 2, 3, 4},
                {4, 5, 6, 2},
                {7, 8, 9, 3}
        };
        SOLUTION.spiralOrder(spiral);


        int[][] searchMatrix = {
                {1, 3, 5, 7},
                {10, 11, 16, 20},
                {23, 30, 34, 60}
        };

        SOLUTION.searchMatrix(searchMatrix, 11);
        SOLUTION.nextGreaterElement(new int[]{5, 9, 2, 4, 8});
        SOLUTION.threeSum(new int[]{-1, 0, 1, 2, -1, -4});
        SOLUTION.threeSum(new int[]{0, 0, 0});
        SOLUTION.threeSum(new int[]{0, 1, 1});

        SOLUTION.intToRoman(3749);
        SOLUTION.romanToInt("MMMDCCXLIX");
        SOLUTION.letterCombinations("123");
        SOLUTION.myAtoi("   -04200654a324");
        SOLUTION.lengthOfLongestSubstring("dfdv");
        SOLUTION.reverse(-2147483648);
        SOLUTION.permute(new int[]{1, 2, 3});
//        String.join("\n", SOLUTION.wordWrap(input, 50));
        SOLUTION.combine(4, 2);
        SOLUTION.reverseWords("the sky is blue");
        SOLUTION.divide(-2147483648, -1);
        SOLUTION.sundayCount("06-07-2024", "28-07-2024");


        SOLUTION.eggs(20);
        println();

        var res = SOLUTION.letterCombinationsAsync("23");
//        out.println(res.get().toString());


        LRUCache lruCache = new LRUCache(2);
        lruCache.put(1, 1);
        lruCache.put(2, 2);
        println(lruCache.get(1));
        lruCache.put(3, 3);
        println(lruCache.get(2));
        lruCache.put(4, 4);
        println(lruCache.get(1));
        println(lruCache.get(3));
        println(lruCache.get(4));

        System.exit(0);
    }

    static class LRUCache {

        private final Map<Integer, ValueRef<Integer>> cache;
        private final int capacity;

        public LRUCache(int capacity) {
            this.capacity = capacity;
            this.cache = new HashMap<>(capacity);
        }

        public int get(int key) {
            var val = cache.get(key);
            var res = -1;
            if (val != null) {
                val.accessTime = System.nanoTime();
                res = val.value;
            }
            return res;
        }

        public void put(int key, int value) {
            if (cache.size() == capacity) {
                removeLRU();
            }
            cache.put(key, new ValueRef<>(System.nanoTime(), value));
        }

        private void removeLRU() {
            int key = -1;
            long lru = System.nanoTime();
            for (var entry : cache.entrySet()) {
                if (lru >= entry.getValue().accessTime) {
                    key = entry.getKey();
                    lru = entry.getValue().accessTime;
                }
            }
            if (key != -1) {
                cache.remove(key);
            }
        }

        private static class ValueRef<T> {
            long accessTime;
            T value;

            public ValueRef(long accessTime, T value) {
                this.accessTime = accessTime;
                this.value = value;
            }
        }
    }

    static class ListNode {
        int val;
        ListNode next;

        ListNode() {
        }

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }

        public static ListNode addTwoNumbers(ListNode l1, ListNode l2) {
            long sum = getResult(l1) + getResult(l2);

            ListNode next = new ListNode((int) (sum % 10));
            ListNode res = next;
            sum = sum / 10;
            while (sum != 0) {
                next.next = new ListNode((int) (sum % 10));
                next = next.next;
                sum = sum / 10;
            }

            return res;
        }

        private static long getResult(ListNode l1) {
            long num = l1.val;
            long i = 10;
            while (l1.next != null) {
                l1 = l1.next;
                num = num + l1.val * i;
                i *= 10;
            }
            out.println(num);
            return num;

        }
    }

    private static Graph<String> getStringGraph() {
        var graph = new Graph<String>();
        graph.addVertex("Arman");
        graph.addVertex("Tigran");
        graph.addVertex("Armine");
        graph.addVertex("Hakob Boy");
        graph.addVertex("Hakob Len");
        graph.addVertex("Geyush");

        graph.addEdge("Arman", "Tigran");
        graph.addEdge("Tigran", "Hakob Len");
        graph.addEdge("Tigran", "Hakob Boy");
        graph.addEdge("Armine", "Geyush");
        graph.addEdge("Hakob Boy", "Armine");
        graph.addEdge("Hakob Len", "Geyush");
        return graph;
    }

    private static Graph<Integer> getIntegerGraph() {
        var graphNum = new Graph<Integer>();

        graphNum.addVertex(40);
        graphNum.addVertex(10);
        graphNum.addVertex(20);
        graphNum.addVertex(30);
        graphNum.addVertex(60);
        graphNum.addVertex(50);
        graphNum.addVertex(70);

        graphNum.addEdge(40, 10);
        graphNum.addEdge(40, 20);
        graphNum.addEdge(10, 30);
        graphNum.addEdge(20, 10);
        graphNum.addEdge(20, 30);
        graphNum.addEdge(20, 60);
        graphNum.addEdge(20, 50);
        graphNum.addEdge(30, 60);
        graphNum.addEdge(60, 70);
        graphNum.addEdge(50, 70);
        return graphNum;
    }


    static class Graph<T> {
        private final Map<Vertex<T>, List<Vertex<T>>> adjMap;

        public Graph() {
            adjMap = new HashMap<>();
        }

        public void addVertex(T value) {
            adjMap.put(new Vertex<>(value), new ArrayList<>());
        }

        public void removeVertex(T edge) {
            var wrapper = new Vertex<>(edge);
            List<Vertex<T>> adj = adjMap.remove(wrapper);
            adj.forEach(it -> adjMap.get(it).remove(wrapper));
        }

        public void addEdge(T from, T to) {
            var fromEdge = new Vertex<>(from);
            var toEdge = new Vertex<>(to);
            adjMap.get(fromEdge).add(toEdge);
            adjMap.get(toEdge).add(fromEdge);
        }

        public void removeEdge(T from, T to) {
            var fromEdge = new Vertex<>(from);
            var toEdge = new Vertex<>(to);
            adjMap.get(fromEdge).remove(toEdge);
            adjMap.get(toEdge).remove(fromEdge);
        }

        public List<T> successors(T edge) {
            var wrapper = new Vertex<>(edge);
            return adjMap.getOrDefault(wrapper, new ArrayList<>()).stream()
                    .map(Vertex::getValue)
                    .toList();
        }

        public void depthFirst(T root, Visitor<T> visitor) {
            var stack = new Stack<T>();
            var visited = new LinkedHashSet<T>();
            stack.add(root);
            while (!stack.isEmpty()) {
                var node = stack.pop();
                if (!visited.contains(node)) {
                    visited.add(node);
                    visitor.visit(node);
                    successors(node).forEach(it -> {
                        if (!visited.contains(it)) {
                            stack.push(it);
                        }
                    });
                }
            }
        }

        public void breadthFirst(T root, Visitor<T> visitor) {
            var queue = new LinkedList<T>();
            var visited = new LinkedHashSet<T>();
            queue.add(root);
            while (!queue.isEmpty()) {
                var node = queue.poll();
                if (!visited.contains(node)) {
                    visited.add(node);
                    visitor.visit(node);
                    successors(node).forEach(it -> {
                        if (!visited.contains(it)) {
                            queue.add(it);
                        }
                    });
                }
            }
        }
    }

    @FunctionalInterface
    interface Visitor<T> {
        void visit(T elem);
    }

    static class Vertex<T> {
        T value;

        public Vertex(T value) {
            this.value = value;
        }

        public T getValue() {
            return value;
        }

        @Override
        public String toString() {
            return getValue().toString();
        }

        @Override
        public int hashCode() {
            return value.hashCode();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Vertex<?> vertex = (Vertex<?>) o;
            return Objects.equals(value, vertex.value);
        }
    }

    private static void println(Object obj) {
        out.println(obj);
    }

    private static void println() {
        out.println();
    }
}

