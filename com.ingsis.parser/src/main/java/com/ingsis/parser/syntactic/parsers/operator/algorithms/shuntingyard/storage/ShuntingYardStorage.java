package com.ingsis.parser.syntactic.parsers.operator.algorithms.shuntingyard.storage;

import java.util.Queue;

import com.ingsis.utils.result.Result;
import com.ingsis.utils.token.Token;

public interface ShuntingYardStorage {
  Result<ShuntingYardStorage> addToken(Token token);

  Queue<Token> postFixOrderedValues();
}
