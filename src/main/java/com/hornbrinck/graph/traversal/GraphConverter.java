package com.hornbrinck.graph.traversal;

import com.hornbrinck.graph.AbstractNode;
import com.hornbrinck.graph.Edge;

import java.util.IdentityHashMap;
import java.util.function.UnaryOperator;

public class GraphConverter<N extends AbstractNode<T, V>, T extends Comparable<T>, V extends Comparable<V>> {

    private final IdentityHashMap<AbstractNode<T, V>, NodeWrapper<N, T, V>> conversionCache = new IdentityHashMap<>();
    private NodeWrapper<N, T, V> root;
    private static final double ZERO = 0.0;

    public GraphConverter() {
    }

    public GraphConverter(N root, TraversalAlgorithm algorithm) {
        switch (algorithm) {
            case BFS, DFS -> this.root = convertNodes(root);
            case DIJKSTRA -> {
                this.root = convertNodes(root);
                this.root.setDistance(ZERO);
            }
            default -> throw new IllegalStateException("Unexpected value: " + algorithm);
        }
    }

    private static UnaryOperator<String> formatLine = line -> {
        String[] split = line.split(" ");
        StringBuilder sb = new StringBuilder();
        for (String s : split) {
            sb.append(s.substring(0, 1).toUpperCase()).append(s.substring(1)).append(" ");
        }
        return sb.toString().trim();
    };

    private NodeWrapper<N, T, V> convertNodes(N node) {
        if (conversionCache.containsKey(node)) {
            return conversionCache.get(node);
        }
        NodeWrapper<N, T, V> nodeWrapper = new NodeWrapper<>(node);
        conversionCache.put(node, nodeWrapper);
        for (Edge<T, V> edge : node.getAdjacencyList()) {
            NodeWrapper<N, T, V> nodeWrapperTarget;
            if (!conversionCache.containsKey(edge.getTargetNode())) {
                nodeWrapperTarget = convertNodes((N) edge.getTargetNode());
            } else {
                nodeWrapperTarget = conversionCache.get(edge.getTargetNode());
            }
            EdgeWrapper<N, T, V> edgeWrapper = new EdgeWrapper<>(edge, nodeWrapper, nodeWrapperTarget);
            nodeWrapper.addEdgeWrapper(edgeWrapper);
        }
        return nodeWrapper;
    }

    public NodeWrapper<N, T, V> getRoot() {
        return root;
    }


}
