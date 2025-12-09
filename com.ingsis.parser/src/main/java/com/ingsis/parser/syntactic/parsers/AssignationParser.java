/*
 * My Project
 */

package com.ingsis.parser.syntactic.parsers;

import com.ingsis.parser.syntactic.NodePriority;
import com.ingsis.utils.iterator.safe.result.SafeIterationResult;
import com.ingsis.utils.nodes.expressions.ExpressionNode;
import com.ingsis.utils.nodes.expressions.atomic.identifier.IdentifierNode;
import com.ingsis.utils.nodes.factories.NodeFactory;
import com.ingsis.utils.process.checkpoint.ProcessCheckpoint;
import com.ingsis.utils.process.result.ProcessResult;
import com.ingsis.utils.token.Token;
import com.ingsis.utils.token.template.TokenTemplate;
import com.ingsis.utils.token.tokenstream.TokenStream;

public class AssignationParser implements Parser<ExpressionNode> {
    private final TokenTemplate equals;
    private final Parser<ExpressionNode> identifierParser;
    private final Parser<ExpressionNode> expressionParser;
    private final NodeFactory nodeFactory;

    public AssignationParser(
            TokenTemplate equals,
            Parser<ExpressionNode> identifierParser,
            Parser<ExpressionNode> expressionParser,
            NodeFactory nodeFactory) {
        this.equals = equals;
        this.identifierParser = identifierParser;
        this.expressionParser = expressionParser;
        this.nodeFactory = nodeFactory;
    }

    @Override
    public ProcessCheckpoint<Token, ProcessResult<ExpressionNode>> parse(TokenStream stream) {
        TokenStream originalStream = stream;
        ProcessCheckpoint<Token, ProcessResult<ExpressionNode>> identifierResult =
                identifierParser.parse(stream);
        if (identifierResult.isUninitialized()) {
            return ProcessCheckpoint.UNINITIALIZED();
        } else if (!identifierResult.result().isComplete()) {
            return ProcessCheckpoint.INITIALIZED(
                    stream, ProcessResult.PREFIX(NodePriority.CALL_FUNCTION.priority()));
        }

        if (!(identifierResult.result().result() instanceof IdentifierNode identifierNode)) {
            return ProcessCheckpoint.UNINITIALIZED();
        }

        stream = ((TokenStream) identifierResult.iterator()).consumeNoise();

        SafeIterationResult<Token> consumeSpace = stream.consume(equals);
        if (!consumeSpace.isCorrect()) {
            return ProcessCheckpoint.INITIALIZED(
                    stream, ProcessResult.PREFIX(NodePriority.EXPRESSION.priority()));
        }

        stream = ((TokenStream) consumeSpace.nextIterator()).consumeNoise();

        ProcessCheckpoint<Token, ProcessResult<ExpressionNode>> parseExpression =
                expressionParser.parse(stream);
        if (parseExpression.isUninitialized()) {
            return ProcessCheckpoint.INITIALIZED(
                    stream, ProcessResult.PREFIX(NodePriority.EXPRESSION.priority()));
        } else if (!identifierResult.result().isComplete()) {
            return ProcessCheckpoint.INITIALIZED(
                    stream, ProcessResult.PREFIX(NodePriority.IDENTIFIER.priority()));
        }

        stream = ((TokenStream) parseExpression.iterator()).consumeNoise();

        return ProcessCheckpoint.INITIALIZED(
                stream,
                ProcessResult.COMPLETE(
                        nodeFactory.createAssignationNode(
                                identifierNode,
                                parseExpression.result().result(),
                                originalStream,
                                identifierNode.line(),
                                identifierNode.column()),
                        NodePriority.EXPRESSION.priority()));
    }
}
