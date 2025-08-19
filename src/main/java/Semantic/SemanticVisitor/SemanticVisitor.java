package Semantic.SemanticVisitor;

import Semantic.SemanticRules.SemanticRulesInterface;
import common.RulesFactory.RulesEngine;
import common.Symbol.Symbol;
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

public record SemanticVisitor(VariablesTableInterface variablesTable, RulesEngine semanticRules) implements VisitorInterface {

    @Override
    public Result visit(LetStatementNode node) {
        Result rightChild = node.expression();
        Result leftChild = node.declaration();
        if(!leftChild.isSuccessful()){
            return new IncorrectResult("Need DeclarationNode");
        }
        DeclarationNode declarationNode = (DeclarationNode) ((CorrectResult<?>) leftChild).newObject();
        if(!(rightChild.isSuccessful())){
            return addOnlyVariable(declarationNode);
        }
        Object obj = ((CorrectResult<?>) rightChild).newObject();
        if(obj instanceof BinaryExpression compositeNode){
            return visit(compositeNode);
        }
        if(obj instanceof LiteralNode literalNode){
            Result rules = semanticRules.checkRules((LiteralNode) ((CorrectResult<?>) declarationNode.leftChild()).newObject(),
                    literalNode,null);
            if(!(rules.isSuccessful())){
                return rules;
            }
            return addValue(declarationNode,literalNode);
        }
        return new IncorrectResult("Unsupported expression type in let statement.");
    }

    @Override
    public Result visit(PrintStatementNode node) {
        Result expr = node.expression();
        if (!expr.isSuccessful()) {
            return new IncorrectResult("Invalid expression in print statement.");
        }
        Object obj = ((CorrectResult<?>) expr).newObject();

        if (obj instanceof IdentifierNode id) {
            Result symbol = variablesTable.getValue(id.value());
            if (!symbol.isSuccessful()) {
                return new IncorrectResult("Variable " + id.value() + " is not defined.");
            }
            return new CorrectResult<>(((Symbol) ((CorrectResult<?>) symbol).newObject()).value());
        }

        return new CorrectResult<>(obj);
    }

    @Override
    public Result visit(AdditionNode node){
        Result leftChild = node.leftChild();
        Result rightChild = node.rightChild();
        if(!(leftChild.isSuccessful()) || !(rightChild.isSuccessful())){
            return new IncorrectResult("Addition has no declaration.");
        }
        return checkSemanticRules(leftChild,rightChild,node);
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
        return handleBinaryExpression(node);
    }

    private Result handleBinaryExpression(BinaryExpression expression){
        Result leftResult = expression.leftChild();
        if (!leftResult.isSuccessful()){
            return leftResult;
        }
        Result rightResult = expression.rightChild();
        if (!rightResult.isSuccessful()){
            return rightResult;
        }
        if(((CorrectResult<?>) leftResult).newObject() instanceof BinaryExpression binaryExpression){
            leftResult = visit(binaryExpression);
        }
        if(((CorrectResult<?>) rightResult).newObject() instanceof BinaryExpression binaryExpression){
            rightResult = visit(binaryExpression);
        }
        return checkSemanticRules(leftResult,rightResult,expression);
    }

    private Result addValue(DeclarationNode declaration, LiteralNode value){
        IdentifierNode id = (IdentifierNode) ((CorrectResult<?>) declaration.rightChild()).newObject();
        variablesTable.addValue(id.value(), new Symbol(declaration.leftChild().toString(),value));
        return new CorrectResult<>(variablesTable);
    }
    private Result addOnlyVariable(DeclarationNode declaration){
        IdentifierNode id = (IdentifierNode) ((CorrectResult<?>) declaration.rightChild()).newObject();
        variablesTable.addValue(id.value(),new Symbol(declaration.leftChild().toString(), null));
        return new CorrectResult<>(variablesTable);
    }

    private Result checkSemanticRules(Result left, Result right, BinaryExpression operator){
        LiteralNode leftLiteral = (LiteralNode) ((CorrectResult<?>) left).newObject();
        LiteralNode rightLiteral = (LiteralNode) ((CorrectResult<?>) right).newObject();
        return semanticRules.checkRules(leftLiteral,rightLiteral,operator);
    }
}
