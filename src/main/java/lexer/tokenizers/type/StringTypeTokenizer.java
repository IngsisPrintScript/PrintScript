package lexer.tokenizers.type;

import analyzers.lexic.tokenizers.TokenizerInterface;
import common.responses.CorrectResponse;
import common.responses.Response;
import common.token.Token;
import common.token.TokenInterface;

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
        TokenInterface stringTokenInterface = new Token("STRING_TYPE_TOKEN",input);
        return new CorrectResponse<>(stringTokenInterface);
    }
}
