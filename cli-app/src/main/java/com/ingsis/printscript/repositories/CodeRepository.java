/*
 * My Project
 */

package com.ingsis.printscript.repositories;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.NoSuchElementException;

public class CodeRepository implements CodeRepositoryInterface {
    private int index = 0;
    private final String content;

    public CodeRepository(Path path) {
        String content;
        try {
            content = Files.readString(path);
        } catch (IOException e) {
            content = "";
        }
        this.content = content;
    }

    @Override
    public Character peek() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        return content.charAt(index);
    }

    @Override
    public boolean hasNext() {
        return content.length() > index;
    }

    @Override
    public Character next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        return content.charAt(index++);
    }
}
