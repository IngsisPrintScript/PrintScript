package repositories;

import iterator.CodeIteratorInterface;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.NoSuchElementException;


public class CodeRepository implements CodeRepositoryInterface {
    private int index = 0;
    private final String content;

    public CodeRepository(Path path) {
        try {
            this.content = Files.readString(path);
        } catch (IOException e) {
            throw new RuntimeException("Error reading the file: " + path, e);
        }
    }

    @Override
    public Character peek() {
        if(!hasNext()){
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
        if(!hasNext()){
            throw new NoSuchElementException();
        }
        return content.charAt(index++);
    }
}
