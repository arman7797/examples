package com.arman;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class Task {

    public static void main(String[] args) {
        Map<String, String> originalMap = new HashMap<>();
        originalMap.put("key1", "value1");
        originalMap.put("key2LongLongLongLong", "value2");
        originalMap.put("key3LongLongLongLong", "value3");
        originalMap.put("key4", "value4");
        originalMap.put("key5LongLongLongLong", "value2");
        originalMap.put("value3", "value5");
        Map<String, ?> swappedMap = swapKeysAndValues(originalMap);
        printMap(swappedMap);
    }

    private static Map<String, ?> swapKeysAndValues(Map<String, String> inputMap) {
        Map<String, String> swapped = new HashMap<>();
        for (var entry : inputMap.entrySet()) {
            var swappedEntry = swapIfNeeded(entry);
            if (swapped.containsKey(swappedEntry.getKey())) {
                swapped.put(swappedEntry.getKey() + "_over", swappedEntry.getValue());
                continue;
            }
            swapped.put(swappedEntry.getKey(), swappedEntry.getValue());
        }

        return swapped;
    }

    private static Entry<String, String> swapIfNeeded(Entry<String, String> it) {
        if (it.getKey().length() > 10) {
            return Map.entry(it.getValue(), it.getKey());
        }
        return it;
    }

    private static void printMap(Map<String, ?> map) {
        map.entrySet().forEach(System.out::println);
    }
}