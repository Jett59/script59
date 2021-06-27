package app.cleancode.script59.serialize;

import java.util.HashMap;
import java.util.Map;

public class SymbolTable {
    private static long symbolCount = 0;

    private Map<String, Symbol> symbols = new HashMap<>();

    public void declareSymbol(String name, SymbolType type, String signature) {
        symbols.put(name, new Symbol(name, symbolCount, type, signature));
        symbolCount++;
    }

    public boolean isSymbolPresent(String name) {
        return symbols.containsKey(name);
    }

    public Symbol getSymbol(String name) {
        return symbols.get(name);
    }
}
