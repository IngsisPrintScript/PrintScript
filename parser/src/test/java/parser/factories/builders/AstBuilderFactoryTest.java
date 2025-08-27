package parser.factories.builders;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import parser.factories.AstBuilderFactory;
import parser.factories.AstBuilderFactoryInterface;

import java.lang.reflect.Method;

public class AstBuilderFactoryTest {
    @Test
    public void createAstBuilderFactoryTest() {
        AstBuilderFactoryInterface astBuilderFactory = new AstBuilderFactory();
        Assertions.assertNotNull(astBuilderFactory);
    }

    @Test
    public void createAstBuilderFactoryMethodsTest() {
        AstBuilderFactoryInterface astBuilderFactory = new AstBuilderFactory();
        for (Method method : AstBuilderFactory.class.getDeclaredMethods()) {
            if (!method.getName().startsWith("create")) continue;
            try {
                Object result = method.invoke(astBuilderFactory);
                Assertions.assertNotNull(result);
            } catch (Exception e) {
                Assertions.fail(e.getMessage());
            }
        }
    }
}
