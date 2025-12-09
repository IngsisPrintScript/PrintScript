/*
 * My Project
 */

package com.ingsis.parser.syntactic.parsers;

import com.ingsis.parser.syntactic.NodePriority;
import com.ingsis.utils.iterator.safe.result.DefaultIterationResultFactory;
import com.ingsis.utils.iterator.safe.result.SafeIterationResult;
import com.ingsis.utils.nodes.expressions.ExpressionNode;
import com.ingsis.utils.nodes.expressions.atomic.identifier.IdentifierNode;
import com.ingsis.utils.nodes.factories.NodeFactory;
import com.ingsis.utils.process.checkpoint.ProcessCheckpoint;
import com.ingsis.utils.process.result.ProcessResult;
import com.ingsis.utils.result.factory.DefaultResultFactory;
import com.ingsis.utils.token.Token;
import com.ingsis.utils.token.factories.DefaultTokensFactory;
import com.ingsis.utils.token.template.TokenTemplate;
import com.ingsis.utils.token.tokenstream.DefaultTokenStream;
import com.ingsis.utils.token.tokenstream.TokenStream;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class CallFunctionParser implements Parser<ExpressionNode> {
    private final TokenTemplate lParen;
    private final TokenTemplate comma;
    private final TokenTemplate rParen;
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

        stream = ((TokenStream) identifierResult.iterator()).consumeNoise();

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

        stream = ((TokenStream) argumentsResult.iterator()).consumeNoise();

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
        stream = ((TokenStream) lParenCheck.nextIterator()).consumeNoise();

        List<ExpressionNode> arguments = new ArrayList<>();
        int priority = NodePriority.CALL_FUNCTION.priority();

        SafeIterationResult<Token> rParenCheck = stream.consume(rParen);
        if (rParenCheck.isCorrect()) {
            stream = ((TokenStream) rParenCheck.nextIterator()).consumeNoise();
            return ProcessCheckpoint.INITIALIZED(
                    stream, ProcessResult.COMPLETE(arguments, priority));
        }

        do {
            // ---------------------------
            // NEW: slice only one argument
            // ---------------------------
            TokenStream argumentSlice = sliceArgument(stream);
            if (argumentSlice == null) {
                return ProcessCheckpoint.INITIALIZED(stream, ProcessResult.PREFIX(priority));
            }

            // Parse using ONLY the sliced substream
            ProcessCheckpoint<Token, ProcessResult<ExpressionNode>> argumentResult =
                    expressionParserSupplier.get().parse(argumentSlice);

            if (argumentResult.isUninitialized()) {
                return ProcessCheckpoint.UNINITIALIZED();
            }
            if (!argumentResult.result().isComplete()) {
                return ProcessCheckpoint.INITIALIZED(stream, ProcessResult.PREFIX(priority));
            }

            // Add argument
            arguments.add(argumentResult.result().result());

            // ---------------------------
            // NEW: advance main stream by slice
            // ---------------------------
            stream = stream.advanceBy((TokenStream) argumentResult.iterator()).consumeNoise();

            // Try comma
            SafeIterationResult<Token> commaResult = stream.consume(comma);
            if (commaResult.isCorrect()) {
                stream = ((TokenStream) commaResult.nextIterator()).consumeNoise();
                continue; // next argument
            }

            // Try closing paren
            rParenCheck = stream.consume(rParen);
            if (!rParenCheck.isCorrect()) {
                return ProcessCheckpoint.INITIALIZED(stream, ProcessResult.PREFIX(priority));
            }

            stream = ((TokenStream) rParenCheck.nextIterator()).consumeNoise();
            break;

        } while (true);

        stream = stream.consumeNoise();

        return ProcessCheckpoint.INITIALIZED(stream, ProcessResult.COMPLETE(arguments, priority));
    }

    private TokenStream sliceArgument(TokenStream stream) {
        int depth = 0;
        int offset = 0;

        while (true) {
            var peekResult = stream.peek(offset);
            if (!peekResult.isCorrect()) {
                return null; // incomplete argument
            }

            Token token = peekResult.result();

            if (lParen.matches(token)) depth++;
            else if (rParen.matches(token)) {
                if (depth == 0) break;
                depth--;
            } else if (comma.matches(token) && depth == 0) {
                break;
            }

            offset++;
        }

        // Build a real slice using the SAME token buffer but NEW max length
        return new DefaultTokenStream(
                stream.tokens().subList(stream.pointer(), stream.pointer() + offset),
                List.of(),
                0,
                new DefaultTokensFactory(),
                new DefaultIterationResultFactory(),
                new DefaultResultFactory());
    }
}
