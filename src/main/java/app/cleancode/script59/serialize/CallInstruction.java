package app.cleancode.script59.serialize;

import app.cleancode.script59.api.Api;

public class CallInstruction implements Instruction {
    private Symbol functionSymbol;

    public CallInstruction(SymbolLookup symbolLookup, String functionName) {
        String functionName = node.associatedTokens().get(0).value();
        this.functionSymbol = symbolLookup.getSymbol(functionName);
    }

    @Override
    public void execute() {
        Api.getInstance().initiateCall(functionSymbol);

    }

}
