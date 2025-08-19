package lexer.factories.tokenizers;

import lexer.tokenizers.factories.TokenizerFactory;
import lexer.tokenizers.factories.TokenizerFactoryInterface;
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
