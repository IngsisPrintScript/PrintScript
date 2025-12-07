/*
 * My Project
 */

package com.ingsis.parser.syntactic.parsers.operator.algorithms.shuntingyard.storage;

import com.ingsis.utils.result.Result;
import com.ingsis.utils.token.Token;
import java.util.Queue;

public interface ShuntingYardStorage {
    Result<ShuntingYardStorage> addToken(Token token);

    Queue<Token> postFixOrderedValues();
}
