/*
 * My Project
 */

package com.ingsis.syntactic.parsers;

import com.ingsis.result.IncorrectResult;
import com.ingsis.result.Result;
import com.ingsis.tokenstream.TokenStream;
import com.ingsis.visitors.Checkable;

public final class FinalParser implements Parser {

    @Override
    public Result<? extends Checkable> parse(TokenStream stream) {
        return new IncorrectResult<>("There was no parser able to handle that stream.");
    }
}
