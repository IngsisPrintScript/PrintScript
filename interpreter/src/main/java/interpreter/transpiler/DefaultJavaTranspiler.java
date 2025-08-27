package interpreter.transpiler;


import common.Node;
import common.responses.Result;
import interpreter.transpiler.visitor.JavaTranspilationVisitor;
import visitor.VisitorInterface;

public record DefaultJavaTranspiler() implements TranspilerInterface {
    @Override
    public Result transpile(Node tree) {
        VisitorInterface visitor = new JavaTranspilationVisitor();
        return tree.accept(visitor);
    }
}
