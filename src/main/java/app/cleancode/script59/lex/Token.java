package app.cleancode.script59.lex;

public record Token(TokenType type, String value) {
    @Override
    public String toString() {
        return String.format("%s: %s", type, value);
    }
}
