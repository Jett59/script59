package app.cleancode.script59;

import java.nio.file.*;
import java.util.*;
import app.cleancode.script59.divider.*;
import app.cleancode.script59.lex.*;
import app.cleancode.script59.parse.*;
import app.cleancode.script59.serialize.*;

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
                    for (String fileName : execution.inputFiles) {
                        String fileContents =
                                Files.readString(Paths.get(fileName).toAbsolutePath());
                        Lexer lexer = new Lexer();
                        var tokens = lexer.lex(fileContents);
                        Divider divider = new Divider();
                        List<List<Token>> dividedTokens = divider.divide(tokens);
                        Parser parser = new Parser();
                        SyntaxTree syntaxTree = parser.parse(dividedTokens);
                        Serializer serializer = new Serializer();
                        List<LanguageComponent> moduleComponents = new ArrayList<>();
                        moduleComponents.addAll(serializer.serialize(syntaxTree));
                        System.out.println(String.join("\n", moduleComponents.stream()
                                .map(LanguageComponent::toString).toList()));
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
