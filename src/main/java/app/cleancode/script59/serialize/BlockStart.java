package app.cleancode.script59.serialize;

public class BlockStart implements LanguageComponent {
    private final String name;

    public BlockStart(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name + ":";
    }

}
