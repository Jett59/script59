package app.cleancode.script59;

import java.util.List;
import app.cleancode.script59.divider.Divider;
import app.cleancode.script59.lex.Lexer;
import app.cleancode.script59.lex.Token;
import app.cleancode.script59.parse.Parser;
import app.cleancode.script59.parse.SyntaxTree;

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
                var tokens = lexer.lex("printf (\"Hello, World!\");");
                long time = System.nanoTime() - start;
                System.out.printf("Lex: Time taken: %.3fS\n", time / 1000000000d);
                Divider divider = new Divider();
                start = System.nanoTime();
                List<List<Token>> dividedTokens = divider.divide(tokens);
                time = System.nanoTime() - start;
                System.out.printf("Divide: Time taken: %.3fS\n", time / 1000000000d);
                Parser parser = new Parser();
                start = System.nanoTime();
                SyntaxTree syntaxTree = parser.parse(dividedTokens);
                time = System.nanoTime() - start;
                System.out.printf("Parse: Time taken: %.3fS\n", time / 1000000000d);
                System.out.println(syntaxTree);
                break;
            }
        }
    }
}
