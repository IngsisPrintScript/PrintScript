package com.ingsis.formatter;

import com.ingsis.formatter.rules.LinesAfterFunctionCall;
import com.ingsis.formatter.rules.TriviaRule;
import com.ingsis.utils.iterator.safe.SafeIterator;
import com.ingsis.utils.iterator.safe.result.IterationResultFactory;
import com.ingsis.utils.iterator.safe.result.SafeIterationResult;
import com.ingsis.utils.token.Token;
import com.ingsis.utils.token.type.TokenType;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class ProgramFormatter implements SafeIterator<String> {
  private final SafeIterator<Token> tokenStream;
  private final IterationResultFactory iterationResultFactory;
  private final List<TriviaRule> triviaRules;
  private final Optional<Token> previousToken;
  private final boolean isOnPrintln;
  private final LinesAfterFunctionCall linesAfterFunctionCall;
  private final Integer indentation;
  private final Integer depth;

  public ProgramFormatter(
          SafeIterator<Token> tokenStream,
          IterationResultFactory iterationResultFactory,
          List<TriviaRule> triviaRules,
          LinesAfterFunctionCall linesAfterFunctionCall,
          Integer indentation) {
    this.tokenStream = tokenStream;
    this.iterationResultFactory = iterationResultFactory;
    this.triviaRules = triviaRules;
    this.previousToken = Optional.empty();
    this.isOnPrintln = false;
    this.linesAfterFunctionCall = linesAfterFunctionCall;
    this.indentation = Objects.requireNonNullElse(indentation, 0);
    this.depth = 0;
  }

  private ProgramFormatter(
          SafeIterator<Token> tokenStream,
          IterationResultFactory iterationResultFactory,
          List<TriviaRule> triviaRules,
          LinesAfterFunctionCall linesAfterFunctionCall,
          Token previousToken,
          boolean isOnPrintln,
          Integer indentation,
          Integer depth) {
    this.tokenStream = tokenStream;
    this.iterationResultFactory = iterationResultFactory;
    this.triviaRules = triviaRules;
    this.linesAfterFunctionCall = linesAfterFunctionCall;
    this.previousToken = Optional.of(previousToken);
    this.isOnPrintln = isOnPrintln;
    this.indentation = indentation;
    this.depth = depth;
  }

  @Override
  public SafeIterationResult<String> next() {
    SafeIterationResult<Token> iterationResult = tokenStream.next();
    if (!iterationResult.isCorrect()) {
      return iterationResultFactory.cloneIncorrectResult(iterationResult);
    }

    Token token = iterationResult.iterationResult();
    int indentDepth = this.depth;
    if (token.type().equals(TokenType.RBRACE)) {
      indentDepth = Math.max(0, this.depth-1);
    }

    StringBuilder sb = new StringBuilder();
    if (previousToken.isEmpty()) {
      for (Token trivia : token.leadingTrivia()) {
        sb.append(trivia.value());
      }
      sb.append(token.value());
    } else {
      if (previousToken.get().type().equals(TokenType.SEMICOLON) && isOnPrintln) {
        linesAfterFunctionCall.apply(previousToken.get(), token.leadingTrivia(), token, sb, indentation * indentDepth);
      } else {
        for (TriviaRule triviaRule : triviaRules) {
          if (triviaRule.applies(previousToken.get(), token)) {
            triviaRule.apply(previousToken.get(), token.leadingTrivia(), token, sb, indentation * indentDepth);
            break;
          }
        }
      }
    }

    int newDepth = indentDepth;
    if (token.type().equals(TokenType.LBRACE)) newDepth++;

    boolean tempBool = isOnPrintln;
    if (!isOnPrintln) tempBool = token.value().equals("println");

    return iterationResultFactory.createCorrectResult(
            sb.toString(),
            new ProgramFormatter(
                    iterationResult.nextIterator(),
                    iterationResultFactory,
                    triviaRules,
                    linesAfterFunctionCall,
                    token,
                    tempBool,
                    indentation,
                    newDepth
            )
    );
  }
}
