/*
 * My Project
 */

package com.ingsis.printscript.compiler;

import com.ingsis.printscript.astnodes.Node;
import com.ingsis.printscript.compiler.executor.CodeExecutorInterface;
import com.ingsis.printscript.compiler.transpiler.TranspilerInterface;
import com.ingsis.printscript.compiler.writer.CodeWriterInterface;
import com.ingsis.printscript.results.IncorrectResult;
import com.ingsis.printscript.results.Result;
import java.nio.file.Path;

public record DefaultCompiler(
        TranspilerInterface transpiler, CodeWriterInterface writer, CodeExecutorInterface executor)
        implements CompilerInterface {
    @Override
    public Result<String> compile(Node tree) {
        Result<String> transpilationResult = transpiler().transpile(tree);
        if (!transpilationResult.isSuccessful()) {
            new IncorrectResult<>(transpilationResult.errorMessage());
        }
        String code = transpilationResult.result();
        Result<Path> codeWritingResult = writer().writeCode(code);
        if (!codeWritingResult.isSuccessful()) {
            return new IncorrectResult<>(codeWritingResult.errorMessage());
        }
        Path filePath = codeWritingResult.result();
        return executor().executeCode(filePath);
    }
}
