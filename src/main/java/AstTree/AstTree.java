package AstTree;

public record AstTree<V>(V value, AstTree<V> left, AstTree<V> right) implements TreeInterface<V> {
    @Override
    public TreeInterface<V> leftChild() {
        return this.left;
    }

    @Override
    public TreeInterface<V> rightChild() {
        return this.right;
    }

    @Override
    public V getValue() {
        return this.value;
    }
}
