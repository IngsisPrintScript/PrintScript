/*
 * My Project
 */

package com.ingsis.syntactic.parsers;

import com.ingsis.nodes.Node;
import com.ingsis.result.Result;
import com.ingsis.tokenstream.TokenStream;

public interface Parser {
    Result<? extends Node> parse(TokenStream stream);
}
