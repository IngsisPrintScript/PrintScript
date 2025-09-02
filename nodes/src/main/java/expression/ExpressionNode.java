package expression;

import common.Node;
import results.Result;
import visitor.InterpretVisitor;
import visitor.InterpretableNode;
import visitor.SemanticallyCheckable;


public interface ExpressionNode extends Node, SemanticallyCheckable, InterpretableNode {
    Result<Object> evaluate();
    Result<String> prettyPrint();
    @Override
    default Result<String> acceptInterpreter(InterpretVisitor interpreter){
        return interpreter.interpret(this);
    }
}
