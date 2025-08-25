package interpreter.transpiler;

import common.nodes.Node;
import common.responses.Result;
import interpreter.transpiler.visitor.JavaTranspilationVisitor;

public record DefaultJavaTranspiler() implements TranspilerInterface {
    @Override
    public Result transpile(Node tree) {
        JavaTranspilationVisitor visitor = new JavaTranspilationVisitor();
        return tree.accept(visitor);
    }
}
