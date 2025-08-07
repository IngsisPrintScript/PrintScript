package analyzers.lexic.tokenizers.IdentifierTokenize;

import analyzers.lexic.tokenizers.TokenizerInterface;
import responses.CorrectResponse;
import responses.IncorrectResponse;
import responses.Response;
import token.Token;
import token.TokenInterface;

import java.util.List;

public record IdentifierTokenizer(TokenizerInterface tokenizerInterface, List<String> DeniedChars) implements TokenizerInterface {
    @Override
    public Boolean canTokenize(String input) {
        for(String chars: DeniedChars) {
            if (input.contains(chars)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public Response tokenize(String input) {
        if(!canTokenize(input)) {
            return new IncorrectResponse("Identifier tokenizer returned an incorrect response");
        }
        TokenInterface tokenizer = new Token("IDENTIFIER", input);
        return new CorrectResponse<>(tokenizer);
    }
}
