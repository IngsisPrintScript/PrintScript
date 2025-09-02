package stream;

import common.TokenInterface;
import results.Result;

public interface TokenStreamInterface {
  Result<TokenInterface> peek();

  Result<TokenInterface> peek(Integer offset);

  Result<TokenInterface> consume();

  Result<TokenInterface> consume(TokenInterface expectedToken);

  Boolean isEndOfStream();

  Boolean contains(TokenInterface token);
}
