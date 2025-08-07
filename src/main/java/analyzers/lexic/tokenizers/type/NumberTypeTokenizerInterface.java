package analyzers.lexic.tokenizers.TYPE_TOKENIZE;

import analyzers.lexic.tokenizers.TOKENIZE_INTERFACE.Tokenizer;
import responses.CorrectResponse;
import responses.IncorrectResponse;
import responses.Response;
import token.Token;
import token.TokenInterface;

public record NUMBER_TYPE_TOKENIZE(Tokenizer nextTokenizer) implements Tokenizer {
    @Override
    public Boolean canTokenize(String input) {
        return input.matches("number");
    }

    @Override
    public Response tokenize(String input) {
        if(!canTokenize(input)) {
            return nextTokenizer().tokenize(input);
        }
        TokenInterface numberTokenInterface = new Token("NUMBER_TYPE", input);
        return new CorrectResponse<>(numberTokenInterface);
    }
}
