package analyzers.lexic.tokenizers;

import responses.Response;

public interface TokenizerInterface {
    Boolean canTokenize(String input);
    Response tokenize(String input);
}
