/*
 * My Project
 */

package com.ingsis.printscript.semantic;

import com.ingsis.printscript.visitor.InterpretableNode;
import com.ingsis.printscript.visitor.RuleVisitor;
import com.ingsis.printscript.visitor.SemanticallyCheckable;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class SemanticAnalyzer implements SemanticInterface {
    private final RuleVisitor rulesEnforcer;
    private final Iterator<SemanticallyCheckable> checkableNodesIterator;
    private final Deque<InterpretableNode> checkableNodesBuffer;

    @SuppressFBWarnings("EI_EXPOSE_REP2")
    public SemanticAnalyzer(
            RuleVisitor rulesEnforcer,
            Iterator<SemanticallyCheckable> checkableNodesIterator,
            Deque<InterpretableNode> checkableNodesBuffer) {
        this.rulesEnforcer = rulesEnforcer;
        this.checkableNodesIterator = checkableNodesIterator;
        this.checkableNodesBuffer = checkableNodesBuffer;
    }

    @SuppressFBWarnings("EI_EXPOSE_REP2")
    public SemanticAnalyzer(
            RuleVisitor rulesEnforcer, Iterator<SemanticallyCheckable> checkableNodesIterator) {
        this(rulesEnforcer, checkableNodesIterator, new ArrayDeque<>());
    }

    @Override
    public Boolean isSemanticallyValid(SemanticallyCheckable tree) {
        return tree.acceptCheck(rulesEnforcer).isSuccessful();
    }

    @Override
    public boolean hasNext() {
        if (!checkableNodesBuffer.isEmpty()) {
            return true;
        }

        InterpretableNode nextInterpretableNode = computeNext();
        if (nextInterpretableNode != null) checkableNodesBuffer.add(nextInterpretableNode);

        return nextInterpretableNode != null;
    }

    @Override
    public InterpretableNode next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        return checkableNodesBuffer.pollFirst();
    }

    @Override
    public InterpretableNode peek() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        return checkableNodesBuffer.peekFirst();
    }

    private InterpretableNode computeNext() {
        InterpretableNode interpretableCandidate = null;
        while (checkableNodesIterator.hasNext()) {
            SemanticallyCheckable checkable = checkableNodesIterator.next();
            if (this.isSemanticallyValid(checkable)) {
                return (InterpretableNode) checkable;
            }
        }
        return interpretableCandidate;
    }
}
