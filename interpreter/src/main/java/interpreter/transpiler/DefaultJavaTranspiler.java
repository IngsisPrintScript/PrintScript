package interpreter.transpiler;


import nodes.common.Node;
import results.Result;
import interpreter.transpiler.visitor.JavaTranspilationVisitor;
import nodes.visitor.VisitorInterface;

public record DefaultJavaTranspiler() implements TranspilerInterface {
    @Override
    public Result<String> transpile(Node tree) {
        VisitorInterface visitor = new JavaTranspilationVisitor();
        return tree.accept(visitor);
    }
}
