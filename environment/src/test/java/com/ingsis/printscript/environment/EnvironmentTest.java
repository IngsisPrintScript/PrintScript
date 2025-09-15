package com.ingsis.printscript.environment;

import com.ingsis.printscript.results.CorrectResult;
import com.ingsis.printscript.results.IncorrectResult;
import com.ingsis.printscript.results.Result;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class EnvironmentTest {

    private Environment env;

    @BeforeEach
    void setUp() {
        env = Environment.getInstance();
        env.clearTypeMap();
        env.clearValueMap();
    }

    @Test
    void testSingletonInstance() {
        Environment another = Environment.getInstance();
        Assertions.assertSame(env, another, "Environment should be singleton");
    }

    @Test
    void testPutAndGetIdType() {
        Result<String> result = env.putIdType("x", "int");
        Assertions.assertInstanceOf(CorrectResult.class, result);
        Assertions.assertEquals("Id has been declared.", result.result());

        Result<String> typeResult = env.getIdType("x");
        Assertions.assertInstanceOf(CorrectResult.class, typeResult);
        Assertions.assertEquals("int", typeResult.result());
    }

    @Test
    void testPutIdTypeAlreadyDeclared() {
        env.putIdType("x", "int");
        Result<String> result = env.putIdType("x", "string");
        Assertions.assertInstanceOf(IncorrectResult.class, result);
        Assertions.assertEquals("Id has already been declared.", result.errorMessage());
    }

    @Test
    void testPutAndGetIdValue() {
        env.putIdType("y", "string");
        Result<Object> result = env.putIdValue("y", "hello");
        Assertions.assertInstanceOf(CorrectResult.class, result);
        Assertions.assertEquals("hello", result.result());

        Result<Object> valueResult = env.getIdValue("y");
        Assertions.assertInstanceOf(CorrectResult.class, valueResult);
        Assertions.assertEquals("hello", valueResult.result());
    }

    @Test
    void testPutIdValueWithoutDeclaration() {
        Result<Object> result = env.putIdValue("z", 123);
        Assertions.assertInstanceOf(IncorrectResult.class, result);
        Assertions.assertEquals("Id has not been declared.", result.errorMessage());
    }

    @Test
    void testGetIdTypeWithoutDeclaration() {
        Result<String> result = env.getIdType("a");
        Assertions.assertInstanceOf(IncorrectResult.class, result);
        Assertions.assertEquals("Id has not been declared.", result.errorMessage());
    }

    @Test
    void testGetIdValueWithoutDeclaration() {
        Result<Object> result = env.getIdValue("b");
        Assertions.assertInstanceOf(IncorrectResult.class, result);
        Assertions.assertEquals("Id has not been declared.", result.errorMessage());
    }

    @Test
    void testClearTypeMap() {
        env.putIdType("x", "int");
        env.clearTypeMap();
        Assertions.assertFalse(env.variableIsDeclared("x"));
    }

    @Test
    void testClearValueMap() {
        env.putIdType("x", "int");
        env.putIdValue("x", 42);
        env.clearValueMap();
        Result<Object> result = env.getIdValue("x");
        Assertions.assertInstanceOf(CorrectResult.class, result);
        Assertions.assertNull(result.result(), "Value map should be cleared");
    }

}
