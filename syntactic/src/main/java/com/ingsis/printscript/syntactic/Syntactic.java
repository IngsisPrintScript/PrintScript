/*
 * My Project
 */

package com.ingsis.printscript.syntactic;

import com.ingsis.printscript.astnodes.Node;
import com.ingsis.printscript.results.CorrectResult;
import com.ingsis.printscript.results.IncorrectResult;
import com.ingsis.printscript.results.Result;
import com.ingsis.printscript.syntactic.ast.builders.ASTreeBuilderInterface;
import com.ingsis.printscript.tokens.TokenInterface;
import com.ingsis.printscript.tokens.factories.TokenFactory;
import com.ingsis.printscript.tokens.stream.TokenStream;
import com.ingsis.printscript.tokens.stream.TokenStreamInterface;
import com.ingsis.printscript.visitor.SemanticallyCheckable;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class Syntactic implements SyntacticInterface {
    private final ASTreeBuilderInterface TREE_BUILDER;
    private final Iterator<TokenInterface> TOKEN_ITERATOR;
    private final Deque<SemanticallyCheckable> NODE_CACHE;

    @SuppressFBWarnings("EI_EXPOSE_REP2")
    public Syntactic(ASTreeBuilderInterface treeBuilder, Iterator<TokenInterface> tokenIterator) {
        this.TREE_BUILDER = treeBuilder;
        this.TOKEN_ITERATOR = tokenIterator;
        this.NODE_CACHE = new ArrayDeque<>();
    }

    @Override
    public Result<SemanticallyCheckable> buildAbstractSyntaxTree(TokenStreamInterface tokenStream) {
        tokenStream.consumeAll(new TokenFactory().createSeparatorToken(""));
        Result<? extends Node> buildResult = TREE_BUILDER.build(tokenStream);
        if (!tokenStream.isEndOfStream()){
            return new IncorrectResult<>("Missing tokens");
        }
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
        if (!NODE_CACHE.isEmpty()) {
            return true;
        }

        SemanticallyCheckable nextNode = computeNext();
        if (nextNode != null) NODE_CACHE.add(nextNode);

        return nextNode != null;
    }

    @Override
    public SemanticallyCheckable next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        return NODE_CACHE.poll();
    }

    @Override
    public SemanticallyCheckable peek() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        return NODE_CACHE.peek();
    }

    private SemanticallyCheckable computeNext() {
        TokenStreamInterface stream = new TokenStream(List.of());
        SemanticallyCheckable biggestBuild = null;

        while (TOKEN_ITERATOR.hasNext()) {
            TokenInterface token = TOKEN_ITERATOR.next();
            List<TokenInterface> tokens = new ArrayList<>(stream.tokens());
            tokens.add(token);
            stream = new TokenStream(tokens);

            Result<SemanticallyCheckable> buildResult = this.buildAbstractSyntaxTree(stream);
            if (buildResult.isSuccessful()) {
                biggestBuild = buildResult.result();
            }
        }

        return biggestBuild;
    }
}
