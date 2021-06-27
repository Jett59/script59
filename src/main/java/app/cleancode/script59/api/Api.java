package app.cleancode.script59.api;

import java.util.ArrayList;
import java.util.List;
import app.cleancode.script59.serialize.Symbol;

public class Api {
    private static final Api INSTANCE = new Api();

    public static Api getInstance() {
        return INSTANCE;
    }

    private final List<Object> arguments = new ArrayList<>();

    public void argumentLoad(Object argument) {
        arguments.add(argument);
    }

    private Object returnVal = null;

    public void initiateCall(Symbol symbol) {
        switch (symbol.name()) {
            case "printf": {
                Stdio.printf((String) arguments.get(0),
                        arguments.subList(1, arguments.size()).toArray());
                break;
            }
            default:
                break;
        }
        arguments.clear();
    }

}
