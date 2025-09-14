/*
 * My Project
 */

package com.ingsis.printscript.lexer;

import com.ingsis.printscript.peekableiterator.PeekableIterator;
import com.ingsis.printscript.results.IncorrectResult;
import com.ingsis.printscript.results.Result;
import com.ingsis.printscript.tokenizers.TokenizerInterface;
import com.ingsis.printscript.tokens.TokenInterface;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;

public class Lexical implements LexicalInterface {
    private final TokenizerInterface tokenizer;
    private final PeekableIterator<Character> characterIterator;
    private final Queue<TokenInterface> tokenBuffer = new LinkedList<>();

    @SuppressFBWarnings("EI_EXPOSE_REP2")
    public Lexical(TokenizerInterface tokenizer, PeekableIterator<Character> characterIterator) {
        this.tokenizer = tokenizer;
        this.characterIterator = characterIterator;
    }

    @Override
    public Result<TokenInterface> analyze(String word) {
        Result<TokenInterface> result = tokenizer.tokenize(word);
        if (!result.isSuccessful()) {
            return new IncorrectResult<>(result.errorMessage());
        }
        return result;
    }

    @Override
    public boolean hasNext() {
        if (!tokenBuffer.isEmpty()) {
            return true;
        }

        TokenInterface nextToken = computeNext();
        if (nextToken != null) tokenBuffer.add(nextToken);

        return nextToken != null;
    }

    @Override
    public TokenInterface next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        return tokenBuffer.poll();
    }

    @Override
    public TokenInterface peek() {
        return tokenBuffer.peek();
    }

    private TokenInterface computeNext() {
        StringBuilder builder = new StringBuilder();
        TokenInterface candidateToken = null;
        while (characterIterator.hasNext()) {
            Character character = characterIterator.peek();
            if (character == null) {
                if (candidateToken != null) {
                    return candidateToken;
                }
                continue;
            }
            builder.append(character);
            String word = builder.toString();
            Result<TokenInterface> result = analyze(word);
            if (result.isSuccessful()) {
                candidateToken = result.result();
            } else if (candidateToken != null) {
                return candidateToken;
            }
            characterIterator.next();
        }
        return candidateToken;
    }
}
