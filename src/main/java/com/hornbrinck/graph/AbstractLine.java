package com.hornbrinck.graph;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;

public abstract class AbstractLine<N extends AbstractNode<T, String>, T extends Comparable<T>> {

    // could add a map that keeps track of each of the nodes additional memory but faster operations
    // maybe I should remove the length variable

    private final String name;
    private N head;
    private final Supplier<N> nodeSupplier;

    public AbstractLine(String name, Supplier<N> nodeSupplier) {
        this.name = name;
        this.nodeSupplier = nodeSupplier;
        this.head = null;
    }

    private N getNewNode(T data, double weight) {
        N newNode = nodeSupplier.get();
        newNode.setData(data);
        newNode.setWeight(weight);
        newNode.setAssociation(this.name);
        return newNode;
    }

    public void addFirst(T data, double weight) {
        N newNode = getNewNode(data, weight);
        if (head == null) {
            head = newNode;
        } else {
            N temp = head;
            head = newNode;
            head.addNext(temp);
            List<N> prevNodes = (List<N>) temp.getPrevNodes();
            temp.addPrev(head);
            for (N prevNode : prevNodes) {
                prevNode.removeEdge(temp, GraphRelationship.NEXT);
                temp.removeEdge(prevNode, GraphRelationship.PREVIOUS);
                prevNode.addNext(head);
                head.addPrev(newNode);
            }
        }
    }


    public void add(T data, double weight) {
        N newNode = getNewNode(data, weight);
        add(newNode);
    }

    public void add(T data, double weight, T nodeAfter) {
        N newNode = getNewNode(data, weight);
        getNode(nodeAfter).ifPresentOrElse(
                node -> add(newNode, node),
                () -> add(newNode)
        );
    }

    public void add(N newNode, N nodeBefore) {
        addAfterNodeAction(newNode, nodeBefore);
    }

    private void add(N newNode) {
        if (head == null) {
            head = newNode;
        } else {
            List<N> prevNodes = (List<N>) head.getPrevNodes();
            if (!prevNodes.isEmpty()) {
                for (N prevNode : prevNodes) {
                    head.removeEdge(prevNode, GraphRelationship.PREVIOUS);
                    prevNode.removeEdge(head, GraphRelationship.NEXT);
                    newNode.addPrev(prevNode);
                    prevNode.addNext(newNode);
                }

            } else {
                HashSet<N> visited = new HashSet<>();
                addRecursive(newNode, head, visited);
            }
        }
    }

    private void addRecursive(N newNode, N current, HashSet<N> visited) {
        visited.add(current);
        List<N> nextNodes = (List<N>) current.getNextNodes();

        if (nextNodes.isEmpty()) {
            current.addNext(newNode);
            newNode.addPrev(current);
            return;
        }

        for (N node : nextNodes) {
            if (!visited.contains(node)) {
                addRecursive(newNode, node, visited);
            }
        }
    }

    private void addAfterNodeAction(N newNode, N otherNode) {
        newNode.addPrev(otherNode);
        List<N> nextNodes = (List<N>) otherNode.getNextNodes();
        otherNode.addNext(newNode);

        for (N nextNode : nextNodes) {
            otherNode.removeEdge(nextNode, GraphRelationship.NEXT);
            nextNode.removeEdge(otherNode, GraphRelationship.PREVIOUS);
            newNode.addNext(nextNode);
            nextNode.addPrev(newNode);
        }
    }


    public Optional<N> getNode(T data) {
        HashSet<N> visited = new HashSet<>();
        return getNode(data, head, visited);
    }

    private Optional<N> getNode(T data, N current, HashSet<N> visited) {
        if (current == null) {
            return Optional.empty();
        }
        if (compareNodeData(data, current)) {
            return Optional.of(current);
        }

        visited.add(current);

        List<N> nextNodes = (List<N>) current.getNextNodes();

        if (nextNodes.isEmpty()) {
            return Optional.empty();
        }

        for (N node : nextNodes) {
            if (!visited.contains(node)) {
                Optional<N> nOptional = getNode(data, node, visited);
                if (nOptional.isPresent()) {
                    return nOptional;
                }
            }
        }
        return Optional.empty();
    }

    public void remove(T data) {
        getNode(data).ifPresent(this::remove);
    }

    public void remove(AbstractNode<T, String> node) {
        List<AbstractNode<T, String>> prevNodes = node.getPrevNodes();
        List<AbstractNode<T, String>> nextNodes = node.getNextNodes();
        for (AbstractNode<T, String> prev : prevNodes) {
            for (AbstractNode<T, String> next : nextNodes) {
                prev.addNext(next);
                next.addPrev(prev);
                prev.removeEdge(node, GraphRelationship.NEXT);
                next.removeEdge(node, GraphRelationship.PREVIOUS);
            }
        }
    }


    private boolean compareNodeData(T data, N current) {
        return Objects.equals(data, current.getData());
    }

    public void clear() {
        head = null;
    }

    public void setHead(N head) {
        this.head = head;
    }

    public String getName() {
        return name;
    }

    public N getHead() {
        return head;
    }


}
