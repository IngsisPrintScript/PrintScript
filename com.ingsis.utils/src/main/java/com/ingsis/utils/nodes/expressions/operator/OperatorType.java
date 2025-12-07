package com.ingsis.utils.nodes.expressions.operator;

import com.ingsis.utils.nodes.expressions.strategies.BinaryAddition;
import com.ingsis.utils.nodes.expressions.strategies.BinaryDivision;
import com.ingsis.utils.nodes.expressions.strategies.BinaryMultiplication;
import com.ingsis.utils.nodes.expressions.strategies.BinarySubtraction;
import com.ingsis.utils.nodes.expressions.strategies.ExpressionStrategy;
import com.ingsis.utils.result.CorrectResult;
import com.ingsis.utils.result.IncorrectResult;
import com.ingsis.utils.result.Result;

public enum OperatorType {
  // Binary operators
  PLUS("+", 10, Associativity.LEFT, 2, new BinaryAddition()),
  MINUS("-", 10, Associativity.LEFT, 2, new BinarySubtraction()),
  STAR("*", 20, Associativity.LEFT, 2, new BinaryMultiplication()),
  SLASH("/", 20, Associativity.LEFT, 2, new BinaryDivision());

  private final String symbol;
  private final int precedence;
  private final Associativity associativity;
  private final int arity;
  private final ExpressionStrategy strategy;

  OperatorType(String symbol, int precedence, Associativity associativity, int arity, ExpressionStrategy strategy) {
    this.symbol = symbol;
    this.precedence = precedence;
    this.associativity = associativity;
    this.arity = arity;
    this.strategy = strategy;
  }

  public ExpressionStrategy strategy() {
    return strategy;
  }

  public String symbol() {
    return symbol;
  }

  public int precedence() {
    return precedence;
  }

  public Associativity associativity() {
    return associativity;
  }

  public int arity() {
    return arity;
  }

  public enum Associativity {
    LEFT, RIGHT
  }

  public static Result<OperatorType> fromSymbol(String symbol) {
    for (OperatorType type : values()) {
      if (type.symbol().equals(symbol)) {
        return new CorrectResult<>(type);
      }
    }
    return new IncorrectResult<>("No operator type for symbol: " + symbol);
  }
}
