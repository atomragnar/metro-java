package com.hornbrinck.controller;

import com.hornbrinck.cli.LinePrinter;
import com.hornbrinck.cli.PathPrinter;
import com.hornbrinck.graph.traversal.TraversalAlgorithm;
import com.hornbrinck.metro.Metro;

public class MetroAppController {

    private final InputHandler inputHandler;
    private final Metro metro;
    private final LinePrinter linePrinter;
    private final PathPrinter pathPrinter;
    private boolean programRunning;

    public MetroAppController(Metro metro) {
        this.metro = metro;
        inputHandler = new InputHandler();
        linePrinter = new LinePrinter();
        pathPrinter = new PathPrinter();
        programRunning = true;
    }

    public void start() {

        while (programRunning) {

            InputValidator.InputCommands inputCommands = inputHandler.readLine();

            if (inputCommands.isValid()) {
                switch (inputCommands.getCommand()) {
                    case APPEND -> metro.addStation(inputCommands.getLineName(), inputCommands.getStationName(), 0, false);
                    case ADD_HEAD -> metro.addStation(inputCommands.getLineName(), inputCommands.getStationName(), 0, true);
                    case ADD -> metro.addStation(inputCommands.getLineName(), inputCommands.getStationName(), inputCommands.getTime(), false);
                    case REMOVE -> metro.removeStation(inputCommands.getLineName(), inputCommands.getStationName());
                    case OUTPUT -> linePrinter.printLine(metro.getMetroLine(inputCommands.getLineName()));
                    case CONNECT -> metro.connectStations(inputCommands.getLineName(), inputCommands.getStationName(), inputCommands.getLineName2(), inputCommands.getStationName2());
                    case ROUTE -> {
                        metro.route(inputCommands.getLineName(), inputCommands.getStationName(), inputCommands.getLineName2(), inputCommands.getStationName2())
                                .ifPresentOrElse(
                                        path -> pathPrinter.printPath(path, inputCommands.getStationName2(), TraversalAlgorithm.BFS),
                                        () -> System.out.println("No path found")
                                );
                    }
                    case FASTEST_ROUTE -> {
                        metro.fastestRoute(inputCommands.getLineName(), inputCommands.getStationName(), inputCommands.getLineName2(), inputCommands.getStationName2())
                                .ifPresentOrElse(
                                        path -> pathPrinter.printPath(path, inputCommands.getStationName2(), TraversalAlgorithm.DIJKSTRA),
                                        () -> System.out.println("No path found")
                                );
                    }
                    case EXIT -> programRunning = false;
                }
            } else {
                System.out.println("Invalid command");
            }

        }


    }


}

