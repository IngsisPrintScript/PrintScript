package com.ingsis.utils.token.template.factories;

import com.ingsis.utils.result.Result;
import com.ingsis.utils.token.template.TokenTemplate;

public interface TokenTemplateFactory {

  TokenTemplate numberLiteral();

  TokenTemplate stringLiteral();

  TokenTemplate booleanLiteral();

  TokenTemplate identifier();

  Result<TokenTemplate> keyword(String keyword);

  Result<TokenTemplate> type(String keyword);

  Result<TokenTemplate> operator(String symbol);

  Result<TokenTemplate> separator(String symbol);
}
