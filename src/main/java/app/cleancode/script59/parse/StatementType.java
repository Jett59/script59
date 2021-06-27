package app.cleancode.script59.parse;

import static app.cleancode.script59.lex.Token.Type.ARGLIST_OPEN;
import static app.cleancode.script59.lex.Token.Type.IDENTIFIER;
import static app.cleancode.script59.lex.Token.Type.STATEMENT_END;
import java.util.List;
import app.cleancode.script59.lex.Token;

public enum StatementType {
    CALL;

    public static StatementType of(List<Token> statement) {
        Token.Type token1 = statement.get(0).type();
        if (token1.equals(IDENTIFIER)) {
            Token.Type token2 = statement.get(1).type();
            if (token2.equals(ARGLIST_OPEN)) {
                Token.Type lastToken = statement.get(statement.size() - 1).type();
                if (lastToken.equals(STATEMENT_END)) {
                    return StatementType.CALL;
                }
            }
        }
        throw new IllegalArgumentException("unknown statement: " + String.join(" ",
                statement.stream().map(Token::toString).toList().toArray(new String[0])));
    }
}
