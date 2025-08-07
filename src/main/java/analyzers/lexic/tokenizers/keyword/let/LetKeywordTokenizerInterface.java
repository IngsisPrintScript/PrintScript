package analyzers.lexic.tokenizers.keyword.let;

import analyzers.lexic.tokenizers.TokenizerInterface;
import responses.CorrectResponse;
import responses.Response;
import token.Token;

public record LetKeywordTokenizerInterface(TokenizerInterface nextTokenizerInterface) implements TokenizerInterface {
    @Override
    public Boolean canTokenize(String input) {
        return input.equals("let");
    }

    @Override
    public Response tokenize(String input) {
        if (!canTokenize(input)) {
            return nextTokenizerInterface().tokenize(input);
        }
        Token keywordToken = new Token("LET_KEYWORD_TOKENIZER", input);
        return new CorrectResponse<>(keywordToken);
    }
}
