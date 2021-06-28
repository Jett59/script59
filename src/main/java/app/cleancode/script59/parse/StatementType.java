package app.cleancode.script59.parse;

import static app.cleancode.script59.lex.TokenType.ARGLIST_OPEN;
import static app.cleancode.script59.lex.TokenType.IDENTIFIER;
import static app.cleancode.script59.lex.TokenType.STATEMENT_END;
import java.util.List;
import app.cleancode.script59.lex.Token;
import app.cleancode.script59.lex.TokenType;

public enum StatementType {
    CALL, FUNCTION_DECLARE, FUNCTION_DEFINE, RETURN;

    public static StatementType of(List<Token> statement) {
        Token token1 = statement.get(0);
        TokenType token1Type = token1.type();
        if (token1Type.equals(IDENTIFIER)) {
            TokenType token2Type = statement.get(1).type();
            if (token2Type.equals(ARGLIST_OPEN)) {
                TokenType lastTokenType = statement.get(statement.size() - 1).type();
                if (lastTokenType.equals(STATEMENT_END)) {
                    return StatementType.CALL;
                }
            } else if (token1.value().equals("dfunc")) {
                Token token2 = statement.get(1);
                if (token2.type().equals(IDENTIFIER)) {
                    Token lastToken = statement.get(statement.size() - 1);
                    TokenType lastTokenType = lastToken.type();
                    switch (lastTokenType) {
                        case STATEMENT_END:
                            return FUNCTION_DECLARE;
                        case BODY_OPEN:
                            return FUNCTION_DEFINE;
                        default:
                            throw new IllegalArgumentException(
                                    "unknown line ending " + lastToken.value());
                    }
                }
            } else if (token1.value().equals("return")) {
                Token lastToken = statement.get(statement.size() - 1);
                TokenType lastTokenType = lastToken.type();
                if (lastTokenType == STATEMENT_END) {
                    return RETURN;
                } else {
                    throw new IllegalArgumentException(
                            "Invalid line ending for return statement: " + lastToken.value());
                }
            }
        }
        throw new IllegalArgumentException("unknown statement: " + String.join(" ",
                statement.stream().map(Token::toString).toList().toArray(new String[0])));
    }
}
