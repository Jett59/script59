package app.cleancode.script59.serialize;

import app.cleancode.script59.lex.Token;
import app.cleancode.script59.lex.TokenType;
import app.cleancode.script59.values.ValueConverter;

public class ArgumentLoadInstruction implements LanguageComponent {
    private final Object argument;

    public ArgumentLoadInstruction(SymbolLookup symbolLookup, Token token) {
        if (token.type().equals(TokenType.STRING)) {
            this.argument =
                    token.value().substring(1, token.value().length() - 1).translateEscapes();
        } else if (token.type().equals(TokenType.NUMBER)) {
            argument = ValueConverter.toNumber(token.value());
        } else {
            throw new IllegalArgumentException("Error: invalid token type " + token.type());
        }
    }

    @Override
    public String toString() {
        return "Load argument: " + argument.toString();
    }
}
