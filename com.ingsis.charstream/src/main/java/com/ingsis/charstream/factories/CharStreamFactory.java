/*
 * My Project
 */

package com.ingsis.charstream.factories;

import com.ingsis.charstream.CharStream;
import com.ingsis.utils.iterator.safe.SafeIterator;
import com.ingsis.utils.iterator.safe.factories.SafeIteratorFactory;
import com.ingsis.utils.iterator.safe.result.IterationResultFactory;
import com.ingsis.utils.metachar.MetaChar;
import java.io.InputStream;

public final class CharStreamFactory implements SafeIteratorFactory<MetaChar> {
    private final IterationResultFactory iterationResultFactory;

    public CharStreamFactory(IterationResultFactory iterationResultFactory) {
        this.iterationResultFactory = iterationResultFactory;
    }

    @Override
    public SafeIterator<MetaChar> fromInputStream(InputStream in) {
        return new CharStream(in, iterationResultFactory);
    }
}
