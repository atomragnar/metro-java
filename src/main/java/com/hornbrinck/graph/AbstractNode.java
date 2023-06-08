package com.hornbrinck.graph;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class AbstractNode<T extends Comparable<T>, V extends Comparable<V>> {

    private T data;
    private V association;
    private Double weight;
    private List<Edge<T, V>> parallelEdges;
    private List<Edge<T, V>> nextEdges;
    private List<Edge<T, V>> prevEdges;

    public AbstractNode() {
        this.data = null;
        this.association = null;
        this.weight = null;
        this.parallelEdges = new ArrayList<>();
        this.prevEdges = new ArrayList<>();
        this.nextEdges = new ArrayList<>();
    }

    public AbstractNode(T data, V association, Double weight) {
        this.data = data;
        this.association = association;
        this.weight = weight;
        this.parallelEdges = new ArrayList<>();
        this.prevEdges = new ArrayList<>();
        this.nextEdges = new ArrayList<>();
    }

    public void addNext(AbstractNode<T, V> next) {
        Edge<T, V> nextEdge = new Edge<>();
        nextEdge.setTargetNode(next);
        nextEdge.setWeight(this.weight);
        nextEdge.setRelationShip(GraphRelationship.NEXT);
        nextEdges.add(nextEdge);
    }

    public void addPrev(AbstractNode<T, V> prev) {
        Edge<T, V> prevEdge = new Edge<>();
        prevEdge.setTargetNode(prev);
        prevEdge.setWeight(prev.weight);
        prevEdge.setRelationShip(GraphRelationship.PREVIOUS);
        prevEdges.add(prevEdge);
    }

    public void addParallelEdge(AbstractNode<T, V> parallel) {
        Edge<T, V> parallelEdge = new Edge<>();
        parallelEdge.setTargetNode(parallel);
        // TODO here I will need to adjust with the time cost of transfer
        parallelEdge.setWeight(5.0);
        parallelEdge.setRelationShip(GraphRelationship.PARALLEL);
        parallelEdges.add(parallelEdge);
    }

    public List<AbstractNode<T, V>> getNextNodes() {
        return nextEdges.isEmpty()
                ? new ArrayList<>()
                : nextEdges.stream().map(Edge::getTargetNode).toList();
    }

    public List<AbstractNode<T, V>> getPrevNodes() {
        return prevEdges.isEmpty()
                ? new ArrayList<>()
                : prevEdges.stream().map(Edge::getTargetNode).toList();
    }

    public List<AbstractNode<T, V>> getPrevAndNextNodes() {
        List<AbstractNode<T, V>> list = new ArrayList<>(getPrevNodes());
        list.addAll(getNextNodes());
        return list;
    }

    public List<AbstractNode<T, V>> getParallelNodes() {
        return parallelEdges.isEmpty()
                ? new ArrayList<>()
                : parallelEdges.stream().map(Edge::getTargetNode).toList();
    }

    public void removeEdge(AbstractNode<T, V> node, GraphRelationship relationShip) {
        switch(relationShip) {
            case NEXT -> nextEdges.stream()
                    .filter(e -> e.getTargetNode().equals(node))
                    .forEach(f -> nextEdges.removeIf(e -> e.getTargetNode().equals(node)));
            case PREVIOUS -> prevEdges.stream()
                    .filter(e -> e.getTargetNode().equals(node))
                    .forEach(f -> prevEdges.removeIf(e -> e.getTargetNode().equals(node)));
            case PARALLEL -> parallelEdges.stream()
                    .filter(e -> e.getTargetNode().equals(node))
                    .forEach(f -> parallelEdges.removeIf(e -> e.getTargetNode().equals(node)));
        }
    }

    public List<Edge<T, V>> getAdjacencyList() {
        return Stream.of(prevEdges, nextEdges, parallelEdges)
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }

    public T getData() {
        return data;
    }

    public V getAssociation() {
        return association;
    }

    public void setData(T data) {
        this.data = data;
    }

    public void setAssociation(V association) {
        this.association = association;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Double getWeight() {
        return weight;
    }
}