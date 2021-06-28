package app.cleancode.script59.parse;

import java.util.ArrayList;
import java.util.List;
import app.cleancode.script59.lex.Token;
import app.cleancode.script59.lex.TokenType;

public class Parser {
    public SyntaxTree parse(List<List<Token>> statements) {
        SyntaxTree result = new SyntaxTree(List.of(), new ArrayList<>(), null);
        SyntaxNode currentScope = result;
        for (List<Token> statement : statements) {
            switch (StatementType.of(statement)) {
                case CALL: {
                    String currentScopeName = currentScope.toString();
                    currentScope.getChildren().ifPresentOrElse(children -> {
                        children.add(buildCallSyntaxNode(statement));
                    }, () -> {
                        throw new RuntimeException(
                                "An internal error occured while processing a statement: children of scope "
                                        + currentScopeName
                                        + " is null or does not support children");
                    });
                    break;
                }
                case FUNCTION_DECLARE: {
                    currentScope.getChildren().get().add(buildDeclareFunctionSyntaxNode(statement));
                    break;
                }
                case FUNCTION_DEFINE: {
                    break;
                }
                case RETURN: {
                    break;
                }
            }
        }
        return result;
    }

    private SyntaxNode buildCallSyntaxNode(List<Token> statement) {
        SyntaxTree result =
                new SyntaxTree(List.of(statement.get(0)), new ArrayList<>(), StatementType.CALL);
        int paramStart = 2;
        for (int i = 2; i < statement.size() - 1; i++) {
            Token token = statement.get(i);
            if (token.value().equals(",") || token.type().equals(TokenType.ARGLIST_CLOSE)) {
                result.getChildren().get()
                        .add(buildValueSyntaxNode(statement.subList(paramStart, i)));
                paramStart = i + 1;
            }
        }
        return result;
    }

    private SyntaxNode buildDeclareFunctionSyntaxNode(List<Token> statement) {
        SyntaxTree result =
                new SyntaxTree(List.of(statement.get(1), statement.get(statement.size() - 2)),
                        new ArrayList<>(), StatementType.FUNCTION_DECLARE);
        SyntaxTree arguments = new SyntaxTree(List.of(), new ArrayList<>(), null);
        result.getChildren().get().add(arguments);
        Token type = null;
        for (int i = 3; i < statement.size() - 2; i++) {
            Token token = statement.get(i);
            if (token.value().equals(",")) {
                if (type != null) {
                    throw new IllegalArgumentException(
                            "Error: Missing parameter name after token " + type.value());
                }
                continue;
            }
            if (token.type().equals(TokenType.ARGLIST_CLOSE)) {
                if (type != null) {
                    throw new IllegalArgumentException(
                            "Error: Missing parameter name after token " + type.value());
                }
                break;
            }
            if (type == null) {
                type = token;
            } else {
                arguments.getChildren().get().add(new SyntaxTree(List.of(type, token), null, null));
                type = null;
            }
        }
        if (type != null) {
            throw new IllegalArgumentException(
                    "Error: Missing parameter name after token " + type.value());
        }
        return result;
    }

    private SyntaxNode buildValueSyntaxNode(List<Token> statement) {
        if (statement.size() > 1) {
            throw new IllegalArgumentException(
                    "Error: only one token is supported in value expressions\nTokens: "
                            + statement);
        }
        SyntaxTree result = new SyntaxTree(new ArrayList<>(), new ArrayList<>(), null);
        Token token = statement.get(0);
        if (token.type().equals(TokenType.STRING) || token.type().equals(TokenType.NUMBER)) {
            result.associatedTokens().add(token);
        } else {
            throw new IllegalArgumentException(
                    "Tokens of type " + token.type() + " are not allowed in value expressions");
        }
        return result;
    }
}
