package app.cleancode.script59.lex;

import java.util.List;
import java.util.Optional;
import app.cleancode.script59.lex.Token.Type;
import app.cleancode.script59.parse.SyntaxNode;

public record Token(Type type, String value) implements SyntaxNode {
    public static enum Type {
        IDENTIFIER, NUMBER, STRING, OPERATOR, ARGLIST_OPEN, ARGLIST_CLOSE, BODY_OPEN, BODY_CLOSE, STATEMENT_END
    }

    @Override
    public List<Token> associatedTokens() {
        return List.of(this);
    }

    @Override
    public Optional<List<SyntaxNode>> getChildren() {
        return Optional.empty();
    }

    @Override
    public String toString() {
        return String.format("%s: %s", type, value);
    }
}
