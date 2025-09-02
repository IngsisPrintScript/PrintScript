package tokenizers;

import common.TokenInterface;
import results.Result;

public interface TokenizerInterface {
    Boolean canTokenize(String input);
    Result<TokenInterface> tokenize(String input);
}
