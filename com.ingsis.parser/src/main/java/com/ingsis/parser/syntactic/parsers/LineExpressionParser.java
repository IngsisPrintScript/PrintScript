/*
 * My Project
 */

package com.ingsis.parser.syntactic.parsers;

import com.ingsis.parser.syntactic.NodePriority;
import com.ingsis.utils.iterator.safe.result.SafeIterationResult;
import com.ingsis.utils.nodes.Node;
import com.ingsis.utils.nodes.expressions.ExpressionNode;
import com.ingsis.utils.process.checkpoint.ProcessCheckpoint;
import com.ingsis.utils.process.result.ProcessResult;
import com.ingsis.utils.token.Token;
import com.ingsis.utils.token.template.TokenTemplate;
import com.ingsis.utils.token.tokenstream.TokenStream;

public class LineExpressionParser implements Parser<Node> {
    private final TokenTemplate semicolonTemplate;
    private final Parser<ExpressionNode> expressionParser;

    public LineExpressionParser(
            TokenTemplate semicolonTemplate, Parser<ExpressionNode> expressionParser) {
        this.semicolonTemplate = semicolonTemplate;
        this.expressionParser = expressionParser;
    }

    @Override
    public ProcessCheckpoint<Token, ProcessResult<Node>> parse(TokenStream stream) {
        ProcessCheckpoint<Token, ProcessResult<ExpressionNode>> processExpressionResult =
                expressionParser.parse(stream);
        if (processExpressionResult.isUninitialized()) {
            return ProcessCheckpoint.UNINITIALIZED();
        } else if (!processExpressionResult.result().isComplete()) {
            return ProcessCheckpoint.INITIALIZED(
                    processExpressionResult.iterator(),
                    ProcessResult.COMPLETE(
                            (Node) (processExpressionResult.result().result()),
                            NodePriority.EXPRESSION.priority()));
        }
        ExpressionNode expressionNode = processExpressionResult.result().result();

        stream = (TokenStream) processExpressionResult.iterator();

        SafeIterationResult<Token> consumeSemicolonResult = stream.consume(semicolonTemplate);
        if (!consumeSemicolonResult.isCorrect()) {
            return ProcessCheckpoint.INITIALIZED(
                    stream, ProcessResult.PREFIX(NodePriority.EXPRESSION.priority()));
        }

        stream = (TokenStream) consumeSemicolonResult.nextIterator();

        return ProcessCheckpoint.INITIALIZED(
                stream, ProcessResult.COMPLETE(expressionNode, NodePriority.EXPRESSION.priority()));
    }
}
