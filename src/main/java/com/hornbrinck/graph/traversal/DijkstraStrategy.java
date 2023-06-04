package com.hornbrinck.graph.traversal;

import com.hornbrinck.graph.AbstractNode;

import java.util.*;

public class DijkstraStrategy implements GraphTraversalStrategy {
    @Override
    public <T extends Comparable<T>, V extends Comparable<V>, N extends AbstractNode<T, V>> Optional<Deque<NodeWrapper<N, T, V>>> traverse(N start, N target) {

        GraphConverter<N, T, V> converter = new GraphConverter<>(start, TraversalAlgorithm.DIJKSTRA);
        NodeWrapper<N, T, V> wrapper = converter.getRoot();
        PriorityQueue<NodeWrapper<N, T, V>> queue = new PriorityQueue<>();
        Map<NodeWrapper<N, T, V>, NodeWrapper<N, T, V>> pathMap = new IdentityHashMap<>();
        Deque<NodeWrapper<N, T, V>> result = new LinkedList<>();
        NodeWrapper<N, T, V> current;
        NodeWrapper<N, T, V> targetWrapper = null;

        queue.add(wrapper);

        while (!queue.isEmpty()) {

            current = queue.poll();

            if (current.getNode().equals(target)) {
                targetWrapper = current;
            }

            for (EdgeWrapper<N, T, V> edge : current.getEdgeWrappers()) {
                NodeWrapper<N, T, V> destination = edge.getTargetNode();

                double newDistance;
                newDistance =  current.getDistance() + edge.getWeight();

                if (newDistance < destination.getDistance()) {
                    queue.remove(destination);
                    destination.setDistance(newDistance);
                    pathMap.put(destination, current);
                    queue.add(destination);
                }

            }

        }

        if (targetWrapper == null) {
            return Optional.empty();
        }
        // Traverse back from the target node to the source node and add it to the result deque.
        current = targetWrapper;

        while (current != null) {
            result.addLast(current);
            //System.out.println(current.getData() + " " + current.getKey() + " " + current.getDistance() + " " + current.getWeight());
            current = pathMap.get(current);
        }
        return Optional.of(result);
    }
}
