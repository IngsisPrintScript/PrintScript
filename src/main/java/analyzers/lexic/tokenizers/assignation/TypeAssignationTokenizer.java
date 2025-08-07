package analyzers.lexic.tokenizers.assignation;

import analyzers.lexic.tokenizers.TokenizerInterface;
import responses.CorrectResponse;
import responses.Response;
import token.Token;
import token.TokenInterface;

public record TypeAssignationTokenizer(TokenizerInterface nextTokenizer) implements TokenizerInterface {
    @Override
    public Boolean canTokenize(String input) {
        return input.equals(":");
    }

    @Override
    public Response tokenize(String input) {
        if(!canTokenize(input)){
            return nextTokenizer().tokenize(input);
        }
        TokenInterface assignationTokenInterface = new Token("TYPE_ASSIGNATION_TOKEN", input);
        return new CorrectResponse<>(assignationTokenInterface);
    }
}
