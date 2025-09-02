package tokenizers;

import common.TokenInterface;
import results.IncorrectResult;
import results.Result;

public class FinalTokenizer implements TokenizerInterface {
  @Override
  public Boolean canTokenize(String input) {
    return false;
  }

  @Override
  public Result<TokenInterface> tokenize(String input) {
    return new IncorrectResult<>("There was no tokenizer able to tokenize the string: " + input);
  }
}
