package tokenizers.keyword;

import common.TokenInterface;
import java.util.List;
import results.Result;
import tokenizers.TokenizerInterface;

public class KeywordTokenizer implements TokenizerInterface {
  private final TokenizerInterface nextTokenizer;
  private final List<Class<? extends KeywordTokenizer>> subclasses =
      List.of(LetKeywordTokenizer.class, PrintlnKeywordTokenizer.class);

  public KeywordTokenizer(TokenizerInterface nextTokenizer) {
    this.nextTokenizer = nextTokenizer;
  }

  @Override
  public Boolean canTokenize(String input) {
    for (Class<? extends KeywordTokenizer> subclass : subclasses) {
      try {
        TokenizerInterface subclassTokenizer = subclass.getDeclaredConstructor().newInstance();
        if (subclassTokenizer.canTokenize(input)) {
          return true;
        }
      } catch (Exception e) {
        continue;
      }
    }
    return false;
  }

  @Override
  public Result<TokenInterface> tokenize(String input) {
    for (Class<? extends KeywordTokenizer> subclass : subclasses) {
      try {
        TokenizerInterface subclassTokenizer = subclass.getDeclaredConstructor().newInstance();
        if (subclassTokenizer.canTokenize(input)) {
          return subclassTokenizer.tokenize(input);
        }
      } catch (Exception e) {
        continue;
      }
    }
    return nextTokenizer.tokenize(input);
  }
}
