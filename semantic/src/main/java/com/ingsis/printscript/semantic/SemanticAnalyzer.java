/*
 * My Project
 */

package com.ingsis.printscript.semantic;

import com.ingsis.printscript.astnodes.visitor.InterpretableNode;
import com.ingsis.printscript.astnodes.visitor.RuleVisitor;
import com.ingsis.printscript.astnodes.visitor.SemanticallyCheckable;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public record SemanticAnalyzer(
        RuleVisitor rulesEnforcer,
        Iterator<SemanticallyCheckable> checkableNodesIterator,
        Deque<InterpretableNode> checkableNodesBuffer)
        implements SemanticInterface {

    public SemanticAnalyzer {
        List<SemanticallyCheckable> copyList = new ArrayList<>();
        while (checkableNodesIterator.hasNext()) {
            copyList.add(checkableNodesIterator.next());
        }
        checkableNodesIterator = copyList.iterator();

        checkableNodesBuffer = new ArrayDeque<>(checkableNodesBuffer);
    }

    public SemanticAnalyzer(
            RuleVisitor rulesEnforcer, Iterator<SemanticallyCheckable> checkableNodesIterator) {
        this(rulesEnforcer, checkableNodesIterator, new ArrayDeque<>());
    }

    @Override
    public Iterator<SemanticallyCheckable> checkableNodesIterator() {
        List<SemanticallyCheckable> copyList = new ArrayList<>();
        while (checkableNodesIterator.hasNext()) {
            copyList.add(checkableNodesIterator.next());
        }
        return copyList.iterator();
    }

    @Override
    public Deque<InterpretableNode> checkableNodesBuffer() {
        return new ArrayDeque<>(checkableNodesBuffer);
    }

    @Override
    public Boolean isSemanticallyValid(SemanticallyCheckable tree) {
        return tree.acceptCheck(rulesEnforcer()).isSuccessful();
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
        while (checkableNodesIterator.hasNext()) {
            SemanticallyCheckable checkable = checkableNodesIterator.next();
            if (this.isSemanticallyValid(checkable)) {
                return (InterpretableNode) checkable;
            }
        }
        return null;
    }
}
