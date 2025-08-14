package Semantic.SemanticVisitor;

import Semantic.SemanticRules.SemanticRulesInterface;
import common.VariablesTable.VariablesTableInterface;
import common.nodes.expression.Declaration.DeclarationNode;
import common.nodes.expression.Identifier.IdentifierNode;
import common.nodes.expression.binary.AdditionNode;
import common.nodes.expression.binary.BinaryExpression;
import common.nodes.expression.literal.LiteralNode;
import common.nodes.statements.LetStatementNode;
import common.nodes.statements.PrintStatementNode;
import common.responses.CorrectResult;
import common.responses.IncorrectResult;
import common.responses.Result;
import common.visitor.VisitorInterface;

import java.util.List;

public record SemanticVisitor(VariablesTableInterface variablesTable, SemanticRulesInterface semanticRules) implements VisitorInterface {

    @Override
    public Result visit(LetStatementNode node) {
        Result child = node.expression();
        if(!(child.isSuccessful())){
            return addOnlyVariable(node);
        }
        Object obj = ((CorrectResult<?>) child).newObject();
        if(obj instanceof BinaryExpression compositeNode){
            return visit(compositeNode);
        }else if(obj instanceof LiteralNode literalNode){
            if(!(checkRules(node).isSuccessful())){
                return checkRules(node);
            }
            return addValue(literalNode, node);
        }
        return new IncorrectResult("Let statement has no declaration.");
    }

    @Override
    public Result visit(PrintStatementNode node) {
        return null;
    }

    @Override
    public Result visit(AdditionNode node){
        return null;
    }

    @Override
    public Result visit(LiteralNode node) {
        return new CorrectResult<>(node.value());
    }

    @Override
    public Result visit(IdentifierNode node) {
        return new CorrectResult<>(node.value());
    }

    @Override
    public Result visit(DeclarationNode node) {
        if(!(node.leftChild().isSuccessful() && node.rightChild().isSuccessful())){
            return new IncorrectResult("Need left and right children");
        }
        return new CorrectResult<>(List.of(node.leftChild(), node.rightChild()));
    }

    @Override
    public Result visit(BinaryExpression node) {
        return null;
    }

    private Result checkRules(LetStatementNode node){
        if(!semanticRules.match(node)){
            Result child2 = semanticRules.checkRules(node);
            String obj2 = ((IncorrectResult) child2).errorMessage();
            return new IncorrectResult(obj2);
        }
        return new CorrectResult<>(null);
    }
    private Result addValue(LiteralNode literalNode, LetStatementNode node){
        Result result = visit(literalNode);
        Object getLiteralValue = ((CorrectResult<?>) result).newObject();
        if(!(getLiteralValue instanceof String literalValue)) {
            return result;
        }
        Result declaration = node.declaration();
        Object object = ((CorrectResult<?>) declaration).newObject();
        if(!(object instanceof DeclarationNode declarationNode)){
            return new IncorrectResult("Not a declaration");
        }
        Result getDeclaration = visit(declarationNode);
        if(getDeclaration.isSuccessful()){
            return getDeclaration;
        }
        Object childList = ((CorrectResult<?>) getDeclaration).newObject();
        if(!(childList instanceof List<?> declarationsChildList)){
            return new IncorrectResult("Not a list with children");
        }
        //Declaration list = [Literal(Type) , Identifier]
        Result getType = visit((IdentifierNode) declarationsChildList.get(1));
        Object type = ((CorrectResult<?>) getType).newObject();
        if(!(type instanceof String id)){
            return new IncorrectResult("Not a Value");
        }
        variablesTable.addValue(id, literalValue);
        return new CorrectResult<>(variablesTable);
    }
    private Result addOnlyVariable(LetStatementNode node){
        Result declaration = node.declaration();
        Object object =  ((CorrectResult<?>) declaration).newObject();
        if(!(object instanceof DeclarationNode declarationNode)){
            return new IncorrectResult("Not a declaration");
        }
        if(!(visit(declarationNode).isSuccessful())){
            return visit(declarationNode);
        }
        Result getIdentifier = declarationNode.rightChild();
        Object getIdentifierValue = ((CorrectResult<?>) getIdentifier).newObject();
        if(!(getIdentifierValue instanceof IdentifierNode identifier)){
            return new IncorrectResult("Not a identifier");
        }
        Result identifierValue = visit(identifier);
        Object identifierValues = ((CorrectResult<?>) identifierValue).newObject();
        if(!(identifierValues instanceof String string)){
            return  new IncorrectResult("Not a Value");
        }
        variablesTable.addValue(string,null);
        return new CorrectResult<>(variablesTable);
    }
}
