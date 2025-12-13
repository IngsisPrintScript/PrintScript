/*
 * My Project
 */

package com.ingsis.parser.syntactic.parsers;

import com.ingsis.parser.syntactic.ParseResult;
import com.ingsis.parser.syntactic.tokenstream.TokenStream;
import com.ingsis.utils.nodes.visitors.Checkable;

public interface Parser<T extends Checkable> {
    ParseResult<T> parse(TokenStream stream);
}
