package app.cleancode.script59.api;

import java.util.ArrayList;
import java.util.List;
import app.cleancode.script59.parse.SyntaxNode;

public class Signatures {
    public static String getSignatureForFunction(SyntaxNode declaration) {
        String returnVal = getIdentifierString(declaration.associatedTokens().get(1).value());
        List<String> params = new ArrayList<>();
        for (SyntaxNode argument : declaration.getChildren().get()) {
            params.add(getIdentifierString(argument.associatedTokens().get(0).value()));
        }
        return String.format("(%s)%s)", String.join(" ", params), returnVal);
    }

    private static String getIdentifierString(String type) {
        return switch (type) {
            case "void" -> "v";
            case "short" -> "h";
            case "int" -> "i";
            case "long" -> "l";
            case "float" -> "f";
            case "double" -> "d";
            case "string" -> "s";
            default -> null;
        };
    }
}
