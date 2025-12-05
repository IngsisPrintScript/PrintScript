/*
 * My Project
 */

package com.ingsis.parser.syntactic.parsers.registry;

import com.ingsis.parser.syntactic.parsers.FinalParser;
import com.ingsis.parser.syntactic.parsers.Parser;
import com.ingsis.utils.nodes.nodes.Node;
import com.ingsis.utils.process.result.ProcessResult;
import com.ingsis.utils.token.tokenstream.TokenStream;
import java.util.ArrayList;
import java.util.List;

public final class DefaultParserRegistry<T extends Node> implements ParserRegistry<T> {
  private final Parser<T> nextRegistry;
  private final List<Parser<T>> parsers;

  public DefaultParserRegistry(Parser<T> nextRegistry, List<Parser<T>> parsers) {
    this.nextRegistry = nextRegistry;
    this.parsers = List.copyOf(parsers);
  }

  public DefaultParserRegistry(Parser<T> nextRegistry) {
    this(nextRegistry, new ArrayList<>());
  }

  public DefaultParserRegistry() {
    this(new FinalParser<T>());
  }

  @Override
  public ParserRegistry<T> registerParser(Parser<T> parser) {
    List<Parser<T>> newParser = new ArrayList<>(parsers);
    newParser.add(parser);
    return new DefaultParserRegistry<>(nextRegistry, newParser);
  }

  public ProcessResult<T> parse(TokenStream stream) {
    for (Parser<T> parser : parsers) {
      ProcessResult<T> result = parser.parse(stream);
      if (result.isComplete()) {
        return result;
      }
    }
    return nextRegistry.parse(stream);
  }
}
