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
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class CallFunctionParser implements Parser<ExpressionNode> {
    private final TokenTemplate lParen;
    private final TokenTemplate comma;
    private final TokenTemplate rParen;
    private final TokenTemplate space;
    private final Parser<ExpressionNode> identifierParser;
    private final Supplier<Parser<ExpressionNode>> expressionParserSupplier;
    private final NodeFactory nodeFactory;

    public CallFunctionParser(
            TokenTemplate lParen,
            TokenTemplate comma,
            TokenTemplate rParen,
            TokenTemplate space,
            Parser<ExpressionNode> identifierParser,
            Supplier<Parser<ExpressionNode>> expressionParserSupplier,
            NodeFactory nodeFactory) {
        this.lParen = lParen;
        this.comma = comma;
        this.rParen = rParen;
        this.space = space;
        this.identifierParser = identifierParser;
        this.expressionParserSupplier = expressionParserSupplier;
        this.nodeFactory = nodeFactory;
    }

    @Override
    public ProcessCheckpoint<Token, ProcessResult<ExpressionNode>> parse(TokenStream stream) {
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

        stream = consumeNoise((TokenStream) identifierResult.iterator());

        ProcessCheckpoint<Token, ProcessResult<List<ExpressionNode>>> argumentsResult =
                parseArguments(stream);

        if (argumentsResult.isUninitialized()) {
            return ProcessCheckpoint.INITIALIZED(
                    stream, ProcessResult.PREFIX(NodePriority.CALL_FUNCTION.priority()));
        } else if (!argumentsResult.result().isComplete()) {
            return ProcessCheckpoint.INITIALIZED(
                    argumentsResult.iterator(),
                    ProcessResult.PREFIX(NodePriority.CALL_FUNCTION.priority()));
        }

        stream = consumeNoise((TokenStream) argumentsResult.iterator());

        return ProcessCheckpoint.INITIALIZED(
                stream,
                ProcessResult.COMPLETE(
                        nodeFactory.createCallFunctionNode(
                                identifierNode,
                                argumentsResult.result().result(),
                                identifierNode.line(),
                                identifierNode.column()),
                        NodePriority.CALL_FUNCTION.priority()));
    }

    private ProcessCheckpoint<Token, ProcessResult<List<ExpressionNode>>> parseArguments(
            TokenStream stream) {
        SafeIterationResult<Token> lParenCheck = stream.consume(lParen);
        if (!lParenCheck.isCorrect()) {
            return ProcessCheckpoint.UNINITIALIZED();
        }
        stream = consumeNoise((TokenStream) lParenCheck.nextIterator());

        List<ExpressionNode> arguments = new ArrayList<>();
        int priority = NodePriority.CALL_FUNCTION.priority();

        SafeIterationResult<Token> rParenCheck = stream.consume(rParen);
        if (rParenCheck.isCorrect()) {
            stream = consumeNoise((TokenStream) rParenCheck.nextIterator());
            return ProcessCheckpoint.INITIALIZED(
                    stream, ProcessResult.COMPLETE(arguments, priority));
        }

        do {
            ProcessCheckpoint<Token, ProcessResult<ExpressionNode>> argumentResult =
                    expressionParserSupplier.get().parse(stream);

            if (argumentResult.isUninitialized()) {
                return ProcessCheckpoint.UNINITIALIZED();
            }
            if (!argumentResult.result().isComplete()) {
                return ProcessCheckpoint.INITIALIZED(stream, ProcessResult.PREFIX(priority));
            }

            arguments.add(argumentResult.result().result());
            stream = consumeNoise((TokenStream) argumentResult.iterator());

            stream = consumeNoise(stream);

            SafeIterationResult<Token> commaResult = stream.consume(comma);
            if (commaResult.isCorrect()) {
                stream = consumeNoise((TokenStream) commaResult.nextIterator());
            } else {
                rParenCheck = stream.consume(rParen);
                if (!rParenCheck.isCorrect()) {
                    return ProcessCheckpoint.INITIALIZED(stream, ProcessResult.PREFIX(priority));
                }
                stream = consumeNoise((TokenStream) rParenCheck.nextIterator());
                break;
            }
        } while (true);

        stream = consumeNoise(stream);

        return ProcessCheckpoint.INITIALIZED(stream, ProcessResult.COMPLETE(arguments, priority));
    }

    private TokenStream consumeNoise(TokenStream stream) {
        return stream.consumeAll(space);
    }
}
