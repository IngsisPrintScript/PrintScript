/*
 * My Project
 */

package com.ingsis.parser.syntactic.parsers;

import com.ingsis.parser.syntactic.NodePriority;
import com.ingsis.utils.iterator.safe.result.SafeIterationResult;
import com.ingsis.utils.nodes.expressions.ExpressionNode;
import com.ingsis.utils.nodes.factories.NodeFactory;
import com.ingsis.utils.process.checkpoint.ProcessCheckpoint;
import com.ingsis.utils.process.result.ProcessResult;
import com.ingsis.utils.token.Token;
import com.ingsis.utils.token.template.TokenTemplate;
import com.ingsis.utils.token.template.factories.TokenTemplateFactory;
import com.ingsis.utils.token.tokenstream.TokenStream;

public class IdentifierParser implements Parser<ExpressionNode> {
    private final TokenTemplate identifierTemplate;
    private final NodeFactory nodeFactory;

    public IdentifierParser(TokenTemplateFactory tokenTemplateFactory, NodeFactory nodeFactory) {
        this.identifierTemplate = tokenTemplateFactory.identifier();
        this.nodeFactory = nodeFactory;
    }

    @Override
    public ProcessCheckpoint<Token, ProcessResult<ExpressionNode>> parse(TokenStream stream) {
        SafeIterationResult<Token> consumeIdentifierResult = stream.consume(identifierTemplate);
        if (!consumeIdentifierResult.isCorrect()) {
            return ProcessCheckpoint.UNINITIALIZED();
        }
        stream = ((TokenStream) consumeIdentifierResult.nextIterator()).consumeNoise();
        return ProcessCheckpoint.INITIALIZED(
                stream,
                ProcessResult.COMPLETE(
                        nodeFactory.createIdentifierNode(
                                consumeIdentifierResult.iterationResult().value(),
                                consumeIdentifierResult.iterationResult().line(),
                                consumeIdentifierResult.iterationResult().column()),
                        NodePriority.IDENTIFIER.priority()));
    }
}
