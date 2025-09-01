package tokenizers.operator;

import common.TokenInterface;
import factories.tokens.TokenFactory;
import results.CorrectResult;
import results.Result;
import tokenizers.FinalTokenizer;
import tokenizers.TokenizerInterface;

public final class AdditionOperatorTokenizer extends OperatorTokenizer {
    public AdditionOperatorTokenizer() {
        super(new FinalTokenizer());
    }
    public AdditionOperatorTokenizer(TokenizerInterface nextTokenizer) {
        super(nextTokenizer);
    }

    @Override
    public Boolean canTokenize(String input) {
        return input.equals("+");
    }

    @Override
    public Result<TokenInterface> tokenize(String input) {
        return new CorrectResult<>(new TokenFactory().createAdditionToken());
    }
}
