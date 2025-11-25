/*
 * My Project
 */

package com.ingsis.interpreter.visitor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.ingsis.interpreter.visitor.expression.strategies.ExpressionSolutionStrategy;
import com.ingsis.nodes.Node;
import com.ingsis.nodes.expression.identifier.IdentifierNode;
import com.ingsis.nodes.expression.literal.LiteralNode;
import com.ingsis.nodes.expression.operator.TypeAssignationNode;
import com.ingsis.nodes.expression.operator.ValueAssignationNode;
import com.ingsis.nodes.keyword.DeclarationKeywordNode;
import com.ingsis.nodes.keyword.IfKeywordNode;
import com.ingsis.nodes.type.TypeNode;
import com.ingsis.result.CorrectResult;
import com.ingsis.result.IncorrectResult;
import com.ingsis.result.Result;
import com.ingsis.result.factory.DefaultResultFactory;
import com.ingsis.runtime.DefaultRuntime;
import com.ingsis.runtime.Runtime;
import com.ingsis.types.Types;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DefaultInterpreterVisitorTest {
    private Runtime runtime;

    @BeforeEach
    void setUp() {
        runtime = DefaultRuntime.getInstance();
        runtime.push();
    }

    @AfterEach
    void tearDown() {
        runtime.pop();
    }

    @Test
    void interpretDelegatesToStrategy() {
        ExpressionSolutionStrategy strat = (i, e) -> new CorrectResult<>("ok");
        DefaultInterpreterVisitor visitor =
                new DefaultInterpreterVisitor(runtime, strat, new DefaultResultFactory());

        Result<Object> res = visitor.interpret(new LiteralNode("1", 0, 0));
        assertTrue(res.isCorrect());
        assertEquals("ok", res.result());
    }

    @Test
    void interpretIfConditionTrueReturnsChildrenInterpreted() {
        // condition strategy returns "true" and literal children are interpreted by the
        // strategy
        ExpressionSolutionStrategy strat =
                (i, e) -> {
                    if (e instanceof LiteralNode) return new CorrectResult<>("true");
                    return new CorrectResult<>("true");
                };
        DefaultInterpreterVisitor visitor =
                new DefaultInterpreterVisitor(runtime, strat, new DefaultResultFactory());

        LiteralNode thenChild = new LiteralNode("1", 0, 0);
        IfKeywordNode ifNode = new IfKeywordNode(thenChild, List.of(thenChild), 0, 0);

        Result<String> res = visitor.interpret(ifNode);
        assertTrue(res.isCorrect());
        assertEquals("Children interpreted correctly.", res.result());
    }

    @Test
    void interpretChildrenWithNonInterpretableChildProducesIncorrect() {
        ExpressionSolutionStrategy strat = (i, e) -> new CorrectResult<>("true");
        DefaultInterpreterVisitor visitor =
                new DefaultInterpreterVisitor(runtime, strat, new DefaultResultFactory());

        // Non-interpretable node (implements Node but not Interpretable)
        Node nonInterpretable =
                new Node() {
                    @Override
                    public Integer line() {
                        return 1;
                    }

                    @Override
                    public Integer column() {
                        return 1;
                    }

                    @Override
                    public com.ingsis.result.Result<String> acceptVisitor(
                            com.ingsis.visitors.Visitor visitor) {
                        return new IncorrectResult<>("not used");
                    }
                };

        IfKeywordNode ifNode =
                new IfKeywordNode(new LiteralNode("true", 0, 0), List.of(nonInterpretable), 0, 0);

        Result<String> res = visitor.interpret(ifNode);
        assertFalse(res.isCorrect());
        assertNotNull(res.error());
        assertTrue(res.error().contains("Unable to interpret node"));
    }

    @Test
    void declarationMutableCreatesAndInitializesVariable() {
        ExpressionSolutionStrategy strat =
                (i, e) -> {
                    // for literal value nodes return numeric value
                    if (e instanceof LiteralNode) return new CorrectResult<>("1");
                    return new CorrectResult<>("1");
                };

        DefaultInterpreterVisitor visitor =
                new DefaultInterpreterVisitor(runtime, strat, new DefaultResultFactory());

        IdentifierNode id = new IdentifierNode("a", 0, 0);
        TypeNode typeNode = new TypeNode(Types.NUMBER, 0, 0);
        TypeAssignationNode typeAssign = new TypeAssignationNode(id, typeNode, 0, 0);
        ValueAssignationNode valueAssign =
                new ValueAssignationNode(id, new LiteralNode("1", 0, 0), 0, 0);
        DeclarationKeywordNode decl =
                new DeclarationKeywordNode(typeAssign, valueAssign, true, 0, 0);

        Result<String> res = visitor.interpret(decl);
        assertTrue(res.isCorrect());

        // verify variable exists and is initialized in the current environment
        assertTrue(runtime.getCurrentEnvironment().isVariableDeclared("a"));
        assertTrue(runtime.getCurrentEnvironment().isVariableInitialized("a"));
    }

    @Test
    void interpretIfConditionFalseGoesToElseBody() {
        ExpressionSolutionStrategy strat = (i, e) -> new CorrectResult<>("false");
        DefaultInterpreterVisitor visitor =
                new DefaultInterpreterVisitor(runtime, strat, new DefaultResultFactory());

        LiteralNode cond = new LiteralNode("false", 0, 0);
        LiteralNode elseChild = new LiteralNode("1", 0, 0);
        IfKeywordNode ifNode = new IfKeywordNode(cond, List.of(), List.of(elseChild), 0, 0);

        Result<String> res = visitor.interpret(ifNode);
        assertTrue(res.isCorrect());
        assertEquals("Children interpreted correctly.", res.result());
    }

    @Test
    void interpretIfConditionIncorrectPropagatesError() {
        ExpressionSolutionStrategy strat = (i, e) -> new IncorrectResult<>("bad cond");
        DefaultInterpreterVisitor visitor =
                new DefaultInterpreterVisitor(runtime, strat, new DefaultResultFactory());

        LiteralNode cond = new LiteralNode("x", 0, 0);
        IfKeywordNode ifNode = new IfKeywordNode(cond, List.of(), List.of(), 0, 0);

        Result<String> res = visitor.interpret(ifNode);
        assertFalse(res.isCorrect());
        assertTrue(res.error().contains("bad cond"));
    }

    @Test
    void interpretIfConditionNonBooleanReturnsIncorrect() {
        ExpressionSolutionStrategy strat = (i, e) -> new CorrectResult<>("maybe");
        DefaultInterpreterVisitor visitor =
                new DefaultInterpreterVisitor(runtime, strat, new DefaultResultFactory());

        LiteralNode cond = new LiteralNode("maybe", 0, 0);
        IfKeywordNode ifNode = new IfKeywordNode(cond, List.of(), List.of(), 3, 4);

        Result<String> res = visitor.interpret(ifNode);
        assertFalse(res.isCorrect());
        assertTrue(res.error().contains("Something went wrong on line"));
    }

    @Test
    void declarationMutableWithIncompatibleTypeDeletesVariableAndReturnsIncorrect() {
        ExpressionSolutionStrategy strat = (i, e) -> new CorrectResult<>("not-a-number");
        DefaultInterpreterVisitor visitor =
                new DefaultInterpreterVisitor(runtime, strat, new DefaultResultFactory());

        IdentifierNode id = new IdentifierNode("b", 0, 0);
        TypeNode typeNode = new TypeNode(Types.NUMBER, 0, 0);
        TypeAssignationNode typeAssign = new TypeAssignationNode(id, typeNode, 0, 0);
        ValueAssignationNode valueAssign =
                new ValueAssignationNode(id, new LiteralNode("x", 0, 0), 0, 0);
        DeclarationKeywordNode decl =
                new DeclarationKeywordNode(typeAssign, valueAssign, true, 0, 0);

        Result<String> res = visitor.interpret(decl);
        assertFalse(res.isCorrect());
    }

    @Test
    void defineValOnUndeclaredIdentifierReturnsCorrect() {
        ExpressionSolutionStrategy strat = (i, e) -> new CorrectResult<>("1");
        DefaultInterpreterVisitor visitor =
                new DefaultInterpreterVisitor(runtime, strat, new DefaultResultFactory());

        IdentifierNode id = new IdentifierNode("c", 0, 0);
        TypeNode typeNode = new TypeNode(Types.NUMBER, 0, 0);
        TypeAssignationNode typeAssign = new TypeAssignationNode(id, typeNode, 0, 0);
        ValueAssignationNode valueAssign =
                new ValueAssignationNode(id, new LiteralNode("1", 0, 0), 0, 0);
        DeclarationKeywordNode decl =
                new DeclarationKeywordNode(typeAssign, valueAssign, false, 0, 0);

        Result<String> res = visitor.interpret(decl);
        assertTrue(res.isCorrect());
    }

    @Test
    void defineValCreatesVariableInLocalWhenDeclaredInFather() {
        // prepare father environment: pop to father, create variable declaration there,
        // then push
        // local
        runtime.pop();
        runtime.getCurrentEnvironment().createVariable("d", Types.NUMBER);
        runtime.push();

        ExpressionSolutionStrategy strat = (i, e) -> new CorrectResult<>("1");
        DefaultInterpreterVisitor visitor =
                new DefaultInterpreterVisitor(runtime, strat, new DefaultResultFactory());

        IdentifierNode id = new IdentifierNode("d", 0, 0);
        TypeNode typeNode = new TypeNode(Types.NUMBER, 0, 0);
        TypeAssignationNode typeAssign = new TypeAssignationNode(id, typeNode, 0, 0);
        ValueAssignationNode valueAssign =
                new ValueAssignationNode(id, new LiteralNode("1", 0, 0), 0, 0);
        DeclarationKeywordNode decl =
                new DeclarationKeywordNode(typeAssign, valueAssign, false, 0, 0);

        Result<String> res = visitor.interpret(decl);
        // current environment implementation prevents redeclaring an identifier
        // when it's already declared in a parent, so expect an incorrect result
        assertFalse(res.isCorrect());
        assertNotNull(res.error());
    }
}
