/*
 * My Project
 */

package com.ingsis.syntactic.parsers;

import com.ingsis.nodes.Node;
import com.ingsis.result.Result;
import com.ingsis.tokenstream.TokenStream;
import java.util.ArrayList;
import java.util.List;

public final class DefaultParserRegistry<T extends Node> implements ParserRegistry<T> {
  private final Parser<? extends T> nextRegistry;
  private final List<Parser<? extends T>> parsers;

  public DefaultParserRegistry(Parser<? extends T> nextRegistry) {
    this.nextRegistry = nextRegistry;
    this.parsers = new ArrayList<>();
  }

  public DefaultParserRegistry() {
    this(new FinalParser<T>());
  }

  @Override
  public void registerParser(Parser<? extends T> parser) {
    parsers.add(parser);
  }

  @SuppressWarnings("unchecked")
  @Override
  public Result<T> parse(TokenStream stream) {
    for (Parser<? extends T> parser : parsers) {
      Result<? extends T> result = parser.parse(stream);
      if (result.isCorrect()) {
        return (Result<T>) result;
      }
    }
    return (Result<T>) nextRegistry.parse(stream);
  }
}
