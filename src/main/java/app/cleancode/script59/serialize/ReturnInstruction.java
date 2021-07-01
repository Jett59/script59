package app.cleancode.script59.serialize;

public class ReturnInstruction implements LanguageComponent {
    public final LanguageComponent returnVal;

    public ReturnInstruction(LanguageComponent returnVal) {
        this.returnVal = returnVal;
    }

    @Override
    public String toString() {
        return "return " + returnVal.toString();
    }

}
