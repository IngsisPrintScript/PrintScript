/*
 * My Project
 */

package com.ingsis.parser.syntactic.parsers;

import com.ingsis.parser.syntactic.NodePriority;
import com.ingsis.utils.iterator.safe.SafeIterator;
import com.ingsis.utils.iterator.safe.result.DefaultIterationResultFactory;
import com.ingsis.utils.iterator.safe.result.SafeIterationResult;
import com.ingsis.utils.nodes.Node;
import com.ingsis.utils.nodes.expressions.ExpressionNode;
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

public class ConditionalParser implements Parser<Node> {

    private final TokenTemplate ifTemplate;
    private final TokenTemplate elseTemplate;
    private final TokenTemplate lParenTemplate;
    private final TokenTemplate rParenTemplate;
    private final TokenTemplate lBraceTemplate;
    private final TokenTemplate rBraceTemplate;
    private final TokenTemplate spaceTemplate;

    private final Parser<ExpressionNode> conditionParser;
    private final Supplier<Parser<Node>> statementParserSupplier;
    private final NodeFactory nodeFactory;

    public ConditionalParser(
            TokenTemplate ifTemplate,
            TokenTemplate elseTemplate,
            TokenTemplate lParenTemplate,
            TokenTemplate rParenTemplate,
            TokenTemplate lBraceTemplate,
            TokenTemplate rBraceTemplate,
            TokenTemplate spaceTemplate,
            Parser<ExpressionNode> conditionParser,
            Supplier<Parser<Node>> statementParserSupplier,
            NodeFactory nodeFactory) {

        this.ifTemplate = ifTemplate;
        this.elseTemplate = elseTemplate;
        this.lParenTemplate = lParenTemplate;
        this.rParenTemplate = rParenTemplate;
        this.lBraceTemplate = lBraceTemplate;
        this.rBraceTemplate = rBraceTemplate;
        this.spaceTemplate = spaceTemplate;
        this.conditionParser = conditionParser;
        this.statementParserSupplier = statementParserSupplier;
        this.nodeFactory = nodeFactory;
    }

    @Override
    public ProcessCheckpoint<Token, ProcessResult<Node>> parse(TokenStream stream) {
        TokenStream originalStream = stream;
        SafeIterationResult<Token> ifResult = stream.consume(ifTemplate);
        if (!ifResult.isCorrect()) {
            return ProcessCheckpoint.UNINITIALIZED();
        }
        Token ifToken = ifResult.iterationResult();
        stream = ((TokenStream) ifResult.nextIterator()).consumeNoise();

        SafeIterationResult<Token> lParenResult = stream.consume(lParenTemplate);
        if (!lParenResult.isCorrect()) {
            return initialized(stream, prefixStatement());
        }
        TokenStream slicedCondition = sliceParenthesizedInner(stream);
        if (slicedCondition == null) {
            return initialized(stream, prefixStatement());
        }

        stream = ((TokenStream) lParenResult.nextIterator()).consumeNoise();

        ProcessCheckpoint<Token, ProcessResult<ExpressionNode>> conditionCheckpoint =
                conditionParser.parse(slicedCondition);

        if (conditionCheckpoint.isUninitialized()) {
            return initialized(stream, prefixStatement());
        }
        if (!conditionCheckpoint.result().isComplete()) {
            return initialized(conditionCheckpoint.iterator(), prefixStatement());
        }

        ExpressionNode condition = conditionCheckpoint.result().result();
        stream = stream.advanceBy(slicedCondition.consumeAll());

        SafeIterationResult<Token> rParenResult = stream.consume(rParenTemplate);
        if (!rParenResult.isCorrect()) {
            return initialized(stream, prefixStatement());
        }
        stream = ((TokenStream) rParenResult.nextIterator()).consumeNoise();

        ProcessCheckpoint<Token, ProcessResult<List<Node>>> thenBlockResult = parseBlock(stream);
        if (thenBlockResult.isUninitialized()) {
            return initialized(stream, prefixStatement());
        } else if (!thenBlockResult.result().isComplete()) {
            return ProcessCheckpoint.INITIALIZED(
                    stream, ProcessResult.PREFIX(NodePriority.STATEMENT.priority()));
        }
        List<Node> thenBody = thenBlockResult.result().result();
        stream = ((TokenStream) thenBlockResult.iterator()).consumeNoise();

        SafeIterationResult<Token> elseResult = stream.consume(elseTemplate);
        List<Node> elseBody = new ArrayList<>();
        if (elseResult.isCorrect()) {
            stream = ((TokenStream) elseResult.nextIterator()).consumeNoise();

            ProcessCheckpoint<Token, ProcessResult<List<Node>>> elseBlockResult =
                    parseBlock(stream);
            if (elseBlockResult.isUninitialized()) {
                return initialized(stream, prefixStatement());
            } else if (!elseBlockResult.result().isComplete()) {
                return ProcessCheckpoint.INITIALIZED(
                        stream, ProcessResult.PREFIX(NodePriority.STATEMENT.priority()));
            }
            elseBody = elseBlockResult.result().result();
            stream = ((TokenStream) elseBlockResult.iterator()).consumeNoise();
        }

        return ProcessCheckpoint.INITIALIZED(
                stream,
                ProcessResult.COMPLETE(
                        nodeFactory.createConditionalNode(
                                condition,
                                thenBody,
                                elseBody,
                                originalStream,
                                ifToken.line(),
                                ifToken.column()),
                        NodePriority.STATEMENT.priority()));
    }

