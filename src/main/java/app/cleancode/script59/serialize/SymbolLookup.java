package app.cleancode.script59.serialize;

import java.util.Stack;

public class SymbolLookup {
    private Stack<SymbolTable> symbolTables = new Stack();

    public void pushSymbolTable(SymbolTable table) {
        symbolTables.push(table);
    }

    public void popSymbolTable() {
        symbolTables.pop();
    }

    public SymbolTable getTopSymbolTable() {
        return symbolTables.lastElement();
    }

    public boolean isPresent(String symbolName) {
        for (SymbolTable table : symbolTables) {
            if (table.isSymbolPresent(symbolName)) {
                return true;
            }
        }
        return false;
    }

    public Symbol getSymbol(String symbolName) {
        for (SymbolTable table : symbolTables) {
            if (table.isSymbolPresent(symbolName)) {
                return table.getSymbol(symbolName);
            }
        }
        throw new IllegalArgumentException("Error: symbol '" + symbolName + "' not found");
    }

}
