package app.cleancode.script59.serialize;

import java.util.ArrayList;
import java.util.List;
import app.cleancode.script59.api.Stdlib;
import app.cleancode.script59.lex.TokenType;
import app.cleancode.script59.parse.SyntaxNode;
import app.cleancode.script59.parse.SyntaxTree;

public class Serializer {
    private final SymbolLookup lookup = new SymbolLookup();

    public Serializer() {
        lookup.pushSymbolTable(Stdlib.SYMBOLS);
    }

    public List<Instruction> serialize(SyntaxTree tree) {
        List<Instruction> result = new ArrayList<>();
        for (SyntaxNode node : tree.getChildren().get()) {
            switch (node.statementType().get()) {
                case CALL: {
                    String functionName = node.associatedTokens().get(0).value();
                    for (int i = 0; i < node.getChildren().get().size(); i++) {
                        SyntaxNode argNode = node.getChildren().get().get(i);
                        TokenType tokenType = argNode.associatedTokens().get(0).type();
                        if (tokenType.equals(TokenType.STRING)
                                || tokenType.equals(TokenType.NUMBER)) {
                            result.add(new ArgumentLoadInstruction(lookup,
                                    argNode.associatedTokens().get(0)));
                        }
                    }
                    result.add(new CallInstruction(lookup, functionName));
                    break;
                }
            }
        }
        return result;
    }
}
