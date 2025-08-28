package lexer.tokenizers;

import common.TokenInterface;
import responses.CorrectResult;
import responses.IncorrectResult;
import responses.Result;
import lexer.tokenizers.type.assignation.TypeAssignationTokenizer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class TypeAssignationTokenizerTest {
    public static TokenizerInterface finalTokenizer;

    @BeforeAll
    public static void setUp(){
        finalTokenizer = new FinalTokenizer();
    }

    @Test
    public void creationEndOfLineTokenizerTest(){
        TokenizerInterface eolTokenizer = new TypeAssignationTokenizer(finalTokenizer);
        Assertions.assertNotNull(eolTokenizer);
    }

    @Test
    public void tokenizeFinalTokenizerTest(){
        TokenizerInterface eolTokenizer = new TypeAssignationTokenizer(finalTokenizer);
        // Asserting if the methods of the tokenizer work as expected
        Assertions.assertFalse(eolTokenizer.canTokenize("placeholder"));
        Assertions.assertTrue(eolTokenizer.canTokenize(":"));
        Assertions.assertInstanceOf(IncorrectResult.class, eolTokenizer.tokenize("placeholder"));
        Assertions.assertInstanceOf(CorrectResult.class, eolTokenizer.tokenize(":"));
        // Asserting the result is what is expected
        Result tokenizeResult = eolTokenizer.tokenize(":");
        Object object = ( (CorrectResult<?>)tokenizeResult).newObject();
        Assertions.assertInstanceOf(TokenInterface.class, object);
        TokenInterface token = (TokenInterface) object;
        Assertions.assertNotNull(token);
        Assertions.assertEquals("TYPE_ASSIGNATION_TOKEN", token.name());
        Assertions.assertEquals(":", token.value());
    }
}
