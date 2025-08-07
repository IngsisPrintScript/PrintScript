package analyzers.lexic.tokenizers.type;

import analyzers.lexic.tokenizers.TokenizerInterface;
import responses.CorrectResponse;
import responses.Response;
import token.Token;
import token.TokenInterface;

public record NumberTypeTokenizerInterface(TokenizerInterface nextTokenizerInterface) implements TokenizerInterface {
    @Override
    public Boolean canTokenize(String input) {
        return input.matches("Number");
    }

    @Override
    public Response tokenize(String input) {
        if(!canTokenize(input)) {
            return nextTokenizerInterface().tokenize(input);
        }
        TokenInterface numberTokenInterface = new Token("NUMBER_TYPE_TOKENIZER", input);
        return new CorrectResponse<>(numberTokenInterface);
    }
}
