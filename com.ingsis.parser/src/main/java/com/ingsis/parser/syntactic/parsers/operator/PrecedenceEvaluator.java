/*
 * My Project
 */

package com.ingsis.parser.syntactic.parsers.operator;

import com.ingsis.utils.token.tokens.Token;

public interface PrecedenceEvaluator {

    /**
     * Returns the precedence of the given operator token. Higher numbers mean higher precedence.
     *
     * @param token the operator token
     * @return precedence value
     */
    public Integer getPrecedence(Token token);
}
