package stream;

import responses.Result;
import common.TokenInterface;

public interface TokenStreamInterface {
    Result peek();
    Result peek(Integer offset);
    Result consume();
    Result consume(TokenInterface expectedToken);
    Boolean isEndOfStream();
    Boolean contains(TokenInterface token);
}
