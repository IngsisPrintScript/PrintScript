package analyzers.lexic.tokenizers.last;

import analyzers.lexic.tokenizers.TokenizerInterface;
import responses.IncorrectResponse;
import responses.Response;

public record LastTokenizer() implements TokenizerInterface {
    @Override
    public Boolean canTokenize(String input) {
        return true;
    }

    @Override
    public Response tokenize(String input) {
        return new IncorrectResponse("There is no available handler for: %s".formatted(input));
    }
}
