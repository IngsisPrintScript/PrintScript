package lexical.tokenizer.operator;

import common.factories.tokens.TokenFactory;
import common.responses.CorrectResult;
import common.responses.Result;
import lexical.tokenizer.TokenizerInterface;

public final class AssignationOperatorTokenizer extends OperatorTokenizer {
    public AssignationOperatorTokenizer(TokenizerInterface nextTokenizer) {
        super(nextTokenizer);
    }

    @Override
    public Boolean canTokenize(String input) {
        return input.equals("=");
    }

    @Override
    public Result tokenize(String input) {
        return new CorrectResult<>(new TokenFactory().createAssignationToken());
    }
}
