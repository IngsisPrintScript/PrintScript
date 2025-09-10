package com.ingsis.printscript.syntactic.ast.builders.expression.binary.operators;

import com.ingsis.printscript.tokens.TokenInterface;
import com.ingsis.printscript.astnodes.expression.binary.AssignationNode;
import com.ingsis.printscript.astnodes.expression.binary.BinaryExpression;
import com.ingsis.printscript.astnodes.factories.NodeFactory;
import com.ingsis.printscript.tokens.factories.TokenFactory;
import com.ingsis.printscript.results.CorrectResult;
import com.ingsis.printscript.results.IncorrectResult;
import com.ingsis.printscript.results.Result;
import com.ingsis.printscript.tokens.stream.TokenStreamInterface;

public class AssignationOperatorBuilder extends BinaryOperatorBuilder {
    private final TokenInterface assignationTemplate = new TokenFactory().createAssignationToken();
    private final TokenInterface identifierTemplate = new TokenFactory().createIdentifierToken("placeholder");
    private final TokenInterface literalTemplate = new TokenFactory().createLiteralToken("placeholder");

    @Override
    public Boolean canBuild(TokenStreamInterface tokenStream) {
        Result<TokenInterface> peekResult = tokenStream.peek();
        if (!peekResult.isSuccessful()) {return false;}
        TokenInterface token = peekResult.result();
        return token.equals(assignationTemplate);
    }

    @Override
    public Result<BinaryExpression> build(TokenStreamInterface tokenStream) {
        if (!canBuild(tokenStream)) {
            return new IncorrectResult<>("Cannot build this token stream.");
        }

        if (!tokenStream.consume(assignationTemplate).isSuccessful()){
            return new IncorrectResult<>("Cannot build this token stream.");
        }
        AssignationNode assignationNode = (AssignationNode) new NodeFactory().createAssignationNode();
        return new CorrectResult<>(assignationNode);
    }
}
