/*
 * My Project
 */

package com.ingsis.syntactic.parsers;

import com.ingsis.result.Result;
import com.ingsis.tokenstream.TokenStream;
import com.ingsis.visitors.Checkable;
import java.util.ArrayList;
import java.util.List;

public final class DefaultParserRegistry implements ParserRegistry {
    private final Parser nextRegistry;
    private final List<Parser> parsers;

    public DefaultParserRegistry(Parser nextRegistry) {
        this.nextRegistry = nextRegistry;
        this.parsers = new ArrayList<>();
    }

    public DefaultParserRegistry() {
        this(new FinalParser());
    }

    @Override
    public void registerParser(Parser parser) {
        parsers.add(parser);
    }

    @Override
    public Result<? extends Checkable> parse(TokenStream stream) {
        for (Parser parser : parsers) {
            Result<? extends Checkable> result = parser.parse(stream);
            if (result.isCorrect()) {
                return result;
            }
        }
        return nextRegistry.parse(stream);
    }
}
