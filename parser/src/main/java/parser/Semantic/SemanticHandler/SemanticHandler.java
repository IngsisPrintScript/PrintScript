package parser.Semantic.SemanticHandler;


import common.Node;
import common.responses.Result;
import parser.Semantic.Context.SemanticVisitorContext;
import parser.Semantic.SemanticVisitor.SemanticVisitor;

public interface SemanticHandler<T> {

    Result handleSemantic(T node, SemanticVisitorContext context, SemanticVisitor visitor);

    boolean canHandle(Node node);

}
