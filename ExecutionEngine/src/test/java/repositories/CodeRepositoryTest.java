package repositories;

import iterator.CodeIteratorInterface;
import iterator.MockCodeIterator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.util.List;

public class CodeRepositoryTest {
    private static CodeIteratorInterface iterator;
    private static List<String> code;

    @BeforeAll
    public static void setUp() {
        code = List.of("placeholder");
        iterator = new MockCodeIterator(code);
    }

//    @Test
//    public void createCodeRepositoryTest(){
//        CodeRepositoryInterface repository = new CodeRepository(iterator, );
//        Assertions.assertNotNull(repository);
//    }

//    @Test
//    public void functionsCodeRepositoryTest(){
//        CodeRepositoryInterface repository = new CodeRepository(iterator);
//        Assertions.assertTrue(repository.hasMoreCode());
//        Assertions.assertEquals(code.getFirst(), repository.nextChunkOfCode());
//        Assertions.assertFalse(repository.hasMoreCode());
//    }
}
