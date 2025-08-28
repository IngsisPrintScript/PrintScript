package parser.ast.builders;


import factories.tokens.TokenFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import parser.ast.builders.literal.LiteralBuilder;
import stream.TokenStream;
import stream.TokenStreamInterface;

import java.util.List;

public class LiteralBuilderTest {
    private static TokenStreamInterface validTokenStream;

    @BeforeAll
    public static void setUp() {
        TokenFactory tokenFactory = new TokenFactory();
        validTokenStream = new TokenStream(
                List.of(
                        tokenFactory.createLiteralToken("placeholder")
                )
        );
    }

    @Test
    public void createLiteralBuilderTest() {
        LiteralBuilder builder = new LiteralBuilder();
        Assertions.assertNotNull(builder);
    }

    @Test
    public void buildLiteralBuilderTest() {
        LiteralBuilder builder = new LiteralBuilder();
        Assertions.assertTrue(builder.build(validTokenStream).isSuccessful());
    }
}
