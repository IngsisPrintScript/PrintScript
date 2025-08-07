import analyzers.Analyzer;
import analyzers.lexic.LexicalAnalyzer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import parsers.MockParser;
import repositories.MockCodeRepository;
import responses.CorrectResponse;
import token.Token;

import java.util.List;
import java.util.Objects;

public class LexicTest{

    @Test
    public void testSeparateWords(){
        MockCodeRepository mockCodeRepository = new MockCodeRepository("let number: String = \"Pepe\"; ");
        MockParser mockParser = new MockParser(mockCodeRepository);
        Assertions.assertTrue(mockParser.parse().isSuccessful());
        Assertions.assertInstanceOf(CorrectResponse.class, mockParser.parse());
        Assertions.assertEquals(mockParser.parse(), new CorrectResponse<>(List.of("let","number:","String","=","\"Pepe\";")));
    }

    @Test
    public void testLexicalAnalyzer(){
        MockCodeRepository mockCodeRepository = new MockCodeRepository(null);
        MockParser mockParser = new MockParser(mockCodeRepository);
        Assertions.assertFalse(mockParser.parse().isSuccessful());
    }

}
