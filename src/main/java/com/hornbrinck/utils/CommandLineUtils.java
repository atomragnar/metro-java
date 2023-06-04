package com.hornbrinck.utils;


import java.io.File;
import java.util.Optional;
import java.util.function.Function;

public class CommandLineUtils {

    public static Function<String[], Optional<File>> handleCommandLineArgs = args -> {
        File file = new File(args[0]);
        return file.exists() ? Optional.of(file) : Optional.empty();
    };


}