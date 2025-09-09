package formatter;

import common.Node;
import common.TokenInterface;
import formatter.FormatterRules.SeparationFormatter;
import formatter.yamlAnalizer.ReadRules;
import lexer.Lexical;
import parser.Syntactic;
import parser.ast.builders.cor.ChanBuilder;
import picocli.CommandLine;
import repositories.CodeRepository;
import results.CorrectResult;
import results.Result;
import tokenizers.factories.TokenizerFactory;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.Callable;

@CommandLine.Command(name = "formatter", mixinStandardHelpOptions = true, version = "1.0")
public class Formatter implements FormatterInterface, Callable<Result<String>>{
    private final ReadRules readRules;

    public Formatter(ReadRules reader){
        this.readRules = reader;
    }


    @CommandLine.Parameters(index = "0", description = "File to format")
    private Path inputFile;


    @Override
    public Result<String> format(Node root) {
        HashMap<String, Object> rulesToFormat = readRules.readRules();
        return root.accept(new FormatterVisitor(new SeparationFormatter(rulesToFormat)));
    }


    @Override
    public Result<String> call() throws Exception {
        Iterator<TokenInterface> tokenIterator = new Lexical(new TokenizerFactory().createDefaultTokenizer(),
                new CodeRepository(inputFile));
        Iterator<Node> nodeIterator = new Syntactic(new ChanBuilder().createDefaultChain(), tokenIterator);
        StringBuilder sb = new StringBuilder();
        while (nodeIterator.hasNext()) {
            Result<String> formatted = format(nodeIterator.next());
            sb.append(formatted.result());
        }
        Files.writeString(inputFile, sb.toString());
        return new CorrectResult<>("Formatted the file");
    }
}
