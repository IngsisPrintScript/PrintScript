/*
 * My Project
 */

package com.ingsis.syntactic.parsers.function;

import com.ingsis.nodes.expression.ExpressionNode;
import com.ingsis.nodes.expression.function.CallFunctionNode;
import com.ingsis.nodes.expression.identifier.IdentifierNode;
import com.ingsis.result.CorrectResult;
import com.ingsis.result.IncorrectResult;
import com.ingsis.result.Result;
import com.ingsis.syntactic.parsers.Parser;
import com.ingsis.syntactic.parsers.factories.ParserFactory;
import com.ingsis.syntactic.parsers.identifier.IdentifierParser;
import com.ingsis.syntactic.parsers.operator.BinaryOperatorParser;
import com.ingsis.tokens.Token;
import com.ingsis.tokens.factories.TokenFactory;
import com.ingsis.tokenstream.TokenStream;
import java.util.ArrayList;
import java.util.List;

public final class CallFunctionParser implements Parser {
  private final Token LEFT_PARENTHESIS_TEMPLATE;
  private final Token RIGHT_PARENTHESIS_TEMPLATE;
  private final Token COMMA_SEPARATOR_TEMPLATE;
  private final Token EOL_TEMPLATE;
  private final IdentifierParser IDENTIFIER_PARSER;
  private final BinaryOperatorParser OPERATOR_PARSER;

  public CallFunctionParser(TokenFactory tokenFactory, ParserFactory parserFactory) {
    LEFT_PARENTHESIS_TEMPLATE = tokenFactory.createSeparatorToken("(");
    RIGHT_PARENTHESIS_TEMPLATE = tokenFactory.createSeparatorToken(")");
    COMMA_SEPARATOR_TEMPLATE = tokenFactory.createSeparatorToken(",");
    EOL_TEMPLATE = tokenFactory.createEndOfLineToken(";");
    IDENTIFIER_PARSER = parserFactory.createIdentifierParser();
    OPERATOR_PARSER = parserFactory.createBinaryOperatorParser();
  }

  private Boolean canParse(TokenStream stream) {
    Token token = stream.peek(1);
    if (token == null) {
      return false;
    }
    return token.equals(LEFT_PARENTHESIS_TEMPLATE);
  }

  @Override
  public Result<CallFunctionNode> parse(TokenStream stream) {
    if (!canParse(stream)) {
      return new IncorrectResult<>("Tried parsing not a function.");
    }
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

    Result<Token> consumeEndOfLineResult = stream.consume(EOL_TEMPLATE);
    if (!consumeEndOfLineResult.isCorrect()) {
      return new IncorrectResult<>(consumeEndOfLineResult);
    }

    return new CorrectResult<>(new CallFunctionNode(identifierNode, arguments));
  }

  private Result<List<ExpressionNode>> parseFunctionCall(TokenStream stream) {
    Result<Token> consumeLeftParenthesisResult = stream.consume(LEFT_PARENTHESIS_TEMPLATE);
    if (!consumeLeftParenthesisResult.isCorrect()) {
      return new IncorrectResult<>(consumeLeftParenthesisResult);
    }

    Result<List<ExpressionNode>> parseArgumentsResult = parseArguments(stream);
    if (!parseArgumentsResult.isCorrect()) {
      return new IncorrectResult<>(parseArgumentsResult);
    }

    Result<Token> consumeRightParenthesisResult = stream.consume(RIGHT_PARENTHESIS_TEMPLATE);
    if (!consumeRightParenthesisResult.isCorrect()) {
      return new IncorrectResult<>(consumeRightParenthesisResult);
    }

    return parseArgumentsResult;
  }

  private Result<List<ExpressionNode>> parseArguments(TokenStream stream) {
    List<ExpressionNode> arguments = new ArrayList<>();
    Result<ExpressionNode> parseFirstArgument = OPERATOR_PARSER.parse(stream);
    if (!parseFirstArgument.isCorrect()) {
      return new CorrectResult<List<ExpressionNode>>(arguments);
    }
    ExpressionNode firstArgument = parseFirstArgument.result();
    arguments.add(firstArgument);

    while (stream.consume(COMMA_SEPARATOR_TEMPLATE).isCorrect()) {
      Result<ExpressionNode> parseExpressionResult = OPERATOR_PARSER.parse(stream);
      if (!parseExpressionResult.isCorrect()) {
        return new IncorrectResult<>(parseExpressionResult);
      }
      ExpressionNode expressionNode = parseExpressionResult.result();
      arguments.add(expressionNode);
    }

    return new CorrectResult<>(arguments);
  }
}
