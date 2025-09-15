package com.ingsis.printscript;

import com.ingsis.printscript.repositories.CodeRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.NoSuchElementException;

public class CodeRepositoryTest {

    @TempDir
    Path tempDir;

    private Path testFile;
    private final String content = "abc";

    @BeforeEach
    void setUp() throws IOException {
        testFile = tempDir.resolve("test.txt");
        Files.writeString(testFile, content);
    }

    @Test
    void testHasNext() {
        CodeRepository repo = new CodeRepository(testFile);
       Assertions.assertTrue(repo.hasNext());
        repo.next();
        repo.next();
        repo.next();
        Assertions.assertFalse(repo.hasNext());
    }

    @Test
    void testPeek() {
        CodeRepository repo = new CodeRepository(testFile);
        Assertions.assertEquals('a', repo.peek());
        Assertions.assertEquals('a', repo.peek()); // peek no avanza
        repo.next();
        Assertions.assertEquals('b', repo.peek());
    }

    @Test
    void testNext() {
        CodeRepository repo = new CodeRepository(testFile);
        Assertions.assertEquals('a', repo.next());
        Assertions.assertEquals('b', repo.next());
        Assertions.assertEquals('c', repo.next());
        Assertions.assertFalse(repo.hasNext());
    }

    @Test
    void testNextThrowsExceptionWhenEmpty() {
        CodeRepository repo = new CodeRepository(testFile);
        repo.next();
        repo.next();
        repo.next();
        Assertions.assertThrows(NoSuchElementException.class, repo::next);
    }

    @Test
    void testPeekThrowsExceptionWhenEmpty() {
        CodeRepository repo = new CodeRepository(testFile);
        repo.next();
        repo.next();
        repo.next();
        Assertions.assertThrows(NoSuchElementException.class, repo::peek);
    }

    @Test
    void testFileNotFoundThrowsRuntimeException() {
        Path invalidPath = tempDir.resolve("nofile.txt");
        RuntimeException ex = Assertions.assertThrows(RuntimeException.class, () -> new CodeRepository(invalidPath));
        Assertions.assertTrue(ex.getMessage().contains("Error reading the file"));
    }

}
