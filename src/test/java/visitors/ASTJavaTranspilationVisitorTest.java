package visitors;

import nodes.AbstractSyntaxTreeComponent;
import nodes.expressions.composite.AssignationExpressionNode;
import nodes.expressions.composite.TypeAssignationExpressionNode;
import nodes.expressions.leaf.IdentifierExpressionNode;
import nodes.expressions.leaf.LiteralExpressionNode;
import nodes.expressions.leaf.TypeExpressionNode;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import responses.CorrectResponse;
import responses.Response;

public class ASTJavaTranspilationVisitorTest {
    private static AbstractSyntaxTreeComponent declarationAndAssignation;
    private static AbstractSyntaxTreeComponent declaration;


    @BeforeAll
    static void setup() {
        AbstractSyntaxTreeComponent temp;
        AbstractSyntaxTreeComponent node = new TypeAssignationExpressionNode();
        node.addChild(new TypeExpressionNode("String"));
        node.addChild(new IdentifierExpressionNode("aVariable"));
        declaration = node;
        temp = node;
        node = new AssignationExpressionNode();
        node.addChild(temp);
        node.addChild(new LiteralExpressionNode("\"aString\""));
        declarationAndAssignation =node;
    }

    @Test
    public void creationTest(){
        AbstractSyntaxNodeVisitorInterface visitor = new AbstractSyntaxNodeJavaTranspilationVisitor();
        Assertions.assertNotNull(visitor);
    }

    @Test
    public void visitTest(){
        AbstractSyntaxNodeVisitorInterface visitor = new AbstractSyntaxNodeJavaTranspilationVisitor();
        Response visitResponse = declarationAndAssignation.accept(visitor);
        Assertions.assertNotNull(visitResponse);
        Assertions.assertTrue(visitResponse.isSuccessful());
        Object responseNewObject = ( (CorrectResponse<?>) visitResponse).newObject();
        Assertions.assertInstanceOf(String.class, responseNewObject);
        String transpiledCode = (String)responseNewObject;
        Assertions.assertEquals("String aVariable = \"aString\"",  transpiledCode);
        visitResponse = declaration.accept(visitor);
        Assertions.assertNotNull(visitResponse);
        Assertions.assertTrue(visitResponse.isSuccessful());
        responseNewObject = ( (CorrectResponse<?>) visitResponse).newObject();
        Assertions.assertInstanceOf(String.class, responseNewObject);
        transpiledCode = (String)responseNewObject;
        Assertions.assertEquals("String aVariable",  transpiledCode);
    }
}
