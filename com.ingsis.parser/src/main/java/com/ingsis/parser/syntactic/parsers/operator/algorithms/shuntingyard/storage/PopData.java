/*
 * My Project
 */

package com.ingsis.parser.syntactic.parsers.operator.algorithms.shuntingyard.storage;

import com.ingsis.utils.token.Token;
import java.util.Deque;
import java.util.Queue;

public record PopData(Deque<Token> newStack, Queue<Token> newValues) {}
