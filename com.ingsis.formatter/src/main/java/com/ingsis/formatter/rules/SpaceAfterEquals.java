/*
 * My Project
 */

package com.ingsis.formatter.rules;

import com.ingsis.utils.token.Token;
import com.ingsis.utils.token.type.TokenType;
import java.util.List;

public class SpaceAfterEquals implements TriviaRule {
    private final boolean hasSpace;

    public SpaceAfterEquals(boolean hasSpace) {
        this.hasSpace = hasSpace;
    }

    @Override
    public boolean applies(Token previousToken, Token currentToken) {
        return previousToken.type().equals(TokenType.EQUAL);
    }

    @Override
    public StringBuilder apply(
            Token previousToken,
            List<Token> trivia,
            Token currentToken,
            StringBuilder sb,
            int indentation) {
        if (hasSpace) {
            sb.append(" ");
        }
        sb.append(currentToken.value());
        return sb;
    }
}
