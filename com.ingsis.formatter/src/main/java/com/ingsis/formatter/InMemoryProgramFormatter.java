/*
 * My Project
 */

package com.ingsis.formatter;

import com.ingsis.interpreter.visitor.DefaultInterpreterVisitor;
import com.ingsis.utils.iterator.safe.SafeIterator;
import com.ingsis.utils.iterator.safe.result.SafeIterationResult;
import com.ingsis.utils.nodes.keyword.DeclarationKeywordNode;
import com.ingsis.utils.nodes.visitors.Checkable;
import com.ingsis.utils.nodes.visitors.Checker;
import com.ingsis.utils.nodes.visitors.Interpretable;
import com.ingsis.utils.result.CorrectResult;
import com.ingsis.utils.result.Result;
import com.ingsis.utils.result.factory.DefaultResultFactory;
import com.ingsis.utils.runtime.DefaultRuntime;
import java.io.StringWriter;

public class InMemoryProgramFormatter implements ProgramFormatter {
    private final SafeIterator<Interpretable> checkableStream;
    private final Checker checker;
    private final StringWriter writer;

    public InMemoryProgramFormatter(
            SafeIterator<Interpretable> checkableStream,
            Checker eventsChecker,
            StringWriter writer) {
        this.checkableStream = checkableStream;
        this.checker = eventsChecker;
        this.writer = writer;
    }

    @Override
    public Result<String> format() {
        try {
            DefaultRuntime.getInstance().push();
            SafeIterationResult<Interpretable> result = checkableStream.next();

            while (result.isCorrect()) {
                if (result.iterationResult() instanceof DeclarationKeywordNode decl) {
                    new DefaultInterpreterVisitor(
                                    DefaultRuntime.getInstance(), new DefaultResultFactory())
                            .interpret(decl);
                }

                Result<String> finalResult =
                        ((Checkable) result.iterationResult()).acceptChecker(checker);

                if (!finalResult.isCorrect()) {
                    return finalResult;
                }

                result = result.nextIterator().next();
            }

            String output = writer.toString().replaceAll("\\n+$", "");
            return new CorrectResult<>(output);

        } finally {
            DefaultRuntime.getInstance().pop();
        }
    }
}
