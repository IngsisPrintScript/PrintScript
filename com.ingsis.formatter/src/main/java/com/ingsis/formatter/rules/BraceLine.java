/*
 * My Project
 */

package com.ingsis.formatter.rules;

import com.ingsis.utils.token.Token;
import com.ingsis.utils.token.type.TokenType;
import java.util.List;

public class BraceLine implements TriviaRule {
    private final boolean onIfLine;

    public BraceLine(boolean onIfLine) {
        this.onIfLine = onIfLine;
    }

    @Override
    public boolean applies(Token previousToken, Token currentToken) {
        return previousToken.type().equals(TokenType.RPAREN)
                && currentToken.type().equals(TokenType.LBRACE);
    }

    @Override
    public StringBuilder apply(
            Token previousToken,
            List<Token> trivia,
            Token currentToken,
            StringBuilder stringBuilder,
            int indentation) {
        if (!onIfLine) {
            stringBuilder.append("\n");
            stringBuilder.append(" ".repeat(indentation));
        } else {
            stringBuilder.append(" ");
        }
        stringBuilder.append(currentToken.value());
        return stringBuilder;
    }
}
