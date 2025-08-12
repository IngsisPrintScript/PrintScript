package lexer.tokenizers;

import common.responses.Response;

public interface TokenizerInterface {
    Boolean canTokenize(String input);
    Response tokenize(String input);
}
