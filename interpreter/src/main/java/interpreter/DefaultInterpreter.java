package interpreter;


import common.Node;
import results.IncorrectResult;
import results.Result;
import interpreter.executor.CodeExecutorInterface;
import interpreter.transpiler.TranspilerInterface;
import interpreter.writer.CodeWriterInterface;

import java.nio.file.Path;

public record DefaultInterpreter(TranspilerInterface transpiler, CodeWriterInterface writer, CodeExecutorInterface executor) implements InterpreterInterface{
    @Override
    public Result<String> interpret(Node tree) {
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
