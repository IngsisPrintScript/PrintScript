/*
 * My Project
 */

package com.ingsis.lexer.tokenizers.operator;

import com.ingsis.lexer.tokenizers.Tokenizer;
import com.ingsis.result.CorrectResult;
import com.ingsis.result.IncorrectResult;
import com.ingsis.result.Result;
import com.ingsis.tokens.Token;
import com.ingsis.tokens.factories.TokenFactory;

public final class GenericOperatorTokenizer implements Tokenizer {
    String template;
    TokenFactory tokenFactory;

    public GenericOperatorTokenizer(TokenFactory tokenFactory, String template) {
        this.template = template;
        this.tokenFactory = tokenFactory;
    }

    private Boolean canTokenize(String input) {
        return input.equals(template);
    }

    @Override
    public Result<Token> tokenize(String input, Integer line, Integer column) {
        if (!canTokenize(input)) {
            return new IncorrectResult<>(
                    String.format("Input is not expected operator '%s': %s", template, input));
        }
        return new CorrectResult<>(tokenFactory.createOperatorToken(input, line, column));
    }
}