    private TokenStream sliceParenthesizedInner(TokenStream stream) {
        TokenStream cleanStart = stream.consumeNoise();

        var first = cleanStart.peek(0);
        if (!first.isCorrect() || !first.result().value().equals("(")) return null;

        int depth = 0;
        int offset = 0;

        while (true) {
            var peek = cleanStart.peek(offset);
            if (!peek.isCorrect()) return null;

            Token t = peek.result();

            if (t.value().equals("(")) {
                depth++;
            } else if (t.value().equals(")")) {
                depth--;
                if (depth == 0) break;
            }

            offset++;
        }

        int start = cleanStart.pointer() + 1;
        int end = cleanStart.pointer() + offset;

        if (end < start) end = start;

        List<Token> slice = cleanStart.tokens().subList(start, end);

        return new DefaultTokenStream(
                slice,
                List.of(),
                0,
                new DefaultTokensFactory(),
                new DefaultIterationResultFactory(),
                new DefaultResultFactory());
    }


    private ProcessCheckpoint<Token, ProcessResult<List<Node>>> parseBlock(TokenStream stream) {
        SafeIterationResult<Token> lBraceResult = stream.consume(lBraceTemplate);
        if (!lBraceResult.isCorrect()) {
            return ProcessCheckpoint.UNINITIALIZED();
        }
        stream = ((TokenStream) lBraceResult.nextIterator()).consumeNoise();
        stream = stream.consumeNoise();

        List<Node> body = new ArrayList<>();
        while (!stream.consume(rBraceTemplate).isCorrect()) {
            ProcessCheckpoint<Token, ProcessResult<Node>> statementCheckpoint =
                    statementParserSupplier.get().parse(stream);
            if (statementCheckpoint.isUninitialized()) {
                return ProcessCheckpoint.INITIALIZED(
                        stream, ProcessResult.PREFIX(NodePriority.STATEMENT.priority()));
            } else if (!statementCheckpoint.result().isComplete()) {
                return ProcessCheckpoint.INITIALIZED(
                        stream, ProcessResult.PREFIX(NodePriority.STATEMENT.priority()));
            }
            body.add(statementCheckpoint.result().result());
            stream = ((TokenStream) statementCheckpoint.iterator()).consumeNoise();
        }

        SafeIterationResult<Token> rBraceResult = stream.consume(rBraceTemplate);
        if (!rBraceResult.isCorrect()) {
            return ProcessCheckpoint.INITIALIZED(
                    stream, ProcessResult.PREFIX(NodePriority.STATEMENT.priority()));
        }

        stream = ((TokenStream) rBraceResult.nextIterator()).consumeNoise();
        return ProcessCheckpoint.INITIALIZED(
                stream, ProcessResult.COMPLETE(body, NodePriority.STATEMENT.priority()));
    }

    private ProcessCheckpoint<Token, ProcessResult<Node>> initialized(
            SafeIterator<Token> stream, ProcessResult<Node> result) {
        return ProcessCheckpoint.INITIALIZED(stream, result);
    }

    private ProcessResult<Node> prefixStatement() {
        return ProcessResult.PREFIX(NodePriority.STATEMENT.priority());
    }
}
