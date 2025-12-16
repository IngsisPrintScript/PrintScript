/*
 * My Project
 */

package com.ingsis.formatter;

import com.ingsis.formatter.rules.LinesAfterFunctionCall;
import com.ingsis.formatter.rules.TriviaRule;
import com.ingsis.utils.iterator.safe.SafeIterator;
import com.ingsis.utils.iterator.safe.result.IterationResultFactory;
import com.ingsis.utils.iterator.safe.result.SafeIterationResult;
import com.ingsis.utils.token.Token;
import com.ingsis.utils.token.type.TokenType;

import java.util.List;
import java.util.Optional;

public class ProgramFormatter implements SafeIterator<String> {
  private final SafeIterator<Token> tokenStream;
  private final IterationResultFactory iterationResultFactory;
  private final List<TriviaRule> triviaRules;
  private final Optional<Token> previousToken;
  private final boolean isOnPrintln;
  private final LinesAfterFunctionCall linesAfterFunctionCall;

  public ProgramFormatter(
      SafeIterator<Token> tokenStream,
      IterationResultFactory iterationResultFactory,
      List<TriviaRule> triviaRules,
      LinesAfterFunctionCall linesAfterFunctionCall) {
    this.tokenStream = tokenStream;
    this.iterationResultFactory = iterationResultFactory;
    this.triviaRules = triviaRules;
    this.previousToken = Optional.empty();
    this.isOnPrintln = false;
    this.linesAfterFunctionCall = linesAfterFunctionCall;
  }

  private ProgramFormatter(
      SafeIterator<Token> tokenStream,
      IterationResultFactory iterationResultFactory,
      List<TriviaRule> triviaRules,
      LinesAfterFunctionCall linesAfterFunctionCall,
      Token previousToken,
      boolean isOnPrintln) {
    this.tokenStream = tokenStream;
    this.iterationResultFactory = iterationResultFactory;
    this.triviaRules = triviaRules;
    this.linesAfterFunctionCall = linesAfterFunctionCall;
    this.previousToken = Optional.of(previousToken);
    this.isOnPrintln = isOnPrintln;
  }

  @Override
  public SafeIterationResult<String> next() {
    StringBuilder sb = new StringBuilder();
    SafeIterationResult<Token> iterationResult = tokenStream.next();
    if (!iterationResult.isCorrect()) {
      return iterationResultFactory.cloneIncorrectResult(iterationResult);
    }
    Token token = iterationResult.iterationResult();
    if (previousToken.isEmpty()) {
      for (Token trivia : token.leadingTrivia()) {
        sb.append(trivia.value());
      }
      sb.append(token.value());
    } else {
      if (previousToken.get().type().equals(TokenType.SEMICOLON) && isOnPrintln){
        linesAfterFunctionCall.apply(this.previousToken.get(), token.leadingTrivia(), token, sb);
        return iterationResultFactory.createCorrectResult(
                sb.toString(),
                new ProgramFormatter(
                        iterationResult.nextIterator(),
                        this.iterationResultFactory,
                        this.triviaRules,
                        this.linesAfterFunctionCall,
                        token,
                        false));
      }
      for (TriviaRule triviaRule : triviaRules) {
        if (triviaRule.appliea(this.previousToken.get(), token)) {
          triviaRule.apply(this.previousToken.get(), token.leadingTrivia(), token, sb);
          break;
        }
      }
    }
    boolean tempBool = isOnPrintln;
    if (!isOnPrintln){
      tempBool = token.value().equals("println");
    }
    return iterationResultFactory.createCorrectResult(
        sb.toString(),
        new ProgramFormatter(
            iterationResult.nextIterator(),
            this.iterationResultFactory,
            this.triviaRules,
            this.linesAfterFunctionCall,
            token,
                tempBool));
  }
}
