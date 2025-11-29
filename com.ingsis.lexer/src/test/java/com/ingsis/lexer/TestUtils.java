/*
 * My Project
 */

package com.ingsis.lexer;

import com.ingsis.utils.result.factory.DefaultResultFactory;
import com.ingsis.utils.result.factory.ResultFactory;
import com.ingsis.utils.token.tokens.factories.DefaultTokensFactory;
import com.ingsis.utils.token.tokens.factories.TokenFactory;

public final class TestUtils {
    public static TokenFactory tokenFactory() {
        return new DefaultTokensFactory();
    }

    public static ResultFactory resultFactory() {
        return new DefaultResultFactory();
    }
}
