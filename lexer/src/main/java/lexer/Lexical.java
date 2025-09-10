package lexer;


import common.PeekableIterator;
import common.TokenInterface;
import results.IncorrectResult;
import results.Result;
import tokenizers.TokenizerInterface;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Lexical implements LexicalInterface {
    private final TokenizerInterface tokenizer;
    private final PeekableIterator<Character> characterIterator;
    private final BlockingQueue<TokenInterface> tokenBuffer = new LinkedBlockingQueue<>();

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

        List<Character> charBuffer = new ArrayList<>();
        TokenInterface candidateToken = null;

        while (characterIterator.hasNext()) {

            Character character = characterIterator.peek();

            if (character == null) {
                if (candidateToken != null) {
                    tokenBuffer.add(candidateToken);
                    return true;
                }
                return false;
            }

            charBuffer.add(character);

            StringBuilder builder = new StringBuilder();
            for (Character c : charBuffer) {
                builder.append(c);
                String word = builder.toString();
                Result<TokenInterface> result = analyze(word);
                if (result.isSuccessful()) {
                    candidateToken = result.result();
                } else if (candidateToken != null) {
                    tokenBuffer.add(candidateToken);
                    return true;
                }
            }

            characterIterator.next();
        }
        if (candidateToken != null) {
            tokenBuffer.add(candidateToken);
        }
        return candidateToken != null || !tokenBuffer.isEmpty();
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
}
