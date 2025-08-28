package parser.ast.builders;


import factories.tokens.TokenFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import parser.ast.builders.identifier.IdentifierBuilder;
import stream.TokenStream;
import stream.TokenStreamInterface;

import java.util.List;

public class IdentifierBuilderTest {
    private static TokenStreamInterface validTokenStream;

    @BeforeAll
    public static void setUp() {
        TokenFactory tokenFactory = new TokenFactory();
        validTokenStream = new TokenStream(
                List.of(
                        tokenFactory.createIdentifierToken("placeholder")
                )
        );
    }

    @Test
    public void createIdentifierBuilderTest() {
        IdentifierBuilder builder = new IdentifierBuilder();
        Assertions.assertNotNull(builder);
    }

    @Test
    public void buildIdentifierBuilderTest() {
        IdentifierBuilder builder = new IdentifierBuilder();
        Assertions.assertTrue(builder.build(validTokenStream).isSuccessful());
    }
}
