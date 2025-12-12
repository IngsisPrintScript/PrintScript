/*
 * My Project
 */

package com.ingsis.parser.syntactic.parsers.operator.algorithms.postfix;

import com.ingsis.parser.syntactic.parsers.Parser;
import com.ingsis.utils.iterator.safe.result.IterationResultFactory;
import com.ingsis.utils.iterator.safe.result.SafeIterationResult;
import com.ingsis.utils.nodes.expressions.ExpressionNode;
import com.ingsis.utils.nodes.expressions.operator.OperatorNode;
import com.ingsis.utils.nodes.expressions.operator.OperatorType;
import com.ingsis.utils.nodes.factories.NodeFactory;
import com.ingsis.utils.process.checkpoint.ProcessCheckpoint;
import com.ingsis.utils.process.result.ProcessResult;
import com.ingsis.utils.result.CorrectResult;
import com.ingsis.utils.result.Result;
import com.ingsis.utils.result.factory.ResultFactory;
import com.ingsis.utils.token.Token;
import com.ingsis.utils.token.factories.TokenFactory;
import com.ingsis.utils.token.tokenstream.DefaultTokenStream;
import com.ingsis.utils.token.tokenstream.TokenStream;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.Queue;

public class PostfixExpressionTreeBuilder implements PostfixToAstBuilder {
    private final NodeFactory nodeFactory;
    private final TokenFactory tokenFactory;
    private final IterationResultFactory iterationResultFactory;
    private final ResultFactory resultFactory;

    public PostfixExpressionTreeBuilder(
            NodeFactory nodeFactory,
            TokenFactory tokenFactory,
            IterationResultFactory iterationResultFactory,
            ResultFactory resultFactory) {
        this.nodeFactory = nodeFactory;
        this.tokenFactory = tokenFactory;
        this.iterationResultFactory = iterationResultFactory;
        this.resultFactory = resultFactory;
    }

    @Override
    public ProcessCheckpoint<Token, ExpressionNode> build(
            Parser<ExpressionNode> leafExpressionNodesParser,
            Queue<Token> postfixTokens,
            TokenStream originalStream) {
        return parsePostFix(leafExpressionNodesParser, postfixTokens, originalStream);
    }

    private ProcessCheckpoint<Token, ExpressionNode> parsePostFix(
            Parser<ExpressionNode> leafExpressionNodesParser,
            Queue<Token> postfixTokens,
            TokenStream originalStream) {
        Deque<ExpressionNode> expressionStack = new ArrayDeque<>();
        TokenStream tokenStream =
                new DefaultTokenStream(
                        List.copyOf(postfixTokens),
                        tokenFactory,
                        iterationResultFactory,
                        resultFactory);
        SafeIterationResult<Token> iterationResult = tokenStream.next();
        while (iterationResult.isCorrect()) {
            Token token = iterationResult.iterationResult();
            Result<OperatorType> opResult = OperatorType.fromSymbol(token.value());
            if (opResult.isCorrect()) {
                Result<Deque<ExpressionNode>> newStackResult =
                        generateOperatorNode(opResult.result(), expressionStack, originalStream);
                if (!newStackResult.isCorrect()) {
                    return ProcessCheckpoint.UNINITIALIZED();
                }
                expressionStack = newStackResult.result();
                tokenStream = (TokenStream) tokenStream.next().nextIterator();
            } else {
                ProcessCheckpoint<Token, ProcessResult<ExpressionNode>> leafResult =
                        leafExpressionNodesParser.parse(tokenStream);
                if (leafResult.isUninitialized()) {
                    return ProcessCheckpoint.UNINITIALIZED();
                }

                if (leafResult.result().isComplete()) {
                    expressionStack.push(leafResult.result().result());
                    tokenStream = (TokenStream) leafResult.iterator();
                } else {
                    break;
                }
            }
            iterationResult = tokenStream.next();
        }

        if (expressionStack.size() != 1) {
            return ProcessCheckpoint.UNINITIALIZED();
        }

        return ProcessCheckpoint.INITIALIZED(tokenStream, expressionStack.pop());
    }

    private Result<Deque<ExpressionNode>> generateOperatorNode(
            OperatorType operatorType,
            Deque<ExpressionNode> expressionNodes,
            TokenStream originalStream) {
        Deque<ExpressionNode> newStack = new ArrayDeque<>(expressionNodes);
        List<ExpressionNode> children = new ArrayList<>();
        for (int i = 0; i < operatorType.arity(); i++) {
            children.add(0, newStack.pop());
        }
        OperatorNode operatorNode =
                nodeFactory.createOperatorNode(
                        operatorType,
                        children,
                        originalStream,
                        children.get(0).line(),
                        children.get(0).column());
        newStack.push(operatorNode);
        return new CorrectResult<>(newStack);
    }
}
