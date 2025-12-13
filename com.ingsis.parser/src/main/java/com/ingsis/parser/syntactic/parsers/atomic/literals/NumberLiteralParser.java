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
import java.math.BigDecimal;

public class NumberLiteralParser implements Parser<ExpressionNode> {
    private final TokenTemplate numberLiteralTemplate;
    private final NodeFactory nodeFactory;

    public NumberLiteralParser(TokenTemplateFactory tokenTemplateFactory, NodeFactory nodeFactory) {
        this.numberLiteralTemplate = tokenTemplateFactory.numberLiteral();
        this.nodeFactory = nodeFactory;
    }

    @Override
    public ParseResult<ExpressionNode> parse(TokenStream stream) {
        return switch (stream.consume(numberLiteralTemplate)) {
            case ConsumeResult.CORRECT C -> createCompleteResult(C);
            case ConsumeResult.INCORRECT I -> new ParseResult.INVALID<>();
        };
    }

    private ParseResult.COMPLETE<ExpressionNode> createCompleteResult(ConsumeResult.CORRECT C) {
        return new ParseResult.COMPLETE<>(
                nodeFactory.createNumberLiteralNode(
                        new BigDecimal(C.consumedToken().value()),
                        C.consumedToken().line(),
                        C.consumedToken().column()),
                C.finalTokenStream(),
                NodePriority.LITERAL,
                true);
    }
}
