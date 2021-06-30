package app.cleancode.script59.serialize;

public class BlockStart implements LanguageComponent {
    public final String name;

    public BlockStart(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name + ":";
    }

}
