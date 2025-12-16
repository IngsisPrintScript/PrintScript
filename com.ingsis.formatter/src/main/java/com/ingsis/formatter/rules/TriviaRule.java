/*
 * My Project
 */

package com.ingsis.formatter.rules;

import com.ingsis.utils.token.Token;
import java.util.List;

public interface TriviaRule {
    boolean applies(Token previousToken, Token currentToken);

    StringBuilder apply(
            Token previousToken,
            List<Token> trivia,
            Token currentToken,
            StringBuilder stringBuilder,
            int indentation);
}
