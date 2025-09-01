package common;

import interpreter.InterpreterInterface;
import lexer.LexicalInterface;
import parser.CodeParserInterface;
import parser.SyntacticInterface;
import parser.semantic.SemanticInterface;
import repositories.CodeRepositoryInterface;
import responses.CorrectResult;
import responses.IncorrectResult;
import responses.Result;
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
        while (repository().hasMoreCode()) {
            String code = repository.nextChunkOfCode();
            Result<List<String>> parseResult = codeParser().parse(code);
            if (!parseResult.isSuccessful()) {
                return new IncorrectResult<>("Error parsing code.");
            }
            List<String> tokens = parseResult.result();
            Result<TokenStreamInterface> tokenizeResult = lexical().analyze(tokens);
            if (!tokenizeResult.isSuccessful()) {
                return new IncorrectResult<>("Error tokenizing code.");
            }
            TokenStreamInterface tokenStream = tokenizeResult.result();
            Result<SemanticallyCheckable> buildAstResult = syntactic().buildAbstractSyntaxTree(tokenStream);
            if (!buildAstResult.isSuccessful()) {
                return new IncorrectResult<>("Error syntactic building code.");
            }
            if (!semantic().isSemanticallyValid(buildAstResult.result())){
                return new IncorrectResult<>("Semantic validation failed.");
            }
            Result<String> interpretResult = interpreter().interpret((Node) buildAstResult.result());
            if (!interpretResult.isSuccessful()) {
                return interpretResult;
            }
        }
        return new CorrectResult<>("Execution of printscript program was successful.");
    }
}
