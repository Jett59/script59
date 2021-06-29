package app.cleancode.script59.api;

import app.cleancode.script59.serialize.SymbolTable;
import app.cleancode.script59.serialize.SymbolType;

public class Stdlib {
    public static final SymbolTable SYMBOLS = new SymbolTable();
    static {
        SYMBOLS.declareSymbol("printf", SymbolType.FUNCTION, "(S...)I");
        SYMBOLS.declareSymbol("topOfFunctionReached", SymbolType.FUNCTION, "()v");
        SYMBOLS.declareSymbol("bottomOfFunctionReached", SymbolType.FUNCTION, "()v");
    }
}
