package lexer.tokenizers.eol;
import analyzers.lexic.tokenizers.TokenizerInterface;
import common.responses.CorrectResponse;
import common.responses.Response;
import common.token.Token;
import common.token.TokenInterface;

public record EndOfLineTokenizer(TokenizerInterface nextTokenizer) implements TokenizerInterface {
    @Override
    public Boolean canTokenize(String input) {
        return input.equals(";");
    }

    @Override
    public Response tokenize(String input) {
        if(!canTokenize(input)) {
            return nextTokenizer().tokenize(input);
        }
        TokenInterface semicolonTokenInterface = new Token("EOL_TOKEN", input);
        return new CorrectResponse<>(semicolonTokenInterface);
    }
}
