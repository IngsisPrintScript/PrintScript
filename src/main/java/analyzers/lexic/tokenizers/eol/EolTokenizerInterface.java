package analyzers.lexic.tokenizers.eol;

import analyzers.lexic.tokenizers.TOKENIZE_INTERFACE.Tokenizer;
import responses.CorrectResponse;
import responses.Response;
import token.Token;
import token.TokenInterface;

public record EolTokenizer(Tokenizer nextTokenizer) implements Tokenizer {
    @Override
    public Boolean canTokenize(String input) {
        return input.equals(";");
    }

    @Override
    public Response tokenize(String input) {
        if(!canTokenize(input)) {
            return nextTokenizer().tokenize(input);
        }
        TokenInterface semicolonTokenInterface = new Token("EOL_TOKENIZER", input);
        return new CorrectResponse<>(semicolonTokenInterface);
    }
}
