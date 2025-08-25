package lexer.tokenizers;

import common.responses.CorrectResult;
import common.responses.IncorrectResult;
import common.responses.Result;
import common.tokens.TokenInterface;
import lexer.tokenizers.identifier.IdentifierTokenizer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class IdentifierTokenizerTest {
    public static TokenizerInterface finalTokenizer;

    @BeforeAll
    public static void setUp(){
        finalTokenizer = new FinalTokenizer();
    }

    @Test
    public void creationIdentifierTokenizerTest(){
        TokenizerInterface eolTokenizer = new IdentifierTokenizer(finalTokenizer);
        Assertions.assertNotNull(eolTokenizer);
    }

    @Test
    public void tokenizeIdentifierTokenizerTest(){
        TokenizerInterface eolTokenizer = new IdentifierTokenizer(finalTokenizer);
        // Asserting if the methods of the tokenizer work as expected
        Assertions.assertFalse(eolTokenizer.canTokenize("%1"));
        Assertions.assertTrue(eolTokenizer.canTokenize("validIdentifier"));
        Assertions.assertInstanceOf(IncorrectResult.class, eolTokenizer.tokenize("%1"));
        Assertions.assertInstanceOf(CorrectResult.class, eolTokenizer.tokenize("validIdentifier"));
        Result tokenizeResult = eolTokenizer.tokenize("validIdentifier");
        // Asserting the result is what is expected
        Object object = ( (CorrectResult<?>)tokenizeResult).newObject();
        Assertions.assertInstanceOf(TokenInterface.class, object);
        TokenInterface token = (TokenInterface) object;
        Assertions.assertNotNull(token);
        Assertions.assertEquals("IDENTIFIER_TOKEN", token.name());
        Assertions.assertEquals("validIdentifier", token.value());
    }
}
