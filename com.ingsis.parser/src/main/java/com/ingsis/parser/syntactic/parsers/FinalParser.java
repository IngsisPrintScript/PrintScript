/*
 * My Project
 */

package com.ingsis.parser.syntactic.parsers;

import com.ingsis.utils.nodes.nodes.Node;
import com.ingsis.utils.process.result.ProcessResult;
import com.ingsis.utils.token.tokenstream.TokenStream;

public final class FinalParser<T extends Node> implements Parser<T> {

  @Override
  public ProcessResult<T> parse(TokenStream stream) {
    return ProcessResult.INVALID();
  }
}
