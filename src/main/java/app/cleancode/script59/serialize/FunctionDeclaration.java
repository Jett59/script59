package app.cleancode.script59.serialize;

import app.cleancode.script59.values.NamedValueType;
import app.cleancode.script59.values.ValueType;
import java.util.List;

public class FunctionDeclaration implements LanguageComponent {
    public final String name;
    public final ValueType returnType;
    public final List<NamedValueType> arguments;

    public FunctionDeclaration(String name, ValueType returnType, List<NamedValueType> arguments) {
        this.name = name;
        this.returnType = returnType;
        this.arguments = arguments;
    }

    @Override
    public String toString() {
        return "dfunc " + name + "("
                + String.join(", ", arguments.stream().map(NamedValueType::toString).toList())
                + ") " + returnType.toString().toLowerCase() + ";";
    }
}
