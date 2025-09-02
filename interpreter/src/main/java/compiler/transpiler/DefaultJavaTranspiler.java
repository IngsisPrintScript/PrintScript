package compiler.transpiler;


import common.Node;
import results.Result;
import compiler.transpiler.visitor.JavaTranspilationVisitor;
import visitor.VisitorInterface;

public record DefaultJavaTranspiler() implements TranspilerInterface {
    @Override
    public Result<String> transpile(Node tree) {
        VisitorInterface visitor = new JavaTranspilationVisitor();
        return tree.accept(visitor);
    }
}
