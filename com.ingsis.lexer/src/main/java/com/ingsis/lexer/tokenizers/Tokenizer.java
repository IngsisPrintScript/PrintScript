/*
 * My Project
 */

package com.ingsis.lexer.tokenizers;

import com.ingsis.lexer.TokenizeResult;
import com.ingsis.utils.metachar.string.builder.MetaCharStringBuilder;
import com.ingsis.utils.token.Token;
import java.util.List;

public interface Tokenizer {
    TokenizeResult tokenize(MetaCharStringBuilder sb, List<Token> trailingTrivia);
}
