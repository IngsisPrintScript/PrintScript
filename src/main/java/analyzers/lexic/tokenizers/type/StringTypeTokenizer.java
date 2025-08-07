package analyzers.lexic.tokenizers.type;

import analyzers.lexic.tokenizers.TokenizerInterface;
import responses.CorrectResponse;
import responses.Response;
import token.Token;
import token.TokenInterface;

public record StringTypeTokenizer(TokenizerInterface nextTokenizer) implements TokenizerInterface {
    @Override
    public Boolean canTokenize(String input) {
        return input.equals("String");
    }

    @Override
    public Response tokenize(String input) {
        if(!canTokenize(input)) {
            return nextTokenizer().tokenize(input);
        }
        TokenInterface stringTokenInterface = new Token("STRING_TYPE_TOKENIZER",input);
        return new CorrectResponse<>(stringTokenInterface);
    }
}
