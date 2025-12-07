/*
 * My Project
 */

package com.ingsis.parser.syntactic.parsers;

import com.ingsis.utils.nodes.Node;
import com.ingsis.utils.process.checkpoint.ProcessCheckpoint;
import com.ingsis.utils.process.result.ProcessResult;
import com.ingsis.utils.token.Token;
import com.ingsis.utils.token.tokenstream.TokenStream;

public interface Parser<T extends Node> {
  ProcessCheckpoint<Token, ProcessResult<T>> parse(TokenStream stream);
}
