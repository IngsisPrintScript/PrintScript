package tokenizers;

import common.TokenInterface;
import responses.CorrectResult;
import responses.IncorrectResult;
import responses.Result;
import tokenizers.FinalTokenizer;
import tokenizers.TokenizerInterface;
import tokenizers.punctuation.parenthesis.LeftParenthesisTokenizer;
import tokenizers.punctuation.parenthesis.RightParenthesisTokenizer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ParenthesisTokenizerTest {
    public static TokenizerInterface finalTokenizer;

    @BeforeAll
    public static void setUp(){
        finalTokenizer = new FinalTokenizer();
    }

    @Test
    public void creationParenthesisTokenizerTest(){
        TokenizerInterface leftParenthesisTokenizer = new LeftParenthesisTokenizer();
        Assertions.assertNotNull(leftParenthesisTokenizer);
        TokenizerInterface rightParenthesisTokenizer = new RightParenthesisTokenizer();
        Assertions.assertNotNull(rightParenthesisTokenizer);
    }

    @Test
    public void tokenizeParenthesisTokenizerTest(){
        TokenizerInterface rightParenthesisTokenizer = new RightParenthesisTokenizer();
        // Asserting if the methods of the tokenizer work as expected
        Assertions.assertFalse(rightParenthesisTokenizer.canTokenize("placeholder"));
        Assertions.assertTrue(rightParenthesisTokenizer.canTokenize(")"));
        Assertions.assertInstanceOf(IncorrectResult.class, rightParenthesisTokenizer.tokenize("placeholder"));
        Assertions.assertInstanceOf(CorrectResult.class, rightParenthesisTokenizer.tokenize(")"));
        Result tokenizeResult = rightParenthesisTokenizer.tokenize(")");
        // Asserting the result is what is expected
        Object object = ( (CorrectResult<?>)tokenizeResult).result();
        Assertions.assertInstanceOf(TokenInterface.class, object);
        TokenInterface token = (TokenInterface) object;
        Assertions.assertNotNull(token);
        Assertions.assertEquals("RIGHT_PARENTHESIS_TOKEN", token.name());
        Assertions.assertEquals(")", token.value());
        TokenizerInterface leftParenthesisTokenizer = new LeftParenthesisTokenizer();
        // Asserting if the methods of the tokenizer work as expected
        Assertions.assertFalse(leftParenthesisTokenizer.canTokenize("placeholder"));
        Assertions.assertTrue(leftParenthesisTokenizer.canTokenize("("));
        Assertions.assertInstanceOf(IncorrectResult.class, leftParenthesisTokenizer.tokenize("placeholder"));
        Assertions.assertInstanceOf(CorrectResult.class, leftParenthesisTokenizer.tokenize("("));
        tokenizeResult = leftParenthesisTokenizer.tokenize("(");
        // Asserting the result is what is expected
        object = ( (CorrectResult<?>)tokenizeResult).result();
        Assertions.assertInstanceOf(TokenInterface.class, object);
        token = (TokenInterface) object;
        Assertions.assertNotNull(token);
        Assertions.assertEquals("LEFT_PARENTHESIS_TOKEN", token.name());
        Assertions.assertEquals("(", token.value());
    }
}
