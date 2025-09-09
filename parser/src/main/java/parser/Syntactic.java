package parser;

import common.Node;
import common.TokenInterface;
import factories.tokens.TokenFactory;
import results.CorrectResult;
import results.IncorrectResult;
import results.Result;
import parser.ast.builders.ASTreeBuilderInterface;
import stream.TokenStream;
import stream.TokenStreamInterface;
import visitor.SemanticallyCheckable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.NoSuchElementException;

public record Syntactic(
        ASTreeBuilderInterface treeBuilder,
        Iterator<TokenInterface> tokenIterator,
        BlockingQueue<Node> nodeCache
) implements SyntacticInterface {

    public Syntactic(ASTreeBuilderInterface treeBuilder, Iterator<TokenInterface> tokenIterator) {
        this(treeBuilder, tokenIterator, new LinkedBlockingQueue<>());
    }

    @Override
    public Result<SemanticallyCheckable> buildAbstractSyntaxTree(TokenStreamInterface tokenStream) {
        tokenStream.consumeAll(new TokenFactory().createSeparatorToken("SPACE"));
        Result<? extends Node> buildResult = treeBuilder().build(tokenStream);
        if (!buildResult.isSuccessful()) {
            return new IncorrectResult<>(buildResult.errorMessage());
        }
        Node root = buildResult.result();
        if (!(root instanceof SemanticallyCheckable semanticallyCheckableNode)) {
            return new IncorrectResult<>("Has built a tree which is not semantically checkable");
        }
        return new CorrectResult<>(semanticallyCheckableNode);
    }

    @Override
    public boolean hasNext() {
        if (!nodeCache.isEmpty()) {
            return true;
        }
        TokenStreamInterface stream = new TokenStream(List.of());
        while (tokenIterator.hasNext()) {
            TokenInterface token = tokenIterator.next();
            List<TokenInterface> tokens = new ArrayList<>(stream.tokens());
            tokens.add(token);
            stream = new TokenStream(tokens);
            Result<SemanticallyCheckable> buildResult = this.buildAbstractSyntaxTree(stream);
            if (buildResult.isSuccessful()) {
                Node node = (Node) buildResult.result();
                nodeCache.add(node);
                return true;
            }
        }
        return false;
    }

    @Override
    public Node next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        return nodeCache.poll();
    }
}
