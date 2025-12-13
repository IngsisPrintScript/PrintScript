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

public class PrefixAwarePatternTokenizer implements Tokenizer {
    private final TokenType type;
    private final TokenCategory category;
    private final TokenFactory tokenFactory;

    public PrefixAwarePatternTokenizer(
            TokenType type, TokenCategory category, TokenFactory tokenFactory) {
        this.type = type;
        this.tokenFactory = tokenFactory;
        this.category = category;
    }

    private Boolean matchesPattern(String input) {
        return input.matches(type.pattern());
    }

    private Boolean matchesPrefixPattern(String input) {
        return input.matches(type.prefixPattern());
    }

    @Override
    public TokenizeResult tokenize(MetaCharStringBuilder sb, List<Token> trailingTrivia) {
        if (matchesPattern(sb.getString())) {
            return new TokenizeResult.COMPLETE(
                    tokenFactory.createKnownToken(
                            type, sb.getString(), trailingTrivia, sb.getLine(), sb.getColumn()),
                    category.priority());
        } else if (matchesPrefixPattern(sb.getString())) {
            return new TokenizeResult.PREFIX(category.priority());
        }
        return new TokenizeResult.INVALID();
    }
}
