/*
 * My Project
 */

package com.ingsis.parser.syntactic.parsers.operator;

import com.ingsis.parser.syntactic.NodePriority;
import com.ingsis.parser.syntactic.parsers.Parser;
import com.ingsis.parser.syntactic.parsers.operator.algorithms.postfix.PostfixToAstBuilder;
import com.ingsis.parser.syntactic.parsers.operator.algorithms.shuntingyard.ShuntingYardTransformer;
import com.ingsis.utils.nodes.expressions.ExpressionNode;
import com.ingsis.utils.process.checkpoint.ProcessCheckpoint;
import com.ingsis.utils.process.result.ProcessResult;
import com.ingsis.utils.result.Result;
import com.ingsis.utils.token.Token;
import com.ingsis.utils.token.tokenstream.TokenStream;
import java.util.Queue;
import java.util.function.Supplier;

public final class OperatorParser implements Parser<ExpressionNode> {
    private final Supplier<Parser<ExpressionNode>> leafParserSupplier;
    private final ShuntingYardTransformer shuntingYardTransformer;
    private final PostfixToAstBuilder postfixToAstBuilder;

    public OperatorParser(
            Supplier<Parser<ExpressionNode>> leafParserSupplier,
            ShuntingYardTransformer shuntingYardTransformer,
            PostfixToAstBuilder postfixToAstBuilder) {
        this.leafParserSupplier = leafParserSupplier;
        this.shuntingYardTransformer = shuntingYardTransformer;
        this.postfixToAstBuilder = postfixToAstBuilder;
    }

    @Override
    public ProcessCheckpoint<Token, ProcessResult<ExpressionNode>> parse(TokenStream stream) {
        TokenStream originalStream = stream;
        stream = stream.consumeNoise();
        Result<Queue<Token>> getTokensQueueResult = shuntingYardTransformer.transform(stream);
        if (!getTokensQueueResult.isCorrect()) {
            return ProcessCheckpoint.UNINITIALIZED();
        }
        ProcessCheckpoint<Token, ExpressionNode> getExpressionResult =
                postfixToAstBuilder.build(
                        leafParserSupplier.get(), getTokensQueueResult.result(), originalStream);
        if (getExpressionResult.isUninitialized()) {
            return ProcessCheckpoint.UNINITIALIZED();
        }
        return ProcessCheckpoint.INITIALIZED(
                stream.consumeAll(),
                ProcessResult.COMPLETE(
                        getExpressionResult.result(), NodePriority.OPERATOR.priority()));
    }
}
