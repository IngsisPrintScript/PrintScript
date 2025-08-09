package AstTree;

import java.util.List;

public interface TreeInterface<V> {
    TreeInterface<V> leftChild();
    TreeInterface<V> rightChild();
    V getValue();
}
