/*
 * My Project
 */

package com.ingsis.parser.syntactic.parsers.operator.algorithms.shuntingyard;

import com.ingsis.parser.syntactic.parsers.operator.algorithms.shuntingyard.storage.ShuntingYardStorage;
import com.ingsis.utils.result.CorrectResult;
import com.ingsis.utils.result.IncorrectResult;
import com.ingsis.utils.result.Result;
import com.ingsis.utils.token.Token;
import com.ingsis.utils.token.tokenstream.TokenStream;
import java.util.Queue;

public class DefaultShuntingYardTransformer implements ShuntingYardTransformer {
    private final ShuntingYardStorage storage;

    public DefaultShuntingYardTransformer(ShuntingYardStorage storage) {
        this.storage = storage;
    }

    @Override
    public Result<Queue<Token>> transform(TokenStream stream) {
        ShuntingYardStorage tempStorage = storage;
        for (Token token : stream.tokens()) {
            Result<ShuntingYardStorage> addTokenResult = tempStorage.addToken(token);
            if (!addTokenResult.isCorrect()) {
                return new IncorrectResult<>(addTokenResult);
            }
            tempStorage = addTokenResult.result();
        }
        return new CorrectResult<Queue<Token>>(tempStorage.postFixOrderedValues());
    }
}
