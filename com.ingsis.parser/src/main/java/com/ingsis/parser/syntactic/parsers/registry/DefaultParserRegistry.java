/*
 * My Project
 */

package com.ingsis.parser.syntactic.parsers.registry;

import com.ingsis.parser.syntactic.ParseResult;
import com.ingsis.parser.syntactic.parsers.Parser;
import com.ingsis.parser.syntactic.tokenstream.TokenStream;
import com.ingsis.utils.nodes.Node;
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

    @Override
    public ParseResult<T> parse(TokenStream stream) {
        ParseResult<T> best = new ParseResult.INVALID<>();
        for (Parser<T> parser : parsers) {
            best = best.comparePriority(parser.parse(stream));
        }
        return best;
    }
}
