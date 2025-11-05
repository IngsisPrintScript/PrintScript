/*
 * My Project
 */

package com.ingsis.syntactic.parsers.expression;

import com.ingsis.nodes.expression.ExpressionNode;
import com.ingsis.result.CorrectResult;
import com.ingsis.result.IncorrectResult;
import com.ingsis.result.Result;
import com.ingsis.syntactic.parsers.Parser;
import com.ingsis.syntactic.parsers.factories.ParserFactory;
import com.ingsis.syntactic.parsers.operator.BinaryOperatorParser;
import com.ingsis.tokens.Token;
import com.ingsis.tokens.factories.TokenFactory;
import com.ingsis.tokenstream.TokenStream;

public final class LineExpressionParser implements Parser<ExpressionNode> {
    private final BinaryOperatorParser CORE_EXPRESSION_PARSER;
    private final Token EOL_TEMPLATE;

    public LineExpressionParser(TokenFactory tokenFactory, ParserFactory parserFactory) {
        this.CORE_EXPRESSION_PARSER = parserFactory.createBinaryOperatorParser();
        this.EOL_TEMPLATE = tokenFactory.createEndOfLineToken(";");
    }

    @Override
    public Result<ExpressionNode> parse(TokenStream stream) {
        Result<ExpressionNode> parseExpressionResult = CORE_EXPRESSION_PARSER.parse(stream);
        if (!parseExpressionResult.isCorrect()) {
            return new IncorrectResult<>(parseExpressionResult);
        }
        Result<Token> consumeEolResult = stream.consume(EOL_TEMPLATE);
        if (!consumeEolResult.isCorrect()) {
            return new IncorrectResult<>(consumeEolResult);
        }
        return new CorrectResult<ExpressionNode>(parseExpressionResult.result());
    }
}
