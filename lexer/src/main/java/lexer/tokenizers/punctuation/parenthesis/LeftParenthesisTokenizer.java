package lexer.tokenizers.punctuation.parenthesis;

import responses.CorrectResult;
import responses.IncorrectResult;
import responses.Result;
import factories.tokens.TokenFactory;
import lexer.tokenizers.punctuation.PunctuationTokenizer;

public class LeftParenthesisTokenizer extends PunctuationTokenizer {

    @Override
    public Boolean canTokenize(String input) {
        return input.equals("(");
    }

    @Override
    public Result tokenize(String input) {
        if (!canTokenize(input)) {return new IncorrectResult("Cannot tokenize provided input");
        }
        return new CorrectResult<>(new TokenFactory().createLeftParenthesisToken());
    }
}