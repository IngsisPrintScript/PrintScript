package lexer.tokenizers.type;

import analyzers.lexic.tokenizers.TokenizerInterface;
import common.responses.CorrectResponse;
import common.responses.Response;
import common.token.Token;
import common.token.TokenInterface;

public record NumberTypeTokenizer(TokenizerInterface nextTokenizer) implements TokenizerInterface {
    @Override
    public Boolean canTokenize(String input) {
        return input.matches("Number");
    }

    @Override
    public Response tokenize(String input) {
        if(!canTokenize(input)) {
            return nextTokenizer().tokenize(input);
        }
        TokenInterface numberTokenInterface = new Token("NUMBER_TYPE_TOKENIZER", input);
        return new CorrectResponse<>(numberTokenInterface);
    }
}
