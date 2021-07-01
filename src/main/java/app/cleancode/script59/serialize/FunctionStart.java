package app.cleancode.script59.serialize;

public class FunctionStart implements LanguageComponent {
    public final FunctionDeclaration declaration;

    public FunctionStart(FunctionDeclaration declaration) {
        this.declaration = declaration;
    }

    @Override
    public String toString() {
        return declaration.toString() + " {";
    }

    @Override
    public LanguageComponentType getType() {
        return LanguageComponentType.FUNCTION_START;
    }

}
