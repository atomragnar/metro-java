package com.hornbrinck.controller;

import java.util.Scanner;

public class InputHandler extends InputValidator {

    private final Scanner scanner = new Scanner(System.in);

    public InputHandler() {
        super();

    }
    protected InputCommands readLine() {
        return validate(scanner.nextLine());
    }

}
