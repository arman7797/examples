package com.arman.interview;

public class Main {

//   MMMDCCXLIX ->  3749

    public static void main(String[] args) {
        reverseString("Hello World!");
        reverseString("Hello");
        reverseString("llo");

        uppercaseFirstChar("hello     from other   world");
        uppercaseFirstChar(". հայերեն  hello     from ուրիշ   world");

        System.out.println(evaluateExpression("12.3 + 2 + 2 - 0.3"));
        System.out.println(evaluateExpression("12.3-0.3+.2"));
    }

    private static double evaluateExpression(String expression) {
        expression = expression.replaceAll("\\s+", "");
        char[] chars = expression.toCharArray();
        int startIndex = 0;
        int endIndex = 0;
        double res = 0.0;
        char operator = '+';

        while (endIndex < chars.length) {
            char current = chars[endIndex];

            if (Character.isDigit(current) || current == '.') {
                endIndex++;
                continue;
            }

            if (current == '+' || current == '-') {
                double value = Double.parseDouble(expression.substring(startIndex, endIndex));
                res = operator == '+' ? res + value : res - value;

                operator = current;
                startIndex = endIndex + 1;
                endIndex = startIndex;
            }
        }

        if (startIndex < expression.length()) {
            double value = Double.parseDouble(expression.substring(startIndex, endIndex));
            res = operator == '+' ? res + value : res - value;
        }

        return res;
    }

//    private static void evaluateExpression(String expression) {
//        Set<String> operators = Set.of("+", "-", "*", "/");
//        Stack<String> operatorStack = new Stack<>();
//        Stack<Double> operands = new Stack<>();
//
//        char[] chars = expression.toCharArray();
//        int digitStart = -1;
//
//        for(int i = 0; i< chars.length; ++i){
//            char current = chars[i];
//            if(Character.isDigit(current)){
//                if(digitStart == -1){
//                    digitStart = i;
//                }
//                continue;
//            }
//            if(current == '.'){
//                System.out.println("Invalid expression");
//            }
//
//
//
//
//        }
//    }


    private static void uppercaseFirstChar(String str) {
        System.out.println(str);
        var chars = str.toCharArray();
        boolean capitalize = true;
        for (int i = 0; i < chars.length; i++) {
            char current = chars[i];

            if (Character.isWhitespace(current)) {
                capitalize = true;
            } else if (capitalize) {
                chars[i] = Character.toUpperCase(current);
                capitalize = false;
            }
        }
        System.out.println(new String(chars));
    }

    private static void reverseString(String str) {
        System.out.println(str);
        var chars = str.toCharArray();
        var length = chars.length;
        for (int i = 0; i < length / 2; i++) {
            var temp = chars[i];
            chars[i] = chars[length - 1 - i];
            chars[length - 1 - i] = temp;
        }
        System.out.println(new String(chars));
    }

}
