package com.hornbrinck.graph.traversal;

import com.hornbrinck.graph.AbstractNode;
import com.hornbrinck.graph.Edge;
import com.hornbrinck.graph.GraphRelationship;

public class EdgeWrapper<N extends AbstractNode<T, V>, T extends Comparable<T>, V extends Comparable<V>>
        extends Edge<T, V> {

    private Edge<T, V> edge;

    public EdgeWrapper(Edge<T, V> edge, NodeWrapper<N, T, V> startNode, NodeWrapper<N, T, V> targetNode) {
        this.edge = edge;
        this.setStartNode(startNode);
        this.setTargetNode(targetNode);
        this.setRelationShip(edge.getRelationShip());
        this.setWeight(edge.getWeight());

    }

    @Override
    public NodeWrapper<N, T, V> getStartNode() {
        return (NodeWrapper<N, T, V>) super.getStartNode();
    }

    @Override
    public NodeWrapper<N, T, V> getTargetNode() {
        return (NodeWrapper<N, T, V>) super.getTargetNode();
    }


}
