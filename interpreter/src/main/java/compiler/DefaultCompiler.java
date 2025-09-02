package compiler;


import common.Node;
import results.IncorrectResult;
import results.Result;
import compiler.executor.CodeExecutorInterface;
import compiler.transpiler.TranspilerInterface;
import compiler.writer.CodeWriterInterface;

import java.nio.file.Path;

public record DefaultCompiler(TranspilerInterface transpiler, CodeWriterInterface writer, CodeExecutorInterface executor) implements CompilerInterface {
    @Override
    public Result<String> compile(Node tree) {
        Result<String> transpilationResult = transpiler().transpile(tree);
        if (!transpilationResult.isSuccessful()) return transpilationResult;
        String code = transpilationResult.result();
        Result<Path> codeWritingResult = writer().writeCode(code);
        if (!codeWritingResult.isSuccessful()) {
            return new IncorrectResult<>("Code writing failed");
        }
        Path filePath = codeWritingResult.result();
        return executor().executeCode(filePath);
    }
}
