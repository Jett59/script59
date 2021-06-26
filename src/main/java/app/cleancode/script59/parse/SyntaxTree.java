package app.cleancode.script59.parse;

import java.util.List;
import java.util.Optional;
import app.cleancode.script59.lex.Token;

public class SyntaxTree implements SyntaxNode {
    private final List<Token> associatedTokens;
    private final List<SyntaxNode> children;

    private SyntaxNode parent;

    public SyntaxTree(List<Token> associatedTokens, List<SyntaxNode> children) {
        this.associatedTokens = associatedTokens;
        this.children = children;
    }

    @Override
    public void setParent(SyntaxNode node) {
        this.parent = node;
    }

    @Override
    public Optional<SyntaxNode> parent() {
        return Optional.ofNullable(parent);
    }

    @Override
    public List<Token> associatedTokens() {
        return associatedTokens;
    }

    @Override
    public Optional<List<SyntaxNode>> getChildren() {
        return Optional.ofNullable(children);
    }

}
