package app.cleancode.script59.lex;

import java.util.ArrayList;
import java.util.List;

public class Lexer {
    public List<String> lex(String input) {
        List<String> tokens = new ArrayList<>();
        Token.Type tokenType;
        int tokenStart = 0;
        for (int i = 0; i < input.length(); i++) {
            if (tokenType == null) {
                tokenStart = i;
                char startChar = input.charAt(i);
                if (isOperator(startChar)) {
                    tokenType = Token.Type.OPERATOR;
                } else if (Character.isDigit(startChar)) {
                    TokenType = Token.Type.NUMBER;
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
                } else if (startChar == ' ' || startChar == '\n' || startChar == '\t') {
                    continue;
                } else {
                    throw new IllegalArgumentException(
                            "Error: token '" + startChar + "' not defined");
                }
            }

        }
    }
}
