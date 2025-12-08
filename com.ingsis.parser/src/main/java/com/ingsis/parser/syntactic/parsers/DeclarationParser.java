/*
 * My Project
 */

package com.ingsis.parser.syntactic.parsers;

import com.ingsis.parser.syntactic.NodePriority;
import com.ingsis.utils.iterator.safe.result.SafeIterationResult;
import com.ingsis.utils.nodes.Node;
import com.ingsis.utils.nodes.expressions.ExpressionNode;
import com.ingsis.utils.nodes.expressions.atomic.identifier.IdentifierNode;
import com.ingsis.utils.nodes.factories.NodeFactory;
import com.ingsis.utils.process.checkpoint.ProcessCheckpoint;
import com.ingsis.utils.process.result.ProcessResult;
import com.ingsis.utils.token.Token;
import com.ingsis.utils.token.template.TokenTemplate;
import com.ingsis.utils.token.tokenstream.TokenStream;
import com.ingsis.utils.type.types.Types;
import java.util.List;

public class DeclarationParser implements Parser<Node> {
    private final List<TokenTemplate> declarationTemplates;
    private final TokenTemplate spaceTemplate;
    private final TokenTemplate colonTemplate;
    private final List<TokenTemplate> typeTemplates;
    private final TokenTemplate equalsTemplate;
    private final TokenTemplate semicolonTemplate;
    private final Parser<ExpressionNode> identifierParser;
    private final Parser<ExpressionNode> expressionParser;
    private final NodeFactory nodeFactory;

    public DeclarationParser(
            List<TokenTemplate> declarationTemplates,
            TokenTemplate spaceTemplate,
            TokenTemplate colonTemplate,
            List<TokenTemplate> typeTemplates,
            TokenTemplate equalsTemplate,
            TokenTemplate semicolonTemplate,
            Parser<ExpressionNode> identifierParser,
            Parser<ExpressionNode> expressionParser,
            NodeFactory nodeFactory) {
        this.declarationTemplates = declarationTemplates;
        this.spaceTemplate = spaceTemplate;
        this.typeTemplates = typeTemplates;
        this.colonTemplate = colonTemplate;
        this.equalsTemplate = equalsTemplate;
        this.semicolonTemplate = semicolonTemplate;
        this.identifierParser = identifierParser;
        this.expressionParser = expressionParser;
        this.nodeFactory = nodeFactory;
    }

