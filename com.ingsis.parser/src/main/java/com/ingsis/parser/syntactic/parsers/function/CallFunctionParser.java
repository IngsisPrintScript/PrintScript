/*
 * My Project
 */

package com.ingsis.parser.syntactic.parsers.function;

import com.ingsis.parser.syntactic.parsers.Parser;
import com.ingsis.parser.syntactic.parsers.factories.ParserFactory;
import com.ingsis.parser.syntactic.parsers.identifier.IdentifierParser;
import com.ingsis.utils.nodes.nodes.expression.ExpressionNode;
import com.ingsis.utils.nodes.nodes.expression.function.CallFunctionNode;
import com.ingsis.utils.nodes.nodes.expression.identifier.IdentifierNode;
import com.ingsis.utils.result.CorrectResult;
import com.ingsis.utils.result.IncorrectResult;
import com.ingsis.utils.result.Result;
import com.ingsis.utils.token.tokens.Token;
import com.ingsis.utils.token.tokens.factories.TokenFactory;
import com.ingsis.utils.token.tokenstream.TokenStream;
import java.util.ArrayList;
import java.util.List;

public final class CallFunctionParser implements Parser<CallFunctionNode> {
    private final Token LEFT_PARENTHESIS_TEMPLATE;
    private final Token RIGHT_PARENTHESIS_TEMPLATE;
    private final Token COMMA_SEPARATOR_TEMPLATE;
    private final IdentifierParser IDENTIFIER_PARSER;
    private final ParserFactory PARSER_FACTORY;

    public CallFunctionParser(TokenFactory tokenFactory, ParserFactory parserFactory) {
        LEFT_PARENTHESIS_TEMPLATE = tokenFactory.createSeparatorToken("(");
        RIGHT_PARENTHESIS_TEMPLATE = tokenFactory.createSeparatorToken(")");
        COMMA_SEPARATOR_TEMPLATE = tokenFactory.createSeparatorToken(",");
        IDENTIFIER_PARSER = parserFactory.createIdentifierParser();
        this.PARSER_FACTORY = parserFactory;
    }

    @Override
    public Result<CallFunctionNode> parse(TokenStream stream) {
        Result<IdentifierNode> parseIdentifierNodeResult = IDENTIFIER_PARSER.parse(stream);
        if (!parseIdentifierNodeResult.isCorrect()) {
            return new IncorrectResult<>(parseIdentifierNodeResult);
        }
        IdentifierNode identifierNode = parseIdentifierNodeResult.result();

        Result<List<ExpressionNode>> parseFunctionCallResult = parseFunctionCall(stream);
        if (!parseFunctionCallResult.isCorrect()) {
            return new IncorrectResult<>(parseFunctionCallResult);
        }
        List<ExpressionNode> arguments = parseFunctionCallResult.result();

        return new CorrectResult<>(
                new CallFunctionNode(
                        identifierNode, arguments, identifierNode.line(), identifierNode.column()));
    }

    private Result<List<ExpressionNode>> parseFunctionCall(TokenStream stream) {
        Result<Token> leftParen = stream.consume(LEFT_PARENTHESIS_TEMPLATE);
        if (!leftParen.isCorrect()) return new IncorrectResult<>(leftParen);

        Result<List<ExpressionNode>> argumentsResult = parseArguments(stream);
        if (!argumentsResult.isCorrect()) return new IncorrectResult<>(argumentsResult);

        Result<Token> rightParen = stream.consume(RIGHT_PARENTHESIS_TEMPLATE);
        if (!rightParen.isCorrect()) return new IncorrectResult<>(rightParen);

        return argumentsResult;
    }

    private Result<List<ExpressionNode>> parseArguments(TokenStream stream) {
        List<ExpressionNode> arguments = new ArrayList<>();
        Parser<ExpressionNode> expressionParser = PARSER_FACTORY.createBinaryOperatorParser();

        boolean first = true;
        while (true) {
            if (!first) {
                Result<Token> commaResult = stream.consume(COMMA_SEPARATOR_TEMPLATE);
                if (!commaResult.isCorrect()) break;
            }

            TokenStream subTokenStream = stream.retrieveNonConsumedStream();
            Result<ExpressionNode> parseResult = expressionParser.parse(subTokenStream);
            if (!parseResult.isCorrect()) {
                if (first) return new CorrectResult<>(arguments);
                return new IncorrectResult<>(parseResult);
            }

            for (int i = 0; i < subTokenStream.pointer(); i++) {
                stream.next();
            }

            arguments.add(parseResult.result());
            first = false;
        }

        return new CorrectResult<>(arguments);
    }
}
