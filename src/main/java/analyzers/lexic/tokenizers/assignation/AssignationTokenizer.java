package analyzers.lexic.tokenizers.assignation;

import analyzers.lexic.tokenizers.TOKENIZE_INTERFACE.Tokenizer;
import responses.CorrectResponse;
import responses.Response;
import token.Token;
import token.TokenInterface;

public record AssignationTokenizer(Tokenizer nextTokenizer) implements Tokenizer {
    @Override
    public Boolean canTokenize(String input) {
        return input.equals("=");
    }

    @Override
    public Response tokenize(String input) {
        if(!canTokenize(input)){
            return nextTokenizer().tokenize(input);
        }
        TokenInterface assignationTokenInterface = new Token("ASSIGNATION_TOKEN", input);
        return new CorrectResponse<>(assignationTokenInterface);
    }
}
