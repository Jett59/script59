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
                Lexer lexer = new Lexer();
                long start = System.nanoTime();
                var tokens = lexer
                        .lex("public int myInt = 4 * 5; System.out.println (\"Hello there!\\t\");");
                long time = System.nanoTime() - start;
                System.out.printf("Lex: Time taken: %.3fS\n", time / 1000000000d);
                System.out.println(tokens);
                break;
            }
        }
    }
}
