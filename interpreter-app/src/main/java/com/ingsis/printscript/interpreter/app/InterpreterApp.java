package com.ingsis.printscript.interpreter.app;

import com.ingsis.printscript.interpreter.Interpreter;
import com.ingsis.printscript.interpreter.InterpreterInterface;
import com.ingsis.printscript.interpreter.visitor.InterpretVisitor;
import com.ingsis.printscript.lexer.Lexical;
import com.ingsis.printscript.lexer.LexicalInterface;
import com.ingsis.printscript.peekableiterator.PeekableIterator;
import com.ingsis.printscript.repositories.CodeRepository;
import com.ingsis.printscript.results.Result;
import com.ingsis.printscript.semantic.SemanticAnalyzer;
import com.ingsis.printscript.semantic.enforcers.SemanticRulesChecker;
import com.ingsis.printscript.syntactic.Syntactic;
import com.ingsis.printscript.syntactic.SyntacticInterface;
import com.ingsis.printscript.syntactic.ast.builders.cor.NodeBuilderChain;
import com.ingsis.printscript.tokenizers.factories.TokenizerFactory;
import com.ingsis.printscript.tokens.TokenInterface;
import com.ingsis.printscript.visitor.InterpretableNode;
import com.ingsis.printscript.visitor.SemanticallyCheckable;
import picocli.CommandLine;

import java.nio.file.Path;
import java.util.Iterator;

@CommandLine.Command(
        name = "Interpreter",
        mixinStandardHelpOptions = true,
        version = "1.0")
public class InterpreterApp implements Runnable {
    public static void main(String[] args) {
        int exitCode = new CommandLine(new InterpreterApp()).execute(args);
        System.exit(exitCode);
    }

    @CommandLine.Parameters(index = "0", description = "File to interpret")
    private Path inputFile;

    @Override
    public void run() {
        CodeRepository repo = new CodeRepository(inputFile);
        LexicalInterface tokenIterator =
                new Lexical(new TokenizerFactory().createDefaultTokenizer(), repo);
        SyntacticInterface checkableNodesIterator =
                new Syntactic(new NodeBuilderChain().createDefaultChain(), tokenIterator);
        SemanticAnalyzer interpretableNodesIterator =
                new SemanticAnalyzer(new SemanticRulesChecker(), checkableNodesIterator);
        InterpreterInterface interpreter = new Interpreter( new InterpretVisitor(), interpretableNodesIterator);
        Result<String> interpretResult = interpreter.interpreter();
        if (!interpretResult.isSuccessful()) {
            System.out.println("Error: " + interpretResult.errorMessage());
        }
    }
}
