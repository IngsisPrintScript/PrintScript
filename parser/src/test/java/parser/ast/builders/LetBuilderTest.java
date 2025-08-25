package parser.ast.builders;

import common.factories.tokens.TokenFactory;
import common.tokens.stream.TokenStream;
import common.tokens.stream.TokenStreamInterface;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import parser.ast.builders.let.LetBuilder;

import java.util.List;

public class LetBuilderTest {
    private static List<TokenStreamInterface> validTokenStreams;
    private static List<TokenStreamInterface> invalidTokenStreams;

    @BeforeAll
    public static void setUp(){
        TokenFactory tokenFactory = new TokenFactory();
        TokenStreamInterface validTokenStreamOne = new TokenStream(
                List.of(
                        tokenFactory.createLetKeywordToken(),
                        tokenFactory.createIdentifierToken("id"),
                        tokenFactory.createTypeAssignationToken(),
                        tokenFactory.createTypeToken("type"),
                        tokenFactory.createEndOfLineToken()
                )
        );
        TokenStreamInterface validTokenStreamTwo = new TokenStream(
                List.of(
                        tokenFactory.createLetKeywordToken(),
                        tokenFactory.createIdentifierToken("id"),
                        tokenFactory.createTypeAssignationToken(),
                        tokenFactory.createTypeToken("type"),
                        tokenFactory.createAssignationToken(),
                        tokenFactory.createLiteralToken("\"literal\""),
                        tokenFactory.createEndOfLineToken()
                )
        );
        validTokenStreams = List.of(validTokenStreamOne, validTokenStreamTwo);
        TokenStreamInterface invalidTokenStreamOne = new TokenStream(
                List.of(
                        tokenFactory.createLetKeywordToken(),
                        tokenFactory.createIdentifierToken("id"),
                        tokenFactory.createAssignationToken(),
                        tokenFactory.createLiteralToken("\"literal\""),
                        tokenFactory.createEndOfLineToken()
                )
        );
        TokenStreamInterface invalidTokenStreamTwo = new TokenStream(
                List.of(
                        tokenFactory.createLetKeywordToken(),
                        tokenFactory.createIdentifierToken("id"),
                        tokenFactory.createTypeAssignationToken(),
                        tokenFactory.createTypeToken("type"),
                        tokenFactory.createAssignationToken(),
                        tokenFactory.createEndOfLineToken()
                )
        );
        TokenStreamInterface invalidTokenStreamThree = new TokenStream(
                List.of(
                        tokenFactory.createLetKeywordToken(),
                        tokenFactory.createIdentifierToken("id"),
                        tokenFactory.createTypeAssignationToken(),
                        tokenFactory.createTypeToken("type"),
                        tokenFactory.createAssignationToken(),
                        tokenFactory.createLiteralToken("\"literal\"")
                )
        );
        invalidTokenStreams = List.of(invalidTokenStreamOne, invalidTokenStreamTwo, invalidTokenStreamThree);
    }

    @Test
    public void createLetBuilderTest(){
        LetBuilder builder = new LetBuilder();
        Assertions.assertNotNull(builder);
    }
    @Test
    public void buildLetBuilderTest(){
        LetBuilder builder = new LetBuilder();
        for (TokenStreamInterface tokenStream : validTokenStreams) {
            Assertions.assertTrue(builder.build(tokenStream).isSuccessful());
        }
        for(TokenStreamInterface tokenStream : invalidTokenStreams){
            Assertions.assertFalse(builder.build(tokenStream).isSuccessful());
        }
    }
}
