package lexical.factories.tokenizers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TokenizersFactoryTest {
    @Test
    public void tokenizersFactoryTest() {
        TokenizerFactoryInterface factory = new TokenizerFactory();
        Assertions.assertNotNull(factory);
        Assertions.assertNotNull(factory.createDefaultTokenizer());
    }
}
