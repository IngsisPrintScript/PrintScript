import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import analyzers.lexic.parsers.MockParser;
import analyzers.lexic.repositories.code.MockCodeRepository;
import responses.CorrectResponse;

import java.util.List;

public class LexicTest{

    @Test
    public void testSeparateWords(){
        MockCodeRepository mockCodeRepository = new MockCodeRepository("let number: String = \"Pepe\"; ");
        MockParser mockParser = new MockParser(mockCodeRepository);
        Assertions.assertTrue(mockParser.parse().isSuccessful());
        Assertions.assertInstanceOf(CorrectResponse.class, mockParser.parse());
        Assertions.assertEquals(mockParser.parse(), new CorrectResponse<>(List.of("let","number:","String","=","\"Pepe\""+";")));
    }

    @Test
    public void testLexicalAnalyzer(){
        MockCodeRepository mockCodeRepository = new MockCodeRepository(null);
        MockParser mockParser = new MockParser(mockCodeRepository);
        Assertions.assertFalse(mockParser.parse().isSuccessful());
    }

}
