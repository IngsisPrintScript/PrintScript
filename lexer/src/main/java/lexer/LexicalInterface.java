package lexer;

import common.PeekableIterator;
import common.TokenInterface;
import results.Result;
import stream.TokenStreamInterface;

import java.util.Iterator;
import java.util.List;

public interface LexicalInterface extends PeekableIterator<TokenInterface> {
    Result<TokenInterface> analyze(String word);
}
