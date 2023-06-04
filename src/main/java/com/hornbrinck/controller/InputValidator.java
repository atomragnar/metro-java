package com.hornbrinck.controller;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.hornbrinck.controller.CLICommand.*;

public class InputValidator {

    private final String quotes = "\"";
    private final Pattern ARG = Pattern.compile("(((?=\\S*\"[^\"]*\\S*\")\"([^\"]*)\"|(\\S+))\\s*((?=\\S*\"[^\"]*\\S*\")\"([^\"]*)\"|(\\S+))){1,2}\\s?(\\d+)?$");
    private final Pattern COMMAND = Pattern.compile("^(\\.?/)([A-Za-z-]+)\\s+[\\w\\s\\S]+");
    private final Pattern FOUR_ARG_PATTERN = Pattern.compile("(((?=\\S*\"[^\"]*\\S*\")\"([^\"]*)\"|(\\S+))\\s*((?=\\S*\"[^\"]*\\S*\")\"([^\"]*)\"|(\\S+)))\\s(((?=\\S*\"[^\"]*\\S*\")\"([^\"]*)\"|(\\S+))\\s*((?=\\S*\"[^\"]*\\S*\")\"([^\"]*)\"|(\\S+)))$");


    InputCommands validate(String input) {
        if (input == null || input.isEmpty()) {
            return InputCommands.invalid();
        }

        Matcher commandMatcher = COMMAND.matcher(input);
        String arg1 = null;
        String arg2 = null;
        String arg3 = null;
        String arg4 = null;


        if (commandMatcher.matches()) {
            String commandText = commandMatcher.group(2);
            CLICommand command = CLICommand.getCommand(commandText);
            if (command == null) {
                return InputCommands.invalid();
            }

            String inputWithoutCommand = input.substring(commandText.length() + 1).trim();

            Matcher inputMatcher = isFourArgCommand(command) ? FOUR_ARG_PATTERN.matcher(inputWithoutCommand) : ARG.matcher(inputWithoutCommand);

            switch (command) {
                case OUTPUT -> {
                    if (inputMatcher.matches()) {
                        arg1 = removeQuotes(inputMatcher.group());
                        return InputCommands.valid(command, arg1);
                    }
                }
                case REMOVE, APPEND, ADD_HEAD -> {
                    if (inputMatcher.matches()) {
                        arg1 = removeQuotes(inputMatcher.group(2) == null ? inputMatcher.group(3) : inputMatcher.group(2));
                        arg2 = removeQuotes(inputMatcher.group(5) == null ? inputMatcher.group(6) : inputMatcher.group(5));
                        return InputCommands.valid(command, arg1, arg2);
                    }

                }
                case ADD -> {
                    if (inputMatcher.matches()) {
                        arg1 = removeQuotes(inputMatcher.group(2) == null ? inputMatcher.group(3) : inputMatcher.group(2));
                        arg2 = removeQuotes(inputMatcher.group(5) == null ? inputMatcher.group(6) : inputMatcher.group(5));
                        arg3 = removeQuotes(inputMatcher.group(8));
                        if (isNumeric(arg3)) {
                            return InputCommands.valid(command, arg1, arg2, arg3);
                        }
                    }
                }
                case CONNECT, ROUTE, FASTEST_ROUTE -> {
                    if (inputMatcher.matches()) {
                        arg1 = removeQuotes(inputMatcher.group(3) == null ? inputMatcher.group(2) : inputMatcher.group(3));
                        arg2 = removeQuotes(inputMatcher.group(6) == null ? inputMatcher.group(5) : inputMatcher.group(6));
                        arg3 = removeQuotes(inputMatcher.group(10) == null ? inputMatcher.group(9) : inputMatcher.group(10));
                        arg4 = removeQuotes(inputMatcher.group(13) == null ? inputMatcher.group(12) : inputMatcher.group(13));
//                        System.out.println("commandText = " + commandText);
//                        System.out.println("arg1 = " + arg1);
//                        System.out.println("arg2 = " + arg2);
//                        System.out.println("arg3 = " + arg3);
//                        System.out.println("arg4 = " + arg4);
                        return InputCommands.valid(command, arg1, arg2, arg3, arg4);
                    }
                }
                case EXIT -> {
                    return InputCommands.valid(command);
                }
            }
            ;
        }

        return InputCommands.invalid();
    }

    private boolean isFourArgCommand(CLICommand command) {
        return command == CONNECT || command == ROUTE || command == FASTEST_ROUTE;
    }

    private boolean isNumeric(String input) {
        return input != null && input.matches("\\d+");
    }

    private String removeQuotes(String input) {
        return input == null ? "" : input.replaceAll(quotes, "");
    }

    public static class InputCommands {
        private final CLICommand command;
        private final String lineName;
        private final String stationName;
        private final String lineName2;
        private final String stationName2;
        private final int time;
        private final boolean isValid;

        private InputCommands(CLICommand command, String arg1, String arg2, String arg3, String arg4, int time, boolean isValid) {
            this.command = command;
            this.lineName = arg1;
            this.stationName = arg2;
            this.lineName2 = arg3;
            this.stationName2 = arg4;
            this.time = time;
            this.isValid = isValid;
        }

        public static InputCommands valid(CLICommand command, String arg1, String arg2, String arg3) {
            return new InputCommands(command, arg1, arg2, null, null, Integer.parseInt(arg3), true);
        }

        public CLICommand getCommand() {
            return command;
        }

        public String getLineName() {
            return lineName;
        }

        public String getStationName() {
            return stationName;
        }

        public String getLineName2() {
            return lineName2;
        }

        public String getStationName2() {
            return stationName2;
        }

        public int getTime() {
            return time;
        }

        public boolean isValid() {
            return isValid;
        }

        public static InputCommands valid(CLICommand command) {
            return new InputCommands(command, null, null, null, null, 0, true);
        }

        public static InputCommands valid(CLICommand command, String arg1) {
            return new InputCommands(command, arg1, null, null, null, 0, true);
        }

        public static InputCommands valid(CLICommand command, String arg1, String arg2) {
            return new InputCommands(command, arg1, arg2, null, null, 0, true);
        }

        public static InputCommands valid(CLICommand command, String arg1, String arg2, String arg3, String arg4) {
            return new InputCommands(command, arg1, arg2, arg3, arg4, 0, true);
        }

        public static InputCommands invalid() {
            return new InputCommands(null, null, null, null, null, 0, false);
        }
    }
}