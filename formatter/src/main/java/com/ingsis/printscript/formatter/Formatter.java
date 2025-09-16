package com.ingsis.printscript.formatter;

import com.ingsis.printscript.astnodes.Node;
import com.ingsis.printscript.formatter.FormatterRules.SeparationFormatter;
import com.ingsis.printscript.formatter.yamlAnalizer.ReadRules;
import com.ingsis.printscript.formatter.yamlAnalizer.ReadRulesInterface;
import com.ingsis.printscript.lexer.Lexical;
import com.ingsis.printscript.repositories.CodeRepository;
import com.ingsis.printscript.results.CorrectResult;
import com.ingsis.printscript.results.IncorrectResult;
import com.ingsis.printscript.results.Result;
import com.ingsis.printscript.semantic.SemanticAnalyzer;
import com.ingsis.printscript.semantic.enforcers.SemanticRulesChecker;
import com.ingsis.printscript.syntactic.Syntactic;
import com.ingsis.printscript.syntactic.ast.builders.cor.NodeBuilderChain;
import com.ingsis.printscript.tokenizers.factories.TokenizerFactory;
import com.ingsis.printscript.tokens.TokenInterface;
import com.ingsis.printscript.visitor.InterpretableNode;
import com.ingsis.printscript.visitor.SemanticallyCheckable;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.Callable;
import picocli.CommandLine;

@CommandLine.Command(
        name = "com/ingsis/printscript/formatter",
        mixinStandardHelpOptions = true,
        version = "1.0")
public class Formatter implements FormatterInterface, Callable<Result<Path>> {
    private ReadRulesInterface readRules = new ReadRules();

    public static void main(String[] args) throws Exception {
        int exitCode = new CommandLine(new Formatter()).execute(args);
        System.exit(exitCode);
    }

    @CommandLine.Parameters(index = "0", description = "File to format")
    private Path inputFile;

    @CommandLine.Option(names = {"-r", "--rules"}, description = "Optional YAML file with formatting rules")
    private Path rulesFile;

    //deberia escribirse asi en cosnola: java -jar formatter.jar input.ps --rules rules.yml
    @Override
    public Result<String> format(Node root) {
        if (readRules == null) {
            readRules = (rulesFile != null) ? new ReadRules(rulesFile) : new ReadRules();
        }
        HashMap<String, Object> rulesToFormat = readRules.readRules();
        return root.accept(new FormatterVisitor(new SeparationFormatter(rulesToFormat)));
    }


    @Override
    public Result<Path> call() throws Exception {
        File txt = inputFile.toFile();
        CodeRepository repo = new CodeRepository(inputFile);
        Iterator<TokenInterface> tokenIterator =
                new Lexical(new TokenizerFactory().createDefaultTokenizer(), repo);
        Iterator<SemanticallyCheckable> checkableNodesIterator =
                new Syntactic(new NodeBuilderChain().createDefaultChain(), tokenIterator);
        Iterator<InterpretableNode> interpretableNodesIterator =
                new SemanticAnalyzer(new SemanticRulesChecker(), checkableNodesIterator);
        StringBuilder sb = new StringBuilder();
        if(txt.length() == 0) {
            return new IncorrectResult<>("No text to format");
        }
        while(interpretableNodesIterator.hasNext()) {
            Result<String> formatted = format(interpretableNodesIterator.next());
            sb.append(formatted.result());
        }
        Files.writeString(inputFile, sb.toString());
        return new CorrectResult<>(inputFile);
    }
    public void setInputFile(Path contentToFormat) {
        this.inputFile = contentToFormat;
    }
    public void setRulesFile(Path rulesFile) {
        this.rulesFile = rulesFile;
    }
}
