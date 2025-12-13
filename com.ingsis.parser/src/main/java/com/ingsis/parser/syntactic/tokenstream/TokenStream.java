/*
 * My Project
 */

package com.ingsis.parser.syntactic.tokenstream;

import com.ingsis.parser.syntactic.parsers.Parser;
import com.ingsis.parser.syntactic.tokenstream.results.ConsumeResult;
import com.ingsis.parser.syntactic.tokenstream.results.ConsumeSequenceResult;
import com.ingsis.utils.iterator.safe.SafeIterator;
import com.ingsis.utils.nodes.Node;
import com.ingsis.utils.result.Result;
import com.ingsis.utils.token.Token;
import com.ingsis.utils.token.template.TokenTemplate;
import java.util.List;

public interface TokenStream extends SafeIterator<Token> {
    ConsumeResult consume();

    ConsumeResult consume(TokenTemplate tokenTemplate);

    <T extends Node> ConsumeSequenceResult<T> consumeSequenceWithNoSeparator(
            TokenTemplate intialToken, TokenTemplate finalToken, Parser<T> elementParser);

    <T extends Node> ConsumeSequenceResult<T> consumeFullSequence(
            TokenTemplate intialToken,
            TokenTemplate separator,
            TokenTemplate finalToken,
            Parser<T> elementParser);

    <T extends Node> ConsumeSequenceResult<T> consumeSequenceWithNoInitialToken(
            TokenTemplate separator, TokenTemplate finalToken, Parser<T> elementParser);

    <T extends Node> ConsumeSequenceResult<T> consumeElementsWithSeparator(
            TokenTemplate separator, Parser<T> elementParser);

    TokenStream consumeAll(TokenTemplate tokenTemplate);

    Result<Token> peek(int offset);

    List<Token> tokens();

    Integer pointer();

    TokenStream withToken(Token token);

    TokenStream reset();

    TokenStream sliceFromPointer();

    TokenStream advanceBy(TokenStream subStream);

    TokenStream consumeAll();

    Boolean isEmpty();
}
