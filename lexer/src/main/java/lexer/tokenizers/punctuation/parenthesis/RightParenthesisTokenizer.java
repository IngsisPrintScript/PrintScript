package lexer.tokenizers.punctuation.parenthesis;

import common.TokenInterface;
import responses.CorrectResult;
import responses.IncorrectResult;
import responses.Result;
import factories.tokens.TokenFactory;
import lexer.tokenizers.punctuation.PunctuationTokenizer;

public class RightParenthesisTokenizer extends PunctuationTokenizer {
    
    @Override
    public Boolean canTokenize(String input) {
        return input.equals(")");
    }

    @Override
    public Result<TokenInterface> tokenize(String input) {
        if (!canTokenize(input)) {return new IncorrectResult<>("Cannot tokenize provided input");}
        return new CorrectResult<>(new TokenFactory().createRightParenthesisToken());
    }
}