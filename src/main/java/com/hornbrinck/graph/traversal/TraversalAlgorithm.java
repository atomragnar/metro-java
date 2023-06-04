package com.hornbrinck.graph.traversal;

import com.hornbrinck.graph.AbstractNode;

import java.util.Deque;
import java.util.Optional;

public enum TraversalAlgorithm {
    DFS(new DFSStrategy()),
    BFS(new BFSStrategy()),
    DIJKSTRA(new DijkstraStrategy());

    private final GraphTraversalStrategy strategy;

    TraversalAlgorithm(GraphTraversalStrategy strategy) {
        this.strategy = strategy;
    }

    public <N extends AbstractNode<T, V>, T extends Comparable<T>, V extends Comparable<V>>
    Optional<Deque<NodeWrapper<N, T, V>>> traverse(N startNode, N targetNode) {
        return strategy.traverse(startNode, targetNode);
    }
}