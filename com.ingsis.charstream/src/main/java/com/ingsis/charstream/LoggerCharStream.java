package com.ingsis.charstream;

import java.lang.System.Logger;
import java.util.ResourceBundle;

import com.ingsis.utils.iterator.safe.SafeIterator;
import com.ingsis.utils.iterator.safe.result.SafeIterationResult;

public class LoggerCharStream implements SafeIterator<Character>, Logger {
  private LoggerCharStream charStream;

  @Override
  public String getName() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'getName'");
  }

  @Override
  public boolean isLoggable(Level level) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'isLoggable'");
  }

  @Override
  public void log(Level level, ResourceBundle bundle, String msg, Throwable thrown) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'log'");
  }

  @Override
  public void log(Level level, ResourceBundle bundle, String format, Object... params) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'log'");
  }

  @Override
  public SafeIterationResult<Character> next() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'next'");
  }

}
