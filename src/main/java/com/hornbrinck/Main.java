package com.hornbrinck;

import com.hornbrinck.controller.MetroAppController;
import com.hornbrinck.utils.CommandLineUtils;
import com.hornbrinck.utils.JsonUtils;

public class Main {


    public static void main(String[] args) {

        new CommandLineUtils().handleCommandLineArgs(args)
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