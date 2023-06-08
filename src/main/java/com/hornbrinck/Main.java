package com.hornbrinck;

import com.hornbrinck.controller.MetroAppController;
import com.hornbrinck.utils.CommandLineUtils;
import com.hornbrinck.utils.JsonUtils;

public class Main {


    public static void main(String[] args) {

        String[] args1 = new String[] {"C:\\Files\\hyperskill\\HyperMetro\\HyperMetro (1)\\task\\src\\metro\\london.json"};

        CommandLineUtils.handleCommandLineArgs.apply(args1)
                .ifPresentOrElse(
                        file -> {
                            JsonUtils.parseJsonToMetro(file)
                                    .ifPresentOrElse(
                                            metro -> {
                                                MetroAppController metroAppController = new MetroAppController(metro);
                                                metroAppController.start();
                                            },
                                            () -> System.out.println("Incorrect file")
                                    );
                        },
                        () -> System.out.println("Error! Such a file doesn't exist!")
                );
    }


}