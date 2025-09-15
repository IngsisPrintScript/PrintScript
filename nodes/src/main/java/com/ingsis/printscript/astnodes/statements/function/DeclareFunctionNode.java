/*
 * My Project
 */

package com.ingsis.printscript.astnodes.statements.function;

import com.ingsis.printscript.astnodes.Node;
import com.ingsis.printscript.astnodes.declaration.TypeNode;
import com.ingsis.printscript.astnodes.expression.identifier.IdentifierNode;
import com.ingsis.printscript.astnodes.statements.function.argument.DeclarationArgumentNode;
import com.ingsis.printscript.results.CorrectResult;
import com.ingsis.printscript.results.IncorrectResult;
import com.ingsis.printscript.results.Result;
import com.ingsis.printscript.visitor.InterpretVisitorInterface;
import com.ingsis.printscript.visitor.InterpretableNode;
import com.ingsis.printscript.visitor.RuleVisitor;
import com.ingsis.printscript.visitor.SemanticallyCheckable;
import com.ingsis.printscript.visitor.VisitorInterface;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public final class DeclareFunctionNode implements Node, SemanticallyCheckable, InterpretableNode {
    private final IdentifierNode identifier;
    private final Collection<DeclarationArgumentNode> arguments;
    private final Collection<InterpretableNode> body;
    private final TypeNode returnType;

    public DeclareFunctionNode(
            IdentifierNode identifier,
            Collection<DeclarationArgumentNode> arguments,
            Collection<InterpretableNode> body,
            TypeNode returnType) {
        this.identifier = identifier;
        this.arguments = Collections.unmodifiableCollection(arguments);
        this.body = Collections.unmodifiableCollection(body);
        this.returnType = returnType;
    }

    public boolean hasIdentifier() {
        return !identifier.isNil();
    }

    public boolean hasReturnType() {
        return !returnType.isNil();
    }

    public Result<IdentifierNode> identifier() {
        if (!hasIdentifier()) {
            return new IncorrectResult<>("Declare Function Node has no identifier");
        }
        return new CorrectResult<>(identifier);
    }

    public Result<Collection<DeclarationArgumentNode>> arguments() {
        return new CorrectResult<>(arguments);
    }

    public Result<Collection<InterpretableNode>> body() {
        return new CorrectResult<>(body);
    }

    public Result<TypeNode> returnType() {
        if (!hasReturnType()) {
            return new IncorrectResult<>("Declare Function Node has no returnType");
        }
        return new CorrectResult<>(returnType);
    }

    @Override
    public List<Node> children() {
        List<Node> children = new ArrayList<>();
        children.add(identifier);
        children.addAll(arguments);
        children.addAll(body);
        children.add(returnType);
        return List.copyOf(children);
    }

    @Override
    public Boolean isNil() {
        return false;
    }

    @Override
    public Result<String> accept(VisitorInterface visitor) {
        return visitor.visit(this);
    }

    @Override
    public Result<String> acceptCheck(RuleVisitor checker) {
        return checker.check(this);
    }

    @Override
    public Result<String> acceptInterpreter(InterpretVisitorInterface interpreter) {
        return interpreter.interpret(this);
    }
}
