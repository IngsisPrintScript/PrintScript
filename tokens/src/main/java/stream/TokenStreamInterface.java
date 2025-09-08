package stream;

import results.Result;
import common.TokenInterface;

import java.util.List;

public interface TokenStreamInterface {
    Result<TokenInterface> peek();
    Result<TokenInterface> peek(Integer offset);
    Result<TokenInterface> consume();
    Result<TokenInterface> consume(TokenInterface expectedToken);
    Result<Integer> consumeAll(TokenInterface expectedToken);
    List<TokenInterface> tokens();
    Boolean isEndOfStream();
    Boolean contains(TokenInterface token);
}
