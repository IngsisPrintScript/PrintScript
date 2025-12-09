/*
 * My Project
 */

package com.ingsis.parser.syntactic.parsers;

import com.ingsis.parser.syntactic.NodePriority;
import com.ingsis.utils.iterator.safe.result.DefaultIterationResultFactory;
import com.ingsis.utils.iterator.safe.result.SafeIterationResult;
import com.ingsis.utils.nodes.Node;
import com.ingsis.utils.nodes.expressions.ExpressionNode;
import com.ingsis.utils.nodes.expressions.atomic.identifier.IdentifierNode;
import com.ingsis.utils.nodes.factories.NodeFactory;
import com.ingsis.utils.process.checkpoint.ProcessCheckpoint;
import com.ingsis.utils.process.result.ProcessResult;
import com.ingsis.utils.result.factory.DefaultResultFactory;
import com.ingsis.utils.token.Token;
import com.ingsis.utils.token.factories.DefaultTokensFactory;
import com.ingsis.utils.token.template.TokenTemplate;
import com.ingsis.utils.token.template.factories.DefaultTokenTemplateFactory;
import com.ingsis.utils.token.template.factories.TokenTemplateFactory;
import com.ingsis.utils.token.tokenstream.DefaultTokenStream;
import com.ingsis.utils.token.tokenstream.TokenStream;
import com.ingsis.utils.token.type.TokenType;
import com.ingsis.utils.type.types.Types;

import java.util.List;

import static java.util.stream.Collectors.toList;

public class DeclarationParser implements Parser<Node> {
    private final List<TokenTemplate> declarationTemplates;
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
        stream = stream.consumeNoise();

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
        stream = stream.consumeNoise();

        SafeIterationResult<Token> consumeTypeAssignationOperator = stream.consume(colonTemplate);
        if (!consumeTypeAssignationOperator.isCorrect()) {
            return ProcessCheckpoint.INITIALIZED(
                    stream, ProcessResult.PREFIX(NodePriority.STATEMENT.priority()));
        }

        stream = (TokenStream) consumeTypeAssignationOperator.nextIterator();
        stream = stream.consumeNoise();

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
        stream = stream.consumeNoise();

        SafeIterationResult<Token> consumeSemiColon = stream.consume(semicolonTemplate);
        if (consumeSemiColon.isCorrect()) {
            stream = ((TokenStream) consumeSemiColon.nextIterator()).consumeNoise();
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

        // move past '='
        stream = (TokenStream) consumeEquals.nextIterator();
        stream = stream.consumeNoise();

        // -----------------------------------------
        // NEW: slice only the expression before ';'
        // -----------------------------------------
        TokenStream exprSlice = sliceExpression(stream);
        if (exprSlice == null) {
            return ProcessCheckpoint.INITIALIZED(
                    stream, ProcessResult.PREFIX(NodePriority.STATEMENT.priority()));
        }
        TokenStream cleanExpr = cleanNoise(exprSlice);

        // parse ONLY the sliced expression
        ProcessCheckpoint<Token, ProcessResult<ExpressionNode>> processExpressionResult =
                expressionParser.parse(cleanExpr);

        if (processExpressionResult.isUninitialized()) {
            return ProcessCheckpoint.INITIALIZED(
                    stream, ProcessResult.PREFIX(NodePriority.STATEMENT.priority()));
        }
        if (!processExpressionResult.result().isComplete()) {
            return ProcessCheckpoint.INITIALIZED(
                    exprSlice,
                    ProcessResult.PREFIX(NodePriority.STATEMENT.priority()));
        }

        ExpressionNode expressionNode = processExpressionResult.result().result();

        // advance the main stream by the slice size
        stream = stream.advanceBy(exprSlice.consumeAll()).consumeNoise();

        SafeIterationResult<Token> consumeFinalSemiColon = stream.consume(semicolonTemplate);
        if (!consumeFinalSemiColon.isCorrect()) {
            return ProcessCheckpoint.INITIALIZED(
                    stream, ProcessResult.PREFIX(NodePriority.STATEMENT.priority()));
        }

        stream = (TokenStream) consumeFinalSemiColon.nextIterator();
        stream = stream.consumeNoise();

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

    private TokenStream sliceExpression(TokenStream stream) {
        int depth = 0;
        int offset = 0;

        TokenStream cleanStart = stream.consumeNoise();

        while (true) {
            var peek = cleanStart.peek(offset);
            if (!peek.isCorrect()) {
                return null; // malformed: never found ';'
            }

            Token t = peek.result();

            // Track parentheses
            if (t.value().equals("(")) {
                depth++;
            } else if (t.value().equals(")")) {
                if (depth > 0) depth--;
            }
            // Stop at first ';' at depth 0
            else if (semicolonTemplate.matches(t) && depth == 0) {
                break;
            }

            offset++;
        }

        // Extract the FULL token list exactly as-is
        List<Token> slice =
                cleanStart.tokens().subList(cleanStart.pointer(), cleanStart.pointer() + offset);

        // Return a REWOUND stream for the slice
        return new DefaultTokenStream(
                slice,
                List.of(),
                0,
                new DefaultTokensFactory(),
                new DefaultIterationResultFactory(),
                new DefaultResultFactory()
        );
    }


    private TokenStream cleanNoise(TokenStream stream) {
        TokenTemplateFactory tokenTemplateFactory = new DefaultTokenTemplateFactory();
        List<TokenTemplate> noise = List.of(
                tokenTemplateFactory.separator(TokenType.SPACE.lexeme()).result(),
                tokenTemplateFactory.separator(TokenType.NEWLINE.lexeme()).result(),
                tokenTemplateFactory.separator(TokenType.TAB.lexeme()).result(),
                tokenTemplateFactory
                        .separator(TokenType.CRETURN.lexeme())
                        .result());

        List<Token> cleanTokens = stream.tokens().stream()
                .filter(t -> noise.stream().noneMatch(nt -> nt.matches(t)))
                .toList();

        return new DefaultTokenStream(
                cleanTokens,
                List.of(), // remove noise config completely
                0,
                new DefaultTokensFactory(),
                new DefaultIterationResultFactory(),
                new DefaultResultFactory()
        );
    }


}
