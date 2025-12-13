/*
 * My Project
 */

package com.ingsis.parser.syntactic.parsers.operator;

import com.ingsis.parser.syntactic.NodePriority;
import com.ingsis.parser.syntactic.ParseResult;
import com.ingsis.parser.syntactic.parsers.Parser;
import com.ingsis.parser.syntactic.tokenstream.TokenStream;
import com.ingsis.parser.syntactic.tokenstream.results.ConsumeResult;
import com.ingsis.utils.nodes.expressions.ExpressionNode;
import com.ingsis.utils.nodes.expressions.OperatorType;
import com.ingsis.utils.nodes.factories.NodeFactory;
import com.ingsis.utils.result.Result;
import java.util.List;
import java.util.function.Supplier;

public final class PrattParser implements Parser<ExpressionNode> {
    private final Supplier<Parser<ExpressionNode>> leafParserSupplier;
    private final NodeFactory nodeFactory;
    private final NodePriority nodePriority;

    public PrattParser(
            Supplier<Parser<ExpressionNode>> leafParserSupplier, NodeFactory nodeFactory) {
        this.leafParserSupplier = leafParserSupplier;
        this.nodeFactory = nodeFactory;
        this.nodePriority = NodePriority.OPERATOR;
    }

    @Override
    public ParseResult<ExpressionNode> parse(TokenStream stream) {
        return parseExpression(stream, 0);
    }

    private ParseResult<ExpressionNode> parseExpression(
            TokenStream stream, Integer rightBindingPower) {
        return switch (leafParserSupplier.get().parse(stream)) {
            case ParseResult.COMPLETE<ExpressionNode> C ->
                    parseTail(C.node(), C.finalStream(), rightBindingPower);
            default -> new ParseResult.INVALID<>();
        };
    }

    private ParseResult<ExpressionNode> parseTail(
            ExpressionNode lhs, TokenStream stream, Integer rBindingPower) {
        if (stream.isEmpty()) {
            return new ParseResult.COMPLETE<>(lhs, stream, nodePriority, true);
        }
        return switch (stream.consume()) {
            case ConsumeResult.CORRECT C -> {
                Result<OperatorType> getOperatorType =
                        OperatorType.fromSymbol(C.consumedToken().value());
                if (!getOperatorType.isCorrect()) {
                    yield new ParseResult.COMPLETE<>(lhs, stream, nodePriority, true);
                }
                OperatorType operatorType = getOperatorType.result();
                if (operatorType.lBindingPower() <= rBindingPower) {
                    yield new ParseResult.COMPLETE<>(lhs, stream, nodePriority, true);
                }
                yield switch (parseExpression(C.finalTokenStream(), operatorType.rBindingPower())) {
                    case ParseResult.COMPLETE<ExpressionNode> RC ->
                            parseTail(
                                    nodeFactory.createOperatorNode(
                                            operatorType,
                                            List.of(lhs, RC.node()),
                                            lhs.line(),
                                            lhs.column()),
                                    RC.finalStream(),
                                    rBindingPower);
                    default -> new ParseResult.INVALID<>();
                };
            }
            case ConsumeResult.INCORRECT I ->
                    new ParseResult.COMPLETE<>(lhs, stream, nodePriority, true);
        };
    }
}
