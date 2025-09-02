package formatter;

import declaration.AscriptionNode;
import declaration.TypeNode;
import expression.binary.AdditionNode;
import expression.identifier.IdentifierNode;
import expression.literal.LiteralNode;
import org.junit.jupiter.api.Test;
import results.Result;
import statements.LetStatementNode;

import static org.junit.jupiter.api.Assertions.*;

class FormatterBasicRules {

    @Test
    void testLetWithAscriptionAndAddition() {
        IdentifierNode identifier = new IdentifierNode("name");
        TypeNode type = new TypeNode("String");
        AscriptionNode ascription = new AscriptionNode();
        ascription.setIdentifier(identifier);
        ascription.setType(type);

        LiteralNode hola = new LiteralNode("\"Hola\"");
        LiteralNode pepe = new LiteralNode("\"pepe\"");
        AdditionNode addition = new AdditionNode();
        addition.setLeftChild(hola);
        addition.setRightChild(pepe);
        LetStatementNode let = new LetStatementNode();
        let.setAscription(ascription);
        let.setExpression(addition);
        ReadRules reader = new ReadRules();
        Result<String> formatter = new Formatter(reader).format(let);

        assertTrue(formatter.isSuccessful());
        assertEquals("let name :String = \"Hola\" + \"pepe\";\n", formatter.result());
    }
}
