package lexer.tokenizers.punctuation.parenthesis;

import common.factories.tokens.TokenFactory;
import common.responses.CorrectResult;
import common.responses.IncorrectResult;
import common.responses.Result;
import lexer.tokenizers.punctuation.PunctuationTokenizer;

public class RightParenthesisTokenizer extends PunctuationTokenizer {
    
    @Override
    public Boolean canTokenize(String input) {
        return input.equals(")");
    }

    @Override
    public Result tokenize(String input) {
        if (!canTokenize(input)) {return new IncorrectResult("Cannot tokenize provided input");}
        return new CorrectResult<>(new TokenFactory().createRightParenthesisToken());
    }
}