package app.cleancode.script59.parse;

import java.util.List;
import java.util.Optional;
import app.cleancode.script59.lex.Token;

public interface SyntaxNode {
    List<Token> associatedTokens();

    Optional<List<SyntaxNode>> getChildren();

    void setParent(SyntaxNode node);

    Optional<SyntaxNode> parent();

    Optional<StatementType> statementType();
}
