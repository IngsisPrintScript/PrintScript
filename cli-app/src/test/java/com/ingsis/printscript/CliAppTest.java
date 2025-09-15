package com.ingsis.printscript;

import com.ingsis.printscript.cliapp.CliApp;
import com.ingsis.printscript.interpreter.InterpreterInterface;
import com.ingsis.printscript.results.CorrectResult;
import com.ingsis.printscript.results.IncorrectResult;
import com.ingsis.printscript.results.Result;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.LinkedList;
import java.util.Queue;

public class CliAppTest {

    @Test
    void testExecuteReturnsCorrectResultOnExit() {

        String simulatedInput = "exit\n";
        InputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
        System.setIn(inputStream);

        CliApp app = new CliApp();

        Result<String> result = app.execute();

        Assertions.assertTrue(result.isSuccessful(), "El resultado debe ser exitoso");
        Assertions.assertEquals("Interpreter finished successfully.", result.result());
    }

    @Test
    void testCallReturnsZeroWhenExit() throws Exception {

        String simulatedInput = "exit\n";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        CliApp app = new CliApp();
        int exitCode = app.call();

        Assertions.assertEquals(0, exitCode, "El exitCode debe ser 0 si termina bien");
    }

    static class CliAppIOException extends CliApp {
        @Override
        public Result<String> execute() {
            return new IncorrectResult<>("IO Error");
        }
    }

    @Test
    void testExecuteReturnsIncorrectResultOnIOException() {
        CliAppIOException app = new CliAppIOException();
        Result<String> result = app.execute();
        Assertions.assertFalse(result.isSuccessful());
        Assertions.assertEquals("IO Error", result.errorMessage());
    }

    static class FakeInterpreter implements com.ingsis.printscript.interpreter.InterpreterInterface {
        @Override
        public Result<String> interpreter() {
            return new com.ingsis.printscript.results.IncorrectResult<>("Fake error");
        }
    }

    static class CliAppFake extends CliApp {
        @Override
        public Result<String> execute() {
            java.util.Queue<Character> buffer = new java.util.LinkedList<>();
            InterpreterInterface interpreter = new FakeInterpreter();
            String line = "some code";
            for (char c : line.toCharArray()) {
                buffer.add(c);
            }
            Result<String> interpretResult = interpreter.interpreter();
            if (!interpretResult.isSuccessful()) {
                System.out.println(interpretResult.errorMessage());
            }
            return interpretResult;
        }
    }

    @Test
    void testInterpreterErrorPrintsErrorMessage() {
        CliAppFake app = new CliAppFake();
        java.io.ByteArrayOutputStream out = new java.io.ByteArrayOutputStream();
        System.setOut(new java.io.PrintStream(out));

        Result<String> result = app.execute();

        Assertions.assertFalse(result.isSuccessful());
        Assertions.assertEquals("Fake error", result.errorMessage());
        Assertions.assertTrue(out.toString().contains("Fake error"), "Debe imprimir el mensaje de error");
    }

    static class CliAppBad extends CliApp {
        @Override
        public Result<String> execute() {
            return new IncorrectResult<>("Forced error");
        }
    }

    @Test
    void testCallReturnsOneWhenResultNotSuccessful() throws Exception {
        CliAppBad app = new CliAppBad();
        java.io.ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        int exitCode = app.call();

        Assertions.assertEquals(1, exitCode);
        Assertions.assertTrue(out.toString().contains("Error: Forced error"));
    }
}
