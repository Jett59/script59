package app.cleancode.script59.serialize;

public class CallInstruction implements LanguageComponent {
    private Symbol functionSymbol;

    public CallInstruction(SymbolLookup symbolLookup, String functionName) {
        this.functionSymbol = symbolLookup.getSymbol(functionName);
    }

    @Override
    public String toString() {
        return "Call " + functionSymbol.name();
    }

}
