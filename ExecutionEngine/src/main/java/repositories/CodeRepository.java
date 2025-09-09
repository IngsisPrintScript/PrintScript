package repositories;

import iterator.CodeIteratorInterface;

import java.util.NoSuchElementException;

public record CodeRepository(
        CodeIteratorInterface codeIterator
) implements CodeRepositoryInterface {

    @Override
    public Character peek() {
        return null;
    }

    @Override
    public boolean hasNext() {
        return false;
    }

    @Override
    public Character next() {
        return null;
    }
}
