package common.tokens.stream;

import common.responses.Result;
import common.tokens.TokenInterface;

public interface TokenStreamInterface {
    Result peek();
    Result peek(Integer offset);
    Result consume();
    Result consume(TokenInterface expectedToken);
    Boolean isEndOfStream();
    Boolean contains(TokenInterface token);
}
