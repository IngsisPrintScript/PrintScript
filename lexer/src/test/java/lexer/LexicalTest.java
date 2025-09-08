package lexer;

import results.Result;
import factories.tokens.TokenFactory;
import tokenizers.factories.TokenizerFactory;
import tokenizers.TokenizerInterface;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import stream.TokenStream;
import stream.TokenStreamInterface;

import java.util.List;

public class LexicalTest {
    public static TokenizerInterface tokenizer;
    public static List<String> stringVarInputs;
    public static TokenStreamInterface stringVarTokens;
    public static List<String> numberVarInputs;
    public static TokenStreamInterface numberVarTokens;
    public static List<String> invalidInputs;

    @BeforeAll
    public static void setup() {
        TokenFactory tokenFactory = new TokenFactory();
        tokenizer = new TokenizerFactory().createDefaultTokenizer();
        stringVarInputs = List.of("let", "aVar", ":", "String", "=", "\"aStr\"", ";");
        stringVarTokens = new TokenStream(
                List.of(
                        tokenFactory.createLetKeywordToken(),
                        tokenFactory.createIdentifierToken("aVar"),
                        tokenFactory.createTypeAssignationToken(),
                        tokenFactory.createTypeToken("String"),
                        tokenFactory.createAssignationToken(),
                        tokenFactory.createLiteralToken("\"aStr\""),
                        tokenFactory.createEndOfLineToken()
                )
        );
        numberVarInputs = List.of("let", "aVar", ":", "Number", "=", "9", ";");
        numberVarTokens = new TokenStream(
                List.of(
                        tokenFactory.createLetKeywordToken(),
                        tokenFactory.createIdentifierToken("aVar"),
                        tokenFactory.createTypeAssignationToken(),
                        tokenFactory.createTypeToken("Number"),
                        tokenFactory.createAssignationToken(),
                        tokenFactory.createLiteralToken("9"),
                        tokenFactory.createEndOfLineToken()
                )
        );
        invalidInputs = List.of("let", "aVar", ":", "Number", "=", "9asd", ";");
    }

    @Test
    public void createLexicalTest(){
        LexicalInterface lexical = null;
        Assertions.assertNotNull(lexical);
    }

    @Test
    public void analyzeLexicalTest(){
        LexicalInterface lexical = null;

        Result<TokenStreamInterface> analysisResult = null;
        Assertions.assertTrue(analysisResult.isSuccessful());
        TokenStreamInterface tokens = analysisResult.result();
        Assertions.assertEquals(stringVarTokens, tokens);

        analysisResult = null;
        Assertions.assertTrue(analysisResult.isSuccessful());
        tokens = analysisResult.result();
        Assertions.assertEquals(numberVarTokens, tokens);

        analysisResult = null;
        Assertions.assertFalse(analysisResult.isSuccessful());

    }
}
