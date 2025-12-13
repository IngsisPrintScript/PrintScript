/*
 * My Project
 */

package com.ingsis.utils.nodes.expressions;

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
    PLUS("+", 12, 11, new BinaryAddition()),
    MINUS("-", 12, 11, new BinarySubtraction()),
    STAR("*", 22, 21, new BinaryMultiplication()),
    SLASH("/", 22, 21, new BinaryDivision());

    private final String symbol;
    private final int lBindingPower;
    private final int rBindingPower;
    private final ExpressionStrategy strategy;

    OperatorType(String symbol, int lBindingPower, int rBindingPower, ExpressionStrategy strategy) {
        this.symbol = symbol;
        this.lBindingPower = lBindingPower;
        this.rBindingPower = rBindingPower;
        this.strategy = strategy;
    }

    public ExpressionStrategy strategy() {
        return strategy;
    }

    public String symbol() {
        return symbol;
    }

    public int lBindingPower() {
        return this.lBindingPower;
    }

    public int rBindingPower() {
        return this.rBindingPower;
    }

    public static Boolean isOperator(String symbol) {
        for (OperatorType type : values()) {
            if (type.symbol().equals(symbol)) {
                return true;
            }
        }
        return false;
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
