package repositories;

import iterator.CodeIteratorInterface;
import java.util.NoSuchElementException;

public record CodeRepository(CodeIteratorInterface codeIterator)
    implements CodeRepositoryInterface {
  @Override
  public Boolean hasMoreCode() {
    return codeIterator().hasNext();
  }

  @Override
  public String nextChunkOfCode() {
    if (codeIterator().hasNext()) {
      return codeIterator().next();
    } else {
      throw new NoSuchElementException();
    }
  }
}
