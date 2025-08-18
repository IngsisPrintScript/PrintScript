package syntactic.ast.builders;

import common.tokens.stream.TokenStream;
import common.tokens.stream.TokenStreamInterface;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

public class FinalBuilderTest {
    private static TokenStreamInterface validTokenStream;

    @BeforeAll
    public static void setUp(){
        validTokenStream = new TokenStream(
                List.of()
        );
    }

    @Test
    public void createTypeBuilderTest(){
        FinalBuilder builder = new FinalBuilder();
        Assertions.assertNotNull(builder);
    }
    @Test
    public void buildTypeBuilderTest(){
        FinalBuilder builder = new FinalBuilder();
        Assertions.assertFalse(builder.build(validTokenStream).isSuccessful());
    }
}
