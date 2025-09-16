package com.ingsis.printscript.linter.cli;

import com.ingsis.printscript.astnodes.Node;
import com.ingsis.printscript.astnodes.visitor.InterpretableNode;
import com.ingsis.printscript.astnodes.visitor.SemanticallyCheckable;
import com.ingsis.printscript.linter.AnalyzerFactory;
import com.ingsis.printscript.linter.AnalyzerRunner;
import com.ingsis.printscript.linter.api.AnalyzerConfig;
import com.ingsis.printscript.linter.api.Violation;
import com.ingsis.printscript.linter.config.ConfigLoader;
import com.ingsis.printscript.lexer.Lexical;
import com.ingsis.printscript.peekableiterator.PeekableIterator;
import com.ingsis.printscript.semantic.SemanticAnalyzer;
import com.ingsis.printscript.semantic.enforcers.SemanticRulesChecker;
import com.ingsis.printscript.syntactic.Syntactic;
import com.ingsis.printscript.syntactic.ast.builders.cor.ChanBuilder;
import com.ingsis.printscript.tokenizers.factories.TokenizerFactory;
import com.ingsis.printscript.tokens.TokenInterface;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import picocli.CommandLine;

@CommandLine.Command(
        name = "printscript-linter",
        mixinStandardHelpOptions = true,
        version = "1.0",
        description = "PrintScript linter CLI")
public final class LinterCli implements Runnable {
    @CommandLine.Parameters(index = "0", description = "Source file to analyze")
    private Path source;

    @CommandLine.Option(
            names = {"-c", "--config"},
            description = "Path to linter config (YAML or JSON)")
    private Path configPath;

    public static void main(String[] args) {
        int exit = new CommandLine(new LinterCli()).execute(args);
        System.exit(exit);
    }

    @Override
    public void run() {
        try {
            AnalyzerConfig config =
                    configPath != null ? ConfigLoader.load(configPath) : AnalyzerConfig.defaults();

            PeekableIterator<Character> charIt = new FileCharacterIterator(source);
            Iterator<TokenInterface> tokenIterator =
                    new Lexical(new TokenizerFactory().createDefaultTokenizer(), charIt);
            Iterator<SemanticallyCheckable> checkableNodesIterator =
                    new Syntactic(new ChanBuilder().createDefaultChain(), tokenIterator);
            Iterator<InterpretableNode> interpretableNodesIterator =
                    new SemanticAnalyzer(new SemanticRulesChecker(), checkableNodesIterator);

            AnalyzerRunner runner = AnalyzerFactory.defaultRunner();
            List<Violation> allViolations = new ArrayList<>();

            System.out.println("Processing nodes...");
            int nodeCount = 0;
            while (interpretableNodesIterator.hasNext()) {
                InterpretableNode node = (InterpretableNode) interpretableNodesIterator.next();
                nodeCount++;
                System.out.println("Processing node " + nodeCount + ": " + node.getClass().getSimpleName());
                List<Violation> nodeViolations = runner.analyze((Node) node, config);
                System.out.println("Found " + nodeViolations.size() + " violations in this node");
                allViolations.addAll(nodeViolations);
            }
            System.out.println("Total nodes processed: " + nodeCount);
            System.out.println("Total violations found: " + allViolations.size());

            if (allViolations.isEmpty()) {
                System.out.println("No violations found.");
            } else {
                for (Violation v : allViolations) {
                    if (v.range() != null) {
                        System.out.printf(
                                "%s: %s at [%d:%d - %d:%d]%n",
                                v.ruleId(),
                                v.message(),
                                v.range().startLine(),
                                v.range().startCol(),
                                v.range().endLine(),
                                v.range().endCol());
                    } else {
                        System.out.printf("%s: %s%n", v.ruleId(), v.message());
                    }
                }
                System.exit(2);
            }
        } catch (Exception e) {
            System.err.println("Linter failed: " + e.getMessage());
            System.exit(1);
        }
    }
}


