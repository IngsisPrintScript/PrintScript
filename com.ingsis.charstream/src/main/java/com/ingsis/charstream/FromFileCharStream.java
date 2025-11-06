/*
 * My Project
 */

package com.ingsis.charstream;

import com.ingsis.peekableiterator.PeekableIterator;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedList;

public final class FromFileCharStream implements PeekableIterator<Character>, AutoCloseable {
  private final BufferedReader reader;
  private final InMemoryCharStream buffer;
  private boolean endOfFile;

  public FromFileCharStream(Path path) throws IOException {
    reader = Files.newBufferedReader(path);
    buffer = new InMemoryCharStream(new LinkedList<>());
    endOfFile = false;
  }

  private void fillBuffer() {
    if (!endOfFile) {
      try {
        int ch = reader.read();
        if (ch == -1) {
          endOfFile = true;
          reader.close();
        } else {
          buffer.addChar((char) ch);
        }
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    }
  }

  @Override
  public Character peek() {
    if (!buffer.hasNext()) {
      fillBuffer();
    }
    return buffer.peek();
  }

  @Override
  public boolean hasNext() {
    if (!buffer.hasNext()) {
      fillBuffer();
    }
    return buffer.hasNext();
  }

  @Override
  public Character next() {
    if (!buffer.hasNext()) {
      fillBuffer();
    }
    return buffer.next();
  }

  @Override
  public void close() throws Exception {
    reader.close();
  }
}
