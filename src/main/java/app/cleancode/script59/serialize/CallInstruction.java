package app.cleancode.script59.serialize;

import java.util.List;

public class CallInstruction implements LanguageComponent {
    public Symbol functionSymbol;
    public final List<LanguageComponent> args;

    public CallInstruction(SymbolLookup symbolLookup, String functionName,
            List<LanguageComponent> args) {
        this.functionSymbol = symbolLookup.getSymbol(functionName);
        this.args = args;
    }

    @Override
    public String toString() {
        return "Call " + functionSymbol.name();
    }

    @Override
    public LanguageComponentType getType() {
        return LanguageComponentType.FUNCTION_CALL;
    }

}
