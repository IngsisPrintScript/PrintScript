/*
 * My Project
 */

package com.ingsis.lexer.tokenizers;

import com.ingsis.lexer.tokenizers.categories.TokenCategory;
import com.ingsis.utils.process.result.ProcessResult;
import com.ingsis.utils.token.Token;
import com.ingsis.utils.token.factories.TokenFactory;
import com.ingsis.utils.token.type.TokenType;

public class ExactMatchTokenizer implements Tokenizer {
    private final TokenType type;
    private final TokenCategory category;
    private final TokenFactory tokenFactory;

    public ExactMatchTokenizer(TokenType type, TokenCategory category, TokenFactory tokenFactory) {
        this.type = type;
        this.category = category;
        this.tokenFactory = tokenFactory;
    }

    private Boolean canTokenize(String input) {
        return input.equals(type.lexeme());
    }

    @Override
    public ProcessResult<Token> tokenize(String input, Integer line, Integer column) {
        if (!canTokenize(input)) {
            return ProcessResult.INVALID();
        }
        return ProcessResult.COMPLETE(
                tokenFactory.createKnownToken(type, type.lexeme(), line, column),
                category.priority());
    }
}
