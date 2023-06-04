package com.hornbrinck.graph.traversal;

import com.hornbrinck.graph.AbstractNode;

import java.util.Deque;
import java.util.Optional;

public interface GraphTraversalStrategy {
    <T extends Comparable<T>, V extends Comparable<V>, N extends AbstractNode<T, V>>
    Optional<Deque<NodeWrapper<N, T, V>>> traverse(N start, N target);

}
