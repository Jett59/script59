package app.cleancode.script59.parse;

import java.util.ArrayList;
import java.util.List;
import app.cleancode.script59.lex.Token;

public class Parser {
    public SyntaxTree parse(List<List<Token>> statements) {
        SyntaxTree result = new SyntaxTree(List.of(), new ArrayList<>());
        SyntaxNode currentScope = result;
        for (List<Token> statement : statements) {
            Token endToken = statement.get(statement.size() - 1);
            switch (endToken.type()) {
                case STATEMENT_END: {
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
                    }
                    break;
                }
                case BODY_OPEN: {
                    break;
                }
                case BODY_CLOSE: {
                    currentScope = currentScope.parent().orElse(null);
                    if (currentScope == null) {
                        throw new IllegalArgumentException("Error: Unexpected token '"
                                + endToken.value() + "' at end of line");
                    }
                    break;
                }
                default:
                    throw new IllegalArgumentException(
                            "Error: lines must always end with one of either {, ; or }\nFor statement: "
                                    + String.join(" ", statement.stream().map(Token::toString)
                                            .toList().toArray(new String[0])));
            }
        }
        return result;
    }

    private SyntaxNode buildCallSyntaxNode(List<Token> statement) {
        SyntaxTree result = new SyntaxTree(List.of(statement.get(0)), new ArrayList<>());
        int paramStart = 2;
        for (int i = 2; i < statement.size() - 2; i++) {
            if (statement.get(i).value().equals(",")) {
                result.getChildren().get()
                        .add(buildValueSyntaxNode(statement.subList(paramStart, i)));
                paramStart = i + 1;
            }
        }
        return result;
    }

    private static SyntaxNode buildValueSyntaxNode(List<Token> statement) {
        if (statement.size() > 1) {
            throw new IllegalArgumentException(
                    "Error: only one token is supported in value expressions");
        }
        SyntaxTree result = new SyntaxTree(new ArrayList<>(), new ArrayList<>());
        Token token = statement.get(0);
        if (token.type().equals(Token.Type.STRING)) {
            result.associatedTokens().add(token);
        } else {
            throw new IllegalArgumentException(
                    "Tokens of type " + token.type() + " are not allowed in value expressions");
        }
        return result;
    }
}
