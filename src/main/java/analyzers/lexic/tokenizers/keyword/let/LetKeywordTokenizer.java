package analyzers.lexic.tokenizers.keyword.let;

import analyzers.lexic.tokenizers.TokenizerInterface;
import responses.CorrectResponse;
import responses.Response;
import token.Token;

public record LetKeywordTokenizer(TokenizerInterface nextTokenizer) implements TokenizerInterface {
    @Override
    public Boolean canTokenize(String input) {
        return input.equals("let");
    }

    @Override
    public Response tokenize(String input) {
        if (!canTokenize(input)) {
            return nextTokenizer().tokenize(input);
        }
        Token keywordToken = new Token("LET_KEYWORD_TOKEN", input);
        return new CorrectResponse<>(keywordToken);
    }
}
