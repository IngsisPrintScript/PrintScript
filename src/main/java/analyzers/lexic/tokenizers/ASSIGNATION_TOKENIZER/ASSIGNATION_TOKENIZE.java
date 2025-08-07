package analyzers.lexic.tokenizers.ASSIGNATION_TOKENIZER;

import analyzers.lexic.tokenizers.TOKENIZE_INTERFACE.Tokenizer;
import responses.CorrectResponse;
import responses.Response;
import token.TOKEN.TOKENS;
import token.TokenInterfaces.Token;

public record ASSIGNATION_TOKENIZE(Tokenizer nextTokenizer) implements Tokenizer {
    @Override
    public Boolean canTokenize(String input) {
        return input.equals("=");
    }

    @Override
    public <T> Response<T> tokenize(String input) {
        if(!canTokenize(input)){
            return nextTokenizer.tokenize(input);
        }
        Token assignationToken = new TOKENS("ASSIGNATION_TOKEN", input);
        return new CorrectResponse<>((T) assignationToken);
    }
}
