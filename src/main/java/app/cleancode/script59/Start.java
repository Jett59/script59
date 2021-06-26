package app.cleancode.script59;

import app.cleancode.script59.lex.Lexer;

public class Start {
    public static void main(String[] args) {
        ExecutionProperties execution = ExecutionProperties.valueOf(args);
        switch (execution.action) {
            case COMPILE: {
                throw new IllegalArgumentException(
                        "Error: compile mode is currently not supported");
            }
            case EXECUTE: {
                System.out.println(new Lexer().lex(
                        "public int myInt = 4 * 5; System.out.println (\"Hello there!\\t\");"));
                break;
            }
        }
    }
}
