package app.cleancode.script59.serialize;

import java.util.List;

public class CallInstruction implements LanguageComponent {
    public Symbol functionSymbol;
    public final List<Object> args;

    public CallInstruction(SymbolLookup symbolLookup, String functionName, List<Object> args) {
        this.functionSymbol = symbolLookup.getSymbol(functionName);
        this.args = args;
    }

    @Override
    public String toString() {
        return "Call " + functionSymbol.name();
    }

}
