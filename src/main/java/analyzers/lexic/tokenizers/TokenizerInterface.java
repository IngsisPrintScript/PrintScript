package analyzers.lexic.tokenizers.TOKENIZE_INTERFACE;

import responses.Response;

public interface Tokenizer {
    Boolean canTokenize(String input);
    Response tokenize(String input);
}
