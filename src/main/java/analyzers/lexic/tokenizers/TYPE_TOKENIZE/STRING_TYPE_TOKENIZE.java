package analyzers.lexic.tokenizers.TYPE_TOKENIZE;

import analyzers.lexic.tokenizers.TOKENIZE_INTERFACE.Tokenizer;
import responses.CorrectResponse;
import responses.IncorrectResponse;
import responses.Response;
import token.TOKEN.TOKENS;
import token.TokenInterfaces.Token;

public class STRING_TYPE_TOKENIZE implements Tokenizer {
    @Override
    public Boolean canTokenize(String input) {
        return input.equals("String");
    }

    @Override
    public <T> Response<T> tokenize(String input) {
        if(!canTokenize(input)) {
            return new IncorrectResponse("Lexical Error");
        }
        Token StringToken = new TOKENS("STRING_TYPE",input);
        return new CorrectResponse<>((T) StringToken);
    }
}
