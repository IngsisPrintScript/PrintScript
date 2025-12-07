/*
 * My Project
 */

package com.ingsis.lexer.tokenizers;

import com.ingsis.utils.process.result.ProcessResult;
import com.ingsis.utils.result.Result;
import com.ingsis.utils.token.Token;
import com.ingsis.utils.token.factories.TokenFactory;

public class PrefixAwarePatternTokenizer implements Tokenizer {
    private final String pattern;
    private final String prefixPattern;
    private final TokenFactory tokenFactory;
    private final Integer priority;

    public PrefixAwarePatternTokenizer(
            String pattern, String prefixPattern, TokenFactory tokenFactory, Integer priority) {
        this.pattern = pattern;
        this.prefixPattern = prefixPattern;
        this.tokenFactory = tokenFactory;
        this.priority = priority;
    }

    private Boolean matchesPattern(String input) {
        return input.matches(pattern);
    }

    private Boolean matchesPrefixPattern(String input) {
        return input.matches(prefixPattern);
    }

    @Override
    public ProcessResult<Token> tokenize(String input, Integer line, Integer column) {
        if (matchesPattern(input)) {
            Result<Token> createTokenResult = tokenFactory.createToken(input, line, column);
            if (!createTokenResult.isCorrect()) {
                return ProcessResult.INVALID();
            }
            return ProcessResult.COMPLETE(createTokenResult.result(), priority);

        } else if (matchesPrefixPattern(input)) {
            return ProcessResult.PREFIX(priority);
        }
        return ProcessResult.INVALID();
    }
}
