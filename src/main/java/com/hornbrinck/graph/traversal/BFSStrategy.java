package com.hornbrinck.graph.traversal;

import com.hornbrinck.graph.AbstractNode;

import java.util.*;

public class BFSStrategy implements GraphTraversalStrategy {
    @Override
    public <T extends Comparable<T>, V extends Comparable<V>, N extends AbstractNode<T, V>> Optional<Deque<NodeWrapper<N, T, V>>> traverse(N start, N target) {
        GraphConverter<N, T, V> converter = new GraphConverter<>(start, TraversalAlgorithm.BFS);
        NodeWrapper<N, T, V> wrapper = converter.getRoot();

        Queue<NodeWrapper<N, T, V>> queue = new LinkedList<>();
        Map<NodeWrapper<N, T, V>, NodeWrapper<N, T, V>> pathMap = new IdentityHashMap<>();
        Deque<NodeWrapper<N, T, V>> result = new LinkedList<>();
        wrapper.setVisited(true);
        queue.add(wrapper);


        while (!queue.isEmpty()) {

            NodeWrapper<N, T, V> current = queue.poll();

            if (target.equals(current.getNode())) {
                while (current != null) {
                    result.addLast(current);
                    current = pathMap.get(current);
                }
                return Optional.of(result);
            }

            List<EdgeWrapper<N, T, V>> edges;
            edges = current.getEdgeWrappers();

            for (EdgeWrapper<N, T, V> edge : edges) {
                NodeWrapper<N, T, V> destination = edge.getTargetNode();
                if (edge.isParallel()) {
                    for (EdgeWrapper<N, T, V> transferEdge : destination.getEdgeWrappers()) {
                        NodeWrapper<N, T, V> transferDestination = transferEdge.getTargetNode();
                        if (!transferDestination.isVisited()) {
                            transferDestination.setVisited(true);
                            pathMap.put(transferDestination, current);
                            queue.add(transferDestination);
                        }
                    }
                } else {
                    if (!destination.isVisited()) {
                        destination.setVisited(true);
                        pathMap.put(destination, current);
                        queue.add(destination);
                    }
                }
            }

        }
        return Optional.of(result);
    }
}
