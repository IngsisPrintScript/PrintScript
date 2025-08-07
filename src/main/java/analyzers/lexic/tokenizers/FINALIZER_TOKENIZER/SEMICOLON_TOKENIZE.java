package analyzers.lexic.tokenizers.FINALIZER_TOKENIZER;

import analyzers.lexic.tokenizers.TOKENIZE_INTERFACE.Tokenizer;
import responses.CorrectResponse;
import responses.IncorrectResponse;
import responses.Response;
import token.TOKEN.TOKENS;
import token.TokenInterfaces.Token;

public class SEMICOLON_TOKENIZE implements Tokenizer {
    @Override
    public Boolean canTokenize(String input) {
        return input.equals(";");
    }

    @Override
    public <T> Response<T> tokenize(String input) {
        if(!canTokenize(input)) {
            return new IncorrectResponse("Lexical Error");
        }
        Token semicolonToken = new TOKENS("FINALIZER_TOKEN", input);
        return new CorrectResponse<>((T) semicolonToken);
    }
}
