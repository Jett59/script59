package app.cleancode.script59.serialize;

import java.util.ArrayList;
import java.util.List;
import app.cleancode.script59.api.Signatures;
import app.cleancode.script59.api.Stdlib;
import app.cleancode.script59.lex.Token;
import app.cleancode.script59.lex.TokenType;
import app.cleancode.script59.parse.SyntaxNode;
import app.cleancode.script59.values.NamedValueType;
import app.cleancode.script59.values.ValueConverter;
import app.cleancode.script59.values.ValueType;

public class Serializer {
    private final SymbolLookup lookup = new SymbolLookup();

    public Serializer() {
        lookup.pushSymbolTable(Stdlib.SYMBOLS);
    }

    public List<LanguageComponent> serialize(SyntaxNode tree) {
        return serialize(tree, 0);
    }

    public List<LanguageComponent> serialize(SyntaxNode tree, int startIndex) {
        lookup.pushSymbolTable(new SymbolTable());
        List<LanguageComponent> result = new ArrayList<>();
        for (int i = startIndex; i < tree.getChildren().get().size(); i++) {
            SyntaxNode node = tree.getChildren().get().get(i);
            switch (node.statementType().get()) {
                case CALL: {
                    String functionName = node.associatedTokens().get(0).value();
                    List<Object> arguments = new ArrayList<>(node.getChildren().get().size());
                    for (SyntaxNode argument : node.getChildren().get()) {
                        Token token = argument.associatedTokens().get(0);
                        switch (token.type()) {
                            case NUMBER:
                                arguments.add(ValueConverter.toNumber(token.value()));
                                break;
                            case STRING:
                                arguments.add(
                                        token.value().substring(1, token.value().length() - 1));
                                break;
                        }
                    }
                    result.add(new CallInstruction(lookup, functionName, arguments));
                    break;
                }
                case FUNCTION_DECLARE: {
                    lookup.getTopSymbolTable().declareSymbol(node.associatedTokens().get(0).value(),
                            SymbolType.FUNCTION, Signatures.getSignatureForFunction(node));
                    result.add(
                            new FunctionDeclaration(node.associatedTokens().get(0).value(),
                                    ValueType.valueOf(
                                            node.associatedTokens().get(1).value().toUpperCase()),
                                    node.getChildren().get().stream()
                                            .map(argument -> new NamedValueType(
                                                    argument.associatedTokens().get(1).value(),
                                                    ValueType.valueOf(argument.associatedTokens()
                                                            .get(0).value().toUpperCase())))
                                            .toList()));
                    break;
                }
                case FUNCTION_DEFINE: {
                    SyntaxNode declaration = node.getChildren().get().get(0);
                    if (!lookup.isPresent(declaration.associatedTokens().get(0).value())) {
                        lookup.getTopSymbolTable().declareSymbol(
                                declaration.associatedTokens().get(0).value(), SymbolType.FUNCTION,
                                Signatures.getSignatureForFunction(declaration));
                    }
                    result.add(new BlockStart("entry"));
                    result.addAll(serialize(node, 0));
                    result.add(new BlockEnd());
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
