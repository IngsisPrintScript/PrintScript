package parser;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import tokenizers.TokenizerInterface;
import tokenizers.factories.TokenizerFactory;


public class CodeParserTest {
    private static TokenizerInterface tokenizer;
    private static String validString;
    private static String invalidString;

    @BeforeAll
    static void setup() {
        tokenizer = new TokenizerFactory().createDefaultTokenizer();
        validString = "let var:String=\"aString\";";
        invalidString = "let varString=\"aString\"";
    }

    @Test
    public void createCodeParserTest(){
        CodeParserInterface parser = new CodeParser(tokenizer);
        Assertions.assertNotNull(parser);
    }

    @Test
    public void parseTest(){
        CodeParserInterface parser = new CodeParser(tokenizer);
        Assertions.assertTrue(parser.parse(validString).isSuccessful());
        Assertions.assertFalse(parser.parse(invalidString).isSuccessful());
    }

}
