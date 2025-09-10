package lexer;


import common.PeekableIterator;
import common.TokenInterface;
import results.IncorrectResult;
import results.Result;
import tokenizers.TokenizerInterface;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Lexical implements LexicalInterface {
    private final TokenizerInterface tokenizer;
    private final PeekableIterator<Character> characterIterator;
    private final Queue<TokenInterface> tokenBuffer = new LinkedList<>();

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

    private TokenInterface computeNext(){
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
