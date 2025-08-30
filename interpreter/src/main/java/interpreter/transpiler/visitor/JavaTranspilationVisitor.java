package interpreter.transpiler.visitor;


import common.NilNode;
import common.Node;
import responses.CorrectResult;
import responses.Result;
import declaration.AscriptionNode;
import declaration.IdentifierNode;
import declaration.TypeNode;
import expression.binary.AdditionNode;
import expression.literal.LiteralNode;
import statements.LetStatementNode;
import statements.PrintStatementNode;
import visitor.VisitorInterface;

public class JavaTranspilationVisitor implements VisitorInterface {
    @Override
    public Result visit(LetStatementNode node) {
        Result getDeclarationResult = node.ascription();
        if (!getDeclarationResult.isSuccessful()) return getDeclarationResult;
        Node declarationNode = ( (CorrectResult<Node>) getDeclarationResult).result();
        Result visitDeclarationResult = declarationNode.accept(this);
        if (!visitDeclarationResult.isSuccessful()) return visitDeclarationResult;
        String declarationString = ( (CorrectResult<String>) visitDeclarationResult).result();

        Result getExpressionResult = node.expression();
        if (!getExpressionResult.isSuccessful()) {
            return new CorrectResult<>(declarationString + ";");
        }
        Node expressionNode = ( (CorrectResult<Node>) getExpressionResult).result();
        Result visitExpressionResult = expressionNode.accept(this);
        if (!visitExpressionResult.isSuccessful()) return visitExpressionResult;
        String expresionString = ( (CorrectResult<String>) visitExpressionResult).result();

        return new CorrectResult<>(declarationString + " = " + expresionString + ";");
    }

    @Override
    public Result visit(PrintStatementNode node) {
        Result getExpressionResult = node.expression();
        if (!getExpressionResult.isSuccessful()) return getExpressionResult;
        Node expressionNode = ( (CorrectResult<Node>) getExpressionResult).result();
        Result visitExpressionResult = expressionNode.accept(this);
        if (!visitExpressionResult.isSuccessful()) return visitExpressionResult;
        String expresionString = ( (CorrectResult<String>) visitExpressionResult).result();

        String resultString = "System.out.println(" + expresionString + ");";

        return new CorrectResult<>(resultString);
    }

    @Override
    public Result visit(AscriptionNode node) {
        Result getTypeResult = node.type();
        if (!getTypeResult.isSuccessful()) return getTypeResult;
        Node typeNode = ( (CorrectResult<Node>) getTypeResult).result();
        Result visitTypeResult = typeNode.accept(this);
        if (!visitTypeResult.isSuccessful()) return visitTypeResult;
        String typeString = ( (CorrectResult<String>) visitTypeResult).result();

        Result getIdentifierResult = node.identifier();
        if (!getIdentifierResult.isSuccessful()) return getIdentifierResult;
        Node identifierNode = ( (CorrectResult<Node>) getIdentifierResult).result();
        Result visitIdentifierResult = identifierNode.accept(this);
        if (!visitIdentifierResult.isSuccessful()) return visitIdentifierResult;
        String identifierString = ( (CorrectResult<String>) visitIdentifierResult).result();

        return new CorrectResult<>(typeString + " " + identifierString);
    }

    @Override
    public Result visit(AdditionNode node) {
        Result getLeftChildResult = node.leftChild();
        if (!getLeftChildResult.isSuccessful()) return getLeftChildResult;
        Node leftChildNode = ( (CorrectResult<Node>) getLeftChildResult).result();
        Result visitLeftChildResult = leftChildNode.accept(this);
        if (!visitLeftChildResult.isSuccessful()) return visitLeftChildResult;
        String leftChildString = ( (CorrectResult<String>) visitLeftChildResult).result();

        Result getRightChildResult = node.rightChild();
        if (!getRightChildResult.isSuccessful()) return getRightChildResult;
        Node rightChildNode = ( (CorrectResult<Node>) getRightChildResult).result();
        Result visitRightChildResult = rightChildNode.accept(this);
        if (!visitRightChildResult.isSuccessful()) return visitRightChildResult;
        String rightChildString = ( (CorrectResult<String>) visitRightChildResult).result();

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
