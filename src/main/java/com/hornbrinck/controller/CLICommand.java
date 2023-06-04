package com.hornbrinck.controller;


public enum CLICommand {
    APPEND("append"),
    ADD_HEAD("add-head"),
    ADD("add"),
    REMOVE("remove"),
    OUTPUT("output"),
    CONNECT("connect"),
    ROUTE("route"),
    FASTEST_ROUTE("fastest-route"),
    EXIT("exit");

    private final String command;

    CLICommand(String command) {
        this.command = command;
    }

    public String getCommand() {
        return command;
    }

    static CLICommand getCommand(String command) {
        for (CLICommand cliCommand : CLICommand.values()) {
            if (cliCommand.getCommand().equals(command)) {
                return cliCommand;
            }
        }
        return null;
    }

}