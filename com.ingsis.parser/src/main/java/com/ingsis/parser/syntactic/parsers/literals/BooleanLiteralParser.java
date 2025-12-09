/*
 * My Project
 */

package com.ingsis.parser.syntactic.parsers.literals;

import com.ingsis.parser.syntactic.NodePriority;
import com.ingsis.parser.syntactic.parsers.Parser;
import com.ingsis.utils.iterator.safe.result.SafeIterationResult;
import com.ingsis.utils.nodes.expressions.ExpressionNode;
import com.ingsis.utils.nodes.factories.NodeFactory;
import com.ingsis.utils.process.checkpoint.ProcessCheckpoint;
import com.ingsis.utils.process.result.ProcessResult;
import com.ingsis.utils.token.Token;
import com.ingsis.utils.token.template.TokenTemplate;
import com.ingsis.utils.token.template.factories.TokenTemplateFactory;
import com.ingsis.utils.token.tokenstream.TokenStream;

public class BooleanLiteralParser implements Parser<ExpressionNode> {
    private final TokenTemplate booleanLiteralTemplate;
    private final NodeFactory nodeFactory;

    public BooleanLiteralParser(
            TokenTemplateFactory tokenTemplateFactory, NodeFactory nodeFactory) {
        this.booleanLiteralTemplate = tokenTemplateFactory.booleanLiteral();
        this.nodeFactory = nodeFactory;
    }

    @Override
    public ProcessCheckpoint<Token, ProcessResult<ExpressionNode>> parse(TokenStream stream) {
        TokenStream originalStream= stream;
        SafeIterationResult<Token> consumeLiteralResult = stream.consume(booleanLiteralTemplate);
        if (!consumeLiteralResult.isCorrect()) {
            return ProcessCheckpoint.UNINITIALIZED();
        }
        stream = ((TokenStream) consumeLiteralResult.nextIterator()).consumeNoise();
        return ProcessCheckpoint.INITIALIZED(
                stream,
                ProcessResult.COMPLETE(
                        nodeFactory.createBooleanLiteralNode(
                                Boolean.parseBoolean(
                                        consumeLiteralResult.iterationResult().value()),
                                originalStream,
                                consumeLiteralResult.iterationResult().line(),
                                consumeLiteralResult.iterationResult().column()),
                        NodePriority.IDENTIFIER.priority()));
    }
}
