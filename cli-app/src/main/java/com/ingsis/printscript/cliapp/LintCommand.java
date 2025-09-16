package com.ingsis.printscript.cliapp;

import com.ingsis.printscript.astnodes.Node;
import com.ingsis.printscript.astnodes.visitor.InterpretableNode;
import com.ingsis.printscript.astnodes.visitor.SemanticallyCheckable;
import com.ingsis.printscript.linter.AnalyzerFactory;
import com.ingsis.printscript.linter.AnalyzerRunner;
import com.ingsis.printscript.linter.api.AnalyzerConfig;
import com.ingsis.printscript.linter.api.Violation;
import com.ingsis.printscript.linter.config.ConfigLoader;
import com.ingsis.printscript.lexer.Lexical;
import com.ingsis.printscript.repositories.CodeRepository;
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

@CommandLine.Command(name = "lint", description = "Run linter on a source file")
public final class LintCommand implements Runnable {
    @CommandLine.Parameters(index = "0", description = "Source file to analyze")
    private Path source;

    @CommandLine.Option(
            names = {"-c", "--config"},
            description = "Path to linter config (YAML or JSON)")
    private Path configPath;

    @Override
    public void run() {
        try {
            AnalyzerConfig config =
                    configPath != null ? ConfigLoader.load(configPath) : AnalyzerConfig.defaults();

            CodeRepository repo = new CodeRepository(source);
            Iterator<TokenInterface> tokenIterator =
                    new Lexical(new TokenizerFactory().createDefaultTokenizer(), repo);
            Iterator<SemanticallyCheckable> checkableNodesIterator =
                    new Syntactic(new ChanBuilder().createDefaultChain(), tokenIterator);
            Iterator<InterpretableNode> interpretableNodesIterator =
                    new SemanticAnalyzer(new SemanticRulesChecker(), checkableNodesIterator);

            AnalyzerRunner runner = AnalyzerFactory.defaultRunner();
            List<Violation> allViolations = new ArrayList<>();

            while (interpretableNodesIterator.hasNext()) {
                InterpretableNode node = (InterpretableNode) interpretableNodesIterator.next();
                allViolations.addAll(runner.analyze((Node) node, config));
            }

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


