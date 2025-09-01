package tokenizers;

import common.TokenInterface;
import responses.CorrectResult;
import responses.IncorrectResult;
import responses.Result;
import tokenizers.FinalTokenizer;
import tokenizers.TokenizerInterface;
import tokenizers.type.TypeTokenizer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class TypeTokenizerTest {
    public static TokenizerInterface finalTokenizer;

    @BeforeAll
    public static void setUp(){
        finalTokenizer = new FinalTokenizer();
    }

    @Test
    public void creationEndOfLineTokenizerTest(){
        TokenizerInterface eolTokenizer = new TypeTokenizer(finalTokenizer);
        Assertions.assertNotNull(eolTokenizer);
    }

    @Test
    public void tokenizeFinalTokenizerTest(){
        TokenizerInterface eolTokenizer = new TypeTokenizer(finalTokenizer);
        // Asserting if the methods of the tokenizer work as expected
        Assertions.assertFalse(eolTokenizer.canTokenize("placeholder"));
        Assertions.assertTrue(eolTokenizer.canTokenize("String"));
        Assertions.assertTrue(eolTokenizer.canTokenize("Number"));
        Assertions.assertInstanceOf(IncorrectResult.class, eolTokenizer.tokenize("placeholder"));
        Assertions.assertInstanceOf(CorrectResult.class, eolTokenizer.tokenize("String"));
        Assertions.assertInstanceOf(CorrectResult.class, eolTokenizer.tokenize("Number"));
        // Asserting the result is what is expected
        // String
        Result tokenizeResult = eolTokenizer.tokenize("String");
        Object object = ( (CorrectResult<?>)tokenizeResult).result();
        Assertions.assertInstanceOf(TokenInterface.class, object);
        TokenInterface token = (TokenInterface) object;
        Assertions.assertNotNull(token);
        Assertions.assertEquals("TYPE_TOKEN", token.name());
        Assertions.assertEquals("String", token.value());
        // Asserting the result is what is expected
        // String
        tokenizeResult = eolTokenizer.tokenize("Number");
        object = ( (CorrectResult<?>)tokenizeResult).result();
        Assertions.assertInstanceOf(TokenInterface.class, object);
        token = (TokenInterface) object;
        Assertions.assertNotNull(token);
        Assertions.assertEquals("TYPE_TOKEN", token.name());
        Assertions.assertEquals("Number", token.value());
    }
}
