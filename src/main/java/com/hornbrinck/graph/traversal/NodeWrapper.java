package com.hornbrinck.graph.traversal;

import com.hornbrinck.graph.AbstractNode;
import com.hornbrinck.graph.Edge;
import com.hornbrinck.graph.GraphRelationship;

import java.util.ArrayList;
import java.util.List;

public class NodeWrapper <N extends AbstractNode<T, V>, T extends Comparable<T>, V extends Comparable<V>>
        extends AbstractNode<T, V> implements Comparable<NodeWrapper<N, T, V>> {

    private final N node;
    private Double distance;
    private boolean visited;
    private final List<EdgeWrapper<N, T, V>> edgeWrappers;

    public NodeWrapper(N node) {
        super();
        this.edgeWrappers = new ArrayList<>();
        this.node = node;
        this.visited = false;
        this.distance = Double.MAX_VALUE;
        this.setWeight(node.getWeight());
        this.setAssociation(node.getAssociation());
    }

    public void addEdgeWrapper(EdgeWrapper<N, T, V> edgeWrapper) {
        edgeWrappers.add(edgeWrapper);
    }

    public N getNode() {
        return node;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public List<EdgeWrapper<N, T, V>> getEdgeWrappers() {
        return edgeWrappers;
    }

    @Override
    public void addNext(AbstractNode<T, V> next) {
        node.addNext(next);
    }

    @Override
    public void addPrev(AbstractNode<T, V> prev) {
        node.addPrev(prev);
    }

    @Override
    public void addParallelEdge(AbstractNode<T, V> parallel) {
        node.addParallelEdge(parallel);
    }

    @Override
    public List<AbstractNode<T, V>> getNextNodes() {
        return node.getNextNodes();
    }

    @Override
    public List<AbstractNode<T, V>> getPrevNodes() {
        return node.getPrevNodes();
    }

    @Override
    public List<AbstractNode<T, V>> getParallelNodes() {
        return node.getParallelNodes();
    }

    @Override
    public void removeEdge(AbstractNode<T, V> node, GraphRelationship relationShip) {
        node.removeEdge(node, relationShip);
    }

    @Override
    public List<Edge<T, V>> getAdjacencyList() {
        return node.getAdjacencyList();
    }


    @Override
    public int compareTo(NodeWrapper<N, T, V> o) {
        return Double.compare(distance, o.getDistance());
    }
}
