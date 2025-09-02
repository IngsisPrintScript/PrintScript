package tokenizers.type.assignation;

import common.TokenInterface;
import factories.tokens.TokenFactory;
import results.CorrectResult;
import results.Result;
import tokenizers.TokenizerInterface;

public record TypeAssignationTokenizer(TokenizerInterface nextTokenizer)
    implements TokenizerInterface {

  @Override
  public Boolean canTokenize(String input) {
    return input.equals(":");
  }

  @Override
  public Result<TokenInterface> tokenize(String input) {
    if (canTokenize(input)) {
      return new CorrectResult<>(new TokenFactory().createTypeAssignationToken());
    } else {
      return nextTokenizer.tokenize(input);
    }
  }
}
