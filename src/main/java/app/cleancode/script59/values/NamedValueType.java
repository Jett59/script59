package app.cleancode.script59.values;

public record NamedValueType(String name, ValueType type) {

    @Override
    public String toString() {
        return type.toString().toLowerCase() + " " + name;
    }

}
