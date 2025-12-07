package com.ingsis.parser.syntactic.parsers.operator.algorithms.shuntingyard;

import java.util.Queue;

import com.ingsis.utils.result.Result;
import com.ingsis.utils.token.Token;
import com.ingsis.utils.token.tokenstream.TokenStream;

public interface ShuntingYardTransformer {
  Result<Queue<Token>> transform(TokenStream stream);
}
