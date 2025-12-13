/*
 * My Project
 */

package com.ingsis.parser.syntactic.parsers.atomic.literals;

import com.ingsis.parser.syntactic.NodePriority;
import com.ingsis.parser.syntactic.ParseResult;
import com.ingsis.parser.syntactic.parsers.Parser;
import com.ingsis.parser.syntactic.tokenstream.TokenStream;
import com.ingsis.parser.syntactic.tokenstream.results.ConsumeResult;
import com.ingsis.utils.nodes.expressions.ExpressionNode;
import com.ingsis.utils.nodes.factories.NodeFactory;
import com.ingsis.utils.token.template.TokenTemplate;
import com.ingsis.utils.token.template.factories.TokenTemplateFactory;

public class StringLiteralParser implements Parser<ExpressionNode> {
    private final TokenTemplate stringLiteralTemplate;
    private final NodeFactory nodeFactory;

    public StringLiteralParser(TokenTemplateFactory tokenTemplateFactory, NodeFactory nodeFactory) {
        this.stringLiteralTemplate = tokenTemplateFactory.stringLiteral();
        this.nodeFactory = nodeFactory;
    }

    @Override
    public ParseResult<ExpressionNode> parse(TokenStream stream) {
        return switch (stream.consume(stringLiteralTemplate)) {
            case ConsumeResult.CORRECT C -> createCompleteResult(C);
            case ConsumeResult.INCORRECT I -> new ParseResult.INVALID<>();
        };
    }

    private ParseResult.COMPLETE<ExpressionNode> createCompleteResult(ConsumeResult.CORRECT C) {
        return new ParseResult.COMPLETE<>(
                nodeFactory.createStringLiteralNode(
                        C.consumedToken()
                                .value()
                                .substring(1, C.consumedToken().value().length() - 1),
                        C.consumedToken().line(),
                        C.consumedToken().column()),
                C.finalTokenStream(),
                NodePriority.LITERAL,
                true);
    }
}
