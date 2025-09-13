/*
 * My Project
 */

package com.ingsis.printscript.compiler.transpiler;

import com.ingsis.printscript.astnodes.Node;
import com.ingsis.printscript.astnodes.visitor.VisitorInterface;
import com.ingsis.printscript.compiler.transpiler.visitor.JavaTranspilationVisitor;
import com.ingsis.printscript.results.Result;

public record DefaultJavaTranspiler() implements TranspilerInterface {
    @Override
    public Result<String> transpile(Node tree) {
        VisitorInterface visitor = new JavaTranspilationVisitor();
        return tree.accept(visitor);
    }
}
