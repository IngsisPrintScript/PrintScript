/*
 * My Project
 */

package com.ingsis.syntactic.parsers;

import com.ingsis.result.Result;
import com.ingsis.tokenstream.TokenStream;
import com.ingsis.visitors.Checkable;

public interface Parser {
    Result<? extends Checkable> parse(TokenStream stream);
}
