package analyzers.lexic.tokenizers.ValueTokens;

import analyzers.lexic.tokenizers.TokenizerInterface;
import responses.CorrectResponse;
import responses.IncorrectResponse;
import responses.Response;
import token.Token;
import token.TokenInterface;

public class LiteralTokenizer implements TokenizerInterface {

    @Override
    public Boolean canTokenize(String input) {
        return true;
    }

    @Override
    public Response tokenize(String input) {
        if(!canTokenize(input)) {
            return new IncorrectResponse("");
        }
        TokenInterface token = new Token("LITERAL_TOKEN",input);
        return new CorrectResponse<>(token);
    }
}
