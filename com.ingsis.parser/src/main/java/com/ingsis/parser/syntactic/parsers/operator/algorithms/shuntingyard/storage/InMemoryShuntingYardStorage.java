/*
 * My Project
 */

package com.ingsis.parser.syntactic.parsers.operator.algorithms.shuntingyard.storage;

import com.ingsis.utils.nodes.expressions.operator.OperatorType;
import com.ingsis.utils.result.CorrectResult;
import com.ingsis.utils.result.IncorrectResult;
import com.ingsis.utils.result.Result;
import com.ingsis.utils.token.Token;
import com.ingsis.utils.token.template.TokenTemplate;
import com.ingsis.utils.token.type.TokenType;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Queue;

public class InMemoryShuntingYardStorage implements ShuntingYardStorage {
    private final Deque<Token> operatorsStack;
    private final Queue<Token> valuesQueue;
    private final TokenTemplate leftParenthesisTemplate;
    private final TokenTemplate rightParenthesisTemplate;

    public InMemoryShuntingYardStorage(
            TokenTemplate leftParenthesisTemplate, TokenTemplate rightParenthesisTemplate) {
        this(
                new ArrayDeque<Token>(),
                new ArrayDeque<>(),
                leftParenthesisTemplate,
                rightParenthesisTemplate);
    }

    private InMemoryShuntingYardStorage(
            Deque<Token> operatorsStack,
            Queue<Token> valuesQueue,
            TokenTemplate leftParenthesisTemplate,
            TokenTemplate rightParenthesisTemplate) {
        this.operatorsStack = new ArrayDeque<>(operatorsStack);
        this.valuesQueue = new ArrayDeque<>(valuesQueue);
        this.leftParenthesisTemplate = leftParenthesisTemplate;
        this.rightParenthesisTemplate = rightParenthesisTemplate;
    }

    @Override
    public Result<ShuntingYardStorage> addToken(Token token) {
        if (TokenType.OPERATORS.contains(token.type())) {
            return withOperator(token);
        } else if (leftParenthesisTemplate.matches(token)) {
            return withLeftParenthesis(token);
        } else if (rightParenthesisTemplate.matches(token)) {
            return withRightParenthesis();
        } else {
            return withAtomic(token);
        }
    }

    private Result<ShuntingYardStorage> createWithNewOperator(Token token) {
        Deque<Token> newOperatorsStack = new ArrayDeque<>(operatorsStack);
        newOperatorsStack.push(token);
        return new CorrectResult<>(
                new InMemoryShuntingYardStorage(
                        newOperatorsStack,
                        this.valuesQueue,
                        this.leftParenthesisTemplate,
                        this.rightParenthesisTemplate));
    }

    private Result<PopData> popHigherOrEqualPrecedenceOperators(
            Deque<Token> originalStack, Queue<Token> originalValues, OperatorType newOperatorType) {
        Deque<Token> stackCopy = new ArrayDeque<>(originalStack);
        Queue<Token> valuesCopy = new ArrayDeque<>(originalValues);
        while (!stackCopy.isEmpty()) {
            Token stackTop = stackCopy.peek();
            Result<OperatorType> getStackTopTypeResult = OperatorType.fromSymbol(stackTop.value());
            if (!getStackTopTypeResult.isCorrect()) {
                return new IncorrectResult<>(getStackTopTypeResult);
            }
            OperatorType stackTopType = getStackTopTypeResult.result();
            if (stackTopType.precedence() >= newOperatorType.precedence()) {
                valuesCopy.add(stackCopy.pop());
            } else {
                break;
            }
        }
        return new CorrectResult<>(new PopData(stackCopy, valuesCopy));
    }

    private Result<ShuntingYardStorage> withStackAndQueu(
            Deque<Token> operators, Queue<Token> values) {
        return new CorrectResult<>(
                new InMemoryShuntingYardStorage(
                        operators,
                        values,
                        this.leftParenthesisTemplate,
                        this.rightParenthesisTemplate));
    }

    private Result<ShuntingYardStorage> withOperator(Token token) {
        if (operatorsStack.isEmpty()) {
            return createWithNewOperator(token);
        }
        Result<OperatorType> getNewOperatorTypeResult = OperatorType.fromSymbol(token.value());
        if (!getNewOperatorTypeResult.isCorrect()) {
            return new IncorrectResult<>(getNewOperatorTypeResult);
        }
        OperatorType newOperatorType = getNewOperatorTypeResult.result();
        Result<PopData> popResult =
                popHigherOrEqualPrecedenceOperators(operatorsStack, valuesQueue, newOperatorType);
        if (!popResult.isCorrect()) {
            return new IncorrectResult<>(popResult);
        }
        PopData popData = popResult.result();
        Deque<Token> newStack = popData.newStack();
        newStack.push(token);
        return withStackAndQueu(newStack, popData.newValues());
    }

    private Result<ShuntingYardStorage> withLeftParenthesis(Token token) {
        return createWithNewOperator(token);
    }

    private Result<ShuntingYardStorage> withRightParenthesis() {
        Result<PopData> popResult = popUntilLeftParenthesis(operatorsStack, valuesQueue);
        if (!popResult.isCorrect()) {
            return new IncorrectResult<>(popResult.error());
        }
        PopData popData = popResult.result();
        return withStackAndQueu(popData.newStack(), popData.newValues());
    }

    private Result<PopData> popUntilLeftParenthesis(
            Deque<Token> originalStack, Queue<Token> originalValues) {
        Deque<Token> stackCopy = new ArrayDeque<>(originalStack);
        Queue<Token> valuesCopy = new ArrayDeque<>(originalValues);

        while (!stackCopy.isEmpty()) {
            Token token = stackCopy.pop();
            if (leftParenthesisTemplate.matches(token)) {
                return new CorrectResult<>(new PopData(stackCopy, valuesCopy));
            } else {
                valuesCopy.add(token);
            }
        }

        return new IncorrectResult<>("Mismatched parentheses: no left parenthesis found");
    }

    private Result<ShuntingYardStorage> withAtomic(Token token) {
        Queue<Token> newValuesQueue = new ArrayDeque<>(valuesQueue);
        newValuesQueue.add(token);
        return new CorrectResult<>(
                new InMemoryShuntingYardStorage(
                        this.operatorsStack,
                        newValuesQueue,
                        this.leftParenthesisTemplate,
                        this.rightParenthesisTemplate));
    }

    @Override
    public Queue<Token> postFixOrderedValues() {
        Queue<Token> resultQueue = new ArrayDeque<>(valuesQueue);
        while (!operatorsStack.isEmpty()) {
            resultQueue.add(operatorsStack.pop());
        }
        return resultQueue;
    }
}
