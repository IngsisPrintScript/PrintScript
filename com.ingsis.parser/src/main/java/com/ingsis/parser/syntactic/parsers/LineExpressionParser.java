/*
 * My Project
 */

package com.ingsis.parser.syntactic.parsers;

import com.ingsis.parser.syntactic.NodePriority;
import com.ingsis.parser.syntactic.ParseResult;
import com.ingsis.parser.syntactic.tokenstream.TokenStream;
import com.ingsis.parser.syntactic.tokenstream.results.ConsumeResult;
import com.ingsis.utils.nodes.Node;
import com.ingsis.utils.nodes.expressions.ExpressionNode;
import com.ingsis.utils.token.template.TokenTemplate;

public class LineExpressionParser implements Parser<Node> {
    private final TokenTemplate semicolonTemplate;
    private final Parser<ExpressionNode> expressionParser;
    private final NodePriority nodePriority;

    public LineExpressionParser(
            TokenTemplate semicolonTemplate, Parser<ExpressionNode> expressionParser) {
        this.semicolonTemplate = semicolonTemplate;
        this.expressionParser = expressionParser;
        this.nodePriority = NodePriority.EXPRESSION;
    }

    @Override
    public ParseResult<Node> parse(TokenStream stream) {
        return switch (expressionParser.parse(stream)) {
            case ParseResult.COMPLETE<ExpressionNode> C -> parseSemicolon(C);
            default -> new ParseResult.INVALID<>();
        };
    }

    private ParseResult<Node> parseSemicolon(ParseResult.COMPLETE<ExpressionNode> in) {
        return switch (in.finalStream().consume(semicolonTemplate)) {
            case ConsumeResult.INCORRECT I -> new ParseResult.PREFIX<>(nodePriority);
            case ConsumeResult.CORRECT C -> createNode(in.node(), C);
        };
    }

    private ParseResult<Node> createNode(ExpressionNode expression, ConsumeResult.CORRECT in) {
        return new ParseResult.COMPLETE<>(expression, in.finalTokenStream(), nodePriority, true);
    }
}
