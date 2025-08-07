package analyzers.lexic.tokenizers.FINALIZER_TOKENIZER;

import analyzers.lexic.tokenizers.TOKENIZE_INTERFACE.Tokenizer;
import responses.CorrectResponse;
import responses.IncorrectResponse;
import responses.Response;
import token.Token;
import token.TokenInterface;

public record SEMICOLON_TOKENIZE(Tokenizer nextTokenizer) implements Tokenizer {
    @Override
    public Boolean canTokenize(String input) {
        return input.equals(";");
    }

    @Override
    public Response tokenize(String input) {
        if(!canTokenize(input)) {
            return nextTokenizer().tokenize(input);
        }
        TokenInterface semicolonTokenInterface = new Token("FINALIZER_TOKEN", input);
        return new CorrectResponse<>(semicolonTokenInterface);
    }
}
