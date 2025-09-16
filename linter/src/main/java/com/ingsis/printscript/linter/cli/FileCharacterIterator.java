package com.ingsis.printscript.linter.cli;

import com.ingsis.printscript.peekableiterator.PeekableIterator;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.NoSuchElementException;

public final class FileCharacterIterator implements PeekableIterator<Character> {
    private final String content;
    private int index = 0;

    public FileCharacterIterator(Path path) {
        String text;
        try {
            text = Files.readString(path);
        } catch (IOException e) {
            text = "";
        }
        this.content = text;
    }

    @Override
    public boolean hasNext() {
        return index < content.length();
    }

    @Override
    public Character next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        return content.charAt(index++);
    }

    @Override
    public Character peek() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        return content.charAt(index);
    }
}


