package syntactic.factories.builders;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AstBuilderFactoryTest {
    @Test
    public void createAstBuilderFactoryTest() {
        AstBuilderFactoryInterface astBuilderFactory = new AstBuilderFactory();
        Assertions.assertNotNull(astBuilderFactory);
    }
}
