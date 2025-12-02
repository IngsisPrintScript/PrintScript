/*
 * My Project
 */

package com.ingsis.parser.syntactic.parsers;

import com.ingsis.utils.nodes.nodes.Node;
import com.ingsis.utils.result.Result;
import com.ingsis.utils.token.tokenstream.TokenStream;
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
            System.out.println("PARSER: " + parser + " RETURED: " + result);
            if (result.isCorrect()) {
                return (Result<T>) result;
            } else {
                stream.resetPointer();
            }
        }
        return (Result<T>) nextRegistry.parse(stream);
    }
}
