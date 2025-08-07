package analyzers.lexic.tokenizers.KEY_WORD_TOKENIZE;

import analyzers.lexic.tokenizers.TOKENIZE_INTERFACE.Tokenizer;
import responses.CorrectResponse;
import responses.IncorrectResponse;
import responses.Response;
import token.TOKEN.TOKENS;

public class LET_KEYWORD_TOKENIZE implements Tokenizer {
    @Override
    public Boolean canTokenize(String input) {
        return input.equals("let");
    }

    @Override
    public <T> Response<T> tokenize(String input) {
        if (!canTokenize(input)) {
            return new IncorrectResponse("Lexical Error");
        }
        TOKENS keywordToken = new TOKENS("KEY_WORD_TOKEN", input);
        return new CorrectResponse<>((T) keywordToken);
    }
}
