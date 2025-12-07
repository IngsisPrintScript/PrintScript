package com.ingsis.parser.syntactic.parsers.operator.algorithms.shuntingyard.storage;

import java.util.Deque;
import java.util.Queue;

import com.ingsis.utils.token.Token;

public record PopData(Deque<Token> newStack, Queue<Token> newValues) {
}
