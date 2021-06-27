package app.cleancode.script59.api;

public class Stdio {
    public static int printf(String format, Object... args) {
        String out = String.format(format, args);
        System.out.print(out);
        return out.length();
    }
}
