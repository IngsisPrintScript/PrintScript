package lexer;

import common.TokenInterface;
import responses.CorrectResult;
import responses.IncorrectResult;
import responses.Result;
import factories.tokens.TokenFactory;
import lexer.tokenizers.factories.TokenizerFactory;
import lexer.tokenizers.TokenizerInterface;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

public class LexicalTest {
    public static TokenizerInterface tokenizer;
    public static List<String> stringVarInputs;
    public static List<TokenInterface> stringVarTokens;
    public static List<String> numberVarInputs;
    public static List<TokenInterface> numberVarTokens;
    public static List<String> invalidInputs;

    @BeforeAll
    public static void setup() {
        TokenFactory tokenFactory = new TokenFactory();
        tokenizer = new TokenizerFactory().createDefaultTokenizer();
        stringVarInputs = List.of("let", "aVar", ":", "String", "=", "\"aStr\"", ";");
        stringVarTokens = List.of(
                tokenFactory.createLetKeywordToken(),
                tokenFactory.createIdentifierToken("aVar"),
                tokenFactory.createTypeAssignationToken(),
                tokenFactory.createTypeToken("String"),
                tokenFactory.createAssignationToken(),
                tokenFactory.createLiteralToken("\"aStr\""),
                tokenFactory.createEndOfLineToken()
        );
        numberVarInputs = List.of("let", "aVar", ":", "Number", "=", "9", ";");
        numberVarTokens = List.of(
                tokenFactory.createLetKeywordToken(),
                tokenFactory.createIdentifierToken("aVar"),
                tokenFactory.createTypeAssignationToken(),
                tokenFactory.createTypeToken("Number"),
                tokenFactory.createAssignationToken(),
                tokenFactory.createLiteralToken("9"),
                tokenFactory.createEndOfLineToken()
        );
        invalidInputs = List.of("let", "aVar", ":", "Number", "=", "9asd", ";");
    }

    @Test
    public void createLexicalTest(){
        LexicalInterface lexical = new Lexical(tokenizer);
        Assertions.assertNotNull(lexical);
    }

    @Test
    public void analyzeLexicalTest(){
        LexicalInterface lexical = new Lexical(tokenizer);

        Result analysisResult = lexical.analyze(stringVarInputs);
        Assertions.assertInstanceOf(CorrectResult.class, analysisResult);
        List<TokenInterface> tokens = ( (CorrectResult<List<TokenInterface>>) analysisResult).newObject();
        Assertions.assertEquals(stringVarTokens, tokens);

        analysisResult = lexical.analyze(numberVarInputs);
        Assertions.assertInstanceOf(CorrectResult.class, analysisResult);
        tokens = ( (CorrectResult<List<TokenInterface>>) analysisResult).newObject();
        Assertions.assertEquals(numberVarTokens, tokens);

        analysisResult = lexical.analyze(invalidInputs);
        Assertions.assertInstanceOf(IncorrectResult.class, analysisResult);

    }
}
