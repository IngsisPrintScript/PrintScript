/*
 * My Project
 */

package com.ingsis.lexer.tokenizers;

import java.util.List;

import com.ingsis.utils.process.result.ProcessResult;
import com.ingsis.utils.token.Token;

public interface Tokenizer {
  ProcessResult<Token> tokenize(String input, List<Token> trailingTrivia, Integer line, Integer column);
}
