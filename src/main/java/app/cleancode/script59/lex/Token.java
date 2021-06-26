package app.cleancode.script59.lex;

import app.cleancode.script59.lex.Token.Type;

public record Token(Type type, String value) {
    public static enum Type {
        IDENTIFIER, NUMBER, STRING, OPERATOR, ARGLIST_OPEN, ARGLIST_CLOSE, BODY_OPEN, BODY_CLOSE, STATEMENT_END
    }

    @Override
    public String toString() {
        return String.format("%s: %s", type, value);
    }
}
