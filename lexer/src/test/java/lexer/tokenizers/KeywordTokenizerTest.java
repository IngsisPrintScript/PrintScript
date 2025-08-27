package lexer.tokenizers;

import common.TokenInterface;
import responses.CorrectResult;
import responses.IncorrectResult;
import responses.Result;
import lexer.tokenizers.keyword.KeywordTokenizer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class KeywordTokenizerTest {
    public static TokenizerInterface finalTokenizer;

    @BeforeAll
    public static void setUp(){
        finalTokenizer = new FinalTokenizer();
    }

    @Test
    public void creationIdentifierTokenizerTest(){
        TokenizerInterface eolTokenizer = new KeywordTokenizer(finalTokenizer);
        Assertions.assertNotNull(eolTokenizer);
    }

    @Test
    public void tokenizeIdentifierTokenizerTest(){
        TokenizerInterface eolTokenizer = new KeywordTokenizer(finalTokenizer);
        // Asserting if the methods of the tokenizer work as expected
        Assertions.assertFalse(eolTokenizer.canTokenize("%1"));
        Assertions.assertTrue(eolTokenizer.canTokenize("let"));
        Assertions.assertTrue(eolTokenizer.canTokenize("println"));
        Assertions.assertInstanceOf(IncorrectResult.class, eolTokenizer.tokenize("%1"));
        Assertions.assertInstanceOf(CorrectResult.class, eolTokenizer.tokenize("let"));
        Assertions.assertInstanceOf(CorrectResult.class, eolTokenizer.tokenize("println"));
        // Asserting the result is what is expected
        // Let
        Result tokenizeResult = eolTokenizer.tokenize("let");
        Object object = ( (CorrectResult<?>)tokenizeResult).newObject();
        Assertions.assertInstanceOf(TokenInterface.class, object);
        TokenInterface token = (TokenInterface) object;
        Assertions.assertNotNull(token);
        Assertions.assertEquals("LET_KEYWORD_TOKEN", token.name());
        Assertions.assertEquals("let", token.value());
        // Println
        tokenizeResult = eolTokenizer.tokenize("println");
        object = ( (CorrectResult<?>)tokenizeResult).newObject();
        Assertions.assertInstanceOf(TokenInterface.class, object);
        token = (TokenInterface) object;
        Assertions.assertNotNull(token);
        Assertions.assertEquals("PRINT_KEYWORD_TOKEN", token.name());
        Assertions.assertEquals("println", token.value());
    }
}
