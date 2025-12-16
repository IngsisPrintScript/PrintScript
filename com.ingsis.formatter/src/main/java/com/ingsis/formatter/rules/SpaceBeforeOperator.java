/*
 * My Project
 */

package com.ingsis.formatter.rules;

import com.ingsis.utils.token.Token;
import com.ingsis.utils.token.type.TokenType;
import java.util.List;

public class SpaceBeforeOperator implements TriviaRule {
    @Override
    public boolean applies(Token previousToken, Token currentToken) {
        return TokenType.OPERATORS.contains(currentToken.type());
    }

    @Override
    public StringBuilder apply(
            Token previousToken,
            List<Token> trivia,
            Token currentToken,
            StringBuilder stringBuilder,
            int indentation) {
        stringBuilder.append(" ");
        stringBuilder.append(currentToken.value());
        return stringBuilder;
    }
}
