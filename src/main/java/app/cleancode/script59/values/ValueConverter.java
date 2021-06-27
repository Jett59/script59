package app.cleancode.script59.values;

public class ValueConverter {
    public static Number toNumber(String str) {
        char endChar = str.charAt(str.length() - 1);
        Number result;
        if (!Character.isDigit(endChar)) {
            switch (endChar) {
                case 'D':
                case 'd': {
                    result = Double.parseDouble(str.substring(0, str.length() - 1));
                    break;
                }
                case 'L':
                case 'l': {
                    result = Long.parseLong(str.substring(0, str.length() - 1));
                    break;
                }
                default:
                    throw new IllegalArgumentException(
                            "Error: integer suffix '" + endChar + "' not defined");
            }
        } else {
            if (str.contains(".")) {
                result = Double.parseDouble(str);
            } else {
                result = Integer.parseInt(str);
            }
        }
        return result;
    }
}
