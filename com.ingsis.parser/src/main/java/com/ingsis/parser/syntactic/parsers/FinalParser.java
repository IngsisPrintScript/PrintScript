/*
 * My Project
 */

package com.ingsis.parser.syntactic.parsers;

import com.ingsis.utils.nodes.nodes.Node;
import com.ingsis.utils.result.IncorrectResult;
import com.ingsis.utils.result.Result;
import com.ingsis.utils.token.tokenstream.TokenStream;

public final class FinalParser<T extends Node> implements Parser<T> {

    @Override
    public Result<T> parse(TokenStream stream) {
        return new IncorrectResult<>("There was no parser able to handle that stream.");
    }
}
