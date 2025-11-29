/*
 * My Project
 */

package com.ingsis.interpreter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.ingsis.utils.nodes.nodes.expression.ExpressionNode;
import com.ingsis.utils.nodes.nodes.keyword.DeclarationKeywordNode;
import com.ingsis.utils.nodes.nodes.keyword.IfKeywordNode;
import com.ingsis.utils.nodes.visitors.Interpretable;
import com.ingsis.utils.nodes.visitors.Interpreter;
import com.ingsis.utils.peekableiterator.PeekableIterator;
import com.ingsis.utils.result.CorrectResult;
import com.ingsis.utils.result.IncorrectResult;
import com.ingsis.utils.result.Result;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DefaultProgramInterpreterTest {
    private List<Interpretable> items;

    @BeforeEach
    void setup() {
        items = new ArrayList<>();
    }

    @Test
    void interpretReturnsLastCorrectResultWhenAllCorrect() {
        items.add(makeInterpretable(new CorrectResult<>("one")));
        items.add(makeInterpretable(new CorrectResult<>("two")));

        PeekableIterator<Interpretable> it = new ListPeekableIterator(items);
        Interpreter dummy =
                new Interpreter() {
                    @Override
                    public Result<String> interpret(IfKeywordNode ifKeywordNode) {
                        throw new AssertionError("Should not be called");
                    }

                    @Override
                    public Result<String> interpret(DeclarationKeywordNode declarationKeywordNode) {
                        throw new AssertionError("Should not be called");
                    }

                    @Override
                    public Result<Object> interpret(ExpressionNode expressionNode) {
                        return new CorrectResult<>("ok");
                    }
                };

        DefaultProgramInterpreter interpreterObj = new DefaultProgramInterpreter(it, dummy);

        Result<String> res = interpreterObj.interpret();
        assertTrue(res.isCorrect());
        assertEquals("two", res.result());
    }

    @Test
    void interpretStopsOnFirstIncorrect() {
        items.add(makeInterpretable(new CorrectResult<>("a")));
        items.add(makeInterpretable(new IncorrectResult<>("boom")));
        items.add(makeInterpretable(new CorrectResult<>("c")));

        PeekableIterator<Interpretable> it = new ListPeekableIterator(items);
        Interpreter dummy =
                new Interpreter() {
                    @Override
                    public Result<String> interpret(IfKeywordNode ifKeywordNode) {
                        throw new AssertionError("Should not be called");
                    }

                    @Override
                    public Result<String> interpret(DeclarationKeywordNode declarationKeywordNode) {
                        throw new AssertionError("Should not be called");
                    }

                    @Override
                    public Result<Object> interpret(ExpressionNode expressionNode) {
                        return new CorrectResult<>("ok");
                    }
                };

        DefaultProgramInterpreter interpreterObj = new DefaultProgramInterpreter(it, dummy);

        Result<String> res = interpreterObj.interpret();
        assertFalse(res.isCorrect());
    }

    private static Interpretable makeInterpretable(Result<String> result) {
        return interpreter -> result;
    }

    private static class ListPeekableIterator implements PeekableIterator<Interpretable> {
        private final List<Interpretable> list;
        private int idx = 0;

        ListPeekableIterator(List<Interpretable> list) {
            this.list = list;
        }

        @Override
        public Interpretable peek() {
            return list.get(idx);
        }

        @Override
        public boolean hasNext() {
            return idx < list.size();
        }

        @Override
        public Interpretable next() {
            return list.get(idx++);
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}
