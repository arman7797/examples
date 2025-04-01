package com.arman.util;

import java.lang.reflect.Array;
import java.util.Arrays;

public class StringUtil {

    public static String toString(Object[] array) {
        return Arrays.toString(flatten(array));
    }

    public static String toString(Object arg) {
        if (arg == null) {
            return "null";
        }

        if (arg.getClass().isArray()) {
            return stringifyArray(arg);
        }

        return arg.toString();
    }

    private static String[] flatten(Object[] args) {
        return Arrays.stream(args)
                .map(StringUtil::toString)
                .toArray(String[]::new);
    }

    private static String stringifyArray(Object arg) {
        var length = Array.getLength(arg);
        var sb = new StringBuilder("[");
        for (int i = 0; i < length; i++) {
            if (i > 0) sb.append(", ");
            sb.append(toString(Array.get(arg, i)));
        }
        sb.append("]");
        return sb.toString();
    }
}
