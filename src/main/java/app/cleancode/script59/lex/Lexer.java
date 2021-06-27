package app.cleancode.script59.lex;

import static app.cleancode.script59.operator.Operators.isOperator;
import java.util.ArrayList;
import java.util.List;

public class Lexer {
    public List<Token> lex(String input) {
        List<Token> tokens = new ArrayList<>();
        Token.Type tokenType = null;
        int tokenStart = 0;
        for (int i = 0; i < input.length(); i++) {
            if (tokenType == null) {
                tokenStart = i;
                char startChar = input.charAt(i);
                if (isOperator(startChar)) {
                    tokenType = Token.Type.OPERATOR;
                } else if (Character.isDigit(startChar)) {
                    tokenType = Token.Type.NUMBER;
                } else if (startChar == '\'' || startChar == '"') {
                    tokenType = Token.Type.STRING;
                } else if (startChar == ';') {
                    tokenType = Token.Type.STATEMENT_END;
                } else if (startChar == '(') {
                    tokenType = Token.Type.ARGLIST_OPEN;
                } else if (startChar == ')') {
                    tokenType = Token.Type.ARGLIST_CLOSE;
                } else if (startChar == '{') {
                    tokenType = Token.Type.BODY_OPEN;
                } else if (startChar == '}') {
                    tokenType = Token.Type.BODY_CLOSE;
                } else if (Character.isLetter(startChar)) {
                    tokenType = Token.Type.IDENTIFIER;
                } else if (startChar == ' ' || startChar == '\n' || startChar == '\r'
                        || startChar == '\t') {
                    continue;
                } else {
                    throw new IllegalArgumentException(
                            "Error: token '" + startChar + "' not defined");
                }
            } else {
                if (tokenType == Token.Type.STRING) {
                    char c = input.charAt(i);
                    if (c == '\\') {
                        i++;
                        continue;
                    } else if (c == '"' || c == '\'') {
                        tokens.add(new Token(tokenType, input.substring(tokenStart, i + 1)));
                        tokenType = null;
                    }
                } else {
                    char c = input.charAt(i);
                    if ((c == ' ' || c == '\n' || c == '\r' || c == '\t')
                            || (tokenType == Token.Type.OPERATOR && !isOperator(c))
                            || (tokenType != Token.Type.OPERATOR && isOperator(c))
                            || (c == ';' || c == '(' || c == ')' || c == '{' || c == '}' || c == '"'
                                    || c == '\'')) {
                        tokens.add(new Token(tokenType, input.substring(tokenStart, i)));
                        tokenType = null;
                        i--;
                    }
                }
            }
        }
        if (tokenType == Token.Type.STRING) {
            throw new IllegalArgumentException("Error: Unexpected end of file before close quote");
        }
        if (tokenType != null) {
            tokens.add(new Token(tokenType, input.substring(tokenStart)));
        }
        return tokens;
    }
}
