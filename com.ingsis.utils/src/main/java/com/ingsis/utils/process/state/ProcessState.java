package com.ingsis.utils.process.state;

public enum ProcessState {
  /**
   * The current accumulated characters form a complete, valid token.
   */
  COMPLETE,

  /**
   * The current accumulated characters could become a valid token
   * if more characters are read.
   */
  PREFIX,

  /**
   * The current accumulated characters are invalid for this token.
   */
  INVALID
}
