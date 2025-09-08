package common;

import compiler.CompilerInterface;
import interpreter.InterpreterInterface;
import lexer.LexicalInterface;
import parser.CodeParserInterface;
import parser.SyntacticInterface;
import parser.semantic.SemanticInterface;
import repositories.CodeRepositoryInterface;
import results.CorrectResult;
import results.IncorrectResult;
import results.Result;
import stream.TokenStreamInterface;
import visitor.SemanticallyCheckable;

import java.util.List;

public record ExecutionEngine(
        CodeRepositoryInterface repository,
        CodeParserInterface codeParser,
        LexicalInterface lexical,
        SyntacticInterface syntactic,
        SemanticInterface semantic,
        InterpreterInterface interpreter
) implements ExecutionEngineInterface{

    @Override
    public Result<String> execute() {
        return new IncorrectResult<>("Not implemented yet.");
    }
}
