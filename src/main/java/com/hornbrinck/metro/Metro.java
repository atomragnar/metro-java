package com.hornbrinck.metro;

import com.hornbrinck.graph.AbstractLine;
import com.hornbrinck.graph.traversal.NodeWrapper;
import com.hornbrinck.graph.traversal.TraversalAlgorithm;
import com.hornbrinck.utils.CaseInsensitiveMap;

import java.util.Deque;
import java.util.Optional;

public class Metro {

    private CaseInsensitiveMap<MetroLine> metroLines;

    public Metro() {
        metroLines = new CaseInsensitiveMap<>();
    }

    public void addLine(String lineName, MetroStation head) {
        metroLines.put(lineName, new MetroLine(lineName, head));
    }

    private Optional<MetroLine> getLine(String lineName) {
        return Optional.ofNullable(metroLines.get(lineName));
    }

    public Optional<Deque<NodeWrapper<MetroStation, String, String>>> route(String lineName, String stationName, String lineName2, String stationName2) {
        Optional<MetroLine> line1 = getLine(lineName);
        Optional<MetroLine> line2 = getLine(lineName2);
        if (line1.isPresent() && line2.isPresent()) {
            return route(line1.get(), stationName, line2.get(), stationName2);
        }
        return Optional.empty();
    }

    private Optional<Deque<NodeWrapper<MetroStation, String, String>>> route(MetroLine metroLine1, String stationName1
            , MetroLine metroLine2, String stationName2) {
        Optional<MetroStation> station1 = metroLine1.getNode(stationName1);
        Optional<MetroStation> station2 = metroLine2.getNode(stationName2);
        if (station1.isPresent() && station2.isPresent()) {
            return TraversalAlgorithm.BFS.traverse(station1.get(), station2.get());
        }
        return Optional.empty();
    }

    public Optional<Deque<NodeWrapper<MetroStation, String, String>>> fastestRoute(String lineName, String stationName, String lineName2, String stationName2) {
        Optional<MetroLine> line1 = getLine(lineName);
        Optional<MetroLine> line2 = getLine(lineName2);
        if (line1.isPresent() && line2.isPresent()) {
            //System.out.println("Line found");
            return fastestRoute(line1.get(), stationName, line2.get(), stationName2);
        }
        //System.out.println("Line not found");
        return Optional.empty();
    }

    private Optional<Deque<NodeWrapper<MetroStation, String, String>>> fastestRoute(MetroLine metroLine1
            , String stationName1, MetroLine metroLine2, String stationName2) {
        Optional<MetroStation> station1 = metroLine1.getNode(stationName1);
        Optional<MetroStation> station2 = metroLine2.getNode(stationName2);
        if (station1.isPresent() && station2.isPresent()) {
            return TraversalAlgorithm.DIJKSTRA
                    .traverse(station1.get(), station2.get());
        }
        return Optional.empty();
    }

    public Optional<MetroStation> getMetroLine(String lineName) {
        getLine(lineName).ifPresentOrElse(
                AbstractLine::getHead
                ,Optional::empty
        );
    }

    public void addStation(String lineName, String stationName, double time, boolean isHead) {
        getLine(lineName).ifPresent( line -> {
            if (isHead) {
                line.addFirst(stationName, time);
            } else {
                line.add(stationName, time);
            }
        });
    }

    // find a good implementation of the transfer operation.

}
