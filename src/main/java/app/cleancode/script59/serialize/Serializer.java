package app.cleancode.script59.serialize;

import java.util.ArrayList;
import java.util.List;
import app.cleancode.script59.api.Api;
import app.cleancode.script59.api.Signatures;
import app.cleancode.script59.api.Stdlib;
import app.cleancode.script59.lex.TokenType;
import app.cleancode.script59.parse.SyntaxNode;

public class Serializer {
    private final SymbolLookup lookup = new SymbolLookup();

    public Serializer() {
        lookup.pushSymbolTable(Stdlib.SYMBOLS);
    }

    public List<Instruction> serialize(SyntaxNode tree) {
        return serialize(tree, 0);
    }

    public List<Instruction> serialize(SyntaxNode tree, int startIndex) {
        lookup.pushSymbolTable(new SymbolTable());
        List<Instruction> result = new ArrayList<>();
        for (int i = startIndex; i < tree.getChildren().get().size(); i++) {
            SyntaxNode node = tree.getChildren().get().get(i);
            switch (node.statementType().get()) {
                case CALL: {
                    String functionName = node.associatedTokens().get(0).value();
                    for (int j = 0; j < node.getChildren().get().size(); j++) {
                        SyntaxNode argNode = node.getChildren().get().get(j);
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
                case FUNCTION_DECLARE: {
                    lookup.getTopSymbolTable().declareSymbol(node.associatedTokens().get(0).value(),
                            SymbolType.FUNCTION, Signatures.getSignatureForFunction(node));
                    break;
                }
                case FUNCTION_DEFINE: {
                    SyntaxNode declaration = node.getChildren().get().get(0);
                    if (!lookup.isPresent(declaration.associatedTokens().get(0).value())) {
                        lookup.getTopSymbolTable().declareSymbol(
                                declaration.associatedTokens().get(0).value(), SymbolType.FUNCTION,
                                Signatures.getSignatureForFunction(declaration));
                    }
                    Api.getInstance().registerSymbolLocation(
                            lookup.getSymbol(declaration.associatedTokens().get(0).value()).id(),
                            result.size());
                    result.add(new CallInstruction(lookup, "topOfFunctionReached"));
                    result.addAll(serialize(node, 1));
                    result.add(new CallInstruction(lookup, "bottomOfFunctionReached"));
                    break;
                }
                case RETURN: {
                    result.add(new ReturnInstruction());
                    break;
                }
                case FUNCTION_END:
                    break;
            }
        }
        lookup.popSymbolTable();
        return result;
    }
}
