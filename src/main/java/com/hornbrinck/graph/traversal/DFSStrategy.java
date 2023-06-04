package com.hornbrinck.graph.traversal;

import com.hornbrinck.graph.AbstractNode;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class DFSStrategy implements GraphTraversalStrategy {
    @Override
    public <T extends Comparable<T>, V extends Comparable<V>, N extends AbstractNode<T, V>> Optional<Deque<NodeWrapper<N, T, V>>> traverse(N start, N target) {
        GraphConverter<N, T, V> converter = new GraphConverter<>(start, TraversalAlgorithm.DFS);
        NodeWrapper<N, T, V> wrapper = converter.getRoot();
        Deque<NodeWrapper<N, T, V>> result = new LinkedList<>();
        V targetKey = target.getAssociation();

        if (dfs(wrapper, target, targetKey, result)) {
            return Optional.of(result);
        } else {
            return Optional.empty();
        }
    }

    private <T extends Comparable<T>, V extends Comparable<V>, N extends AbstractNode<T, V>>
    boolean dfs(NodeWrapper<N, T, V> current, N target, V targetKey, Deque<NodeWrapper<N, T, V>> result) {
        current.setVisited(true);
        result.addLast(current);

        if (target.equals(current.getNode())) {
            return true;
        }

        List<EdgeWrapper<N, T, V>> edges;
        edges = current.getEdgeWrappers();


        for (EdgeWrapper<N, T, V> edge : edges) {
            NodeWrapper<N, T, V> destination = edge.getTargetNode();
            if (!destination.isVisited()) {
                if (dfs(destination, target, targetKey, result)) {
                    return true;
                }
            }
        }

        // backtrack
        result.removeLast();
        return false;
    }
}
