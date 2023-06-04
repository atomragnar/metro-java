package com.hornbrinck.graph;

public class Edge<T extends Comparable<T>, V extends Comparable<V>> {

    private AbstractNode<T, V> startNode;
    private AbstractNode<T, V> targetNode;
    private Double weight;
    private GraphRelationship relationShip;

    public AbstractNode<T, V> getStartNode() {
        return startNode;
    }

    public void setStartNode(AbstractNode<T, V> startNode) {
        this.startNode = startNode;
    }

    public AbstractNode<T, V> getTargetNode() {
        return targetNode;
    }

    public void setTargetNode(AbstractNode<T, V> targetNode) {
        this.targetNode = targetNode;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public GraphRelationship getRelationShip() {
        return relationShip;
    }

    public void setRelationShip(GraphRelationship relationShip) {
        this.relationShip = relationShip;
    }

    public boolean isParallel() {
        return this.relationShip == GraphRelationship.PARALLEL;
    }

}
