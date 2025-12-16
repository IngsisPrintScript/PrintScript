/*
 * My Project
 */

package com.ingsis.formatter.rules;

import com.ingsis.utils.token.Token;
import com.ingsis.utils.token.type.TokenType;
import java.util.List;

public class LineBreakAfterStatement implements TriviaRule {
    @Override
    public boolean applies(Token previousToken, Token currentToken) {
        return previousToken.type().equals(TokenType.SEMICOLON);
    }

    @Override
    public StringBuilder apply(
            Token previousToken,
            List<Token> trivia,
            Token currentToken,
            StringBuilder stringBuilder,
            int indentation) {
        stringBuilder.append("\n");
        stringBuilder.append(" ".repeat(indentation));
        stringBuilder.append(currentToken.value());
        return stringBuilder;
    }
}
