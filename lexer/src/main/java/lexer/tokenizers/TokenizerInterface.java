package lexer.tokenizers;

import common.TokenInterface;
import responses.Result;

public interface TokenizerInterface {
    Boolean canTokenize(String input);
    Result<TokenInterface> tokenize(String input);
}
