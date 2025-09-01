package tokenizers;

import common.TokenInterface;
import responses.CorrectResult;
import responses.IncorrectResult;
import responses.Result;
import tokenizers.FinalTokenizer;
import tokenizers.TokenizerInterface;
import tokenizers.eol.EndOfLineTokenizer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class EndOfLineTokenizerTest {
    public static TokenizerInterface finalTokenizer;

    @BeforeAll
    public static void setUp(){
        finalTokenizer = new FinalTokenizer();
    }

    @Test
    public void creationEndOfLineTokenizerTest(){
        TokenizerInterface eolTokenizer = new EndOfLineTokenizer(finalTokenizer);
        Assertions.assertNotNull(eolTokenizer);
    }

    @Test
    public void tokenizeFinalTokenizerTest(){
        TokenizerInterface eolTokenizer = new EndOfLineTokenizer(finalTokenizer);
        // Asserting if the methods of the tokenizer work as expected
        Assertions.assertFalse(eolTokenizer.canTokenize("placeholder"));
        Assertions.assertTrue(eolTokenizer.canTokenize(";"));
        Assertions.assertInstanceOf(IncorrectResult.class, eolTokenizer.tokenize("placeholder"));
        Assertions.assertInstanceOf(CorrectResult.class, eolTokenizer.tokenize(";"));
        Result tokenizeResult = eolTokenizer.tokenize(";");
        // Asserting the result is what is expected
        Object object = ( (CorrectResult<?>)tokenizeResult).result();
        Assertions.assertInstanceOf(TokenInterface.class, object);
        TokenInterface token = (TokenInterface) object;
        Assertions.assertNotNull(token);
        Assertions.assertEquals("EOL_TOKEN", token.name());
        Assertions.assertEquals(";", token.value());
    }
}
