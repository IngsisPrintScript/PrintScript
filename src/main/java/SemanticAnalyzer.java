import common.interfaces.Analyzer;
import common.responses.Response;
import parser.SemanticRulesInterface.SemanticRulesInterface;

public record SemanticAnalyzer(SemanticRulesInterface semanticRules) implements Analyzer {
    @Override
    public Response analyze() {
        return null;
    }
}
