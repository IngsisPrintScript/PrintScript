/*
 * My Project
 */

package com.ingsis.syntactic.parsers;

import com.ingsis.nodes.Node;
import com.ingsis.result.IncorrectResult;
import com.ingsis.result.Result;
import com.ingsis.tokenstream.TokenStream;

public final class FinalParser implements Parser {

    @Override
    public Result<Node> parse(TokenStream stream) {
        return new IncorrectResult<>("There was no parser able to handle that stream.");
    }
}
