package parser.Semantic;

import common.Node;
import responses.IncorrectResult;
import responses.Result;
import parser.Semantic.Context.SemanticVisitorContext;
import parser.Semantic.SemanticHandler.SemanticHandler;
import parser.Semantic.SemanticVisitor.SemanticVisitor;

import java.util.Map;


public record SemanticAnalyzer(SemanticVisitorContext context,
                               Map<Class<?>, SemanticHandler<?>> handlerMap, Node root) {
    public Result analyze() {
        if (context == null || handlerMap == null || root == null) {
            return new IncorrectResult("Variables are null.");
        }
        return root.accept(new SemanticVisitor(context, handlerMap));
    }
}
