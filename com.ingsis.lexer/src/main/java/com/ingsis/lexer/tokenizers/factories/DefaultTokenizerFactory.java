/*
 * My Project
 */

package com.ingsis.lexer.tokenizers.factories;

import com.ingsis.lexer.tokenizers.DefaultTokenizersRegistry;
import com.ingsis.lexer.tokenizers.Tokenizer;
import com.ingsis.lexer.tokenizers.TokenizersRegistry;
import com.ingsis.lexer.tokenizers.identifier.IdentifierTokenizer;
import com.ingsis.lexer.tokenizers.keyword.ConstKeywordTokenizer;
import com.ingsis.lexer.tokenizers.keyword.ElseKeywordTokenizer;
import com.ingsis.lexer.tokenizers.keyword.IfKeywordTokenizer;
import com.ingsis.lexer.tokenizers.keyword.LetKeywordTokenizer;
import com.ingsis.lexer.tokenizers.literal.BooleanLiteralTokenizer;
import com.ingsis.lexer.tokenizers.literal.NumberLiteralTokenizer;
import com.ingsis.lexer.tokenizers.literal.StringLiteralTokenizer;
import com.ingsis.lexer.tokenizers.operator.GenericOperatorTokenizer;
import com.ingsis.lexer.tokenizers.separator.EndOfLineSeparatorTokenizer;
import com.ingsis.lexer.tokenizers.separator.GenericSeparatorTokenizer;
import com.ingsis.lexer.tokenizers.separator.SpaceSeparatorTokenizer;
import com.ingsis.lexer.tokenizers.type.GenericTypeTokenizer;
import com.ingsis.tokens.factories.TokenFactory;
import com.ingsis.types.Types;
import java.util.List;

public final class DefaultTokenizerFactory implements TokenizerFactory {
  private final TokenFactory tokenFactory;

  public DefaultTokenizerFactory(TokenFactory tokenFactory) {
    this.tokenFactory = tokenFactory;
  }

  @Override
  public Tokenizer createTokenizer() {
    return separatorTokenizer(
        operatorTokenizer(
            keywordTokenizer(literalTokenizer(typeTokenizer(identifierTokenizer())))));
  }

  private Tokenizer identifierTokenizer() {
    TokenizersRegistry registry = new DefaultTokenizersRegistry();
    registry.registerTokenizer(new IdentifierTokenizer(tokenFactory));
    return registry;
  }

  private Tokenizer keywordTokenizer(Tokenizer nextTokenizer) {
    TokenizersRegistry registry = new DefaultTokenizersRegistry(nextTokenizer);
    registry.registerTokenizer(new LetKeywordTokenizer(tokenFactory));
    registry.registerTokenizer(new ConstKeywordTokenizer(tokenFactory));
    registry.registerTokenizer(new IfKeywordTokenizer(tokenFactory));
    registry.registerTokenizer(new ElseKeywordTokenizer(tokenFactory));
    return registry;
  }

  private Tokenizer literalTokenizer(Tokenizer nextTokenizer) {
    TokenizersRegistry registry = new DefaultTokenizersRegistry(nextTokenizer);
    registry.registerTokenizer(new StringLiteralTokenizer(tokenFactory));
    registry.registerTokenizer(new NumberLiteralTokenizer(tokenFactory));
    registry.registerTokenizer(new BooleanLiteralTokenizer(tokenFactory));
    return registry;
  }

  private Tokenizer operatorTokenizer(Tokenizer nextTokenizer) {
    TokenizersRegistry registry = new DefaultTokenizersRegistry(nextTokenizer);
    List<String> operators = List.of(":", "=", "+", "-", "/", "*");
    for (String operator : operators) {
      registry.registerTokenizer(new GenericOperatorTokenizer(tokenFactory, operator));
    }
    return registry;
  }

  private Tokenizer separatorTokenizer(Tokenizer nextTokenizer) {
    TokenizersRegistry registry = new DefaultTokenizersRegistry(nextTokenizer);
    registry.registerTokenizer(new SpaceSeparatorTokenizer(tokenFactory, " "));
    registry.registerTokenizer(new SpaceSeparatorTokenizer(tokenFactory, "\t"));
    registry.registerTokenizer(new SpaceSeparatorTokenizer(tokenFactory, "\n"));
    List<String> separators = List.of(",", "(", ")", "[", "]", "{", "}");
    for (String separator : separators) {
      registry.registerTokenizer(new GenericSeparatorTokenizer(tokenFactory, separator));
    }
    registry.registerTokenizer(new EndOfLineSeparatorTokenizer(tokenFactory));
    return registry;
  }

  private Tokenizer typeTokenizer(Tokenizer nextTokenizer) {
    TokenizersRegistry registry = new DefaultTokenizersRegistry(nextTokenizer);
    for (Types type : Types.values()) {
      registry.registerTokenizer(new GenericTypeTokenizer(tokenFactory, type));
    }
    return registry;
  }
}
