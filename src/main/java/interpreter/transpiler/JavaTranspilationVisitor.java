package interpreter.transpiler;

import common.nodes.NilNode;
import common.nodes.declaration.AscriptionNode;
import common.nodes.declaration.IdentifierNode;
import common.nodes.declaration.TypeNode;
import common.nodes.expression.binary.AdditionNode;
import common.nodes.expression.literal.LiteralNode;
import common.nodes.statements.LetStatementNode;
import common.nodes.statements.PrintStatementNode;
import common.responses.CorrectResult;
import common.responses.Result;

public class JavaTranspilationVisitor extends TranspilerVisitor {
    @Override
    public Result visit(LetStatementNode node) {
        Result getDeclarationResult = node.declaration();
        if (!getDeclarationResult.isSuccessful()) return getDeclarationResult;
        String declarationString = ( (CorrectResult<String>) getDeclarationResult).newObject();

        Result getExpressionResult = node.expression();
        if (!getExpressionResult.isSuccessful()) {
            return new CorrectResult<>(declarationString + ";");
        }
        String expresionString = ( (CorrectResult<String>) getExpressionResult).newObject();
        return new CorrectResult<>(declarationString + " = " + expresionString + ";");
    }

    @Override
    public Result visit(PrintStatementNode node) {
        Result getExpressionResult = node.expression();
        if (!getExpressionResult.isSuccessful()) return getExpressionResult;
        String expresionString = ( (CorrectResult<String>) getExpressionResult).newObject();

        String resultString = "System.out.println(" + expresionString + ");";

        return new CorrectResult<>(resultString);
    }

    @Override
    public Result visit(AscriptionNode node) {
        Result getTypeResult = node.type();
        if (!getTypeResult.isSuccessful()) return getTypeResult;
        String typeString = ( (CorrectResult<String>) getTypeResult).newObject();

        Result getIdentifierResult = node.identifier();
        if (!getIdentifierResult.isSuccessful()) return getIdentifierResult;
        String identifierString = ( (CorrectResult<String>) getIdentifierResult).newObject();

        return new CorrectResult<>(typeString + " " + identifierString);
    }

    @Override
    public Result visit(AdditionNode node) {
        Result getLeftChildResult = node.leftChild();
        if (!getLeftChildResult.isSuccessful()) return getLeftChildResult;
        String leftChildString = ( (CorrectResult<String>) getLeftChildResult).newObject();

        Result getRightChildResult = node.rightChild();
        if (!getRightChildResult.isSuccessful()) return getRightChildResult;
        String rightChildString = ( (CorrectResult<String>) getRightChildResult).newObject();

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