    @Override
    public ProcessCheckpoint<Token, ProcessResult<Node>> parse(TokenStream stream) {

        SafeIterationResult<Token> consumeDeclaration = null;
        for (TokenTemplate declarationTemplate : declarationTemplates) {
            consumeDeclaration = stream.consume(declarationTemplate);
            if (consumeDeclaration.isCorrect()) {
                break;
            }
        }
        if (consumeDeclaration == null || !consumeDeclaration.isCorrect()) {
            return ProcessCheckpoint.UNINITIALIZED();
        }
        Token declarationToken = consumeDeclaration.iterationResult();

        stream = (TokenStream) consumeDeclaration.nextIterator();
        stream = consumeNoice(stream);

        ProcessCheckpoint<Token, ProcessResult<ExpressionNode>> processIdentifierResult =
                identifierParser.parse(stream);
        if (processIdentifierResult.isUninitialized()) {
            return ProcessCheckpoint.INITIALIZED(
                    stream, ProcessResult.PREFIX(NodePriority.STATEMENT.priority()));
        } else if (!processIdentifierResult.result().isComplete()) {
            return ProcessCheckpoint.INITIALIZED(
                    processIdentifierResult.iterator(),
                    ProcessResult.PREFIX(NodePriority.STATEMENT.priority()));
        }

        if (!(processIdentifierResult.result().result() instanceof IdentifierNode identifierNode)) {
            return ProcessCheckpoint.INITIALIZED(
                    processIdentifierResult.iterator(),
                    ProcessResult.PREFIX(NodePriority.STATEMENT.priority()));
        }

        stream = (TokenStream) processIdentifierResult.iterator();
        stream = consumeNoice(stream);

        SafeIterationResult<Token> consumeTypeAssignationOperator = stream.consume(colonTemplate);
        if (!consumeTypeAssignationOperator.isCorrect()) {
            return ProcessCheckpoint.INITIALIZED(
                    stream, ProcessResult.PREFIX(NodePriority.STATEMENT.priority()));
        }

        stream = (TokenStream) consumeTypeAssignationOperator.nextIterator();
        stream = consumeNoice(stream);

        SafeIterationResult<Token> consumeType = null;
        for (TokenTemplate typeTemplate : typeTemplates) {
            consumeType = stream.consume(typeTemplate);
            if (consumeType.isCorrect()) {
                break;
            }
        }
        if (consumeType == null || !consumeType.isCorrect()) {
            return ProcessCheckpoint.INITIALIZED(
                    stream, ProcessResult.PREFIX(NodePriority.STATEMENT.priority()));
        }
        Types declaredType = Types.fromKeyword(consumeType.iterationResult().value());

        stream = (TokenStream) consumeType.nextIterator();
        stream = consumeNoice(stream);

        SafeIterationResult<Token> consumeSemiColon = stream.consume(semicolonTemplate);
        if (consumeSemiColon.isCorrect()) {
            stream = (TokenStream) consumeSemiColon.nextIterator();
            return ProcessCheckpoint.INITIALIZED(
                    stream,
                    ProcessResult.COMPLETE(
                            nodeFactory.createDeclarationNode(
                                    identifierNode,
                                    nodeFactory.createNilExpressionNode(),
                                    declaredType,
                                    declarationToken.value().equals("let"),
                                    declarationToken.line(),
                                    declarationToken.column()),
                            NodePriority.STATEMENT.priority()));
        }

        SafeIterationResult<Token> consumeEquals = stream.consume(equalsTemplate);
        if (!consumeEquals.isCorrect()) {
            return ProcessCheckpoint.INITIALIZED(
                    stream, ProcessResult.PREFIX(NodePriority.STATEMENT.priority()));
        }

        stream = (TokenStream) consumeEquals.nextIterator();
        stream = consumeNoice(stream);

        ProcessCheckpoint<Token, ProcessResult<ExpressionNode>> processExpressionResult =
                expressionParser.parse(stream);
        if (processExpressionResult.isUninitialized()) {
            return ProcessCheckpoint.INITIALIZED(
                    stream, ProcessResult.PREFIX(NodePriority.STATEMENT.priority()));
        } else if (!processExpressionResult.result().isComplete()) {
            return ProcessCheckpoint.INITIALIZED(
                    processExpressionResult.iterator(),
                    ProcessResult.PREFIX(NodePriority.STATEMENT.priority()));
        }
        ExpressionNode expressionNode = processExpressionResult.result().result();

        stream = (TokenStream) processExpressionResult.iterator();
        stream = consumeNoice(stream);

        SafeIterationResult<Token> consumeFinalSemiColon = stream.consume(semicolonTemplate);
        if (!consumeFinalSemiColon.isCorrect()) {
            return ProcessCheckpoint.INITIALIZED(
                    stream, ProcessResult.PREFIX(NodePriority.STATEMENT.priority()));
        }

        stream = (TokenStream) consumeFinalSemiColon.nextIterator();
        stream = consumeNoice(stream);

        return ProcessCheckpoint.INITIALIZED(
                stream,
                ProcessResult.COMPLETE(
                        nodeFactory.createDeclarationNode(
                                identifierNode,
                                expressionNode,
                                declaredType,
                                declarationToken.value().equals("let"),
                                declarationToken.line(),
                                declarationToken.column()),
                        NodePriority.STATEMENT.priority()));
    }

    private TokenStream consumeNoice(TokenStream stream) {
        return stream.consumeAll(spaceTemplate);
    }
}
