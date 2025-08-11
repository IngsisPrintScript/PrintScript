package analyzers.semantic.SemanticSandbox;

import analyzers.Analyzer;
import analyzers.semantic.SemanticRulesInterface.SemanticRulesInterface;
import responses.Response;

public record SemanticAnalyzer(SemanticRulesInterface semanticRules) implements Analyzer {
    @Override
    public Response analyze() {
        return null;
    }
}
