/*
 * My Project
 */

package com.ingsis.parser.syntactic.parsers;

import com.ingsis.parser.syntactic.NodePriority;
import com.ingsis.utils.iterator.safe.SafeIterator;
import com.ingsis.utils.iterator.safe.result.SafeIterationResult;
import com.ingsis.utils.nodes.Node;
import com.ingsis.utils.nodes.expressions.ExpressionNode;
import com.ingsis.utils.nodes.factories.NodeFactory;
import com.ingsis.utils.process.checkpoint.ProcessCheckpoint;
import com.ingsis.utils.process.result.ProcessResult;
import com.ingsis.utils.token.Token;
import com.ingsis.utils.token.template.TokenTemplate;
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
        // 'if' keyword
        SafeIterationResult<Token> ifResult = stream.consume(ifTemplate);
        if (!ifResult.isCorrect()) {
            return ProcessCheckpoint.UNINITIALIZED();
        }
        Token ifToken = ifResult.iterationResult();
        stream = consumeNoise((TokenStream) ifResult.nextIterator());

        // '('
        SafeIterationResult<Token> lParenResult = stream.consume(lParenTemplate);
        if (!lParenResult.isCorrect()) {
            return initialized(stream, prefixStatement());
        }
        stream = consumeNoise((TokenStream) lParenResult.nextIterator());

        // condition expression
        ProcessCheckpoint<Token, ProcessResult<ExpressionNode>> conditionCheckpoint =
                conditionParser.parse(stream);

        if (conditionCheckpoint.isUninitialized()) {
            return initialized(stream, prefixStatement());
        }
        if (!conditionCheckpoint.result().isComplete()) {
            return initialized(conditionCheckpoint.iterator(), prefixStatement());
        }

        ExpressionNode condition = conditionCheckpoint.result().result();
        stream = consumeNoise((TokenStream) conditionCheckpoint.iterator());

        // ')'
        SafeIterationResult<Token> rParenResult = stream.consume(rParenTemplate);
        if (!rParenResult.isCorrect()) {
            return initialized(stream, prefixStatement());
        }
        stream = consumeNoise((TokenStream) rParenResult.nextIterator());

        // then branch: { ... }
        ProcessCheckpoint<Token, ProcessResult<List<Node>>> thenBlockResult = parseBlock(stream);
        if (thenBlockResult.isUninitialized()) {
            return initialized(stream, prefixStatement());
        } else if (!thenBlockResult.result().isComplete()) {
            return ProcessCheckpoint.INITIALIZED(
                    stream, ProcessResult.PREFIX(NodePriority.STATEMENT.priority()));
        }
        List<Node> thenBody = thenBlockResult.result().result();
        stream = consumeNoise((TokenStream) thenBlockResult.iterator());

        // optional 'else'
        SafeIterationResult<Token> elseResult = stream.consume(elseTemplate);
        List<Node> elseBody = new ArrayList<>();
        if (elseResult.isCorrect()) {
            stream = consumeNoise((TokenStream) elseResult.nextIterator());

            ProcessCheckpoint<Token, ProcessResult<List<Node>>> elseBlockResult =
                    parseBlock(stream);
            if (elseBlockResult.isUninitialized()) {
                return initialized(stream, prefixStatement());
            } else if (!elseBlockResult.result().isComplete()) {
                return ProcessCheckpoint.INITIALIZED(
                        stream, ProcessResult.PREFIX(NodePriority.STATEMENT.priority()));
            }
            elseBody = elseBlockResult.result().result();
            stream = consumeNoise((TokenStream) elseBlockResult.iterator());
        }

        return ProcessCheckpoint.INITIALIZED(
                stream,
                ProcessResult.COMPLETE(
                        nodeFactory.createConditionalNode(
                                condition, thenBody, elseBody, ifToken.line(), ifToken.column()),
                        NodePriority.STATEMENT.priority()));
    }

    private ProcessCheckpoint<Token, ProcessResult<List<Node>>> parseBlock(TokenStream stream) {
        SafeIterationResult<Token> lBraceResult = stream.consume(lBraceTemplate);
        if (!lBraceResult.isCorrect()) {
            return ProcessCheckpoint.UNINITIALIZED();
        }
        stream = consumeNoise((TokenStream) lBraceResult.nextIterator());

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
            stream = consumeNoise((TokenStream) statementCheckpoint.iterator());
        }

        SafeIterationResult<Token> rBraceResult = stream.consume(rBraceTemplate);
        if (!rBraceResult.isCorrect()) {
            return ProcessCheckpoint.INITIALIZED(
                    stream, ProcessResult.PREFIX(NodePriority.STATEMENT.priority()));
        }

        stream = consumeNoise(stream);
        return ProcessCheckpoint.INITIALIZED(
                stream, ProcessResult.COMPLETE(body, NodePriority.STATEMENT.priority()));
    }

    private TokenStream consumeNoise(TokenStream stream) {
        return stream.consumeAll(spaceTemplate);
    }

    private ProcessCheckpoint<Token, ProcessResult<Node>> initialized(
            SafeIterator<Token> stream, ProcessResult<Node> result) {
        return ProcessCheckpoint.INITIALIZED(stream, result);
    }

    private ProcessResult<Node> prefixStatement() {
        return ProcessResult.PREFIX(NodePriority.STATEMENT.priority());
    }
}
