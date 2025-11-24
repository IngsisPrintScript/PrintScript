/*
 * My Project
 */

package com.ingsis.lexer;

import com.ingsis.result.factory.DefaultResultFactory;
import com.ingsis.result.factory.ResultFactory;
import com.ingsis.tokens.factories.DefaultTokensFactory;
import com.ingsis.tokens.factories.TokenFactory;

public final class TestUtils {
    public static TokenFactory tokenFactory() {
        return new DefaultTokensFactory();
    }

    public static ResultFactory resultFactory() {
        return new DefaultResultFactory();
    }
}
