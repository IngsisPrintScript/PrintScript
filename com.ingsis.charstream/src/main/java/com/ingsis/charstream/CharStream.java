/*
 * My Project
 */

package com.ingsis.charstream;

import com.ingsis.charstream.source.CharSource;
import com.ingsis.utils.iterator.safe.SafeIterator;
import com.ingsis.utils.iterator.safe.result.IterationResultFactory;
import com.ingsis.utils.iterator.safe.result.SafeIterationResult;
import com.ingsis.utils.metachar.MetaChar;

public final class CharStream implements SafeIterator<MetaChar> {
    private final CharSource source;
    private final int index;
    private final int line;
    private final int column;
    private final IterationResultFactory factory;

    public CharStream(CharSource source, IterationResultFactory factory) {
        this(source, 0, 1, 1, factory);
    }

    private CharStream(
            CharSource source, int index, int line, int column, IterationResultFactory factory) {
        this.source = source;
        this.index = index;
        this.line = line;
        this.column = column;
        this.factory = factory;
    }

    @Override
    public SafeIterationResult<MetaChar> next() {
        int raw = -1;
        try {
            raw = source.charAt(index);
        } catch (Exception e) {
            raw = -1;
        }

        if (raw == -1) {
            return factory.createIncorrectResult("No more chars");
        }

        char c = (char) raw;

        int newLine = line;
        int newColumn = column;

        if (c == '\n') {
            newLine++;
            newColumn = 1;
        } else {
            newColumn++;
        }

        return factory.createCorrectResult(
                new MetaChar(c, line, column),
                new CharStream(source, index + 1, newLine, newColumn, factory));
    }
}
