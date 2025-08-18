package interpreter.transpiler.visitor;

import common.nodes.NilNode;
import common.nodes.Node;
import common.nodes.declaration.AscriptionNode;
import common.nodes.declaration.IdentifierNode;
import common.nodes.declaration.TypeNode;
import common.nodes.expression.binary.AdditionNode;
import common.nodes.expression.literal.LiteralNode;
import common.nodes.statements.LetStatementNode;
import common.nodes.statements.PrintStatementNode;
import common.responses.CorrectResult;
import common.responses.Result;
import common.visitor.VisitorInterface;

public class JavaTranspilationVisitor implements VisitorInterface {
    @Override
    public Result visit(LetStatementNode node) {
        Result getDeclarationResult = node.ascription();
        if (!getDeclarationResult.isSuccessful()) return getDeclarationResult;
        Node declarationNode = ( (CorrectResult<Node>) getDeclarationResult).newObject();
        Result visitDeclarationResult = declarationNode.accept(this);
        if (!visitDeclarationResult.isSuccessful()) return visitDeclarationResult;
        String declarationString = ( (CorrectResult<String>) visitDeclarationResult).newObject();

        Result getExpressionResult = node.expression();
        if (!getExpressionResult.isSuccessful()) {
            return new CorrectResult<>(declarationString + ";");
        }
        Node expressionNode = ( (CorrectResult<Node>) getExpressionResult).newObject();
        Result visitExpressionResult = expressionNode.accept(this);
        if (!visitExpressionResult.isSuccessful()) return visitExpressionResult;
        String expresionString = ( (CorrectResult<String>) visitExpressionResult).newObject();

        return new CorrectResult<>(declarationString + " = " + expresionString + ";");
    }

    @Override
    public Result visit(PrintStatementNode node) {
        Result getExpressionResult = node.expression();
        if (!getExpressionResult.isSuccessful()) return getExpressionResult;
        Node expressionNode = ( (CorrectResult<Node>) getExpressionResult).newObject();
        Result visitExpressionResult = expressionNode.accept(this);
        if (!visitExpressionResult.isSuccessful()) return visitExpressionResult;
        String expresionString = ( (CorrectResult<String>) visitExpressionResult).newObject();

        String resultString = "System.out.println(" + expresionString + ");";

        return new CorrectResult<>(resultString);
    }

    @Override
    public Result visit(AscriptionNode node) {
        Result getTypeResult = node.type();
        if (!getTypeResult.isSuccessful()) return getTypeResult;
        Node typeNode = ( (CorrectResult<Node>) getTypeResult).newObject();
        Result visitTypeResult = typeNode.accept(this);
        if (!visitTypeResult.isSuccessful()) return visitTypeResult;
        String typeString = ( (CorrectResult<String>) visitTypeResult).newObject();

        Result getIdentifierResult = node.identifier();
        if (!getIdentifierResult.isSuccessful()) return getIdentifierResult;
        Node identifierNode = ( (CorrectResult<Node>) getIdentifierResult).newObject();
        Result visitIdentifierResult = identifierNode.accept(this);
        if (!visitIdentifierResult.isSuccessful()) return visitIdentifierResult;
        String identifierString = ( (CorrectResult<String>) visitIdentifierResult).newObject();

        return new CorrectResult<>(typeString + " " + identifierString);
    }

    @Override
    public Result visit(AdditionNode node) {
        Result getLeftChildResult = node.leftChild();
        if (!getLeftChildResult.isSuccessful()) return getLeftChildResult;
        Node leftChildNode = ( (CorrectResult<Node>) getLeftChildResult).newObject();
        Result visitLeftChildResult = leftChildNode.accept(this);
        if (!visitLeftChildResult.isSuccessful()) return visitLeftChildResult;
        String leftChildString = ( (CorrectResult<String>) visitLeftChildResult).newObject();

        Result getRightChildResult = node.rightChild();
        if (!getRightChildResult.isSuccessful()) return getRightChildResult;
        Node rightChildNode = ( (CorrectResult<Node>) getRightChildResult).newObject();
        Result visitRightChildResult = rightChildNode.accept(this);
        if (!visitRightChildResult.isSuccessful()) return visitRightChildResult;
        String rightChildString = ( (CorrectResult<String>) visitRightChildResult).newObject();

        return new CorrectResult<>(leftChildString + " + " + rightChildString);
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
    public Result visit(TypeNode node) {
        return new CorrectResult<>(node.value());
    }

    @Override
    public Result visit(NilNode node) {
        return new CorrectResult<>("");
    }
}
