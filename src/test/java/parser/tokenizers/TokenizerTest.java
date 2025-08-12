package tokenizers;

import analyzers.lexic.tokenizers.TokenizerInterface;
import analyzers.lexic.tokenizers.factories.DefaultTokenizerFactory;
import analyzers.lexic.tokenizers.factories.TokenizerFactoryInterface;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import responses.CorrectResponse;
import responses.Response;
import token.TokenInterface;

import java.util.Map;

public class TokenizerTest {
    private static TokenizerFactoryInterface factory;

    @BeforeAll
    public static void setUp() {
        factory = new DefaultTokenizerFactory();
    }

    @Test
    public void defaultTokenizerCreationTest() {
        TokenizerInterface tokenizer = factory.createTokenizer();
        Assertions.assertNotNull(tokenizer);
    }

    @Test
    public void defaultTokenizerFunctionTest() {
        Map<String, String> stringToToken = Map.of(
                "let", "LET_KEYWORD_TOKEN",
                "variableName", "IDENTIFIER_TOKEN",
                ":", "TYPE_ASSIGNATION_TOKEN",
                "String", "STRING_TYPE_TOKEN",
                "=", "ASSIGNATION_TOKEN",
                "\"Literal\"", "LITERAL_TOKEN",
                ";", "EOL_TOKEN"
                );
        TokenizerInterface tokenizer = factory.createTokenizer();
        for (Map.Entry<String, String> entry : stringToToken.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            Response response = tokenizer.tokenize(key);
            Assertions.assertInstanceOf(CorrectResponse.class, response);
            Object object = ((CorrectResponse<?>) response).newObject();
            Assertions.assertInstanceOf(TokenInterface.class, object);
            TokenInterface token = (TokenInterface) object;
            Assertions.assertEquals(value, token.name());
        }
    }

}
