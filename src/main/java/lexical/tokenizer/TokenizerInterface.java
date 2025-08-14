package lexical.tokenizer;

import common.responses.Result;

public interface TokenizerInterface {
    Boolean canTokenize(String token);
    Result tokenize(String token);
}
