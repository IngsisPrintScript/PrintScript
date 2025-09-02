package lexer;

import factories.tokens.TokenFactory;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import results.Result;
import stream.TokenStream;
import stream.TokenStreamInterface;
import tokenizers.TokenizerInterface;
import tokenizers.factories.TokenizerFactory;

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
    stringVarTokens =
        new TokenStream(
            List.of(
                tokenFactory.createLetKeywordToken(),
                tokenFactory.createIdentifierToken("aVar"),
                tokenFactory.createTypeAssignationToken(),
                tokenFactory.createTypeToken("String"),
                tokenFactory.createAssignationToken(),
                tokenFactory.createLiteralToken("\"aStr\""),
                tokenFactory.createEndOfLineToken()));
    numberVarInputs = List.of("let", "aVar", ":", "Number", "=", "9", ";");
    numberVarTokens =
        new TokenStream(
            List.of(
                tokenFactory.createLetKeywordToken(),
                tokenFactory.createIdentifierToken("aVar"),
                tokenFactory.createTypeAssignationToken(),
                tokenFactory.createTypeToken("Number"),
                tokenFactory.createAssignationToken(),
                tokenFactory.createLiteralToken("9"),
                tokenFactory.createEndOfLineToken()));
    invalidInputs = List.of("let", "aVar", ":", "Number", "=", "9asd", ";");
  }

  @Test
  public void createLexicalTest() {
    LexicalInterface lexical = new Lexical(tokenizer);
    Assertions.assertNotNull(lexical);
  }

  @Test
  public void analyzeLexicalTest() {
    LexicalInterface lexical = new Lexical(tokenizer);

    Result<TokenStreamInterface> analysisResult = lexical.analyze(stringVarInputs);
    Assertions.assertTrue(analysisResult.isSuccessful());
    TokenStreamInterface tokens = analysisResult.result();
    Assertions.assertEquals(stringVarTokens, tokens);

    analysisResult = lexical.analyze(numberVarInputs);
    Assertions.assertTrue(analysisResult.isSuccessful());
    tokens = analysisResult.result();
    Assertions.assertEquals(numberVarTokens, tokens);

    analysisResult = lexical.analyze(invalidInputs);
    Assertions.assertFalse(analysisResult.isSuccessful());
  }
}
