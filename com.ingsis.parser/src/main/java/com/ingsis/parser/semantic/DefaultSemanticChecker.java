/*
 * My Project
 */

package com.ingsis.parser.semantic;

import com.ingsis.runtime.Runtime;
import com.ingsis.utils.nodes.visitors.Checkable;
import com.ingsis.utils.nodes.visitors.Checker;
import com.ingsis.utils.nodes.visitors.Interpretable;
import com.ingsis.utils.peekableiterator.PeekableIterator;
import com.ingsis.utils.result.CorrectResult;
import com.ingsis.utils.result.IncorrectResult;
import com.ingsis.utils.result.Result;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;

@SuppressFBWarnings("EI_EXPOSE_REP2")
public final class DefaultSemanticChecker implements SemanticChecker {
    private final PeekableIterator<Checkable> checkableStream;
    private final Checker checker;
    private final Runtime runtime;
    private final Queue<Interpretable> interpretableBuffer;

    public DefaultSemanticChecker(
            PeekableIterator<Checkable> checkableStream,
            Checker checker,
            Runtime runtime,
            Queue<Interpretable> interpretableBuffer) {
        this.checkableStream = checkableStream;
        this.checker = checker;
        this.runtime = runtime;
        this.interpretableBuffer = new LinkedList<>(interpretableBuffer);
    }

    public DefaultSemanticChecker(
            PeekableIterator<Checkable> checkableStream, Checker checker, Runtime runtime) {
        this(checkableStream, checker, runtime, new LinkedList<>());
    }

    @Override
    public Result<Interpretable> parse() {
        runtime.push();
        Checkable checkable = checkableStream.next();
        Result<String> checkResult = checkable.acceptChecker(checker);
        runtime.pop();
        if (!checkResult.isCorrect()) {
            return new IncorrectResult<>(checkResult);
        }
        return new CorrectResult<Interpretable>((Interpretable) checkable);
    }

    @Override
    public Interpretable peek() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }

        return interpretableBuffer.peek();
    }

    @Override
    public boolean hasNext() {
        if (!interpretableBuffer.isEmpty()) {
            return true;
        }

        Interpretable next = computeNext();

        if (next != null) {
            interpretableBuffer.add(next);
        }

        return next != null;
    }

    @Override
    public Interpretable next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }

        return interpretableBuffer.poll();
    }

    private Interpretable computeNext() {
        Interpretable candidate = null;
        if (checkableStream.hasNext()) {
            Result<Interpretable> parseResult = parse();
            if (parseResult.isCorrect()) {
                candidate = parseResult.result();
            }
        }
        return candidate;
    }
}
