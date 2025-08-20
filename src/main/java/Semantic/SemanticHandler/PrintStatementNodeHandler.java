package Semantic.SemanticHandler;

import Semantic.Context.SemanticVisitorContext;
import Semantic.SemanticVisitor.SemanticVisitor;
import common.nodes.Node;
import common.nodes.statements.PrintStatementNode;
import common.responses.CorrectResult;
import common.responses.IncorrectResult;
import common.responses.Result;

public class PrintStatementNodeHandler implements SemanticHandler<PrintStatementNode> {
    @Override
    public Result handleSemantic(PrintStatementNode node, SemanticVisitorContext context, SemanticVisitor visitor) {
        Result result = context.semanticRules().checkSemanticRules(node.expression(),null,node);

        return result;
    }

    @Override
    public boolean canHandle(Node node) {
        return node instanceof PrintStatementNode;
    }
}
