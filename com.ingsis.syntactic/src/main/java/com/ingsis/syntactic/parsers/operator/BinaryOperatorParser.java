/*
 * My Project
 */

package com.ingsis.syntactic.parsers.operator;

import com.ingsis.nodes.Node;
import com.ingsis.nodes.expression.operator.OperatorNode;
import com.ingsis.nodes.factories.NodeFactory;
import com.ingsis.result.CorrectResult;
import com.ingsis.result.IncorrectResult;
import com.ingsis.result.Result;
import com.ingsis.syntactic.parsers.Parser;
import com.ingsis.tokens.Token;
import com.ingsis.tokens.factories.TokenFactory;
import com.ingsis.tokenstream.TokenStream;
import com.ingsis.visitors.Checkable;

public final class BinaryOperatorParser implements Parser {
    private final Token OPERATOR_TEMPLATE;
    private final Parser LEAF_NODES_PARSER;
    private final NodeFactory NODE_FACTORY;

    public BinaryOperatorParser(
            NodeFactory NODE_FACTORY, TokenFactory TOKEN_FACTORY, Parser LEAF_NODES_PARSER) {
        this.OPERATOR_TEMPLATE = TOKEN_FACTORY.createOperatorToken("");
        this.LEAF_NODES_PARSER = LEAF_NODES_PARSER;
        this.NODE_FACTORY = NODE_FACTORY;
    }

    @Override
    public Result<OperatorNode> parse(TokenStream stream) {
        Result<? extends Node> consumeLeftChildResult = LEAF_NODES_PARSER.parse(stream);
        if (!consumeLeftChildResult.isCorrect()) {
            return new IncorrectResult<>(consumeLeftChildResult);
        }
        OperatorNode leftChild = (OperatorNode) consumeLeftChildResult.result();

        if (!stream.match(OPERATOR_TEMPLATE)) {
            return new CorrectResult<>(leftChild);
        }
        Token operatorToken = stream.next();
        String symbol = operatorToken.value();

        Result<? extends Checkable> parseRightResult = this.parse(stream);
        if (!parseRightResult.isCorrect()) {
            return new IncorrectResult<>(parseRightResult);
        }
        OperatorNode rightChild = (OperatorNode) parseRightResult.result();

        return new CorrectResult<>(
                NODE_FACTORY.createBinaryOperatorNode(symbol, leftChild, rightChild));
    }
}
