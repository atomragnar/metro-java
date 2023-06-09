package com.hornbrinck.utils;



import java.io.File;
import java.net.URISyntaxException;
import java.util.Optional;


public class CommandLineUtils {

    public Optional<File> handleCommandLineArgs(String[] args) {
        File file;
        if (args == null || args.length == 0) {
            try {
                file = new File(this.getClass().getResource("/london.json").toURI());
                return Optional.of(file);
            } catch (URISyntaxException e) {
                return Optional.empty();
            }
        } else {
            file = new File(args[0]);
            return file.exists() ? Optional.of(file) : Optional.empty();
        }
    }


}