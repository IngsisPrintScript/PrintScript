/*
 * My Project
 */

package com.ingsis.syntactic.factories;

import com.ingsis.syntactic.parsers.DefaultParserRegistry;
import com.ingsis.syntactic.parsers.Parser;
import com.ingsis.syntactic.parsers.ParserRegistry;
import com.ingsis.syntactic.parsers.factories.ParserFactory;

public final class DefaultParserChainFactory implements ParserChainFactory {
    private final ParserFactory PARSER_FACTORY;

    public DefaultParserChainFactory(ParserFactory PARSER_FACTORY) {
        this.PARSER_FACTORY = PARSER_FACTORY;
    }

    @Override
    public ParserRegistry createDefaultChain() {
        return createKeywordsParserRegistry(createOperatorParserRegistry());
    }

    private ParserRegistry createKeywordsParserRegistry(Parser nextParser) {
        ParserRegistry registry = new DefaultParserRegistry(nextParser);
        registry.registerParser(PARSER_FACTORY.createDeclarationParser());
        return registry;
    }

    private ParserRegistry createOperatorParserRegistry() {
        ParserRegistry registry = new DefaultParserRegistry();
        registry.registerParser(PARSER_FACTORY.createBinaryOperatorParser());
        return registry;
    }
}
