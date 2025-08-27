package lexer.tokenizers;

import responses.Result;

public interface TokenizerInterface {
    Boolean canTokenize(String input);
    Result tokenize(String input);
}
