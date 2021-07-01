package app.cleancode.script59.serialize;

public class FunctionEnd implements LanguageComponent {

    @Override
    public String toString() {
        return "}";
    }

    @Override
    public LanguageComponentType getType() {
        return LanguageComponentType.FUNCTION_START;
    }

}
