package iterator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class CodeIteratorTest {
    private static String code;

    @BeforeAll
    static void setUp() {
        code = "placeholder";
    }

    @Test
    public void createIteratorTest() {
        CodeIteratorInterface iterator = new MockCodeIterator(code);
        Assertions.assertNotNull(iterator);
    }

    @Test
    public void useIteratorTest() {
        CodeIteratorInterface iterator = new MockCodeIterator(code);
        Assertions.assertTrue(iterator.hasNext());
        Assertions.assertEquals(code, iterator.next());
        Assertions.assertFalse(iterator.hasNext());
    }
}
