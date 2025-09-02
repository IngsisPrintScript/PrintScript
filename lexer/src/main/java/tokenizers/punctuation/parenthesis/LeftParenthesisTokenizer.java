package tokenizers.punctuation.parenthesis;

import common.TokenInterface;
import factories.tokens.TokenFactory;
import results.CorrectResult;
import results.IncorrectResult;
import results.Result;
import tokenizers.punctuation.PunctuationTokenizer;

public class LeftParenthesisTokenizer extends PunctuationTokenizer {

  @Override
  public Boolean canTokenize(String input) {
    return input.equals("(");
  }

  @Override
  public Result<TokenInterface> tokenize(String input) {
    if (!canTokenize(input)) {
      return new IncorrectResult<>("Cannot tokenize provided input");
    }
    return new CorrectResult<>(new TokenFactory().createLeftParenthesisToken());
  }
}
