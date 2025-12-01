/*
 * My Project
 */

package com.ingsis.parser.syntactic.parsers.operator;

import com.ingsis.utils.token.tokens.Token;

public class DefaultPrecedenceEvaluator implements PrecedenceEvaluator {
    public Integer getPrecedence(Token token) {
        return switch (token.value()) {
            case "*", "/" -> 2;
            case "+", "-" -> 1;
            default -> 0;
        };
    }
}
