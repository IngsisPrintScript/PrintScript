package lexer.tokenizers;

import common.TokenInterface;
import common.responses.CorrectResult;
import common.responses.IncorrectResult;
import common.responses.Result;
import lexer.tokenizers.literal.LiteralTokenizer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class LiteralTokenizerTest {
    public static TokenizerInterface finalTokenizer;

    @BeforeAll
    public static void setUp(){
        finalTokenizer = new FinalTokenizer();
    }

    @Test
    public void creationEndOfLineTokenizerTest(){
        TokenizerInterface eolTokenizer = new LiteralTokenizer(finalTokenizer);
        Assertions.assertNotNull(eolTokenizer);
    }

    @Test
    public void tokenizeFinalTokenizerTest(){
        TokenizerInterface eolTokenizer = new LiteralTokenizer(finalTokenizer);
        // Asserting if the methods of the tokenizer work as expected
        Assertions.assertFalse(eolTokenizer.canTokenize("placeholder"));
        Assertions.assertTrue(eolTokenizer.canTokenize("\"str\""));
        Assertions.assertTrue(eolTokenizer.canTokenize("9"));
        Assertions.assertInstanceOf(IncorrectResult.class, eolTokenizer.tokenize("placeholder"));
        Assertions.assertInstanceOf(CorrectResult.class, eolTokenizer.tokenize("\"str\""));
        Assertions.assertInstanceOf(CorrectResult.class, eolTokenizer.tokenize("9"));
        // Asserting the result is what is expected
        // String literal
        Result tokenizeResult = eolTokenizer.tokenize("\"str\"");
        Object object = ( (CorrectResult<?>)tokenizeResult).newObject();
        Assertions.assertInstanceOf(TokenInterface.class, object);
        TokenInterface token = (TokenInterface) object;
        Assertions.assertNotNull(token);
        Assertions.assertEquals("LITERAL_TOKEN", token.name());
        Assertions.assertEquals("\"str\"", token.value());
        // Number literal
        tokenizeResult = eolTokenizer.tokenize("9");
        object = ( (CorrectResult<?>)tokenizeResult).newObject();
        Assertions.assertInstanceOf(TokenInterface.class, object);
        token = (TokenInterface) object;
        Assertions.assertNotNull(token);
        Assertions.assertEquals("LITERAL_TOKEN", token.name());
        Assertions.assertEquals("9", token.value());
    }
}
