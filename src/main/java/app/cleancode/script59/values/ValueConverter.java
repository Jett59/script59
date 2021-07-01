package app.cleancode.script59.values;

import app.cleancode.script59.serialize.Value;

public class ValueConverter {
    public static Value toNumber(String str) {
        char endChar = str.charAt(str.length() - 1);
        Value result;
        if (!Character.isDigit(endChar)) {
            switch (endChar) {
                case 'D':
                case 'd': {
                    result = new Value(Double.parseDouble(str.substring(0, str.length() - 1)),
                            ValueType.DOUBLE);
                    break;
                }
                case 'L':
                case 'l': {
                    result = new Value(Long.parseLong(str.substring(0, str.length() - 1)),
                            ValueType.LONG);
                    break;
                }
                default:
                    throw new IllegalArgumentException(
                            "Error: integer suffix '" + endChar + "' not defined");
            }
        } else {
            if (str.contains(".")) {
                result = new Value(Double.parseDouble(str), ValueType.DOUBLE);
            } else {
                result = new Value(Integer.parseInt(str), ValueType.INT);
            }
        }
        return result;
    }
}
