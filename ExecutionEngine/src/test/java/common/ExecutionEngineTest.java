/*package common;


import factories.DefaultEngineFactory;
import iterator.CodeIteratorInterface;
import iterator.MockCodeIterator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import repositories.CodeRepository;
import repositories.CodeRepositoryInterface;

import java.util.List;

public class ExecutionEngineTest {
    private static CodeRepositoryInterface repository;


    @BeforeAll
    static void setUp() {
        List<String> code = List.of(
                "let varOne:String = \"varA\";",
                "let varTwo:String = \"varB\";",
                "println(varOne + varTwo);"
        );
        CodeIteratorInterface iterator = new MockCodeIterator(code);
        repository = new CodeRepository(iterator);
    }

    @Test
    public void createExecutionEngineTest() {
        ExecutionEngineInterface engine = new DefaultEngineFactory().getInterpreterEngine(repository);
        Assertions.assertNotNull(engine);
    }

    @Test
    public void executeExecutionEngineTest(){
        ExecutionEngineInterface engine = new DefaultEngineFactory().getInterpreterEngine(repository);
        Assertions.assertTrue(engine.execute().isSuccessful());
    }
}

 */
