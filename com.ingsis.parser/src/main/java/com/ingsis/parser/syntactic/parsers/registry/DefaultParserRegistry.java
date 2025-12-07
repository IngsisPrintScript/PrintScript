/*
 * My Project
 */

package com.ingsis.parser.syntactic.parsers.registry;

import com.ingsis.parser.syntactic.parsers.Parser;
import com.ingsis.utils.nodes.Node;
import com.ingsis.utils.process.checkpoint.ProcessCheckpoint;
import com.ingsis.utils.process.result.ProcessResult;
import com.ingsis.utils.token.Token;
import com.ingsis.utils.token.tokenstream.TokenStream;
import java.util.ArrayList;
import java.util.List;

public final class DefaultParserRegistry<T extends Node> implements ParserRegistry<T> {
  private final List<Parser<T>> parsers;

  public DefaultParserRegistry(List<Parser<T>> parsers) {
    this.parsers = List.copyOf(parsers);
  }

  public DefaultParserRegistry() {
    this(new ArrayList<>());
  }

  @Override
  public ParserRegistry<T> registerParser(Parser<T> parser) {
    List<Parser<T>> newParser = new ArrayList<>(parsers);
    newParser.add(parser);
    return new DefaultParserRegistry<>(newParser);
  }

  private ProcessCheckpoint<Token, ProcessResult<T>> getBetterResult(
      ProcessCheckpoint<Token, ProcessResult<T>> best,
      ProcessCheckpoint<Token, ProcessResult<T>> candidate) {
    ProcessResult<T> bestResult = best.result();
    ProcessResult<T> candidateResult = candidate.result();

    if (bestResult.comparePriority(candidateResult).equals(candidateResult)) {
      return candidate;
    }
    return best;
  }

  @Override
  public ProcessCheckpoint<Token, ProcessResult<T>> parse(TokenStream stream) {
    ProcessCheckpoint<Token, ProcessResult<T>> best = ProcessCheckpoint.UNINITIALIZED();
    for (Parser<T> parser : parsers) {
      ProcessCheckpoint<Token, ProcessResult<T>> candidate = parser.parse(stream);
      if (best.isUninitialized() && candidate.isInitialized()) {
        best = (ProcessCheckpoint<Token, ProcessResult<T>>) candidate;
        continue;
      } else if (candidate.isUninitialized() || !candidate.result().status().isValid()) {
        continue;
      }
      best = getBetterResult(best, candidate);
    }
    return best;
  }
}
