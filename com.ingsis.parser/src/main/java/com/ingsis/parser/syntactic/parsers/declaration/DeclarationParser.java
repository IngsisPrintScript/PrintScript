/*
 * My Project
 */

package com.ingsis.parser.syntactic.parsers.declaration;

import com.ingsis.parser.syntactic.parsers.Parser;
import com.ingsis.parser.syntactic.parsers.expression.LineExpressionParser;
import com.ingsis.parser.syntactic.parsers.factories.ParserFactory;
import com.ingsis.parser.syntactic.parsers.identifier.IdentifierParser;
import com.ingsis.utils.nodes.nodes.expression.ExpressionNode;
import com.ingsis.utils.nodes.nodes.expression.identifier.IdentifierNode;
import com.ingsis.utils.nodes.nodes.factories.NodeFactory;
import com.ingsis.utils.nodes.nodes.keyword.DeclarationKeywordNode;
import com.ingsis.utils.result.CorrectResult;
import com.ingsis.utils.result.IncorrectResult;
import com.ingsis.utils.result.Result;
import com.ingsis.utils.token.tokens.Token;
import com.ingsis.utils.token.tokens.factories.TokenFactory;
import com.ingsis.utils.token.tokenstream.TokenStream;
import com.ingsis.utils.type.types.Types;

public final class DeclarationParser implements Parser<DeclarationKeywordNode> {
  private final Token LET_TOKEN_TEMPLATE;
  private final Token CONST_TOKEN_TEMPLATE;
  private final Token VALUE_ASSIGNATION_OPERATOR_TEMPLATE;
  private final Token TYPE_ASSIGNATION_OPERATOR_TEMPLATE;
  private final Token TYPE_TEMPLATE;
  private final Token EOL_TEMPLATE;
  private final LineExpressionParser EXPRESSION_PARSER;
  private final IdentifierParser IDENTIFIER_PARSER;
  private final NodeFactory NODE_FACTORY;

  public DeclarationParser(
      TokenFactory TOKEN_FACTORY, ParserFactory PARSER_FACTORY, NodeFactory NODE_FACTORY) {
    this.LET_TOKEN_TEMPLATE = TOKEN_FACTORY.createKeywordToken("let");
    this.CONST_TOKEN_TEMPLATE = TOKEN_FACTORY.createKeywordToken("const");
    this.VALUE_ASSIGNATION_OPERATOR_TEMPLATE = TOKEN_FACTORY.createOperatorToken("=");
    this.TYPE_ASSIGNATION_OPERATOR_TEMPLATE = TOKEN_FACTORY.createOperatorToken(":");
    this.TYPE_TEMPLATE = TOKEN_FACTORY.createTypeToken("");
    this.EOL_TEMPLATE = TOKEN_FACTORY.createEndOfLineToken(";");
    this.EXPRESSION_PARSER = PARSER_FACTORY.createLineExpressionParser();
    this.IDENTIFIER_PARSER = PARSER_FACTORY.createIdentifierParser();
    this.NODE_FACTORY = NODE_FACTORY;
  }

  private Result<Token> consumeDeclarationKeyword(TokenStream stream) {
    Result<Token> consumeLetResult = stream.consume(LET_TOKEN_TEMPLATE);
    if (consumeLetResult.isCorrect()) {
      return consumeLetResult;
    }
    Result<Token> consumeConstResult = stream.consume(CONST_TOKEN_TEMPLATE);
    if (consumeConstResult.isCorrect()) {
      return consumeConstResult;
    }
    return new IncorrectResult<>("No declaration keyword present");
  }

  @Override
  public Result<DeclarationKeywordNode> parse(TokenStream stream) {
    Result<Token> consumeDeclarationKeywordResult = consumeDeclarationKeyword(stream);
    if (!consumeDeclarationKeywordResult.isCorrect())
      return new IncorrectResult<>(consumeDeclarationKeywordResult);
    Token declarationKeyword = consumeDeclarationKeywordResult.result();
    Boolean isMutable = declarationKeyword.value().equals("let");
    Integer line = declarationKeyword.line();
    Integer column = declarationKeyword.column();

    Result<IdentifierNode> parseIdentifierResult = IDENTIFIER_PARSER.parse(stream);
    if (!parseIdentifierResult.isCorrect())
      return new IncorrectResult<>(parseIdentifierResult);
    IdentifierNode identifierNode = parseIdentifierResult.result();

    Result<Token> consumeTypeAssignationResult = stream.consume(TYPE_ASSIGNATION_OPERATOR_TEMPLATE);
    if (!consumeTypeAssignationResult.isCorrect())
      return new IncorrectResult<>(consumeTypeAssignationResult);

    Result<Token> consumeTypeResult = stream.consume(TYPE_TEMPLATE);
    if (!consumeTypeResult.isCorrect())
      return new IncorrectResult<>(consumeTypeResult);
    Token typeToken = consumeTypeResult.result();
    Types declaredType = Types.fromKeyword(typeToken.value());
    Result<Token> consumeValueAssignationResult = stream.consume(VALUE_ASSIGNATION_OPERATOR_TEMPLATE);
    if (!consumeValueAssignationResult.isCorrect()
        && stream.consume(EOL_TEMPLATE).isCorrect()) {
      return buildResult(
          identifierNode,
          NODE_FACTORY.createNilExpressionNode(),
          declaredType,
          isMutable,
          line,
          column);
    }
    return parseInitialization(stream, identifierNode, declaredType, isMutable, line, column);
  }

  private Result<DeclarationKeywordNode> parseInitialization(
      TokenStream stream,
      IdentifierNode identifierNode,
      Types declaredType,
      Boolean isMutable,
      Integer line,
      Integer column) {
    TokenStream subStream = stream.retrieveNonConsumedStream();
    Result<ExpressionNode> parseExpressionResult = EXPRESSION_PARSER.parse(subStream);
    stream.advanceMovedTokens(subStream);
    if (!parseExpressionResult.isCorrect())
      return new IncorrectResult<>(parseExpressionResult);
    ExpressionNode expressionNode = parseExpressionResult.result();
    return buildResult(identifierNode, expressionNode, declaredType, isMutable, line, column);
  }

  private Result<DeclarationKeywordNode> buildResult(
      IdentifierNode identifierNode,
      ExpressionNode expressionNode,
      Types declaredType,
      Boolean isMutable,
      Integer line,
      Integer column) {
    return new CorrectResult<>(
        NODE_FACTORY.createDeclarationNode(
            identifierNode, expressionNode, declaredType, isMutable, line, column));
  }
}
