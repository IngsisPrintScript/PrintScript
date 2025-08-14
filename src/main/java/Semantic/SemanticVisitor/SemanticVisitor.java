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
        Result leftChild = node.declaration();
        if(!leftChild.isSuccessful()){
            return new IncorrectResult("Need DeclarationNode");
        }
        Object leftObj = ((CorrectResult<?>) leftChild).newObject();
        if(!(leftObj instanceof DeclarationNode declarationNode)){
            return new IncorrectResult("Let statement has no declaration.");
        }
        if(!(child.isSuccessful())){
            return addOnlyVariable(declarationNode);
        }
        Object obj = ((CorrectResult<?>) child).newObject();
        if(obj instanceof BinaryExpression compositeNode){
            Result expression = visit(compositeNode);
            Object leftLiteral = ((CorrectResult<?>) expression).newObject();
            if(!(leftLiteral instanceof LiteralNode left )){
                return new IncorrectResult("Incorrect left literal");
            }
            return addValue(left, node);
        }else if(obj instanceof LiteralNode literalNode){
            Result expression = declarationNode.leftChild();
            Object leftLiteral = ((CorrectResult<?>) expression).newObject();
            if(!(leftLiteral instanceof List<?> left )){
                return  new IncorrectResult("Incorrect left literal");
            }
            Result rules = checkSemanticRules(literalNode, (LiteralNode) left.getFirst(),null);
            if(!(rules.isSuccessful())){
                return rules;
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
        Result leftChild = node.leftChild();
        Result rightChild = node.rightChild();
        if(!(leftChild.isSuccessful()) || !(rightChild.isSuccessful())){
            return new IncorrectResult("Addition has no declaration.");
        }
        return calculateBinary(leftChild,rightChild,node);
    }

    @Override
    public Result visit(LiteralNode node) {
        return new CorrectResult<>(node.value());
    }

    @Override
    public Result visit(IdentifierNode node) {
        return new CorrectResult<>(node.value());
    }

    /*Can be added
    if(((CorrectResult<?>) node.leftChild()).newObject() instanceof LiteralNode literalNode
            && ((CorrectResult<?>) node.rightChild()).newObject() instanceof IdentifierNode id){
            return new IncorrectResult("Declaration has not valued.");
        }
      */
    @Override
    public Result visit(DeclarationNode node) {
        if(!(node.leftChild().isSuccessful() && node.rightChild().isSuccessful())){
            return new IncorrectResult("Need left and right children");
        }
        return new CorrectResult<>(List.of(node.leftChild(), node.rightChild()));
    }

    @Override
    public Result visit(BinaryExpression node) {
        Result leftResult = node.leftChild();
        if (!leftResult.isSuccessful()){
            return leftResult;
        }
        Result rightResult = node.rightChild();
        if (!rightResult.isSuccessful()){
            return rightResult;
        }
        return calculateBinary(leftResult,rightResult,node);
    }

    private Result addValue(LiteralNode literalNode, LetStatementNode node){
        Result result = visit(literalNode);
        Object getLiteralValue = ((CorrectResult<?>) result).newObject();
        if(!(getLiteralValue instanceof String value)) {
            return result;
        }
        Result declaration = node.declaration();
        Object object = ((CorrectResult<?>) declaration).newObject();
        if(!(object instanceof DeclarationNode declarationNode)){
            return new IncorrectResult("Not a declaration");
        }
        Result rightDeclaration = declarationNode.rightChild();
        //Declaration list = [Literal(Type) , Identifier]
        Object type = ((CorrectResult<?>) rightDeclaration).newObject();
        if(!(type instanceof IdentifierNode id)){
            return new IncorrectResult("Not a Value");
        }
        variablesTable.addValue(id.value(), value);
        return new CorrectResult<>(variablesTable);
    }
    private Result addOnlyVariable(DeclarationNode node){
        Result getIdentifier = node.rightChild();
        if(!(getIdentifier.isSuccessful())) {
            return getIdentifier;
        }
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

    private Result checkSemanticRules(LiteralNode leftLiteral, LiteralNode rightLiteral, BinaryExpression operator){
        if(!semanticRules.match(leftLiteral, rightLiteral, operator)){
            return semanticRules.checkRules(leftLiteral,rightLiteral, operator);
        }
        return new CorrectResult<>(null);
    }

    private Result calculateBinary(Result leftResult, Result rightResult, BinaryExpression node) {
        if(((CorrectResult<?>) leftResult).newObject() instanceof BinaryExpression binaryExpression){
            leftResult = visit(binaryExpression);
        }
        if(((CorrectResult<?>) rightResult).newObject() instanceof BinaryExpression binaryExpression){
            rightResult = visit(binaryExpression);
        }
        Object obj2 = ((CorrectResult<?>) leftResult).newObject();
        Object obj3 = ((CorrectResult<?>) rightResult).newObject();
        if (!(obj2 instanceof LiteralNode leftLiteral)) {
            return new IncorrectResult("Literal node is not a LiteralNode");
        }
        if (!(obj3 instanceof LiteralNode rightLiteral)) {
            return new IncorrectResult("Literal node is not a LiteralNode");
        }
        Result semanticRules = checkSemanticRules(leftLiteral,rightLiteral, node);
        if(!semanticRules.isSuccessful()){
            return semanticRules;
        }
        return new CorrectResult<>(new LiteralNode("(" + leftLiteral.value() + node + rightLiteral.value() + ")"));
    }
}
