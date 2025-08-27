package interpreter;


import common.Node;
import common.responses.CorrectResult;
import common.responses.Result;
import interpreter.executor.CodeExecutorInterface;
import interpreter.transpiler.TranspilerInterface;
import interpreter.writer.CodeWriterInterface;

import java.nio.file.Path;

public record DefaultInterpreter(TranspilerInterface transpiler, CodeWriterInterface writer, CodeExecutorInterface executor) implements InterpreterInterface{
    @Override
    public Result interpret(Node tree) {
        Result transpilationResult = transpiler().transpile(tree);
        if (!transpilationResult.isSuccessful()) return transpilationResult;
        String code = ((CorrectResult<String>) transpilationResult).newObject();
        Result codeWritingResult = writer().writeCode(code);
        if (!codeWritingResult.isSuccessful()) return codeWritingResult;
        Path filePath = ((CorrectResult<Path>) codeWritingResult).newObject();
        return executor().executeCode(filePath);
    }
}
