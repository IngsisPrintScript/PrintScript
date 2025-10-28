/*
 * My Project
 */

package com.ingsis.syntactic.parsers.declaration;

import com.ingsis.nodes.Node;
import com.ingsis.nodes.expression.ExpressionNode;
import com.ingsis.nodes.expression.operator.TypeAssignationNode;
import com.ingsis.nodes.expression.operator.ValueAssignationNode;
import com.ingsis.nodes.factories.NodeFactory;
import com.ingsis.result.CorrectResult;
import com.ingsis.result.IncorrectResult;
import com.ingsis.result.Result;
import com.ingsis.syntactic.parsers.Parser;
import com.ingsis.syntactic.parsers.factories.ParserFactory;
import com.ingsis.syntactic.parsers.operator.BinaryOperatorParser;
import com.ingsis.syntactic.parsers.operators.TypeAssignationParser;
import com.ingsis.tokens.Token;
import com.ingsis.tokens.factories.TokenFactory;
import com.ingsis.tokenstream.TokenStream;

public final class DeclarationParser implements Parser {
  private final Token LET_TOKEN_TEMPLATE;
  private final Token CONST_TOKEN_TEMPLATE;
  private final Token VALUE_ASSIGNATION_TEMPLATE;
  private final Token EOL_TEMPLATE;
  private final TypeAssignationParser TYPE_ASSIGNATION_PARSER;
  private final BinaryOperatorParser OPERATOR_PARSER;
  private final NodeFactory NODE_FACTORY;

  public DeclarationParser(
      TokenFactory TOKEN_FACTORY, ParserFactory PARSER_FACTORY, NodeFactory NODE_FACTORY) {
    this.LET_TOKEN_TEMPLATE = TOKEN_FACTORY.createKeywordToken("let");
    this.CONST_TOKEN_TEMPLATE = TOKEN_FACTORY.createKeywordToken("const");
    this.VALUE_ASSIGNATION_TEMPLATE = TOKEN_FACTORY.createOperatorToken("=");
    EOL_TEMPLATE = TOKEN_FACTORY.createEndOfLineToken(";");
    this.OPERATOR_PARSER = PARSER_FACTORY.createBinaryOperatorParser();
    this.TYPE_ASSIGNATION_PARSER = PARSER_FACTORY.createTypeAssignationParser();
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
  public Result<Node> parse(TokenStream stream) {
    Result<Token> consumeDeclarationKeywordResult = consumeDeclarationKeyword(stream);
    if (!consumeDeclarationKeywordResult.isCorrect()) {
      return new IncorrectResult<>(consumeDeclarationKeywordResult);
    }
    Boolean isConst = consumeDeclarationKeywordResult.result().value().equals("const");

    Result<TypeAssignationNode> parseTypeAssignationResult = TYPE_ASSIGNATION_PARSER.parse(stream);
    if (!parseTypeAssignationResult.isCorrect()) {
      return new IncorrectResult<>(parseTypeAssignationResult);
    }
    TypeAssignationNode typeAssignationNode = parseTypeAssignationResult.result();

    Result<Token> consumeValueAssignationResult = stream.consume(VALUE_ASSIGNATION_TEMPLATE);
    if (!consumeValueAssignationResult.isCorrect()) {
      return new IncorrectResult<>(consumeValueAssignationResult);
    }

    Result<ExpressionNode> parseExpressionResult = OPERATOR_PARSER.parse(stream);
    if (!parseExpressionResult.isCorrect()) {
      return new IncorrectResult<>(parseExpressionResult);
    }
    ExpressionNode expressionNode = parseExpressionResult.result();

    Result<Token> consumeEndOfLineResult = stream.consume(EOL_TEMPLATE);
    if (!consumeEndOfLineResult.isCorrect()) {
      return new IncorrectResult<>(consumeEndOfLineResult);
    }

    ValueAssignationNode valueAssignationNode = NODE_FACTORY.createValueAssignationNode(
        typeAssignationNode.identifierNode(), expressionNode);

    if (isConst) {
      return new CorrectResult<>(
          NODE_FACTORY.createConstNode(typeAssignationNode, valueAssignationNode));
    } else {
      return new CorrectResult<>(
          NODE_FACTORY.createLetNode(typeAssignationNode, valueAssignationNode));
    }
  }
}
