package analyzers.lexic.tokenizers.IdentifierTokenize;

import analyzers.lexic.tokenizers.TokenizerInterface;
import responses.CorrectResponse;
import responses.Response;
import token.Token;
import token.TokenInterface;

import java.util.List;

public record IdentifierTokenizer(TokenizerInterface nextTokenizer, List<String> DeniedChars) implements TokenizerInterface {
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
            return nextTokenizer().tokenize(input);
        }
        TokenInterface tokenizer = new Token("IDENTIFIER_TOKEN", input);
        return new CorrectResponse<>(tokenizer);
    }
}
