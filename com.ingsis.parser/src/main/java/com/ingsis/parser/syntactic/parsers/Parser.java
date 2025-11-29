/*
 * My Project
 */

package com.ingsis.parser.syntactic.parsers;

import com.ingsis.utils.nodes.nodes.Node;
import com.ingsis.utils.result.Result;
import com.ingsis.utils.token.tokenstream.TokenStream;

public interface Parser<T extends Node> {
    Result<T> parse(TokenStream stream);
}
