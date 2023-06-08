package com.hornbrinck.cli;

import com.hornbrinck.graph.traversal.NodeWrapper;
import com.hornbrinck.graph.traversal.TraversalAlgorithm;
import com.hornbrinck.metro.MetroStation;

import java.util.Deque;

public class PathPrinter extends ConsolePrinter {

    private static final String TRANSITION = "Transition to line %s";
    public void printPath(Deque<NodeWrapper<MetroStation, String, String>> path, String targetDestination, TraversalAlgorithm algorithm) {
        if (path.isEmpty()) {
            System.out.println("There is no path to the destination");
            return;
        }

        switch (algorithm) {
            case BFS -> printBFS(path, targetDestination);
            case DIJKSTRA -> printDijkstra(path, targetDestination);
            default -> throw new IllegalStateException("Unexpected value: " + algorithm);
        }

    }

    public void printBFS(Deque<NodeWrapper<MetroStation, String, String>> path, String targetDestination) {
        NodeWrapper<MetroStation, String, String> current = path.peekFirst();
        assert current != null;
        if (!current.getData().equalsIgnoreCase(targetDestination)) {
            return;
        }

        current = path.pollLast();
        assert current != null;
        String currentLine = current.getAssociation();
        out(current.getData());

        while (!path.isEmpty()) {
            String prev = current.getData();
            current = path.pollLast();
            assert current != null;
            String nextLine = current.getAssociation();
            if (!nextLine.equalsIgnoreCase(currentLine)) {
                out(String.format(TRANSITION, nextLine));
                out(prev);
                currentLine = nextLine;
            }
            if (!prev.equalsIgnoreCase(current.getData())) {
                out(current.getData());
            }
        }
    }

    public void printDijkstra(Deque<NodeWrapper<MetroStation, String, String>> path, String targetDestination) {

        NodeWrapper<MetroStation, String, String> current = path.peekFirst();
        assert current != null;
        if (!current.getData().equalsIgnoreCase(targetDestination)) {
            return;
        }

        double totalTime = current.getDistance();
        current = path.peekLast();
        assert current != null;
        totalTime -= current.getDistance();

        current = path.pollLast();
        assert current != null;
        String currentLine = current.getAssociation();
        out(current.getData());

        while (!path.isEmpty()) {
            String prev = current.getData();
            current = path.pollLast();
            assert current != null;
            String nextLine = current.getAssociation();
            if (!nextLine.equalsIgnoreCase(currentLine)) {
                //out(String.format(TRANSITION, nextLine));
                //out(prev);
                out(nextLine);
                currentLine = nextLine;
            }
            out(current.getData());
            //current.getAdjacencyList().forEach((edge) -> out(edge.getKey() + " " + edge.getWeight() + " " + edge.getTargetNode().getData()));
        }

        out(String.format("Total: %d minutes", (long) totalTime));

    }

}
