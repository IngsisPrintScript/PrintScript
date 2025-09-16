/*
 * My Project
 */

package com.ingsis.printscript.astnodes.factories;

import com.ingsis.printscript.astnodes.Node;
import com.ingsis.printscript.astnodes.declaration.AscriptionNode;
import com.ingsis.printscript.astnodes.declaration.TypeNode;
import com.ingsis.printscript.astnodes.expression.binary.AdditionNode;
import com.ingsis.printscript.astnodes.expression.binary.AssignationNode;
import com.ingsis.printscript.astnodes.expression.binary.v1.DoubleEqualNode;
import com.ingsis.printscript.astnodes.expression.binary.v1.GreaterThanNode;
import com.ingsis.printscript.astnodes.expression.binary.v1.LesserThanNode;
import com.ingsis.printscript.astnodes.expression.identifier.IdentifierNode;
import com.ingsis.printscript.astnodes.expression.literal.LiteralNode;
import com.ingsis.printscript.astnodes.statements.LetStatementNode;
import com.ingsis.printscript.astnodes.statements.PrintStatementNode;

public class NodeFactory implements NodeFactoryInterface {
    @Override
    public Node createLetStatementNode() {
        return new LetStatementNode();
    }

    @Override
    public Node createPrintlnStatementNode() {
        return new PrintStatementNode();
    }

    @Override
    public Node createIdentifierNode(String identifier) {
        return new IdentifierNode(identifier);
    }

    @Override
    public Node createAscriptionNode() {
        return new AscriptionNode();
    }

    public Node createAssignationNode() {
        return new AssignationNode();
    }

    @Override
    public Node createTypeNode(String type) {
        return new TypeNode(type);
    }

    @Override
    public Node createLiteralNode(String literal) {
        return new LiteralNode(literal);
    }

    @Override
    public Node createAdditionNode() {
        return new AdditionNode();
    }

    @Override
    public Node createDoubleEqualNode() {
        return new DoubleEqualNode();
    }

    @Override
    public Node createGreaterThanNode() {
        return new GreaterThanNode();
    }

    @Override
    public Node createLesserThanNode() {
        return new LesserThanNode();
    }
}
