package com.hornbrinck.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hornbrinck.metro.Metro;
import com.hornbrinck.metro.MetroStation;

import java.lang.reflect.Type;
import java.io.*;
import java.nio.file.Files;
import java.util.*;
import java.util.List;


public class JsonUtils {

    public static Optional<Metro> parseJsonToMetro(File jsonFile) {

        if (!verifyFile(jsonFile)) {
            return Optional.empty();
        }

        Gson gson = new Gson();
        CaseInsensitiveMap<MetroStation> nodeMap = new CaseInsensitiveMap<>();
        Map<MetroStation, Station> nodeToStations = new IdentityHashMap<>();
        IdentityHashMap<MetroStation, List<Transfer>> nodeToTransfer = new IdentityHashMap<>();
        CaseInsensitiveMap<Queue<MetroStation>> lineMap = new CaseInsensitiveMap<>();

        try {

            String json = new String(Files.readAllBytes(jsonFile.toPath()));
            Type type = new TypeToken<Map<String, List<Station>>>() {
            }.getType();
            Map<String, List<Station>> lines = gson.fromJson(json, type);

            Metro metro = new Metro();

            for (Map.Entry<String, List<Station>> entry : lines.entrySet()) {

                String lineKey = entry.getKey();

                List<Station> stations = entry.getValue();

                Queue<MetroStation> lineStations = new ArrayDeque<>();
                lineMap.put(lineKey, lineStations);


                for (Station stationData : stations) {
                    MetroStation metroStation = new MetroStation();
                    nodeToStations.put(metroStation, stationData);
                    metroStation.setData(stationData.name);
                    metroStation.setAssociation(lineKey);
                    if (stationData.time != null) {
                        metroStation.setWeight(Double.valueOf(stationData.time));
                    }
                    if (stationData.transfer != null && !stationData.transfer.isEmpty()) {
                        nodeToTransfer.put(metroStation, stationData.transfer);
                    }
                    nodeMap.put(stationData.name + lineKey, metroStation);
                    lineStations.add(metroStation);
                    nodeToStations.put(metroStation, stationData);
                }
                // Here I would need to intitate each station on this line.
                // add next and prev method in the line, department.
                // will always have to take the weight from the targetNodes assigned weight.

                // prolly have to add each list of stations to a map of line to stations.
            }

            lineMap.forEach( (k, v) -> {

                MetroStation head = v.peek();

                while (!v.isEmpty()) {

                    MetroStation current = v.poll();

                    Station stationData = nodeToStations.get(current);

                    if (!stationData.next.isEmpty()) {
                        for (String name : stationData.next) {
                            current.addNext(nodeMap.get(name + current.getAssociation()));
                        }
                    }

                    if (!stationData.prev.isEmpty()) {
                        for (String name : stationData.prev) {
                            current.addPrev(nodeMap.get(name + current.getAssociation()));
                        }
                    }

                    List<Transfer> transferData = nodeToTransfer.get(current);

                    if (transferData != null && !transferData.isEmpty()) {

                        for (Transfer transfer : transferData) {
                            current.addParallelEdge(nodeMap.get(transfer.station + transfer.line));
                        }

                    }

                }

                metro.addLine(k, head);

            });

            // here i need to intiate and setup each transfers and connections in the metro
            // each station/node can also have several next and previous stations within the same line.

            // add next, and add prev

            return Optional.of(metro);
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
            return Optional.empty();
        }
    }

    private static boolean verifyFile(File jsonFile) {
        if (!jsonFile.exists()) {
            return false;
        }

        if (!jsonFile.isFile()) {
            return false;
        }

        if (!jsonFile.getName().endsWith(".json")) {
            return false;
        }

        return true;
    }

    public static class Station {
        String name;
        List<String> prev;
        List<String> next;
        List<Transfer> transfer;
        Integer time;

        public Station() {
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<Transfer> getTransfer() {
            return transfer;
        }

        public void setTransfer(List<Transfer> transfer) {
            this.transfer = transfer;
        }

        public List<String> getPrev() {
            return prev;
        }

        public void setPrev(List<String> prev) {
            this.prev = prev;
        }

        public List<String> getNext() {
            return next;
        }

        public void setNext(List<String> next) {
            this.next = next;
        }

        public Integer getTime() {
            return time;
        }

        public void setTime(Integer time) {
            this.time = time;
        }

    }

    public static class Transfer {
        String line;
        String station;

        public Transfer() {
        }

        public String getLine() {
            return line;
        }

        public void setLine(String line) {
            this.line = line;
        }

        public String getStation() {
            return station;
        }

        public void setStation(String station) {
            this.station = station;
        }

    }

}
