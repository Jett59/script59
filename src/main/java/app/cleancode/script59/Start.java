package app.cleancode.script59;

import java.util.List;
import app.cleancode.script59.divider.Divider;
import app.cleancode.script59.lex.Lexer;
import app.cleancode.script59.lex.Token;

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
                Divider divider = new Divider();
                start = System.nanoTime();
                List<List<Token>> dividedTokens = divider.divide(tokens);
                time = System.nanoTime() - start;
                System.out.printf("Divide: Time taken: %.3fS\n", time / 1000000000d);
                System.out.println(dividedTokens);
                break;
            }
        }
    }
}
