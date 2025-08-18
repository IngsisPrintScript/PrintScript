package interpreter.transpiler;

import common.nodes.Node;
import common.repositories.ast.ASTRepositoryInterface;
import common.responses.CorrectResult;
import common.responses.Result;
import interpreter.transpiler.visitor.JavaTranspilationVisitor;

public record DefaultJavaTranspiler(ASTRepositoryInterface repository) implements TranspilerInterface {
    @Override
    public Result transpile() {
        JavaTranspilationVisitor visitor = new JavaTranspilationVisitor();
        Result getAstResult = repository().getAst();
        if (!getAstResult.isSuccessful()) return getAstResult;
        Node tree = ((CorrectResult<Node>) getAstResult).newObject();
        return tree.accept(visitor);
    }
}
