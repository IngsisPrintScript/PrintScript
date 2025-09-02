package lexer.factories.tokenizers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import tokenizers.factories.TokenizerFactory;
import tokenizers.factories.TokenizerFactoryInterface;

public class TokenizersFactoryTest {
  @Test
  public void tokenizersFactoryTest() {
    TokenizerFactoryInterface factory = new TokenizerFactory();
    Assertions.assertNotNull(factory);
    Assertions.assertNotNull(factory.createDefaultTokenizer());
  }
}
