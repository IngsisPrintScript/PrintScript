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

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.NoSuchElementException;

public record Syntactic(
        ASTreeBuilderInterface treeBuilder,
        Iterator<TokenInterface> tokenIterator,
        Deque<SemanticallyCheckable> nodeCache
) implements SyntacticInterface {

    public Syntactic(ASTreeBuilderInterface treeBuilder, Iterator<TokenInterface> tokenIterator) {
        this(treeBuilder, tokenIterator, new ArrayDeque<>());
    }

    @Override
    public Result<SemanticallyCheckable> buildAbstractSyntaxTree(TokenStreamInterface tokenStream) {
        tokenStream.consumeAll(new TokenFactory().createSeparatorToken("SPACE"));
        Result<? extends Node> buildResult = treeBuilder().build(tokenStream);
        if (!buildResult.isSuccessful()) {
            return new IncorrectResult<>(buildResult.errorMessage());
        }
        if (!tokenStream.consume(new TokenFactory().createEndOfLineToken()).isSuccessful()){
            return new IncorrectResult<>("Did not have an End Of Line character.");
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

        SemanticallyCheckable nextNode = computeNext();
        if (nextNode != null) nodeCache.add(nextNode);

        return nextNode != null;
    }

    @Override
    public SemanticallyCheckable next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        return nodeCache.poll();
    }

    @Override
    public SemanticallyCheckable peek() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        return nodeCache.peek();
    }

    private SemanticallyCheckable computeNext(){
        TokenStreamInterface stream = new TokenStream(List.of());
        while (tokenIterator.hasNext()) {
            TokenInterface token = tokenIterator.next();
            List<TokenInterface> tokens = new ArrayList<>(stream.tokens());
            tokens.add(token);
            stream = new TokenStream(tokens);
            Result<SemanticallyCheckable> buildResult = this.buildAbstractSyntaxTree(stream);
            if (buildResult.isSuccessful()) {
                return buildResult.result();
            }
        }
        return null;
    }
}
