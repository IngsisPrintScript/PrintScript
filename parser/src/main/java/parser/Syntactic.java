package parser;

import common.Node;
import common.TokenInterface;
import results.CorrectResult;
import results.IncorrectResult;
import results.Result;
import parser.ast.builders.ASTreeBuilderInterface;
import stream.TokenStream;
import stream.TokenStreamInterface;
import visitor.InterpretableNode;
import visitor.SemanticallyCheckable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public record Syntactic(
        ASTreeBuilderInterface treeBuilder,
        Iterator<TokenInterface> tokenIterator,
        List<InterpretableNode> mueCache
) implements SyntacticInterface {

    @Override
    public Result<InterpretableNode> buildAbstractSyntaxTree(TokenStreamInterface tokenStream) {
        Result<? extends Node> buildResult = treeBuilder().build(tokenStream);
        if (!buildResult.isSuccessful()) {
            return new IncorrectResult<>(buildResult.errorMessage());
        }
        Node root = buildResult.result();
        if (!(root instanceof InterpretableNode interpretableNode)) {
            return new IncorrectResult<>("Has built a tree which is not semantically checkable");
        }
        return new CorrectResult<>(interpretableNode);
    }

    @Override
    public boolean hasNext() {
        if (!mueCache.isEmpty()){
            return true;
        }
        if (!tokenIterator.hasNext()){
            return false;
        }
        List<TokenInterface> tokens = new ArrayList<>();
        while (tokenIterator.hasNext()){
            TokenInterface token = tokenIterator.next();
            tokens.add(token);
            TokenStreamInterface newTokenStream = new TokenStream(tokens);
            Result<InterpretableNode> builtTree = buildAbstractSyntaxTree(newTokenStream);
            if (builtTree.isSuccessful()) {
                mueCache.add(builtTree.result());
                return true;
            }
        }
        return false;
    }

    @Override
    public InterpretableNode next() {
        if (!hasNext()){
            throw new NoSuchElementException();
        }
        return mueCache().getLast();
    }
}
