package app.cleancode.script59.serialize;

import app.cleancode.script59.api.Api;
import app.cleancode.script59.lex.Token;

public class ArgumentLoadInstruction implements Instruction {
    private final Object argument;

    public ArgumentLoadInstruction(SymbolLookup symbolLookup, Token token) {
        if (token.type().equals(Token.Type.STRING)) {
            this.argument = token.value().substring(1, token.value().length() - 1);
        } else {
            throw new IllegalArgumentException("Error: invalid token type " + token.type());
        }
    }

    @Override
    public void execute() {
        Api.getInstance().argumentLoad(argument);
    }

    @Override
    public String toString() {
        return "Load argument: " + argument.toString();
    }
}
