package stream;

import results.Result;
import common.TokenInterface;

public interface TokenStreamInterface {
    Result<TokenInterface> peek();
    Result<TokenInterface> peek(Integer offset);
    Result<TokenInterface> consume();
    Result<TokenInterface> consume(TokenInterface expectedToken);
    Boolean isEndOfStream();
    Boolean contains(TokenInterface token);
}
