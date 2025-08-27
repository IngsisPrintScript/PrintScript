package lexer.tokenizers.type.assignation;

import responses.CorrectResult;
import responses.Result;
import factories.tokens.TokenFactory;
import lexer.tokenizers.TokenizerInterface;

public record TypeAssignationTokenizer(TokenizerInterface nextTokenizer) implements TokenizerInterface {

    @Override
    public Boolean canTokenize(String input) {
        return input.equals(":");
    }

    @Override
    public Result tokenize(String input) {
        if (canTokenize(input)) {
            return new CorrectResult<>(new TokenFactory().createTypeAssignationToken());
        } else {
            return nextTokenizer.tokenize(input);
        }
    }
}
