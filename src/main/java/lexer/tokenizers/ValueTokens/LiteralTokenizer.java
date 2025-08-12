package lexer.tokenizers.ValueTokens;

import analyzers.lexic.tokenizers.TokenizerInterface;
import common.responses.CorrectResponse;
import common.responses.IncorrectResponse;
import common.responses.Response;
import common.token.Token;
import common.token.TokenInterface;

public record LiteralTokenizer(TokenizerInterface nextTokenizer) implements TokenizerInterface {

    @Override
    public Boolean canTokenize(String input) {
        return true;
    }

    @Override
    public Response tokenize(String input) {
        if(!canTokenize(input)) {
            return new IncorrectResponse("");
        }
        TokenInterface token = new Token("LITERAL_TOKEN",input);
        return new CorrectResponse<>(token);
    }
}
