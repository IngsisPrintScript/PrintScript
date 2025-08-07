package analyzers.lexic.tokenizers.type;

import analyzers.lexic.tokenizers.TokenizerInterface;
import responses.CorrectResponse;
import responses.Response;
import token.Token;
import token.TokenInterface;

public record NumberTypeTokenizer(TokenizerInterface nextTokenizer) implements TokenizerInterface {
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
