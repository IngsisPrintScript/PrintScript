package tokenizers;

import responses.IncorrectResult;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import tokenizers.FinalTokenizer;
import tokenizers.TokenizerInterface;

public class FinalTokenizerTest {
    @Test
    public void createFinalTokenizerTest(){
        TokenizerInterface finalTokenizer = new FinalTokenizer();
        Assertions.assertNotNull(finalTokenizer);
    }
    @Test
    public void tokenizeFinalTokenizerTest(){
        TokenizerInterface finalTokenizer = new FinalTokenizer();
        Assertions.assertFalse(finalTokenizer.canTokenize("placeholder"));
        Assertions.assertInstanceOf(IncorrectResult.class, finalTokenizer.tokenize("placeholder"));
    }
}
