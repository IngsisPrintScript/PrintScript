package lexer;


import common.TokenInterface;
import parser.Reader;
import results.CorrectResult;
import results.IncorrectResult;
import results.Result;
import tokenizers.TokenizerInterface;
import stream.TokenStream;
import stream.TokenStreamInterface;

import java.nio.Buffer;
import java.nio.CharBuffer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.BlockingQueue;

public class Lexical implements LexicalInterface {
    private final TokenizerInterface tokenizer;
    private final Iterator<Character> characterIterator;
    private BlockingQueue<TokenInterface> tokenBuffer;

    public Lexical(TokenizerInterface tokenizer, Iterator<Character> characterIterator) {
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
        if (!characterIterator.hasNext()) {
            return false;
        }
        List<Character> charBuffer = new ArrayList<>();
        while (characterIterator.hasNext()) {
            charBuffer.add(characterIterator.next());
            StringBuilder builder = new StringBuilder();
            for(Character c : charBuffer) {
                builder.append(c);
            }
            String word = builder.toString();
            Result<TokenInterface> result = tokenizer.tokenize(word);
            if (result.isSuccessful()) {
                tokenBuffer.add(result.result());
                return true;
            }
        }
        return false;
    }

    @Override
    public TokenInterface next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        return tokenBuffer.poll();
    }
}
