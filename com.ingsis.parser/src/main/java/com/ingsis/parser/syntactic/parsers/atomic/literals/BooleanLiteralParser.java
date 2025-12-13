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

public class BooleanLiteralParser implements Parser<ExpressionNode> {
    private final TokenTemplate booleanLiteralTemplate;
    private final NodeFactory nodeFactory;

    public BooleanLiteralParser(
            TokenTemplateFactory tokenTemplateFactory, NodeFactory nodeFactory) {
        this.booleanLiteralTemplate = tokenTemplateFactory.booleanLiteral();
        this.nodeFactory = nodeFactory;
    }

    @Override
    public ParseResult<ExpressionNode> parse(TokenStream stream) {
        return switch (stream.consume(booleanLiteralTemplate)) {
            case ConsumeResult.CORRECT C -> createCompleteResult(C);
            case ConsumeResult.INCORRECT I -> new ParseResult.INVALID<>();
        };
    }

    private ParseResult.COMPLETE<ExpressionNode> createCompleteResult(ConsumeResult.CORRECT C) {
        return new ParseResult.COMPLETE<>(
                nodeFactory.createBooleanLiteralNode(
                        Boolean.parseBoolean(C.consumedToken().value()),
                        C.consumedToken().line(),
                        C.consumedToken().column()),
                C.finalTokenStream(),
                NodePriority.LITERAL,
                true);
    }
}
