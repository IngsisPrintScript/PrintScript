/*
 * My Project
 */

package com.ingsis.lexer.tokenizers;

import com.ingsis.lexer.TokenizeResult;
import com.ingsis.lexer.tokenizers.categories.TokenCategory;
import com.ingsis.utils.metachar.string.builder.MetaCharStringBuilder;
import com.ingsis.utils.token.Token;
import com.ingsis.utils.token.factories.TokenFactory;
import com.ingsis.utils.token.type.TokenType;
import java.util.List;

public class PatternMatchTokenizer implements Tokenizer {
    private final TokenType type;
    private final TokenCategory category;
    private final TokenFactory tokenFactory;

    public PatternMatchTokenizer(
            TokenType type, TokenCategory category, TokenFactory tokenFactory) {
        this.type = type;
        this.category = category;
        this.tokenFactory = tokenFactory;
    }

    private Boolean canTokenize(String input) {
        return input.matches(type.pattern());
    }

    @Override
    public TokenizeResult tokenize(MetaCharStringBuilder sb, List<Token> trailingTrivia) {
        if (!canTokenize(sb.getString())) {
            return new TokenizeResult.INVALID();
        }
        return new TokenizeResult.COMPLETE(
                tokenFactory.createKnownToken(
                        type, sb.getString(), trailingTrivia, sb.getLine(), sb.getColumn()),
                category.priority());
    }
}
