package app.cleancode.script59.serialize;

import app.cleancode.script59.api.Api;

public class CallInstruction implements Instruction {
    private Symbol functionSymbol;

    public CallInstruction(SymbolLookup symbolLookup, String functionName) {
        this.functionSymbol = symbolLookup.getSymbol(functionName);
    }

    @Override
    public void execute() throws Throwable {
        Api.getInstance().initiateCall(functionSymbol);
    }

    @Override
    public String toString() {
        return "Call " + functionSymbol.name();
    }

}
