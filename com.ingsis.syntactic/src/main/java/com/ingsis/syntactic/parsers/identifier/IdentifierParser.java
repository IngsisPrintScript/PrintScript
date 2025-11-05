/*
 * My Project
 */

package com.ingsis.syntactic.parsers.identifier;

import com.ingsis.nodes.expression.identifier.IdentifierNode;
import com.ingsis.nodes.factories.NodeFactory;
import com.ingsis.result.CorrectResult;
import com.ingsis.result.IncorrectResult;
import com.ingsis.result.Result;
import com.ingsis.syntactic.parsers.Parser;
import com.ingsis.tokens.Token;
import com.ingsis.tokens.factories.TokenFactory;
import com.ingsis.tokenstream.TokenStream;

public final class IdentifierParser implements Parser<IdentifierNode> {
  private final Token IDENTIFIER_TOKEN_TEMPLATE;
  private final NodeFactory NODE_FACTORY;

  public IdentifierParser(TokenFactory TOKEN_FACTORY, NodeFactory NODE_FACTORY) {
    this.IDENTIFIER_TOKEN_TEMPLATE = TOKEN_FACTORY.createIdentifierToken("");
    this.NODE_FACTORY = NODE_FACTORY;
  }

  @Override
  public Result<IdentifierNode> parse(TokenStream stream) {
    Result<Token> consumeIdentifierResult = stream.consume(IDENTIFIER_TOKEN_TEMPLATE);
    if (!consumeIdentifierResult.isCorrect()) {
      return new IncorrectResult<>(consumeIdentifierResult);
    }
    Token identifierToken = consumeIdentifierResult.result();
    return new CorrectResult<>(NODE_FACTORY.createIdentifierNode(identifierToken.value()));
  }
}
