/*
 * My Project
 */

package com.ingsis.parser.syntactic.parsers;

import com.ingsis.utils.nodes.nodes.Node;
import com.ingsis.utils.process.result.ProcessResult;
import com.ingsis.utils.token.tokenstream.TokenStream;

public interface Parser<T extends Node> {
  ProcessResult<T> parse(TokenStream stream);
}
