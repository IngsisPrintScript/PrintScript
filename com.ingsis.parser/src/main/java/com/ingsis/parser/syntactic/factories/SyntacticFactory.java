/*
 * My Project
 */

package com.ingsis.parser.syntactic.factories;

import com.ingsis.parser.syntactic.LogerSyntacticParser;
import com.ingsis.parser.syntactic.SyntacticParser;
import com.ingsis.parser.syntactic.tokenstream.DefaultTokenStream;
import com.ingsis.utils.iterator.safe.SafeIterator;
import com.ingsis.utils.iterator.safe.factories.SafeIteratorFactory;
import com.ingsis.utils.iterator.safe.result.IterationResultFactory;
import com.ingsis.utils.nodes.visitors.Checkable;
import com.ingsis.utils.token.Token;
import com.ingsis.utils.token.factories.TokenFactory;
import java.io.InputStream;

public final class SyntacticFactory implements SafeIteratorFactory<Checkable> {
    private final SafeIteratorFactory<Token> tokenIteratorFactory;
    private final ParserChainFactory parserChainFactory;
    private final IterationResultFactory iterationResultFactory;
    private final TokenFactory tokenFactory;

    public SyntacticFactory(
            SafeIteratorFactory<Token> tokenIteratorFactory,
            ParserChainFactory parserFactory,
            IterationResultFactory iterationResultFactory,
            TokenFactory tokenFactory) {
        this.tokenIteratorFactory = tokenIteratorFactory;
        this.parserChainFactory = parserFactory;
        this.iterationResultFactory = iterationResultFactory;
        this.tokenFactory = tokenFactory;
    }

    @Override
    public SafeIterator<Checkable> fromInputStream(InputStream in) {
        return new SyntacticParser(
                tokenIteratorFactory.fromInputStream(in),
                parserChainFactory.createDefaultChain(),
                new DefaultTokenStream(tokenFactory, iterationResultFactory),
                iterationResultFactory);
    }

    @Override
    public SafeIterator<Checkable> fromInputStreamLogger(InputStream in, String debugPath) {
        try {
            return new LogerSyntacticParser(
                    new SyntacticParser(
                            tokenIteratorFactory.fromInputStreamLogger(in, debugPath),
                            parserChainFactory.createDefaultChain(),
                            new DefaultTokenStream(tokenFactory, iterationResultFactory),
                            iterationResultFactory),
                    debugPath,
                    iterationResultFactory);
        } catch (Exception exception) {
            throw new RuntimeException();
        }
    }
}
