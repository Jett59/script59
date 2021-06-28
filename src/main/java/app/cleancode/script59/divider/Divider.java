package app.cleancode.script59.divider;

import java.util.ArrayList;
import java.util.List;
import app.cleancode.script59.lex.Token;
import app.cleancode.script59.lex.TokenType;

public class Divider {
    public List<List<Token>> divide(List<Token> tokens) {
        List<List<Token>> result = new ArrayList<>();
        int statementStart = 0;
        for (int i = 0; i < tokens.size(); i++) {
            Token token = tokens.get(i);
            if (token.type().equals(TokenType.STATEMENT_END)
                    || token.type().equals(TokenType.BODY_OPEN)
                    || token.type().equals(TokenType.BODY_CLOSE)) {
                result.add(tokens.subList(statementStart, i + 1));
                statementStart = i + 1;
            }
        }
        return result;
    }
}
