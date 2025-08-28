package parser.Semantic.SemanticHandler;


import common.Node;
import responses.CorrectResult;
import responses.IncorrectResult;
import responses.Result;
import declaration.AscriptionNode;
import expression.literal.LiteralNode;
import parser.Semantic.Context.SemanticVisitorContext;
import parser.Semantic.SemanticVisitor.SemanticVisitor;
import statements.LetStatementNode;

public class LetStatementNodeHandler implements SemanticHandler<LetStatementNode> {

    @Override
    public Result handleSemantic(LetStatementNode node, SemanticVisitorContext context, SemanticVisitor visitor) {
        Result rightChild = node.expression();
        Result leftChild = node.ascription();
        if (!leftChild.isSuccessful()) {
            return new IncorrectResult("Need DeclarationNode");
        }
        AscriptionNode declarationNode = (AscriptionNode) ((CorrectResult<?>) leftChild).newObject();
        if (!(rightChild.isSuccessful())) {
            return context.variablesTable().addOnlyVariable(declarationNode);
        }
        Object obj = ((CorrectResult<?>) rightChild).newObject();
        Result resolved = visitor.dispatch(obj);

        Result typeCheck = context.semanticRules().checkSemanticRules(declarationNode.type(), resolved, node);
        if (!typeCheck.isSuccessful()) {
            return typeCheck;
        }
        return context.variablesTable().addValue(declarationNode, (LiteralNode) obj);
    }

    @Override
    public boolean canHandle(Node node) {
        return node instanceof LetStatementNode;
    }
}
