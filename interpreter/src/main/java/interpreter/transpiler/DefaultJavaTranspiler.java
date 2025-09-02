package interpreter.transpiler;

import interpreter.transpiler.visitor.JavaTranspilationVisitor;
import nodes.common.Node;
import nodes.visitor.VisitorInterface;
import results.Result;

public record DefaultJavaTranspiler() implements TranspilerInterface {
  @Override
  public Result<String> transpile(Node tree) {
    VisitorInterface visitor = new JavaTranspilationVisitor();
    return tree.accept(visitor);
  }
}
