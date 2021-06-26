package app.cleancode.script59.operator;

public class Operators {
    private static char[] OPERATORS = new char[] {'+', '-', '*', '/', '='};

    public static boolean isOperator(char c) {
        for (char operator : OPERATORS) {
            if (c == operator) {
                return true;
            }
        }
        return false;
    }
}
