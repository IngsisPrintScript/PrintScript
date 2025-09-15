package com.ingsis.printscript;

import com.ingsis.printscript.repositories.CliRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;

class CliRepositoryTest {

    @Test
    void hasNextReturnsTrueWhenBufferNotEmpty() {
        Queue<Character> buffer = new LinkedList<>();
        buffer.add('a');
        CliRepository repo = new CliRepository(buffer);

        Assertions.assertTrue(repo.hasNext());
    }

    @Test
    void nextReturnsAndRemovesFirstCharacter() {
        Queue<Character> buffer = new LinkedList<>();
        buffer.add('a');
        buffer.add('b');
        CliRepository repo = new CliRepository(buffer);

        char first = repo.next();
        Assertions.assertEquals('a', first);
        Assertions.assertEquals('b', repo.peek());
    }

    @Test
    void peekReturnsFirstCharacterWithoutRemovingIt() {
        Queue<Character> buffer = new LinkedList<>();
        buffer.add('x');
        CliRepository repo = new CliRepository(buffer);

        char peeked = repo.peek();
        Assertions.assertEquals('x', peeked);
        Assertions.assertTrue(repo.hasNext());
        Assertions.assertEquals('x', repo.next());
    }

    @Test
    void nextThrowsWhenEmpty() {
        Queue<Character> buffer = new LinkedList<>();
        CliRepository repo = new CliRepository(buffer);

        Assertions.assertThrows(NoSuchElementException.class, repo::next);
    }

    @Test
    void peekThrowsWhenEmpty() {
        Queue<Character> buffer = new LinkedList<>();
        CliRepository repo = new CliRepository(buffer);

        Assertions.assertThrows(NoSuchElementException.class, repo::peek);
    }
}
