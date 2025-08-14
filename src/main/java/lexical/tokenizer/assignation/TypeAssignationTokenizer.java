package lexical.tokenizer.assignation;

import common.factories.tokens.TokenFactory;
import common.responses.CorrectResult;
import common.responses.Result;
import lexical.tokenizer.TokenizerInterface;

public record TypeAssignationTokenizer(TokenizerInterface nextTokenizer) implements TokenizerInterface {

    @Override
    public Boolean canTokenize(String token) {
        return token.equals(":");
    }

    @Override
    public Result tokenize(String token) {
        if (canTokenize(token)) {
            return new CorrectResult<>(new TokenFactory().createTypeAssignationToken());
        } else {
            return nextTokenizer.tokenize(token);
        }
    }
}
