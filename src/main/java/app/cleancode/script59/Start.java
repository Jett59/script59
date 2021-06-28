package app.cleancode.script59;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import app.cleancode.script59.divider.Divider;
import app.cleancode.script59.lex.Lexer;
import app.cleancode.script59.lex.Token;
import app.cleancode.script59.parse.Parser;
import app.cleancode.script59.parse.SyntaxTree;
import app.cleancode.script59.serialize.Instruction;
import app.cleancode.script59.serialize.Serializer;

public class Start {
    public static void main(String[] args) {
        try {
            ExecutionProperties execution = ExecutionProperties.valueOf(args);
            if (execution.inputFiles.size() < 1) {
                throw new IllegalArgumentException("Error: No input files");
            }
            switch (execution.action) {
                case COMPILE: {
                    throw new IllegalArgumentException(
                            "Error: compile mode is currently not supported");
                }
                case EXECUTE: {
                    List<Instruction> instructions = new ArrayList<>();
                    for (String fileName : execution.inputFiles) {
                        String fileContents =
                                Files.readString(Paths.get(fileName).toAbsolutePath());
                        Lexer lexer = new Lexer();
                        var tokens = lexer.lex(fileContents);
                        Divider divider = new Divider();
                        List<List<Token>> dividedTokens = divider.divide(tokens);
                        Parser parser = new Parser();
                        SyntaxTree syntaxTree = parser.parse(dividedTokens);
                        System.out.println(syntaxTree);
                        Serializer serializer = new Serializer();
                        instructions.addAll(serializer.serialize(syntaxTree));
                    }
                    for (Instruction instruction : instructions) {
                        instruction.execute();
                    }
                    break;
                }
            }
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
}
