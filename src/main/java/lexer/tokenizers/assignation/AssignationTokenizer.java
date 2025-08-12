package lexer.tokenizers.assignation;

import analyzers.lexic.tokenizers.TokenizerInterface;
import common.responses.CorrectResponse;
import common.responses.Response;
import common.token.Token;
import common.token.TokenInterface;

public record AssignationTokenizer(TokenizerInterface nextTokenizer) implements TokenizerInterface {
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
