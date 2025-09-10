package parser.semantic;

import visitor.InterpretableNode;
import visitor.RuleVisitor;
import visitor.SemanticallyCheckable;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import java.util.NoSuchElementException;


public record SemanticAnalyzer(
        RuleVisitor rulesEnforcer,
        Iterator<SemanticallyCheckable> checkableNodesIterator,
        Deque<InterpretableNode> checkableNodesBuffer
) implements SemanticInterface {

    public SemanticAnalyzer(RuleVisitor rulesEnforcer, Iterator<SemanticallyCheckable> checkableNodesIterator){
        this(rulesEnforcer, checkableNodesIterator, new ArrayDeque<>());
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

    private InterpretableNode computeNext(){
        while (checkableNodesIterator().hasNext()){
            SemanticallyCheckable checkable = checkableNodesIterator.next();
            if (this.isSemanticallyValid(checkable)) {
                return (InterpretableNode) checkable;
            }
        }
        return null;
    }
}
