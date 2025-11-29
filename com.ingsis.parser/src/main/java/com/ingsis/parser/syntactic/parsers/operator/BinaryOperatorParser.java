/*
 * My Project
 */

package com.ingsis.parser.syntactic.parsers.operator;

import com.ingsis.parser.syntactic.parsers.Parser;
import com.ingsis.utils.nodes.nodes.Node;
import com.ingsis.utils.nodes.nodes.expression.ExpressionNode;
import com.ingsis.utils.nodes.nodes.factories.NodeFactory;
import com.ingsis.utils.result.CorrectResult;
import com.ingsis.utils.result.IncorrectResult;
import com.ingsis.utils.result.Result;
import com.ingsis.utils.token.tokens.Token;
import com.ingsis.utils.token.tokens.factories.TokenFactory;
import com.ingsis.utils.token.tokenstream.TokenStream;

public final class BinaryOperatorParser implements Parser<ExpressionNode> {
    private final Token OPERATOR_TEMPLATE;
    private final Parser<ExpressionNode> LEAF_NODES_PARSER;
    private final NodeFactory NODE_FACTORY;

    public BinaryOperatorParser(
            NodeFactory NODE_FACTORY,
            TokenFactory TOKEN_FACTORY,
            Parser<ExpressionNode> LEAF_NODES_PARSER) {
        this.OPERATOR_TEMPLATE = TOKEN_FACTORY.createOperatorToken("");
        this.LEAF_NODES_PARSER = LEAF_NODES_PARSER;
        this.NODE_FACTORY = NODE_FACTORY;
    }

    @Override
    public Result<ExpressionNode> parse(TokenStream stream) {
        Result<? extends Node> consumeLeftChildResult = LEAF_NODES_PARSER.parse(stream);
        if (!consumeLeftChildResult.isCorrect()) {
            return new IncorrectResult<>(consumeLeftChildResult);
        }
        ExpressionNode leftChild;
        try {
            leftChild = (ExpressionNode) consumeLeftChildResult.result();
        } catch (Exception e) {
            return new IncorrectResult<>(e.getMessage());
        }

        if (!stream.match(OPERATOR_TEMPLATE)) {
            return new CorrectResult<>(leftChild);
        }

        Token operatorToken = stream.next();
        String symbol = operatorToken.value();

        // parse right-hand side recursively
        Result<? extends ExpressionNode> parseRightResult = this.parse(stream);
        if (!parseRightResult.isCorrect()) {
            return new IncorrectResult<>(parseRightResult);
        }

        ExpressionNode rightChild = parseRightResult.result();

        return new CorrectResult<>(
                NODE_FACTORY.createBinaryOperatorNode(
                        symbol,
                        leftChild,
                        rightChild,
                        operatorToken.line(),
                        operatorToken.column()));
    }
}
