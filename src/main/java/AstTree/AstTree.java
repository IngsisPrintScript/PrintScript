package AstTree;

import token.TokenInterface;

import java.io.Serializable;
import java.util.List;

public class AstTree<V> implements AstTreeInterface<V> {
    private AstTree<V> left;
    private AstTree<V> right;
    private final V value;

    public AstTree(V value){
        this.value = value;
    }
    @Override
    public List<AstTreeInterface<V>> getChildren() {
        return List.of(left, right);
    }

    @Override
    public V getValue() {
        return value;
    }
}
