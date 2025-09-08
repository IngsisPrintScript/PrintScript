package factories;

import common.ExecutionEngine;
import common.ExecutionEngineInterface;
import interpreter.Interpreter;
import interpreter.InterpreterInterface;
import interpreter.visitor.InterpretVisitor;
import lexer.Lexical;
import lexer.LexicalInterface;
import parser.CodeParser;
import parser.CodeParserInterface;
import parser.Syntactic;
import parser.SyntacticInterface;
import parser.ast.builders.cor.ChanBuilder;
import parser.semantic.SemanticAnalyzer;
import parser.semantic.SemanticInterface;
import parser.semantic.enforcers.SemanticRulesChecker;
import repositories.CodeRepositoryInterface;
import tokenizers.TokenizerInterface;
import tokenizers.factories.TokenizerFactory;


public class DefaultEngineFactory implements EngineFactoryInterface {
    @Override
    public ExecutionEngineInterface getInterpreterEngine(CodeRepositoryInterface repository) {
        return null;
    }
}
