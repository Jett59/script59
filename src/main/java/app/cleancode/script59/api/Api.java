package app.cleancode.script59.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import app.cleancode.script59.serialize.Symbol;

public class Api {
    private static final Api INSTANCE = new Api();

    public static Api getInstance() {
        return INSTANCE;
    }

    private Map<Long, Long> symbols = new HashMap<>();

    public void registerSymbolLocation(long symbolId, long location) {
        symbols.put(symbolId, location);
    }

    private final List<Object> arguments = new ArrayList<>();

    public void argumentLoad(Object argument) {
        arguments.add(argument);
    }

    private Object returnVal = null;

    public long executionLocation = 0;
    private Stack<Long> callStack = new Stack<>();

    public void initiateCall(Symbol symbol) throws NoSuchMethodException {
        switch (symbol.name()) {
            case "printf": {
                if (arguments.size() < 1) {
                    throw new IllegalArgumentException(
                            "Not enough arguments passed to function " + symbol.name());
                }
                Stdio.printf((String) arguments.get(0),
                        arguments.subList(1, arguments.size()).toArray());
                break;
            }
            case "exit": {
                returnVal = arguments.get(0);
                callStack.clear();
                executionLocation = -2;
                break;
            }
            default:
                if (symbols.containsKey(symbol.id())) {
                    callStack.push(executionLocation);
                    executionLocation = symbols.get(symbol.id());
                } else {
                    throw new NoSuchMethodException(symbol.name());
                }
        }
        arguments.clear();
    }

    public void returnFromCall() {
        executionLocation = callStack.pop();
    }

}
