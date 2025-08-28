package parser.ast.builders;


import factories.tokens.TokenFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import parser.ast.builders.type.TypeBuilder;
import stream.TokenStream;
import stream.TokenStreamInterface;

import java.util.List;

public class TypeBuilderTest {
    private static TokenStreamInterface validTokenStream;

    @BeforeAll
    public static void setUp() {
        TokenFactory tokenFactory = new TokenFactory();
        validTokenStream = new TokenStream(
                List.of(
                        tokenFactory.createTypeToken("placeholder")
                )
        );
    }

    @Test
    public void createTypeBuilderTest() {
        TypeBuilder builder = new TypeBuilder();
        Assertions.assertNotNull(builder);
    }

    @Test
    public void buildTypeBuilderTest() {
        TypeBuilder builder = new TypeBuilder();
        Assertions.assertTrue(builder.build(validTokenStream).isSuccessful());
    }
}
