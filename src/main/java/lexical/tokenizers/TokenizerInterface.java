package lexical.tokenizers;

import common.responses.Result;

public interface TokenizerInterface {
    Boolean canTokenize(String input);
    Result tokenize(String input);
}
