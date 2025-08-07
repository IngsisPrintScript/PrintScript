package analyzers.lexic.tokenizers.TYPE_TOKENIZE;

import analyzers.lexic.tokenizers.TOKENIZE_INTERFACE.Tokenizer;
import responses.CorrectResponse;
import responses.IncorrectResponse;
import responses.Response;
import token.Token;
import token.TokenInterface;

public record STRING_TYPE_TOKENIZE(Tokenizer nextTokenizer) implements Tokenizer {
    @Override
    public Boolean canTokenize(String input) {
        return input.equals("String");
    }

    @Override
    public Response tokenize(String input) {
        if(!canTokenize(input)) {
            return nextTokenizer().tokenize(input);
        }
        TokenInterface stringTokenInterface = new Token("STRING_TYPE",input);
        return new CorrectResponse<>(stringTokenInterface);
    }
}
