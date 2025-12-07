/*
 * My Project
 */

package com.ingsis.charstream;

import com.ingsis.utils.iterator.safe.SafeIterator;
import com.ingsis.utils.iterator.safe.result.IterationResultFactory;
import com.ingsis.utils.iterator.safe.result.SafeIterationResult;
import com.ingsis.utils.metachar.MetaChar;
import java.io.IOException;
import java.io.InputStream;
import java.io.PushbackInputStream;

public final class CharStream implements SafeIterator<MetaChar> {
    private final PushbackInputStream in;
    private final int line;
    private final int column;
    private final boolean closed;
    private final IterationResultFactory iterationResultFactory;

    public CharStream(
            InputStream in,
            IterationResultFactory iterationResultFactory,
            Integer line,
            Integer column,
            Boolean closed) {
        this.in = new PushbackInputStream(in, 1);
        this.iterationResultFactory = iterationResultFactory;
        this.line = line;
        this.column = column;
        this.closed = closed;
    }

    public CharStream(InputStream in, IterationResultFactory iterationResultFactory) {
        this(in, iterationResultFactory, 0, 0, false);
    }

    @Override
    public SafeIterationResult<MetaChar> next() {

        if (closed) {
            return iterationResultFactory.createIncorrectResult(
                    "There are no more characters to read.");
        }

        try {
            int raw = in.read();

            if (raw == -1) {
                return iterationResultFactory.createIncorrectResult("End of input reached.");
            }

            char ch = (char) raw;

            int newLine = line;
            int newColumn = column;

            if (ch == '\n') {
                newLine++;
                newColumn = 0;
            } else {
                newColumn++;
            }

            MetaChar metaChar = new MetaChar(ch, newLine, newColumn);

            CharStream nextStream = new CharStream(in, iterationResultFactory);

            return iterationResultFactory.createCorrectResult(metaChar, nextStream);

        } catch (IOException ex) {
            return iterationResultFactory.createIncorrectResult(
                    "Error while reading character: " + ex.getMessage());
        }
    }
}
