package analyzers.lexic.tokenizers.KEY_WORD_TOKENIZE;

import analyzers.lexic.tokenizers.TOKENIZE_INTERFACE.Tokenizer;
import responses.CorrectResponse;
import responses.IncorrectResponse;
import responses.Response;
import token.Token;

public record LET_KEYWORD_TOKENIZE(Tokenizer nextTokenizer) implements Tokenizer {
    @Override
    public Boolean canTokenize(String input) {
        return input.equals("let");
    }

    @Override
    public Response tokenize(String input) {
        if (!canTokenize(input)) {
            return nextTokenizer().tokenize(input);
        }
        Token keywordToken = new Token("KEY_WORD_TOKEN", input);
        return new CorrectResponse<>(keywordToken);
    }
}
